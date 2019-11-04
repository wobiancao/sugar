package com.sugar.sugarlibrary.image.photoview

import android.content.Context
import android.graphics.PointF
import android.graphics.drawable.Animatable
import android.net.Uri
import android.text.TextUtils
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.imagepipeline.core.ImagePipeline
import com.facebook.imagepipeline.image.ImageInfo
import com.sugar.sugarlibrary.R
import com.sugar.sugarlibrary.image.LargePhotoView
import com.sugar.sugarlibrary.image.Phoenix
import com.sugar.sugarlibrary.image.listener.OnProgressListener
import com.sugar.sugarlibrary.image.loading.LoadingProgressBarView
import com.sugar.sugarlibrary.image.photoview.entity.PhotoInfo
import com.sugar.sugarlibrary.image.photoview.photodraweeview.OnPhotoTapListener
import com.sugar.sugarlibrary.image.photoview.photodraweeview.OnViewTapListener
import com.sugar.sugarlibrary.image.photoview.photodraweeview.PhotoDraweeView
import com.sugar.sugarlibrary.image.utils.DensityUtil
import com.sugar.sugarlibrary.util.image.ImageLoadUtil

import java.util.ArrayList
import java.util.Locale

/**
 * Created by android_ls on 16/9/13.
 */
class PictureBrowseAdapter(context: Context, private val mItems: ArrayList<PhotoInfo>?,
                           private var mOnPhotoTapListener: OnPhotoTapListener?, private var mOnLongClickListener: View.OnLongClickListener?) : PagerAdapter() {

    private val widthPixels: Int
    private val heightPixels: Int
    private val mMaxValue: Long = 10000

    init {
        widthPixels = DensityUtil.getDisplayWidth(context)
        heightPixels = DensityUtil.getDisplayHeight(context)
    }

    override fun getCount(): Int {
        return mItems?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val photoInfo = mItems!![position]

        if (isBigImage(photoInfo)) {
            val contentView = createLargePhotoView(container.context, photoInfo)
            container.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return contentView
        }

        val contentView = createPhotoDraweeView(container.context, photoInfo)
        container.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return contentView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (mItems != null && mItems.size > 0 && position < mItems.size) {
            val photoInfo = mItems[position]
            if (isBigImage(photoInfo)) {
                val imageView = container.findViewById<View>(R.id.photo_view) as SubsamplingScaleImageView
                imageView?.recycle()
            } else {
                evictFromMemoryCache(photoInfo)
            }
        }
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    private fun createPhotoDraweeView(context: Context, photoInfo: PhotoInfo): View {
        val contentView = View.inflate(context, R.layout.picture_browse_item, null)

        val progressBarView = contentView.findViewById<View>(R.id.progress_view) as LoadingProgressBarView
        progressBarView.progress = 0
        progressBarView.text = String.format(Locale.getDefault(), "%d%%", 0)
        progressBarView.visibility = View.VISIBLE

        val photoDraweeView = contentView.findViewById<View>(R.id.photo_drawee_view) as PhotoDraweeView
        val controller = Fresco.newDraweeControllerBuilder()

        var uri = Uri.parse(photoInfo.originalUrl)
        if (!UriUtil.isNetworkUri(uri)) {
            uri = Uri.Builder()
                    .scheme(UriUtil.LOCAL_FILE_SCHEME)
                    .path(photoInfo.originalUrl)
                    .build()
        }
        controller.setUri(uri)

        val hierarchy = photoDraweeView.hierarchy
        hierarchy.setProgressBarImage(object : ProgressBarDrawable() {
            override fun onLevelChange(level: Int): Boolean {
                val progress = (level.toDouble() / mMaxValue * 100).toInt()
                progressBarView.progress = progress
                progressBarView.text = String.format(Locale.getDefault(), "%d%%", progress)
                if (progress == 100) {
                    progressBarView.visibility = View.GONE
                }
                return super.onLevelChange(progress)
            }
        })

        controller.oldController = photoDraweeView.controller
        controller.controllerListener = object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                super.onFinalImageSet(id, imageInfo, animatable)
                if (imageInfo == null) {
                    return
                }
                photoDraweeView.update(imageInfo.width, imageInfo.height)
            }

            override fun onIntermediateImageSet(id: String?, imageInfo: ImageInfo?) {
                super.onIntermediateImageSet(id, imageInfo)
            }
        }
        photoDraweeView.controller = controller.build()

        photoDraweeView.onPhotoTapListener = OnPhotoTapListener { view, x, y ->
            if (mOnPhotoTapListener != null) {
                mOnPhotoTapListener!!.onPhotoTap(view, x, y)
            }
        }

        if (mOnLongClickListener != null) {
            photoDraweeView.setOnLongClickListener(mOnLongClickListener)
        }

        photoDraweeView.onViewTapListener = OnViewTapListener { view, x, y ->
            if (mOnPhotoTapListener != null) {
                mOnPhotoTapListener!!.onPhotoTap(view, x, y)
            }
        }
        return contentView
    }

    private fun createLargePhotoView(context: Context, photoInfo: PhotoInfo): View {
        val contentView = View.inflate(context, R.layout.big_image_item, null)
        val progressBarView = contentView.findViewById<View>(R.id.progress_view) as LoadingProgressBarView
        progressBarView.progress = 0
        progressBarView.text = String.format(Locale.getDefault(), "%d%%", 0)
        progressBarView.visibility = View.VISIBLE

        val imageView = contentView.findViewById<View>(R.id.photo_view) as LargePhotoView
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM)
        imageView.minScale = 1.0f
        imageView.maxScale = 2.0f
        imageView.setOnProgressListener { progress ->
            progressBarView.progress = progress
            progressBarView.text = String.format(Locale.getDefault(), "%d%%", progress)
            if (progress == 100) {
                progressBarView.visibility = View.GONE
            }
        }

        val gestureDetector = GestureDetector(context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                        if (imageView.isReady) {
                            val sCoord = imageView.viewToSourceCoord(e.x, e.y)

                            if (mOnPhotoTapListener != null) {
                                mOnPhotoTapListener!!.onPhotoTap(imageView, sCoord!!.x.toInt().toFloat(), sCoord.y.toInt().toFloat())
                            }
                        } else {
                        }
                        return false
                    }

                    override fun onLongPress(e: MotionEvent) {
                        if (imageView.isReady) {
                            val sCoord = imageView.viewToSourceCoord(e.x, e.y)

                            if (mOnLongClickListener != null) {
                                mOnLongClickListener!!.onLongClick(imageView)
                            }
                        } else {
                        }
                    }

                    override fun onDoubleTap(e: MotionEvent): Boolean {
                        if (imageView.isReady) {
                            val sCoord = imageView.viewToSourceCoord(e.x, e.y)
                        } else {
                        }
                        return false
                    }
                })

        imageView.setOnTouchListener { view, motionEvent -> gestureDetector.onTouchEvent(motionEvent) }

        // 加载网络的
        val fileCacheDir = context.cacheDir.absolutePath
        Phoenix.with(imageView)
                .setDiskCacheDir(fileCacheDir)
                .load(photoInfo.originalUrl)

        return contentView
    }

    private fun evictFromMemoryCache(photoInfo: PhotoInfo) {
        if (!TextUtils.isEmpty(photoInfo.originalUrl)) {
            val imagePipeline = Fresco.getImagePipeline()
            val uri = Uri.parse(photoInfo.originalUrl)
            if (imagePipeline.isInBitmapMemoryCache(uri)) {
                imagePipeline.evictFromMemoryCache(uri)
            }
        }
    }

    private fun isBigImage(photoInfo: PhotoInfo): Boolean {
        return photoInfo.width > 2 * widthPixels || photoInfo.height > 2 * heightPixels
    }

    fun recycler() {
        mOnPhotoTapListener = null
        mOnLongClickListener = null
    }

}
package com.sugar.sugarlibrary.image.photoview

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.sugar.sugarlibrary.R
import com.sugar.sugarlibrary.base.BaseActivity
import com.sugar.sugarlibrary.image.photoview.anim.TransitionCompat
import com.sugar.sugarlibrary.image.photoview.entity.PhotoInfo
import com.sugar.sugarlibrary.image.photoview.photodraweeview.OnPhotoTapListener
import com.sugar.sugarlibrary.image.utils.DragCloseHelper
import com.sugar.sugarlibrary.image.utils.PhotoConstant
import java.util.*

/**
 * Created by android_ls on 16/9/13.
 */
class PictureBrowseActivity : BaseActivity(), ViewPager.OnPageChangeListener, OnPhotoTapListener, View.OnLongClickListener {

    /**
     * 获取当前PhotoInfo在集合中Position
     *
     * @return
     */
    private var currentPosition: Int = 0
    private var mPhotoCount: Int = 0

    private lateinit var rlPhotoContainer: RelativeLayout
    private var rlPhotoBottom: RelativeLayout? = null
    private lateinit var flContainer: FrameLayout
    private var tvPhotoIndex: TextView? = null
    private var mViewPager: MViewPager? = null

    private var mAdapter: PictureBrowseAdapter? = null
    var items: ArrayList<PhotoInfo>? = null


    private var mTransitionCompat: TransitionCompat? = null
    private var mDragCloseHelper: DragCloseHelper? = null

    private var mPhotoOnlyOne: Boolean = false
    private var isAnimation: Boolean = false
    private var isDragClose: Boolean = false
    private var mLongClick: Boolean = false

    private val layoutResId: Int
        get() = R.layout.lib_picture_browse_fragment

    val currentPhotoInfo: PhotoInfo?
        get() = if (items != null && items!!.size > 0) {
            items!![currentPosition]
        } else null

    override fun getContentView(): Int {
        return layoutResId
    }

    override fun init(savedInstanceState: Bundle?) {
        val data = intent
        items = data.getParcelableArrayListExtra(PhotoConstant.PHOTO_LIST_KEY)
        if (items == null || items!!.size == 0) {
            onBackPressed()
            return
        }

        currentPosition = data.getIntExtra(PhotoConstant.PHOTO_CURRENT_POSITION_KEY, 0)
        isAnimation = data.getBooleanExtra(PhotoConstant.PHOTO_ANIMATION_KEY, false)
        mPhotoOnlyOne = data.getBooleanExtra(PhotoConstant.PHOTO_ONLY_ONE_KEY, false)
        mLongClick = data.getBooleanExtra(PhotoConstant.PHOTO_LONG_CLICK_KEY, true)
        isDragClose = data.getBooleanExtra(PhotoConstant.PHOTO_DRAG_CLOSE, false)


        setupViews()

        if (isAnimation) {
            mTransitionCompat = TransitionCompat(this@PictureBrowseActivity)
            mTransitionCompat!!.setCurrentPosition(currentPosition)
            mTransitionCompat!!.startTransition()
        }

        if (isDragClose) {
            setDragClose()
        }
    }

    override fun loadData() {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (mPhotoOnlyOne) {
            return
        }

        currentPosition = position
        setPhotoIndex()

        if (mTransitionCompat != null && isAnimation) {
            mTransitionCompat!!.setCurrentPosition(currentPosition)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onBackPressed() {
        if (mTransitionCompat != null && isAnimation) {
            mTransitionCompat!!.finishAfterTransition()
        } else {
            finish()
            overridePendingTransition(0, 0)
        }
    }

    override fun onPhotoTap(view: View, x: Float, y: Float) {
        onBackPressed()
    }

    protected fun setupViews() {
        rlPhotoContainer = findViewById(R.id.rl_photo_container)
        flContainer = findViewById(R.id.fl_container)
        rlPhotoBottom = findViewById(R.id.rl_photo_bottom)
        tvPhotoIndex = findViewById<View>(R.id.tv_photo_count) as TextView
        mViewPager = findViewById<View>(R.id.vp_picture_browse) as MViewPager
        mViewPager!!.clearOnPageChangeListeners()
        mViewPager!!.addOnPageChangeListener(this)

        mAdapter = PictureBrowseAdapter(this, items, this, if (mLongClick) this else null)
        mViewPager!!.adapter = mAdapter

        mPhotoCount = items!!.size
        setupBottomViews()
        mViewPager!!.currentItem = currentPosition
    }

    protected fun setupBottomViews() {
        if (mPhotoOnlyOne) {
            if (rlPhotoBottom != null) {
                rlPhotoBottom!!.visibility = View.GONE
                tvPhotoIndex!!.visibility = View.GONE
            }
        } else {
            setPhotoIndex()
        }
    }

    protected fun setPhotoIndex() {
        if (tvPhotoIndex != null) {
            tvPhotoIndex!!.text = String.format(Locale.getDefault(), "%d/%d", currentPosition + 1, mPhotoCount)
        }
    }

    override fun onLongClick(view: View): Boolean {
        return false
    }

    fun getItem(position: Int): PhotoInfo? {
        return if (items != null && items!!.size > 0) {
            items!![position]
        } else null
    }

    /**
     * 向下拖动关闭
     */
    protected fun setDragClose() {
        mDragCloseHelper = DragCloseHelper(this)
        mDragCloseHelper!!.setShareElementMode(true)
        mDragCloseHelper!!.setMinScale(0.2f)
        mDragCloseHelper!!.setMaxExitY(200)
        mDragCloseHelper!!.setDragCloseViews(rlPhotoContainer, flContainer)
        mDragCloseHelper!!.setDragCloseListener(object : DragCloseHelper.OnDragCloseListener {
            override fun intercept(): Boolean {
                return false
            }

            override fun onDragStart() {
                if (rlPhotoBottom != null) {
                    rlPhotoBottom!!.visibility = View.GONE
                }
            }

            override fun onDragging(percent: Float) {

            }

            override fun onDragCancel() {
                if (rlPhotoBottom != null) {
                    rlPhotoBottom!!.visibility = View.VISIBLE
                }
            }

            override fun onDragClose(isShareElementMode: Boolean) {
                if (isShareElementMode) {
                    onBackPressed()
                }
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return if (mDragCloseHelper != null && mDragCloseHelper!!.handleEvent(ev)) {
            true
        } else {
            super.dispatchTouchEvent(ev)
        }
    }

    override fun onDestroy() {
        if (items != null) {
            items = null
        }

        if (mAdapter != null) {
            mAdapter!!.recycler()
            mAdapter = null
        }

        if (mViewPager != null) {
            mViewPager!!.removeOnPageChangeListener(this)
            mViewPager!!.adapter = null
            mViewPager = null
        }

        super.onDestroy()
    }

}

package com.sugar.demo.ui

import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ConvertUtils
import com.gyf.immersionbar.ImmersionBar
import com.sugar.demo.R
import com.sugar.demo.router.RouterPageContant
import com.sugar.sugarlibrary.base.BaseActivity
import com.sugar.sugarlibrary.image.photoview.PhotoX
import com.sugar.sugarlibrary.image.photoview.entity.PhotoInfo
import com.sugar.sugarlibrary.router.ARouterUtils
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = RouterPageContant.MAIN_PAGE)
class MainActivity : BaseActivity() {
     private val mLayoutId = R.id.main_frame_layout
     private var mContainerFragment: ContainerFragment? = null


     private val showAnimation = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 1f, TranslateAnimation.RELATIVE_TO_SELF, 0f)
     private val hideAnimation = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 1f)


    private val images = arrayOf("https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg", "https://ww1.sinaimg.cn/large/0065oQSqgy1ftwcw4f4a5j30sg10j1g9.jpg", "https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg", "https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg", "https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg", "https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg", "https://ws1.sinaimg.cn/large/0065oQSqly1fv5n6daacqj30sg10f1dw.jpg", "https://ws1.sinaimg.cn/large/0065oQSqly1fvexaq313uj30qo0wldr4.jpg", "https://ws1.sinaimg.cn/large/0065oQSqly1fw0vdlg6xcj30j60mzdk7.jpg", "https://ws1.sinaimg.cn/large/0065oQSqly1fw8wzdua6rj30sg0yc7gp.jpg", "https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg", "https://ws1.sinaimg.cn/large/0065oQSqgy1fwyf0wr8hhj30ie0nhq6p.jpg", "https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg", "https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg", "https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fitcjyruajj20u011h412.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fjfae1hjslj20u00tyq4x.jpg", "https://ww1.sinaimg.cn/large/610dc034ly1fjaxhky81vj20u00u0ta1.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fivohbbwlqj20u011idmx.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fj78mpyvubj20u011idjg.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fiz4ar9pq8j20u010xtbk.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fis7dvesn6j20u00u0jt4.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fir1jbpod5j20ip0newh3.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fik2q1k3noj20u00u07wh.jpg", "https://ws1.sinaimg.cn/large/610dc034ly1fiednrydq8j20u011itfz.jpg", "https://a-ssl.duitang.com/uploads/item/201701/19/20170119220617_WwTh8.jpeg", "https://b-ssl.duitang.com/uploads/item/201705/14/20170514095630_L5aEZ.jpeg", "https://b-ssl.duitang.com/uploads/item/201505/23/20150523091525_n2xkL.jpeg", "https://a-ssl.duitang.com/uploads/item/201503/28/20150328132442_nRNSQ.jpeg", "https://b-ssl.duitang.com/uploads/item/201608/28/20160828190814_fucBX.jpeg", "https://b-ssl.duitang.com/uploads/item/201610/08/20161008040554_rAwev.jpeg", "https://b-ssl.duitang.com/uploads/item/201701/02/20170102174129_vJ2Ns.thumb.700_0.jpeg", "https://b-ssl.duitang.com/uploads/item/201504/04/20150404H0402_d8S3E.thumb.700_0.jpeg", "https://ww3.sinaimg.cn/large/610dc034jw1f9nuk0nvrdj20u011haci.jpg", "https://ws1.sinaimg.cn/large/0065oQSqgy1fze94uew3jj30qo10cdka.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f9mp3xhjdhj20u00u0ta9.jpg", "https://ww2.sinaimg.cn/large/610dc034gw1f9lmfwy2nij20u00u076w.jpg", "https://ww2.sinaimg.cn/large/610dc034gw1f9kjnm8uo1j20u00u0q5q.jpg", "https://ww2.sinaimg.cn/large/610dc034jw1f9j7nvnwjdj20u00k0jsl.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f9frojtu31j20u00u0go9.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f9em0sj3yvj20u00w4acj.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f9dh2ohx2vj20u011hn0r.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f9cayjaa96j20u011hqbs.jpg", "https://ww2.sinaimg.cn/large/610dc034jw1f9b46kpoeoj20ku0kuwhc.jpg", "https://ww2.sinaimg.cn/large/610dc034jw1f978bh1cerj20u00u0767.jpg", "https://ww4.sinaimg.cn/large/610dc034gw1f96kp6faayj20u00jywg9.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f95hzq3p4rj20u011htbm.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f9469eoojtj20u011hdjy.jpg", "https://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f8zlenaornj20u011idhv.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f8xz7ip2u5j20u011h78h.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f8xff48zauj20u00x5jws.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f8w2tr9bgzj20ku0mjdi8.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f8uxlbptw7j20ku0q1did.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f8rgvvm5htj20u00u0q8s.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f8qd9a4fx7j20u011hq78.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f8kmud15q1j20u011hdjg.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f8bc5c5n4nj20u00irgn8.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f8a5uj64mpj20u00u0tca.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f88ylsqjvqj20u011hn5i.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f87z2n2taej20u011h11h.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f867mvc6qjj20u00u0wh7.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f837uocox8j20f00mggoo.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f820oxtdzzj20hs0hsdhl.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f80uxtwgxrj20u011hdhq.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f7z9uxopq0j20u011hju5.jpg", "https://ww2.sinaimg.cn/large/610dc034jw1f7y296c5taj20u00u0tay.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f7wws4xk5nj20u011hwhb.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f7sszr81ewj20u011hgog.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f7rmrmrscrj20u011hgp1.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f7qgschtz8j20hs0hsac7.jpg", "https://ww2.sinaimg.cn/large/610dc034jw1f7mixvc7emj20ku0dv74p.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f7lughzrjmj20u00k9jti.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f7kpy9czh0j20u00vn0us.jpg", "https://ww2.sinaimg.cn/large/610dc034jw1f7jkj4hl41j20u00mhq68.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f7hu7d460oj20u00u075u.jpg", "https://ww1.sinaimg.cn/large/610dc034jw1f7ef7i5m1zj20u011hdjm.jpg", "https://ww3.sinaimg.cn/large/610dc034jw1f7d97id9mbj20u00u0q4g.jpg", "https://ww4.sinaimg.cn/large/610dc034jw1f7cmpd95iaj20u011hjtt.jpg", "https://ww2.sinaimg.cn/large/610dc034gw1f7bm1unn17j20u00u00wm.jpg", "https://f10.baidu.com/it/u=1733621832,446747761&fm=76", "https://ww3.sinaimg.cn/large/610dc034jw1f8mssipb9sj20u011hgqk.jpg")

    override fun getContentView(): Int {
        return R.layout.activity_main
    }


    override fun initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .init()
    }

    override fun init(savedInstanceState: Bundle?) {

        showAnimation.duration = 500
        hideAnimation.duration = 500
        mContainerFragment = ContainerFragment()
        supportFragmentManager.beginTransaction()
                .add(mLayoutId, mContainerFragment!!)
                .commit()
    }


    override fun loadData() {

    }


    fun changeBottom() {
        if (main_frame_layout.visibility == View.VISIBLE) {
            main_frame_layout.visibility = View.INVISIBLE
            main_frame_layout.startAnimation(hideAnimation)
        } else {
            main_frame_layout.visibility = View.VISIBLE
            main_frame_layout.startAnimation(showAnimation)
        }
    }

    fun UiMiddle(view: View) {
        mContainerFragment?.setHasMiddle(true)
        changeBottom()
    }

    fun UiTest(view: View) {
        mContainerFragment?.setHasMiddle(false)
        changeBottom()
    }


    fun changeView(progress: Float) {
        if (progress >= 0 && progress < 0.5f) {
            main_center_layout.scaleX = 1f
            main_center_layout.scaleY = 1f
            main_center_layout.translationY = 0f
            animation_view.scaleX = 1f
            animation_view.scaleY = 1f
            animation_view.translationY = 0f
        } else {
            main_center_layout.scaleX = 1 - progress
            main_center_layout.scaleY = 1 - progress
            main_center_layout.translationY = (-ConvertUtils.dp2px((1 - progress) * 100)).toFloat()
            animation_view.scaleX = progress
            animation_view.scaleY = progress
            animation_view.translationY = ConvertUtils.dp2px((1 - progress) * 100).toFloat()
        }

    }


    fun Gank(view: View) {
        ARouterUtils.navigation(RouterPageContant.GANK_HOME)
    }

    fun Wan(view: View) {
        ARouterUtils.navigation(RouterPageContant.WAN_HOME)
    }

    fun KtGank(view: View) {
        ARouterUtils.navigation(RouterPageContant.KT_CONTAINER)
    }

    fun Anko(view: View) {
        ARouterUtils.navigation(RouterPageContant.KT_ANKO)
    }

    fun ImageLoade(view: View) {
        ARouterUtils.navigation(RouterPageContant.KT_IMAGE)
    }

    fun ImageBrowser(view: View) {
        val photos = ArrayList<PhotoInfo>()
        for (url in images){
            val photoInfo = PhotoInfo()
            photoInfo.originalUrl = url
            photos.add(photoInfo)
        }
                PhotoX.with(mContext)
                        .setPhotoList(photos)
                        .setCurrentPosition(0)
                        .enabledAnimation(false)
                        .enabledDragClose(true)
                        .start()
    }
}

package com.sugar.demo.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ConvertUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.sugar.demo.R;
import com.sugar.demo.router.RouterPageContant;
import com.sugar.sugarlibrary.base.BaseActivity;
import com.sugar.sugarlibrary.router.ARouterUtils;

@Route(path = RouterPageContant.MAIN_PAGE)
public class MainActivity extends BaseActivity {
    final int mLayoutId = R.id.main_frame_layout;
    FrameLayout mBoxLayout;
    ContainerFragment mContainerFragment;
    LinearLayout mCenterLayout;
    LottieAnimationView mAnimationView;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mBoxLayout = findViewById(R.id.main_frame_layout);
        mCenterLayout = findViewById(R.id.main_center_layout);
        mAnimationView = findViewById(R.id.animation_view);
        showAnimation.setDuration(500);
        hideAnimation.setDuration(500);
        mContainerFragment = new ContainerFragment();
        getSupportFragmentManager().beginTransaction()
                .add(mLayoutId, mContainerFragment)
                .commit();
    }


    @Override
    public void loadData() {

    }




    public void changeBottom() {
        if (mBoxLayout.getVisibility() == View.VISIBLE) {
            mBoxLayout.setVisibility(View.INVISIBLE);
            mBoxLayout.startAnimation(hideAnimation);
        } else {
            mBoxLayout.setVisibility(View.VISIBLE);
            mBoxLayout.startAnimation(showAnimation);
        }
    }


    final TranslateAnimation showAnimation = new TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
            TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
    final TranslateAnimation hideAnimation = new TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 1);

    public void UiMiddle(View view) {
        mContainerFragment.setHasMiddle(true);
        changeBottom();
    }

    public void UiTest(View view) {
        mContainerFragment.setHasMiddle(false);
        changeBottom();
    }


    public void changeView(float progress) {
        if (progress >= 0 && progress < 0.5f) {
            mCenterLayout.setScaleX(1f);
            mCenterLayout.setScaleY(1f);
            mCenterLayout.setTranslationY(0);
            mAnimationView.setScaleX(1f);
            mAnimationView.setScaleY(1f);
            mAnimationView.setTranslationY(0);
        } else {
            mCenterLayout.setScaleX(1 - progress);
            mCenterLayout.setScaleY(1 - progress);
            mCenterLayout.setTranslationY(-ConvertUtils.dp2px((1 - progress) * 100));
            mAnimationView.setScaleX(progress);
            mAnimationView.setScaleY(progress);
            mAnimationView.setTranslationY(ConvertUtils.dp2px((1 - progress) * 100));
        }

    }



    public void Gank(View view) {
        ARouterUtils.navigation(RouterPageContant.GANK_HOME);
    }

    public void Wan(View view) {
        ARouterUtils.navigation(RouterPageContant.WAN_HOME);
    }

    public void KtGank(View view) {
        ARouterUtils.navigation(RouterPageContant.KT_GANK);
    }

    public void KtFragmentGank(View view) {
        ARouterUtils.navigation(RouterPageContant.KT_CONTAINER);
    }
}

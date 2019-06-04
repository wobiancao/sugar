package com.sugar.demo.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ConvertUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.sugar.demo.R;
import com.sugar.demo.router.RouterPageContant;
import com.sugar.sugarlibrary.base.BaseActivity;
import com.sugar.sugarlibrary.router.ARouterUtils;

@Route(path = RouterPageContant.MAIN_PAGE)
public class MainActivity extends BaseActivity {
    public static final int TYPE_BOTTOM = 0;
    public static final int TYPE_MIDDLE = 1;
    public static final int TYPE_TOP = 2;

    final int mLayoutId = R.id.main_frame_layout;
    FrameLayout mBoxLayout;
    ContainerFragment mContainerFragment;
    ImageView mLogoView;
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
        mLogoView = findViewById(R.id.main_logo);
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

    public void Gank(View view) {
        ARouterUtils.navigation(RouterPageContant.GANK_HOME);
    }

    public void Wan(View view) {
        ARouterUtils.navigation(RouterPageContant.WAN_HOME);
    }



    public void changeBottom(){
        if (mBoxLayout.getVisibility() == View.VISIBLE){
            mBoxLayout.setVisibility(View.INVISIBLE);
            mBoxLayout.startAnimation(hideAnimation);
        }else {
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

    private int mType = TYPE_BOTTOM;
    public void changeView(int type){
        if (mType != type){
            switch (type){
                case TYPE_BOTTOM:
                    mLogoView.setScaleX(1f);
                    mLogoView.setScaleY(1f);
                    mLogoView.setTranslationY(0);
                    break;
                case TYPE_MIDDLE:
                    mLogoView.setScaleX(0.7f);
                    mLogoView.setScaleY(0.7f);
                    mLogoView.setTranslationY(-ConvertUtils.dp2px(30));
                    break;
                case TYPE_TOP:
                    mLogoView.setScaleX(0.5f);
                    mLogoView.setScaleY(0.5f);
                    mLogoView.setTranslationY(-ConvertUtils.dp2px(60));
                    break;
            }
            mType = type;
        }




    }
}

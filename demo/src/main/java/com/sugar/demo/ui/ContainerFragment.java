package com.sugar.demo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.motion.widget.MotionLayout;

import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.sugar.demo.R;
import com.sugar.demo.ui.widget.SignleTouchMotionLayout;
import com.sugar.sugarlibrary.base.BaseFragment;

/**
 * @author wobiancao
 * @date 2019-05-31
 * desc :
 */
public class ContainerFragment extends BaseFragment {
    SignleTouchMotionLayout mTouchMotionLayout;
    ImageView mCloseView;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mTouchMotionLayout = (SignleTouchMotionLayout) findViewById(R.id.bottom_motionlayout);
        mCloseView = (ImageView) findViewById(R.id.content_close);
        mCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).changeBottom();
            }
        });
        mTouchMotionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                float progress = Math.abs(v);
                if (progress > SignleTouchMotionLayout.PROGRESS_BOTTOM){
                    mCloseView.setVisibility(View.INVISIBLE);
                }else {
                    mCloseView.setVisibility(View.VISIBLE);
                }
                ((MainActivity)getActivity()).changeView(progress);
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });


    }

    public void setHasMiddle(boolean hasMiddle) {
        mTouchMotionLayout.setHasMiddle(hasMiddle);
    }

}

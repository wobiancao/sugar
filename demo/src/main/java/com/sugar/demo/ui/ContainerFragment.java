package com.sugar.demo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.motion.widget.MotionLayout;

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
                .statusBarColor(R.color.colorPrimary)
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
        mTouchMotionLayout.setProgressDelegate(new SignleTouchMotionLayout.ProgressDelegate() {
            @Override
            public void changeProgress(float progress) {
                if (progress > SignleTouchMotionLayout.PROGRESS_BOTTOM){
                    mCloseView.setVisibility(View.INVISIBLE);
                }else {
                    mCloseView.setVisibility(View.VISIBLE);
                }
                if (progress >= SignleTouchMotionLayout.PROGRESS_TOP){
                    ((MainActivity)getActivity()).changeView(MainActivity.TYPE_TOP);
                }
                if (progress < SignleTouchMotionLayout.PROGRESS_TOP && progress >= SignleTouchMotionLayout.PROGRESS_MIDDLE){
                    ((MainActivity)getActivity()).changeView(MainActivity.TYPE_MIDDLE);
                }
                if (progress < SignleTouchMotionLayout.PROGRESS_MIDDLE){
                    ((MainActivity)getActivity()).changeView(MainActivity.TYPE_BOTTOM);
                }
            }
        });

    }

    public void setHasMiddle(boolean hasMiddle) {
        mTouchMotionLayout.setHasMiddle(hasMiddle);
    }

}

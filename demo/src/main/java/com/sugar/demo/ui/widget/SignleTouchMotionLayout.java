package com.sugar.demo.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.motion.widget.MotionLayout;

import com.sugar.demo.R;

/**
 * @author wobiancao
 * @date 2019-06-03
 * desc :
 */
public class SignleTouchMotionLayout extends MotionLayout {

    public final static float PROGRESS_START = 0f;
    public final static float PROGRESS_TOP = 0.8f;
    public final static float PROGRESS_BOTTOM = 0.1f;
    public final static float PROGRESS_MIDDLE = 0.55f;
    public final static float PROGRESS_END = 1.0f;
    private float startY = 0;
    private boolean mTouchStared = false;
    /**
     * 有没有中间间断
     */
    private boolean hasMiddle;
    private ProgressDelegate mProgressDelegate;

    public SignleTouchMotionLayout(Context context) {
        super(context);
    }


    public SignleTouchMotionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public void setProgressDelegate(ProgressDelegate progressDelegate) {
        mProgressDelegate = progressDelegate;
    }

    public void setHasMiddle(boolean hasMiddle) {
        this.hasMiddle = hasMiddle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float progress = getProgress();
        View viewGroup  = findViewById(R.id.content);
        Rect mRect = new Rect();
        if (!mTouchStared) {
            viewGroup.getHitRect(mRect);
            mTouchStared = mRect.contains((int)event.getX(), (int)event.getY());
        }

        float endY;
        if (hasMiddle){
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_CANCEL:
                    mTouchStared = false;
                    break;
                case MotionEvent.ACTION_DOWN:
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    endY = event.getY();
                    //手势向下
                    if ((endY - startY) > 0){
                        if (progress >= PROGRESS_TOP){
                            mTouchStared = false;
                            setProgress(PROGRESS_END);
                        }
                        if (progress < PROGRESS_TOP && progress >= PROGRESS_MIDDLE){
                            setProgress(PROGRESS_MIDDLE);
                        }
                        if (progress < PROGRESS_MIDDLE){
                            setProgress(PROGRESS_START);
                        }
                        //手势向上
                    } else {
                        if (progress <= PROGRESS_BOTTOM){
                            setProgress(PROGRESS_START);
                        }
                        if (progress > PROGRESS_BOTTOM && progress <= PROGRESS_MIDDLE){
                            setProgress(PROGRESS_MIDDLE);
                        }
                        if (progress > PROGRESS_MIDDLE){
                            mTouchStared = false;
                            setProgress(PROGRESS_END);
                        }
                    }
                    return  mTouchStared;
            }
        }else {
            if (event.getActionMasked() == MotionEvent.ACTION_CANCEL || event.getActionMasked() == MotionEvent.ACTION_UP){
                mTouchStared = false;
                return super.onTouchEvent(event);
            }
        }
        return mTouchStared && super.onTouchEvent(event);

    }

    public interface ProgressDelegate{
        void changeProgress(float progress);
    }

    @Override
    public void setProgress(float pos) {
        super.setProgress(pos);
        if (mProgressDelegate != null){
            mProgressDelegate.changeProgress(pos);
        }
    }
}

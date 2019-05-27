package com.sugar.demo.ui.widget;

import android.view.Gravity;

import com.hjq.toast.IToastStyle;

/**
 * @author wobiancao
 * @date 2019-05-24
 * desc :
 */
public class ToastStyle implements IToastStyle {
    @Override
    public int getGravity() {
        return Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 240;
    }

    @Override
    public int getZ() {
        return 30;
    }

    @Override
    public int getCornerRadius() {
        return 10;
    }

    @Override
    public int getBackgroundColor() {
        return 0XFFEB9D3E;
    }

    @Override
    public int getTextColor() {
        return 0XFFFFFFFF;
    }

    @Override
    public float getTextSize() {
        return 16;
    }

    @Override
    public int getMaxLines() {
        return 4;
    }

    @Override
    public int getPaddingLeft() {
        return  16;
    }

    @Override
    public int getPaddingTop() {
        return  10;
    }

    @Override
    public int getPaddingRight() {
        return  getPaddingLeft();
    }

    @Override
    public int getPaddingBottom() {
        return  getPaddingTop();
    }
}

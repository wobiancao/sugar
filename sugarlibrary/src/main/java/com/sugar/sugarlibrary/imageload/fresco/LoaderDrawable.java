package com.sugar.sugarlibrary.imageload.fresco;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import com.sugar.sugarlibrary.imageload.framework.OnLoaderProgressCallback;


public class LoaderDrawable extends Drawable {
    private OnLoaderProgressCallback onLoaderProgressCallback;
    public LoaderDrawable(OnLoaderProgressCallback onLoaderProgressCallback){
        this.onLoaderProgressCallback = onLoaderProgressCallback;
    }
    @Override
    public void draw(@androidx.annotation.NonNull Canvas canvas) {

    }

    @Override
    protected boolean onLevelChange(int level) {
        if (onLoaderProgressCallback != null) {
            onLoaderProgressCallback.onProgress(level);
        }
        return true;
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@androidx.annotation.Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}

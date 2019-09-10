package com.sugar.sugarlibrary.imageload.framework;

import android.view.View;


public class ImageLoader {
    public static ImageLoaderOptions.Builder createImageOptions(@androidx.annotation.NonNull View v, @androidx.annotation.NonNull String url){
        return new ImageLoaderOptions.Builder(v, url);
    }
    public static ImageLoaderOptions.Builder createImageOptions(@androidx.annotation.NonNull View v, @androidx.annotation.NonNull int resource){
        return new ImageLoaderOptions.Builder(v, resource);
    }
}

package com.sugar.sugarlibrary.base.config;

import androidx.annotation.DrawableRes;

import com.sugar.sugarlibrary.imageload.framework.ImageLoaderConfig;


/**
 * @author wobiancao
 * @date 2019-09-06
 * desc :
 */
public class AppImageLoadSetting {
    private int placeholder;
    private int errorholder;
    private boolean isCrossFade;
    private ImageLoaderConfig mImageLoaderConfig;
    public AppImageLoadSetting(Builder builder){
        this.placeholder = builder.placeholder;
        this.errorholder = builder.errorholder;
        this.isCrossFade = builder.isCrossFade;
        this.mImageLoaderConfig = builder.mImageLoaderConfig;
    }

    public ImageLoaderConfig getImageLoaderConfig() {
        return mImageLoaderConfig;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorholder() {
        return errorholder;
    }



    public boolean isCrossFade() {
        return isCrossFade;
    }

    public static AppImageLoadSetting.Builder builder() {
        return new AppImageLoadSetting.Builder();
    }
    public static class Builder{
        int placeholder;
        int errorholder;
        boolean isCrossFade;
        ImageLoaderConfig mImageLoaderConfig;
        public Builder placeholder(@DrawableRes int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorholder(@DrawableRes int errorholder) {
            this.errorholder = errorholder;
            return this;
        }


        public Builder isCrossFade(boolean crossFade) {
            isCrossFade = crossFade;
            return this;
        }

        public Builder imageLoaderConfig(ImageLoaderConfig imageLoaderConfig) {
            mImageLoaderConfig = imageLoaderConfig;
            return this;
        }

        public AppImageLoadSetting build(){
            if (null == mImageLoaderConfig){
                throw new IllegalStateException("ImageLoaderConfig is required");
            }
            return new AppImageLoadSetting(this);
        }
    }
}

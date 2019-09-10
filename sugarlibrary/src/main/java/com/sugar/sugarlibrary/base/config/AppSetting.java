/*
 * Copyright 2019 wobiancao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sugar.sugarlibrary.base.config;

import android.app.Application;

import com.billy.android.loading.Gloading;
import com.sugar.sugarlibrary.rx.errorhandler.ResponseErrorListener;
import com.sugar.sugarlibrary.widget.BaseLoadingDialog;


/**
 * @author wobiancao
 * @date 2019/5/16
 * desc :
 */
public class AppSetting {
    private ResponseErrorListener responseErrorListener;
    private BaseLoadingDialog mBaseLoadingDialog;
    private Gloading.Adapter adapter;
    private AppHttpSetting mAppHttpSetting;
    private Application application;
    private AppImageLoadSetting mAppImageLoadSetting;
    private boolean isDebug;
    public AppSetting(Builder builder) {
        this.responseErrorListener = builder.responseErrorListener;
        this.mBaseLoadingDialog = builder.mBaseLoadingDialog;
        this.adapter = builder.adapter;
        this.mAppHttpSetting = builder.mAppHttpSetting;
        this.application = builder.application;
        this.isDebug = builder.isDebug;
        this.mAppImageLoadSetting = builder.mAppImageLoadSetting;
    }

    public Application getApplication() {
        return application;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public AppImageLoadSetting getAppImageLoadSetting() {
        return mAppImageLoadSetting;
    }

    /**
     * 网络配置
     * @return
     */
    public AppHttpSetting getHttpSetting() {
        return mAppHttpSetting;
    }

    public ResponseErrorListener getResponseErrorListener() {
        return responseErrorListener;
    }

    public BaseLoadingDialog getBaseLoadingDialog() {
        return mBaseLoadingDialog;
    }


    public Gloading.Adapter getAdapter() {
        return adapter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        Application application;
        BaseLoadingDialog mBaseLoadingDialog;
        ResponseErrorListener responseErrorListener;
        Gloading.Adapter adapter;
        AppHttpSetting mAppHttpSetting;
        boolean isDebug;
        AppImageLoadSetting mAppImageLoadSetting;
        private Builder() {
        }
        public Builder with(Application application) {
            this.application = application;
            return this;
        }
        /**
         * 设置网络参数等
         * @param mAppHttpSetting
         * @return
         */
        public Builder setHttpSetting(AppHttpSetting mAppHttpSetting){
            this.mAppHttpSetting = mAppHttpSetting;
            return this;
        }


        /**
         * 统一loading dialog
         * @param mBaseLoadingDialog
         * @return
         */
        public Builder setLoadingDialog(BaseLoadingDialog mBaseLoadingDialog) {
            this.mBaseLoadingDialog = mBaseLoadingDialog;
            return this;
        }

        /**
         * 统一异常处理
         * @param responseErrorListener
         * @return
         */
        public Builder setResponseErrorListener(ResponseErrorListener responseErrorListener) {
            this.responseErrorListener = responseErrorListener;
            return this;
        }

        /**
         * 统一页面状态适配器
         * @param adapter
         * @return
         */
        public Builder setGloadingAdapter(Gloading.Adapter adapter){
            this.adapter = adapter;
            return this;
        }

        /**
         * 全局控制是否是debug
         * @return
         */
        public Builder isDebug(boolean isDebug){
            this.isDebug = isDebug;
            return this;
        }

        /**
         * 图片加载配置
         * @return
         */
        public Builder setImageLoadSetting(AppImageLoadSetting mAppImageLoadSetting){
            this.mAppImageLoadSetting = mAppImageLoadSetting;
            return this;
        }

        public AppSetting build() {
            if (null == application){
                throw new IllegalStateException("application is required");
            }
            if (null == responseErrorListener){
                throw new IllegalStateException("responseErrorListener is required");
            }
            if (null == mAppHttpSetting){
                throw new IllegalStateException("AppHttpSetting is required");
            }

            return new AppSetting(this);
        }

    }
}

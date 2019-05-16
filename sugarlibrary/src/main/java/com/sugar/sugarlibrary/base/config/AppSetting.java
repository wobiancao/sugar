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
import com.sugar.sugarlibrary.widget.BaseLoadingDialog;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author wobiancao
 * @date 2019/5/16
 * desc :
 */
public class AppSetting {
    private RxErrorHandler rxErrorHandler;
    private BaseLoadingDialog mBaseLoadingDialog;
    private Gloading.Adapter adapter;
    public AppSetting(Builder builder) {
        this.rxErrorHandler = builder.rxErrorHandler;
        this.mBaseLoadingDialog = builder.mBaseLoadingDialog;
        this.adapter = builder.adapter;
    }


    public RxErrorHandler getRxErrorHandler() {
        return rxErrorHandler;
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
        RxErrorHandler rxErrorHandler;
        Gloading.Adapter adapter;
        private Builder() {
        }
        public Builder with(Application application) {
            this.application = application;
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
         * @param rxErrorHandler
         * @return
         */
        public Builder setErrorHandler(RxErrorHandler rxErrorHandler) {
            this.rxErrorHandler = rxErrorHandler;
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

        public AppSetting build() {
            if (null == application){
                throw new IllegalStateException("application is required");
            }
            if (null == rxErrorHandler){
                throw new IllegalStateException("rxErrorHandler is required");
            }
            return new AppSetting(this);
        }

    }
}

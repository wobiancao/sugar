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
import com.hjq.toast.IToastStyle;
import com.sugar.sugarlibrary.http.interceptor.SugarCustomHeaderInterceptor;
import com.sugar.sugarlibrary.http.interceptor.SugarExceptionInterceptor;
import com.sugar.sugarlibrary.http.interceptor.SugarHeaderInterceptor;
import com.sugar.sugarlibrary.imageload.framework.ImageLoaderConfig;
import com.sugar.sugarlibrary.imageload.framework.LoaderEnum;
import com.sugar.sugarlibrary.imageload.fresco.FrescoImageLoader;
import com.sugar.sugarlibrary.widget.BaseLoadingDialog;
import com.sugar.sugarlibrary.widget.gloading.SugarGloadAdapter;

import java.util.TreeMap;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author wobiancao
 * @date 2019/5/20
 * desc :app统一配置模块 需要的模块自行添加即可
 */
public abstract class SugarConfigure implements AppConfigureDelegate {

    protected  Application mApplication;


    public SugarConfigure(Application application) {
        mApplication = application;
    }

    @Override
    public SugarExceptionInterceptor getExceptionInterceptor() {
        return new SugarExceptionInterceptor() {
            @Override
            public boolean isResponseExpired(Response response, String bodyString) {
                return false;
            }

            @Override
            public Response responseExpired(Chain chain, String bodyString) {
                return null;
            }
        };
    }

    @Override
    public SugarCustomHeaderInterceptor getCustomHeader() {
        return new SugarCustomHeaderInterceptor() {
            @Override
            public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
                return dynamicMap;
            }
        };
    }

    @Override
    public SugarHeaderInterceptor getHeader() {
        return new SugarHeaderInterceptor() {
            @Override
            protected Headers getHeaders(Chain chain) {
                Headers.Builder headrBuilder = new Headers.Builder();
                return headrBuilder.build();
            }
        };
    }



    @Override
    public BaseLoadingDialog getLoadingDialog() {
        return new BaseLoadingDialog();
    }

    @Override
    public Gloading.Adapter getGloadingAdapter() {
        return new SugarGloadAdapter();
    }


    @Override
    public AppSetting.Builder getAppSettingBuilder() {
        return AppSetting
                .builder()
                .with(mApplication)
                //网络配置 具体配置看下面
                .setHttpSetting(getHttpSetting())
                //rxjava异常统一抓取 https://github.com/JessYanCoding/RxErrorHandler
                .setResponseErrorListener(getErrorResponse())
                //全局不同状态页解耦
                .setGloadingAdapter(getGloadingAdapter())
                //统一弹窗loading
                .setLoadingDialog(getLoadingDialog())
                //1.0.1.7新增
                .setImageLoadSetting(getImageLoadSetting())
                //是否打印log
                .isDebug(false)
                ;
    }

    @Override
    public AppSetting getAppSetting() {
        return getAppSettingBuilder()
                .build();
    }

    @Override
    public AppHttpSetting getHttpSetting() {
        return AppHttpSetting
                .builder()
                .with(mApplication)
                .build();
    }

    @Override
    public Application getApplication() {
        return mApplication;
    }

    @Override
    public IToastStyle getToastStyle() {
        return null;
    }

    @Override
    public AppImageLoadSetting getImageLoadSetting() {
        return AppImageLoadSetting
                .builder()
                .imageLoaderConfig(getImageLoaderConfig())
                .isCrossFade(true)
                .build();
    }

    @Override
    public ImageLoaderConfig getImageLoaderConfig() {
        return new ImageLoaderConfig
                .Builder(LoaderEnum.FRESCO, new FrescoImageLoader())
                .maxMemory(40*1024*1024L)
                .build();
    }
}

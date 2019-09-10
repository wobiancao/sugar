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
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.IToastStyle;
import com.sugar.sugarlibrary.http.interceptor.SugarCustomHeaderInterceptor;
import com.sugar.sugarlibrary.http.interceptor.SugarExceptionInterceptor;
import com.sugar.sugarlibrary.http.interceptor.SugarHeaderInterceptor;
import com.sugar.sugarlibrary.imageload.framework.ImageLoaderConfig;
import com.sugar.sugarlibrary.rx.errorhandler.ResponseErrorListener;
import com.sugar.sugarlibrary.widget.BaseLoadingDialog;


/**
 * @author wobiancao
 * @date 2019/5/20
 * desc :
 */
public interface AppConfigureDelegate {

    /**
     * token异常等拦截
     */
    SugarExceptionInterceptor getExceptionInterceptor();

    /**
     * 公共请求头明文参数
     * @return
     */
    SugarCustomHeaderInterceptor getCustomHeader();

    /**
     * 标准request的header
     * @return
     */
    SugarHeaderInterceptor getHeader();

    /**
     * 统一异常获取
     */
    ResponseErrorListener getErrorResponse();

    /**
     * 统一的loading dialog
     */
    BaseLoadingDialog getLoadingDialog();

    /**
     * 统一状态切换
     * @return
     */
    Gloading.Adapter getGloadingAdapter();


    /**
     * 网络配置
     */
    AppHttpSetting getHttpSetting();

    /**
     * app配置
     */
    AppSetting getAppSetting();


    /**
     * 获取全局的application
     * @return
     */
    Application getApplication();

    /**
     * toast样式 https://github.com/getActivity/ToastUtils
     */
    IToastStyle getToastStyle();

    /**
     * 统一配置状态栏，直接提出来用
     */
    ImmersionBar getImmersionBar(ImmersionBar bar);

    //===============1.0.1.7新增===============
    /**
     * app配置Buidler
     */
    AppSetting.Builder getAppSettingBuilder();

    /**
     * imageloader 配置
     */
    AppImageLoadSetting getImageLoadSetting();

    /**
     * ImageLoaderConfig 默认加载为Fresco
     */
    ImageLoaderConfig getImageLoaderConfig();

}

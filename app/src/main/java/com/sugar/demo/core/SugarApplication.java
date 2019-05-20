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
package com.sugar.demo.core;

import com.billy.android.loading.Gloading;
import com.sugar.demo.api.GirlsApi;
import com.sugar.demo.custom.SugarLoadingDialog;
import com.sugar.sugarlibrary.base.LibApplication;
import com.sugar.sugarlibrary.base.config.AppHttpSetting;
import com.sugar.sugarlibrary.base.config.AppSetting;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author wobiancao
 * @date 2019/5/17
 * desc :
 */
public class SugarApplication extends LibApplication {

    @Override
    protected void init() {

    }

    @Override
    protected AppSetting getSetting() {

        return AppSetting
                .builder()
                .with(this)
                //网络配置 具体配置看下面
                .setHttpSetting(getHttpSetting())
                //rxjava异常统一抓取 https://github.com/JessYanCoding/RxErrorHandler
                .setErrorHandler(getRxErrorHandler())
                //全局不同状态页解耦
                .setGloadingAdapter(getAdapter())
                //统一弹窗loading
                .setLoadingDialog(new SugarLoadingDialog())
                .build();
    }

    @Override
    protected AppHttpSetting getHttpSetting() {
        return AppHttpSetting
                .builder()
                .with()
                //设置初始的host可以动态修改baseUrl 具体看https://github.com/JessYanCoding/RetrofitUrlManager
                .setBaseUrl(GirlsApi.HOST)
                //是否打印网络请求日志 默认否
                .setHttpLog(true)
                //百度Stetho即可 网络监测等 默认否
                .setHttpMoniter(true)
                //设置缓存时间 默认60s
                .setCacheMaxTime(65)
                //设置连接超时 默认20s
                .connectTimeout(20)
                //设置读取超时 默认20s
                .readTimeout(20)
                //设置写入超时 默认20s
                .writeTimeout(20)
                //请求header
                .addHeaderInterceptor(new HeaderInterceptor())
                //添加请求明文公共参数
                .addCustomHeaderInterceptor(new CustonHeaderInterceptorSugar())
                //token过期等请求成功处理 一般不需要处理
                .addExceptionInterceptor(new TokenInterceptor())
                //其它拦截头
//                .setOtherInterceptor()
                .build();
    }

    @Override
    protected RxErrorHandler getRxErrorHandler() {
        return null;
    }

    @Override
    protected Gloading.Adapter getAdapter() {
        return null;
    }

}

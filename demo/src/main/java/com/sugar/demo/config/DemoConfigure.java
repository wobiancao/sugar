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
package com.sugar.demo.config;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.IToastStyle;
import com.hjq.toast.ToastUtils;
import com.sugar.demo.R;
import com.sugar.demo.http.api.Gank;
import com.sugar.demo.http.api.Wan;
import com.sugar.demo.ui.widget.ToastStyle;
import com.sugar.sugarlibrary.base.config.AppHttpSetting;
import com.sugar.sugarlibrary.base.config.SugarConfigure;
import com.sugar.sugarlibrary.rx.errorhandler.ResponseErrorListener;

import retrofit2.converter.fastjson.FastJsonConverterFactory;


/**
 * @author wobiancao
 * @date 2019/5/20
 * desc :
 */
public class DemoConfigure extends SugarConfigure {


    public DemoConfigure(Application application) {
        super(application);
    }

    @Override
    public ResponseErrorListener getErrorResponse() {
        return new ResponseErrorListener() {
            @Override
            public void handleResponseError(Context context, Throwable t) {
                LogUtils.i("捕获异常---" + t.getMessage());
                ToastUtils.show("发生异常---" + t.getMessage());
            }
        };
    }


    @Override
    public AppHttpSetting getHttpSetting() {
        return AppHttpSetting
                .builder()
                .with(mApplication)
                //设置初始的baseUrl host
                .setBaseUrl(Gank.HOST)
                //动态修改baseUrl 具体看https://github.com/JessYanCoding/RetrofitUrlManager
                .putDomain(Wan.DOMAN, Wan.HOST)
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
                .addHeaderInterceptor(getHeader())
                //添加请求明文公共参数
                .addCustomHeaderInterceptor(getCustomHeader())
                //token过期等请求成功处理 一般不需要处理
//                .addExceptionInterceptor(getExceptionInterceptor())
                //其它拦截
//                .addInterceptor(xx)
//                .addNetworkInterceptor(xxx)
//                配置自己的缓存
//                .cache(xx)
                //甚至另外写一套自己的okhttp builder 也行
//                .setOkHttpBuilder(xxx)
                //自定义ConverterFactory
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }

    @Override
    public IToastStyle getToastStyle() {
        return new ToastStyle();
    }

    @Override
    public ImmersionBar getImmersionBar(ImmersionBar bar) {
        return bar
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.colorPrimary)
                ;
    }
}

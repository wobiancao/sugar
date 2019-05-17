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
package com.sugar.sugarlibrary.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.base.config.AppHttpSetting;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wobiancao
 * @date 2019/5/17
 * desc :
 */
public class AppHttpClient {
    private static <T> T initService(Class<T> clazz) {
        return getRetrofitInstance().create(clazz);
    }
    /**单例retrofit*/
    private static Retrofit retrofitInstance;
    private static Retrofit getRetrofitInstance(){
        if (retrofitInstance == null) {
            synchronized (AppHttpClient.class) {
                if (retrofitInstance == null) {
                    AppHttpSetting httpSetting = AppConfig.INSTANCE.getAppSetting().getHttpSetting();
                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(httpSetting.getBaseUrl())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClientInstance())
                            .build();
                }
            }
        }
        return retrofitInstance;
    }

    /**单例OkHttpClient*/
    private static OkHttpClient okHttpClientInstance;
    private static OkHttpClient getOkHttpClientInstance(){
        if (okHttpClientInstance == null) {
            synchronized (AppHttpClient.class) {
                if (okHttpClientInstance == null) {
                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
                    AppHttpSetting httpSetting = AppConfig.INSTANCE.getAppSetting().getHttpSetting();
                    if (httpSetting.isHttpLog()) {
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(httpLoggingInterceptor);
                    }
                    builder.connectTimeout(httpSetting.getConnectTimeout(), TimeUnit.MILLISECONDS);
                    builder.readTimeout(httpSetting.getReadTimeout(), TimeUnit.MILLISECONDS);
                    builder.writeTimeout(httpSetting.getWriteTimeout(), TimeUnit.MILLISECONDS);
                    builder.addInterceptor(httpSetting.getHeaderInterceptor());
                    builder.hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
                    final File httpCacheDirectory = new File(AppConfig.INSTANCE.getApplication().getCacheDir(), "sugarHttpCache");
                    //设置缓存 10M
                    Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
                    builder.cache(cache);
                    //是否可以监听网络使用facebook库
                    if (httpSetting.isHttpMonitor()){
                        builder.addNetworkInterceptor(new StethoInterceptor());
                    }
                    //如果header拦截器不为空 会加入headers
                    if (httpSetting.getHeaderInterceptor() != null){
                        builder.addInterceptor(httpSetting.getHeaderInterceptor());
                    }
                    //如果异常处理header拦截器不为空 token过期等等 请求成功却异常的处理 一般此异常会忽略 不排除有需要的时候
                    if (httpSetting.getExceptionInterceptor() != null){
                        builder.addInterceptor(httpSetting.getExceptionInterceptor());
                    }
                    //公共头参数 所有请求都需要添加的明文参数
                    if (httpSetting.getCustomHeaderInterceptor() != null){
                        builder.addInterceptor(httpSetting.getCustomHeaderInterceptor());
                    }
                    //其它还需要处理的拦截
                    if (httpSetting.getOtherInterceptor() != null){
                        builder.addInterceptor(httpSetting.getOtherInterceptor());
                    }
                    okHttpClientInstance = builder.build();
                }
            }
        }
        return okHttpClientInstance;
    }
}

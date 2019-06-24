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

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sugar.sugarlibrary.http.interceptor.SugarCustomHeaderInterceptor;
import com.sugar.sugarlibrary.http.interceptor.SugarExceptionInterceptor;
import com.sugar.sugarlibrary.http.interceptor.SugarHeaderInterceptor;
import com.sugar.sugarlibrary.http.interceptor.SugarNormalChaceInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wobiancao
 * @date 2019/5/17
 * desc :网络配置
 */
public class AppHttpSetting {
    private int cacheTime;
    private boolean httpMonitor;
    private boolean httpLog;
    private String baseUrl;
    private OkHttpClient.Builder okHttpBuilder;
    private Converter.Factory httpConverter;
    public AppHttpSetting(Builder builder) {
        this.httpMonitor = builder.httpMonitor;
        this.httpLog = builder.httpLog;
        this.baseUrl = builder.baseUrl;
        this.cacheTime = builder.cacheTime;
        this.okHttpBuilder = builder.okHttpBuilder;
        this.httpConverter = builder.httpConverter;
    }

    public OkHttpClient.Builder getOkHttpBuilder() {
        return okHttpBuilder;
    }


    public int getCacheTime() {
        return cacheTime;
    }

    public boolean isHttpLog() {
        return httpLog;
    }

    public boolean isHttpMonitor() {
        return httpMonitor;
    }


    public Converter.Factory getHttpConverter() {
        if (httpConverter == null){
            return GsonConverterFactory.create();
        }else {
            return httpConverter;
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public static AppHttpSetting.Builder builder() {
        return new AppHttpSetting.Builder();
    }

    public static final class Builder {
        String baseUrl;
        int cacheTime = 60;
        boolean httpMonitor;
        OkHttpClient.Builder okHttpBuilder;
        boolean httpLog;
        Converter.Factory httpConverter;
        private Builder() {
            okHttpBuilder = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder());
            okHttpBuilder.connectTimeout(20, TimeUnit.SECONDS);
            okHttpBuilder.readTimeout(20, TimeUnit.SECONDS);
            okHttpBuilder.writeTimeout(20, TimeUnit.SECONDS);

            okHttpBuilder.addNetworkInterceptor(new SugarNormalChaceInterceptor());

            okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }

        public AppHttpSetting.Builder with(Application application) {
            final File httpCacheDirectory = new File(application.getCacheDir(), "sugarHttpCache");
            //设置缓存 10M
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            okHttpBuilder.cache(cache);
            return this;
        }

        /**
         * 自定义okHttpBuilder
         * @param okHttpBuilder
         */
        public void setOkHttpBuilder(OkHttpClient.Builder okHttpBuilder) {
            this.okHttpBuilder = okHttpBuilder;
        }


        /**
         * 设置缓存
         * @param cache
         * @return
         */
        public AppHttpSetting.Builder cache(Cache cache){
            this.okHttpBuilder.cache(cache);
            return this;
        }
        /**
         * add需要添加的拦截
         * @param networkInterceptor
         * @return
         */
        public AppHttpSetting.Builder addNetworkInterceptor(Interceptor networkInterceptor){
            this.okHttpBuilder.addNetworkInterceptor(networkInterceptor);
            return this;
        }
        /**
         * add需要添加的拦截
         * @param interceptor
         * @return
         */
        public AppHttpSetting.Builder addInterceptor(Interceptor interceptor){
            this.okHttpBuilder.addInterceptor(interceptor);
            return this;
        }
        /**
         * 设置公共参数header拦截
         * @param customHeaderInterceptor
         * @return
         */
        public AppHttpSetting.Builder addCustomHeaderInterceptor(SugarCustomHeaderInterceptor customHeaderInterceptor){
            this.okHttpBuilder.addInterceptor(customHeaderInterceptor);
            return this;
        }
        /**
         * 设置异常header拦截
         * @param exceptionInterceptor
         * @return
         */
        public AppHttpSetting.Builder addExceptionInterceptor(SugarExceptionInterceptor exceptionInterceptor){
            this.okHttpBuilder.addInterceptor(exceptionInterceptor);
            return this;
        }
        /**
         * 设置header拦截
         * @param headerInterceptor
         * @return
         */
        public AppHttpSetting.Builder addHeaderInterceptor(SugarHeaderInterceptor headerInterceptor){
            this.okHttpBuilder.addInterceptor(headerInterceptor);
            return this;
        }
        /**
         * 是否需要打印网络日志 默认否
         * @param httpLog
         * @return
         */
        public AppHttpSetting.Builder setHttpLog(boolean httpLog){
            this.httpLog = httpLog;
            if (httpLog) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpBuilder.addInterceptor(httpLoggingInterceptor);
            }
            return this;
        }

        /**
         * 是否需要监听网络 默认否
         * @param httpMonitor
         * @return
         */
        public AppHttpSetting.Builder setHttpMoniter(boolean httpMonitor){
            //是否可以监听网络使用facebook库
            if (httpMonitor){
                okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
            }
            return this;
        }
        /**
         * 缓存超时时长默认60s
         * @param cacheTime
         * @return
         */
        public AppHttpSetting.Builder setCacheMaxTime(int cacheTime){
            this.cacheTime = cacheTime;
            return this;
        }
        /**
         * 写入超时 默认20s
         * @param writeTimeout
         * @return
         */
        public AppHttpSetting.Builder writeTimeout(int writeTimeout){
            okHttpBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
            return this;
        }
        /**
         * 连接超时 默认20s
         * @param connectTimeout
         * @return
         */
        public AppHttpSetting.Builder connectTimeout(int connectTimeout){
            okHttpBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
            return this;
        }

        /**
         * 读取超时 默认20s
         * @param readTimeout
         * @return
         */
        public AppHttpSetting.Builder readTimeout(int readTimeout){
            okHttpBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
            return this;
        }

        /**
         * 主的baseUrl
         * @param baseUrl
         * @return
         */
        public AppHttpSetting.Builder setBaseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 添加url host域名
         * @param domanName
         * @param domanHost
         * @return
         */
        public AppHttpSetting.Builder putDomain(String domanName, String domanHost){
            RetrofitUrlManager.getInstance().putDomain(domanName, domanHost);
            return this;
        }

        public AppHttpSetting build() {
            return new AppHttpSetting(this);
        }

        public AppHttpSetting.Builder addConverterFactory(Converter.Factory factory) {
            this.httpConverter = factory;
            return this;
        }

    }
}

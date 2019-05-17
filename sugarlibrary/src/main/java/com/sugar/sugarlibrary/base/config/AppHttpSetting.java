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

import com.sugar.sugarlibrary.http.interceptor.AppExceptionInterceptor;
import com.sugar.sugarlibrary.http.interceptor.CustomHeaderInterceptor;
import com.sugar.sugarlibrary.http.interceptor.HttpHeaderInterceptor;
import com.sugar.sugarlibrary.http.interceptor.OtherInterceptor;

/**
 * @author wobiancao
 * @date 2019/5/17
 * desc :网络配置
 */
public class AppHttpSetting {
    private long connectTimeout;
    private long readTimeout;
    private long writeTimeout;
    private int cacheTime;
    private boolean httpMonitor;
    private boolean httpLog;
    private String baseUrl;
    private HttpHeaderInterceptor headerInterceptor;
    private AppExceptionInterceptor exceptionInterceptor;
    private CustomHeaderInterceptor customHeaderInterceptor;
    private OtherInterceptor otherInterceptor;
    public AppHttpSetting(Builder builder) {
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.httpMonitor = builder.httpMonitor;
        this.httpLog = builder.httpLog;
        this.headerInterceptor = builder.headerInterceptor;
        this.baseUrl = builder.baseUrl;
        this.writeTimeout = builder.writeTimeout;
        this.cacheTime = builder.cacheTime;
        this.exceptionInterceptor = builder.exceptionInterceptor;
        this.customHeaderInterceptor = builder.customHeaderInterceptor;
        this.otherInterceptor = builder.otherInterceptor;
    }

    public int getCacheTime() {
        return cacheTime;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public boolean isHttpMonitor() {
        return httpMonitor;
    }

    public boolean isHttpLog() {
        return httpLog;
    }

    public HttpHeaderInterceptor getHeaderInterceptor() {
        return headerInterceptor;
    }


    public OtherInterceptor getOtherInterceptor() {
        return otherInterceptor;
    }

    public AppExceptionInterceptor getExceptionInterceptor() {
        return exceptionInterceptor;
    }

    public CustomHeaderInterceptor getCustomHeaderInterceptor() {
        return customHeaderInterceptor;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public static final class Builder {
        Application application;
        String baseUrl;
        long connectTimeout = 20 * 1000;
        long readTimeout = 20 * 1000;
        long writeTimeout = 20 * 1000;
        int cacheTime = 60;
        boolean httpMonitor;
        boolean httpLog;
        HttpHeaderInterceptor headerInterceptor;
        AppExceptionInterceptor exceptionInterceptor;
        CustomHeaderInterceptor customHeaderInterceptor;
        OtherInterceptor otherInterceptor;
        private Builder() {
        }

        public AppHttpSetting.Builder with(Application application) {
            this.application = application;
            return this;
        }
        /**
         * 设置其它需要添加的拦截
         * @param otherInterceptor
         * @return
         */
        public AppHttpSetting.Builder setOtherInterceptor(OtherInterceptor otherInterceptor){
            this.otherInterceptor = otherInterceptor;
            return this;
        }
        /**
         * 设置公共参数header拦截
         * @param customHeaderInterceptor
         * @return
         */
        public AppHttpSetting.Builder setCustomHeaderInterceptor(CustomHeaderInterceptor customHeaderInterceptor){
            this.customHeaderInterceptor = customHeaderInterceptor;
            return this;
        }
        /**
         * 设置异常header拦截
         * @param exceptionInterceptor
         * @return
         */
        public AppHttpSetting.Builder setExceptionInterceptor(AppExceptionInterceptor exceptionInterceptor){
            this.exceptionInterceptor = exceptionInterceptor;
            return this;
        }
        /**
         * 设置header拦截
         * @param headerInterceptor
         * @return
         */
        public AppHttpSetting.Builder setHeaderInterceptor(HttpHeaderInterceptor headerInterceptor){
            this.headerInterceptor = headerInterceptor;
            return this;
        }
        /**
         * 是否需要打印网络日志 默认否
         * @param httpLog
         * @return
         */
        public AppHttpSetting.Builder setHttpLog(boolean httpLog){
            this.httpLog = httpLog;
            return this;
        }

        /**
         * 是否需要监听网络 默认否
         * @param httpMonitor
         * @return
         */
        public AppHttpSetting.Builder setHttpMoniter(boolean httpMonitor){
            this.httpMonitor = httpMonitor;
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
        public AppHttpSetting.Builder setWriteTimeout(long writeTimeout){
            this.writeTimeout = writeTimeout;
            return this;
        }
        /**
         * 连接超时 默认20s
         * @param connectTimeout
         * @return
         */
        public AppHttpSetting.Builder setConnectTimeout(long connectTimeout){
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 读取超时 默认20s
         * @param readTimeout
         * @return
         */
        public AppHttpSetting.Builder setReadTimeout(long readTimeout){
            this.readTimeout = readTimeout;
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
    }
}

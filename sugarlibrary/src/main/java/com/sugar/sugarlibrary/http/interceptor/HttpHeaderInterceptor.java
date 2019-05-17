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
package com.sugar.sugarlibrary.http.interceptor;

import com.sugar.sugarlibrary.base.config.AppConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wobiancao
 * @date 2019/5/17
 * desc :获取公共请求Headers
 */
public abstract class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        CacheControl cacheControl = new CacheControl.Builder()
                .noTransform()
                //缓存有效期时长
                .maxAge(AppConfig.INSTANCE.getAppSetting().getHttpSetting().getCacheTime(), TimeUnit.SECONDS)
                .build();
        Request request = chain.request()
                .newBuilder()
                .headers(getHeaders(chain))
                .cacheControl(cacheControl)
                .build();
        return chain.proceed(request);
    }

    protected abstract Headers getHeaders(Chain chain);
}

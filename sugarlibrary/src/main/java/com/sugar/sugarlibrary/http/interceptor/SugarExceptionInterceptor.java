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
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;

import static com.sugar.sugarlibrary.util.HttpUtil.UTF8;

/**
 * @author wobiancao
 * @date 2019/5/17
 * desc :请求成功但是有异常状态code 比如token过期 签名sign错误 等等
 * 用的时候较少 一般服务器直接返回401异常处理就行了
 */
public abstract class SugarExceptionInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        // Buffer the entire body.
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String bodyString = buffer.clone().readString(charset);
        if (AppConfig.INSTANCE.getAppSetting().getHttpSetting().isHttpLog()){
            Timber.i("网络拦截器:" + bodyString + " host:" + request.url().toString());
        }

        boolean isText = isText(contentType);
        if (!isText) {
            return response;
        }
        //判断响应是否过期（无效）
        if (isResponseExpired(response, bodyString)) {
            return responseExpired(chain, bodyString);
        }
        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType == null){
            return false;
        }
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理响应是否有效
     */
    public abstract boolean isResponseExpired(Response response, String bodyString);

    /**
     * 无效响应处理
     */
    public abstract Response responseExpired(Chain chain, String bodyString);
}

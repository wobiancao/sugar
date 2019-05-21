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
import com.sugar.sugarlibrary.util.HttpUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

import static com.sugar.sugarlibrary.util.HttpUtil.UTF8;

/**
 * @author wobiancao
 * @date 2019/5/17
 * desc :公共头 所有请求都需要添加的明文参数
 */
public abstract class SugarCustomHeaderInterceptor implements Interceptor {
    private HttpUrl httpUrl;
    final static String GET = "GET";
    final static String POST = "POST";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals(GET)) {
            this.httpUrl = HttpUrl.parse(parseUrl(request.url().url().toString()));
            request = addGetParamsSign(request);
        } else if (request.method().equals(POST)) {
            this.httpUrl = request.url();
            request = addPostParamsSign(request);
        }
        return chain.proceed(request);
    }

    private String parseUrl(String url) {
        // 如果URL不是空字符串
        if (!"".equals(url) && url.contains("?")) {
            url = url.substring(0, url.indexOf('?'));
        }
        return url;
    }

    /**
     * get 添加签名和公共动态参数
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private Request addGetParamsSign(Request request) throws UnsupportedEncodingException {
        HttpUrl httpUrl = request.url();
        HttpUrl.Builder newBuilder = httpUrl.newBuilder();
        //获取原有的参数
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        TreeMap<String, String> oldparams = new TreeMap<>();
        for (int i = 0; i < nameList.size(); i++) {
            String value = httpUrl.queryParameterValues(nameList.get(i)) != null && httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "";
            oldparams.put(nameList.get(i), value);
        }
        String nameKeys = Collections.singletonList(nameList).toString();
        //拼装新的参数
        TreeMap<String, String> newParams = dynamic(oldparams);
        HttpUtil.checkNotNull(newParams, "newParams==null");
        for (Map.Entry<String, String> entry : newParams.entrySet()) {
            String urlValue = URLEncoder.encode(entry.getValue(), UTF8.name());
            //避免重复添加
            if (!nameKeys.contains(entry.getKey())) {
                newBuilder.addQueryParameter(entry.getKey(), urlValue);
            }
        }
        httpUrl = newBuilder.build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    /**
     * 添加签名和公共动态参数
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private Request addPostParamsSign(Request request) throws UnsupportedEncodingException {
        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //原有的参数
            TreeMap<String, String> oldparams = new TreeMap<>();
            for (int i = 0; i < formBody.size(); i++) {
                oldparams.put(formBody.encodedName(i), formBody.encodedValue(i));
            }

            //拼装新的参数
            TreeMap<String, String> newParams = dynamic(oldparams);
            HttpUtil.checkNotNull(newParams, "newParams==null");
            for (Map.Entry<String, String> entry : newParams.entrySet()) {
                String value = URLDecoder.decode(entry.getValue(), UTF8.name());
                bodyBuilder.addEncoded(entry.getKey(), value);
            }
            String url = HttpUtil.createUrlFromParams(httpUrl.url().toString(), newParams);
            if (AppConfig.INSTANCE.getAppSetting().getHttpSetting().isHttpLog()){
                Timber.i(url);
            }
            formBody = bodyBuilder.build();
            request = request.newBuilder().post(formBody).build();
        } else if (request.body() instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) request.body();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            List<MultipartBody.Part> oldparts = multipartBody.parts();
            //拼装新的参数
            List<MultipartBody.Part> newparts = new ArrayList<>();
            newparts.addAll(oldparts);
            TreeMap<String, String> oldparams = new TreeMap<>();
            TreeMap<String, String> newParams = dynamic(oldparams);
            for (Map.Entry<String, String> stringStringEntry : newParams.entrySet()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(stringStringEntry.getKey(), stringStringEntry.getValue());
                newparts.add(part);
            }
            for (MultipartBody.Part part : newparts) {
                bodyBuilder.addPart(part);
            }
            multipartBody = bodyBuilder.build();
            request = request.newBuilder().post(multipartBody).build();
        }
        return request;
    }

    /**
     * 动态处理参数
     *
     * @param dynamicMap
     * @return 返回新的参数集合
     */
    public abstract TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap);
}

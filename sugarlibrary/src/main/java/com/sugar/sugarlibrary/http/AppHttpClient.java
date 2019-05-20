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

import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.base.config.AppHttpSetting;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
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
                    AppHttpSetting httpSetting = AppConfig.INSTANCE.getAppSetting().getHttpSetting();
                    OkHttpClient.Builder builder = httpSetting.getOkHttpBuilder();
                    okHttpClientInstance = RetrofitUrlManager.getInstance().with(builder).build();
                }
            }
        }
        return okHttpClientInstance;
    }
}

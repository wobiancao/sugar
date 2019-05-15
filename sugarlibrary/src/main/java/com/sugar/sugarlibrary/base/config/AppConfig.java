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

import com.alibaba.android.arouter.launcher.ARouter;
import com.billy.android.loading.Gloading;
import com.blankj.utilcode.util.Utils;
import com.sugar.sugarlibrary.BuildConfig;
import com.sugar.sugarlibrary.core.ActivityLifecycleCallback;
import com.sugar.sugarlibrary.util.CrashReportingTree;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import timber.log.Timber;

/**
 * author by wobiancao
 * on 2019/4/11
 * desc :
 */
public enum AppConfig {
    //对象
    INSTANCE;
    private RxErrorHandler rxErrorHandler;
    public void initConfig(Application application, ResponseErrorListener errorListener, Gloading.Adapter adapter){
        Utils.init(application);
        ActivityLifecycleCallback.getInstance().init(application);
        initARouter(application);
        if (BuildConfig.DEBUG) {
            //debug版本
            Timber.plant(new Timber.DebugTree());
        } else {//release版本
            //正式打印开关，同时gradle中的release的debuggable要设置为false
            Timber.plant(new CrashReportingTree());
        }
        //rx错误统一配置
        rxErrorHandler = RxErrorHandler
                .builder()
                .with(application)
                .responseErrorListener(errorListener)
                .build();
        Gloading.debug(BuildConfig.DEBUG);
        Gloading.initDefault(adapter);
    }



    public RxErrorHandler getRxErrorHandler() {
        return rxErrorHandler;
    }

    /**
     * 路由
     */
    private void initARouter(Application application) {
        if (BuildConfig.DEBUG) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        //推荐在Application中初始化
        ARouter.init(application);

    }






    public class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        public UnSafeHostnameVerifier(String host) {
            this.host = host;
            Timber.i("###############　UnSafeHostnameVerifier " + host);
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            Timber.i("############### verify " + hostname + " " + this.host);
            if (this.host == null || "".equals(this.host) || !this.host.contains(hostname)){
                return false;
            }else {
                return true;
            }
        }
    }




}

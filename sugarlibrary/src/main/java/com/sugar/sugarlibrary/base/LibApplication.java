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
package com.sugar.sugarlibrary.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.billy.android.loading.Gloading;
import com.bumptech.glide.Glide;
import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.router.ARouterUtils;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import timber.log.Timber;

/**
 * @author wobiancao
 * @date 2019/5/15
 * desc :
 */
public abstract class LibApplication  extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.INSTANCE.initConfig(this, getResponesErrorListener(), getGloadingAdapter());
        initHttp();

    }

    /**
     * 统一的rx error handler
     *
     */
    protected abstract ResponseErrorListener getResponesErrorListener();

    /**
     * 获取统一的状态view适配器
     * https://github.com/luckybilly/Gloading
     */
    protected abstract Gloading.Adapter getGloadingAdapter();

    /**
     *初始化网络配置
     */
    protected abstract void initHttp();

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        Timber.i("onTerminate");
        super.onTerminate();
        ARouterUtils.destroy();
    }

    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        Timber.i("onLowMemory");
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    /**
     * HOME键退出应用程序
     * 程序在内存清理的时候执行
     */
    @Override
    public void onTrimMemory(int level) {
        Timber.i("onTrimMemory");
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }


}

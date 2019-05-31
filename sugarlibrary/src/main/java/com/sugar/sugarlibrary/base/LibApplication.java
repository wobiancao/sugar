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

import androidx.multidex.MultiDex;

import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.base.config.SugarConfigure;
import com.sugar.sugarlibrary.router.ARouterUtils;

import timber.log.Timber;

/**
 * @author wobiancao
 * @date 2019/5/15
 * desc :
 */
public abstract class LibApplication<S extends SugarConfigure>  extends Application {
    protected S mConfigure;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initConfigure();
        if (mConfigure == null){
            throw new IllegalStateException("SugarConfigure is not init");
        }
        AppConfig.INSTANCE.initConfig(mConfigure);
        init();
    }

    /**
     * 初始化app相关配置
     */
    protected abstract void initConfigure();

    /**
     * 初始化一些操作
     */
    protected abstract void init();


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
    }

    /**
     * HOME键退出应用程序
     * 程序在内存清理的时候执行
     */
    @Override
    public void onTrimMemory(int level) {
        Timber.i("onTrimMemory");
        super.onTrimMemory(level);

    }


}

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
import android.app.Dialog;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.billy.android.loading.Gloading;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.hjq.toast.ToastUtils;
import com.sugar.sugarlibrary.BuildConfig;
import com.sugar.sugarlibrary.core.ActivityLifecycleCallback;
import com.sugar.sugarlibrary.imageload.framework.ImageLoaderManager;
import com.sugar.sugarlibrary.rx.errorhandler.RxErrorHandler;
import com.sugar.sugarlibrary.util.CrashReportingTree;

import java.util.Objects;

import timber.log.Timber;

/**
 * author by wobiancao
 * on 2019/4/11
 * desc :
 */
public enum AppConfig {
    //对象
    INSTANCE;
    private AppSetting mAppSetting;
    private SugarConfigure mSugarConfigure;
    /**
     * 全局统一loading
     */
    public void initConfig(SugarConfigure sugarConfigure){
        this.mSugarConfigure = sugarConfigure;
        this.mAppSetting = sugarConfigure.getAppSetting();
        //常用工具盒
        Utils.init(getAppSetting().getApplication());
        //生命周期监控
        ActivityLifecycleCallback.getInstance().init(getAppSetting().getApplication());
        //阿里路由
        initARouter(getAppSetting().getApplication());
        //toast初始化
        ToastUtils.init(sugarConfigure.mApplication);
        //toast样式设置
        if (null != sugarConfigure.getToastStyle()){
            ToastUtils.initStyle(sugarConfigure.getToastStyle());
        }
        if (this.getAppSetting().isDebug()) {
            //debug版本
            Timber.plant(new Timber.DebugTree());
        } else {//release版本
            //正式打印开关，同时gradle中的release的debuggable要设置为false
            Timber.plant(new CrashReportingTree());
        }
        //多状态页切换
        if (null != getAppSetting().getAdapter()){
            Gloading.debug(BuildConfig.DEBUG);
            Gloading.initDefault(getAppSetting().getAdapter());
        }
        //是否使用facebook网络监控
        if (getAppSetting().getHttpSetting().isHttpMonitor()){
            Stetho.initializeWithDefaults(getApplication());
        }


        //图片加载库初始化配置
        if (null != getAppSetting().getAppImageLoadSetting()){
            ImageLoaderManager.getInstance().init(getAppSetting().getApplication(), Objects.requireNonNull(getAppSetting().getAppImageLoadSetting().getImageLoaderConfig()));
        }
    }

    public Application getApplication() {
        return getAppSetting().getApplication();
    }

    public AppSetting getAppSetting() {
        return mAppSetting;
    }

    /**
     * 统一dialog
     * @return
     */
    public Dialog getLoadingDialog(Context context, String msg) {
        if (null == getAppSetting()  || null == getAppSetting().getBaseLoadingDialog()){
            return null;
        }
        return getAppSetting().getBaseLoadingDialog().createLoadingDialog(context, msg);
    }

    /**
     * 统一异常获取处理
     * @return
     */
    public RxErrorHandler getRxErrorHandler() {
        return  RxErrorHandler
                .builder()
                .with(getAppSetting().getApplication())
                .responseErrorListener(getAppSetting().getResponseErrorListener())
                .build();
    }


    /**
     * 路由
     */
    private void initARouter(Application application) {
        if (this.getAppSetting().isDebug()) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        //推荐在Application中初始化
        ARouter.init(application);

    }

    /**
     * 统一的配置
     * @return
     */
    public SugarConfigure getSugarConfigure() {
        return mSugarConfigure;
    }

}

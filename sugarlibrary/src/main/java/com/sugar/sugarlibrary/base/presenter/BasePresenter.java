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
package com.sugar.sugarlibrary.base.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sugar.sugarlibrary.base.BaseIView;
import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.http.SugarRepository;
import com.sugar.sugarlibrary.rx.RxEventBus;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * @author wobiancao
 * @date 2019/5/13
 * desc :
 */
public abstract class BasePresenter <V extends BaseIView, M extends SugarRepository>{
    protected RxErrorHandler rxErrorHandler;
    protected AppCompatActivity mContext;
    protected V mView;
    protected M mModel;
    private RxPermissions mRxPermissions;
    protected void attachView(AppCompatActivity activity, V view) {
        this.mContext = activity;
        this.mView = view;
        mModel = (M) new SugarRepository(mView);
        rxErrorHandler = AppConfig.INSTANCE.getRxErrorHandler();
        mRxPermissions = new RxPermissions(activity);
        initRepository();
        handleEvent();
    }

    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    /**
     * 初始化相关数据仓库
     */
    protected abstract void initRepository();

    protected void detachView() {
        this.mView = null;
    }

    protected boolean isAttachView() {
        return this.mView != null;
    }

    protected void handleEvent(){

    }


    protected void subscribeEvent(final Class<?> eventType, ErrorHandleSubscriber subscriber){
        if (mView == null) {return;}
        RxEventBus.getInstance().toObservable(eventType)
                .compose(mView.getProvider().bindToLifecycle())
                .subscribe(subscriber);
    }


    protected void subscribeStickyEvent(final Class<?> eventType, ErrorHandleSubscriber subscriber){
        if (mView == null) {return;}
        RxEventBus.getInstance().toObservableSticky(eventType)
                .compose(mView.getProvider().bindToLifecycle())
                .subscribe(subscriber);
    }

    protected void onCleared() {

    }

    protected void onCreatePresenter(@Nullable Bundle savedState) {

    }

    protected void onSaveInstanceState(Bundle outState) {

    }

    protected void onDestroyPresenter() {
        this.mContext = null;
        detachView();
    }

}

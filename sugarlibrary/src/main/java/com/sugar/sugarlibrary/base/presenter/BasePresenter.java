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

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sugar.sugarlibrary.base.BaseIView;
import com.sugar.sugarlibrary.rx.RxEventBus;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * @author wobiancao
 * @date 2019/5/13
 * desc :
 */
public class BasePresenter <V extends BaseIView>{
    protected AppCompatActivity mContext;
    protected V mView;
    protected LifecycleProvider<Lifecycle.Event> provider;
    protected void attachView(AppCompatActivity activity, V view) {
        this.mContext = activity;
        this.mView = view;
        provider = AndroidLifecycle.createLifecycleProvider(activity);
        handleEvent();
    }

    protected void detachView() {
        this.mView = null;
        this.provider = null;
    }

    protected boolean isAttachView() {
        return this.mView != null;
    }

    protected void handleEvent(){

    }

    protected void subscribe(final Observable observable, ErrorHandleSubscriber subscriber) {
        if (mView == null || provider == null) {return;}
        observable.subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    mView.showDialogLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mView.hideDialogLoading();
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(provider.bindToLifecycle())
                .subscribe(subscriber);
    }

    protected void subscribeEvent(final Class<?> eventType, ErrorHandleSubscriber subscriber){
        if (mView == null || provider == null) {return;}
        RxEventBus.getInstance().toObservable(eventType)
                .compose(provider.bindToLifecycle())
                .subscribe(subscriber);
    }


    protected void subscribeStickyEvent(final Class<?> eventType, ErrorHandleSubscriber subscriber){
        if (mView == null || provider == null) {return;}
        RxEventBus.getInstance().toObservableSticky(eventType)
                .compose(provider.bindToLifecycle())
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

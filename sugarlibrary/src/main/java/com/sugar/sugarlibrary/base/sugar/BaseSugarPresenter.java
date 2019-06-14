package com.sugar.sugarlibrary.base.sugar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sugar.sugarlibrary.base.BaseIView;
import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.rx.RxEventBus;
import com.sugar.sugarlibrary.rx.SugarHandleSubscriber;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * @author wobiancao
 * @date 2019-06-14
 * desc :
 */
public abstract class BaseSugarPresenter {
    protected AppCompatActivity mContext;
    private RxPermissions mRxPermissions;
    private BaseIView mView;
    public void attachView(AppCompatActivity activity, BaseIView view){
        this.mContext = activity;
        this.mView = view;
        mRxPermissions = new RxPermissions(activity);
        handleEvent();
    }

    protected void handleEvent() {

    }

    protected RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    public abstract void detachView();

    public abstract boolean isAttachView();


    protected void subscribeEvent(final Class<?> eventType, SugarHandleSubscriber subscriber){
        if (mView == null) {return;}
        RxEventBus.getInstance().toObservable(eventType)
                .compose(mView.getProvider().bindToLifecycle())
                .subscribe(subscriber);
    }


    protected void subscribeStickyEvent(final Class<?> eventType, SugarHandleSubscriber subscriber){
        if (mView == null) {return;}
        RxEventBus.getInstance().toObservableSticky(eventType)
                .compose(mView.getProvider().bindToLifecycle())
                .subscribe(subscriber);
    }



    public void onCleared() {

    }

    public void onCreatePresenter(Bundle savedState) {

    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onDestroyPresenter() {
        this.mContext = null;
        detachView();
    }
}

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

import com.blankj.utilcode.util.LogUtils;
import com.sugar.sugarlibrary.base.BaseIView;
import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.rx.errorhandler.RetryWithDelay;
import com.sugar.sugarlibrary.rx.errorhandler.RxErrorHandler;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wobiancao
 * @date 2019/5/20
 * desc :
 */
public class SugarRepository {
    /**
     * 0 没loading 1 dialog形式  2page形式
     */
    protected final static int LOADING_TYPE_NULL = 0;
    /**
     * 0 没loading 1 dialog形式  2page形式
     */
    protected final static int LOADING_TYPE_DIALOG = 1;
    /**
     * 0 没loading 1 dialog形式  2page形式
     */
    protected final static int LOADING_TYPE_PAGE = 2;
    protected BaseIView mIView;

    public SugarRepository(BaseIView IView) {
        mIView = IView;
        rxErrorHandler = AppConfig.INSTANCE.getRxErrorHandler();
    }
    protected RxErrorHandler rxErrorHandler;

    protected Observable addObservable(Observable observable) {
        if (mIView == null) {
            return null;
        }
        return customObservable(observable);
    }

    protected Observable addObservable(Observable observable, int loadingType) {
        if (mIView == null) {
            return null;
        }
        return customObservable(observable)
                .doOnSubscribe(disposable -> {
                    if (loadingType > 0) {
                        if (loadingType == LOADING_TYPE_DIALOG) {
                            mIView.showDialogLoading();
                        } else {
                            mIView.showLoading();
                        }
                    }
                });
    }

    private Observable customObservable(Observable observable) {
        return observable
                .compose(mIView.getProvider().bindToLifecycle())
                .retryWhen(new RetryWithDelay(2, 2))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mIView != null) {
                        mIView.hideDialogLoading();
                    }
                })
                .doOnNext(o -> {
                    if (mIView != null) {
                        mIView.showLoadSuccess();
                    }
                })
                .doOnError(throwable -> {
                    LogUtils.i("doOnError------" + throwable);
                    if (mIView != null) {
                        mIView.showLoadFailed();
                    }
                    if (rxErrorHandler != null){
                        rxErrorHandler.getHandlerFactory().handleError((Throwable) throwable);
                    }

                });
    }
}

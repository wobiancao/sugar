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
package com.sugar.demo.ui.mvp;

import com.sugar.demo.bean.GirlsData;
import com.sugar.sugarlibrary.base.presenter.BasePresenter;

import java.util.List;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author wobiancao
 * @date 2019/5/20
 * desc :
 */
public class GankPresenter extends BasePresenter<GankContract.IView, GankModel> implements GankContract.PView {

    @Override
    protected void initRepository() {
        mModel = new GankModel(provider);
    }

    @Override
    public void getFuliDataRepository(String size, String index) {

        mModel.getFuliDataRepository(size, index)
                .doOnSubscribe(disposable -> {
                    mView.showDialogLoading();
                })
                .doFinally(() -> {
                    mView.hideDialogLoading();
                })
                .subscribe(new ErrorHandleSubscriber<List<GirlsData>>(rxErrorHandler) {
                    @Override
                    public void onNext(List<GirlsData> girlsResults) {
                        mView.bindData(girlsResults);
                    }
                });
    }


}

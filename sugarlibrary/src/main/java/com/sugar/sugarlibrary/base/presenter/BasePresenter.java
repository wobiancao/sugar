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

import androidx.appcompat.app.AppCompatActivity;

import com.sugar.sugarlibrary.base.BaseIView;
import com.sugar.sugarlibrary.base.sugar.BaseSugarPresenter;
import com.sugar.sugarlibrary.http.SugarRepository;


/**
 * @author wobiancao
 * @date 2019/5/13
 * desc :
 */
public abstract class BasePresenter <V extends BaseIView, M extends SugarRepository> extends BaseSugarPresenter {
    protected V mView;
    protected M mModel;

    @Override
    public void attachView(AppCompatActivity activity, BaseIView view) {
        super.attachView(activity, view);
        this.mView = (V) view;
        mModel = (M) new SugarRepository(mView);
        initRepository();
    }






    /**
     * 初始化相关数据仓库
     */
    protected abstract void initRepository();

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public boolean isAttachView() {
        return this.mView != null;
    }




}

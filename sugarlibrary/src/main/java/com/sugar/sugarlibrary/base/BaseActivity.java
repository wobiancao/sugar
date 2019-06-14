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

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.base.presenter.BasePresenter;
import com.sugar.sugarlibrary.base.presenter.PresenterDispatch;
import com.sugar.sugarlibrary.base.presenter.PresenterProviders;
import com.sugar.sugarlibrary.base.sugar.BaseSugarActivity;

import static com.sugar.sugarlibrary.util.Constant.USE_SELEF_VIEW;

/**
 * @author wobiancao
 * @date 2019/5/13
 * desc :基类
 */
public abstract class BaseActivity extends BaseSugarActivity {
    protected PresenterProviders mPresenterProviders;
    protected PresenterDispatch mPresenterDispatch;





    @Override
    protected void initPresenterProvider(Bundle savedInstanceState) {
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);
        mPresenterDispatch.attachView(this, this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterDispatch.onDestroyPresenter();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }


}

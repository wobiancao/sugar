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
import android.view.View;

import androidx.annotation.IdRes;

import com.sugar.sugarlibrary.base.presenter.PresenterDispatch;
import com.sugar.sugarlibrary.base.presenter.PresenterProviders;
import com.sugar.sugarlibrary.base.sugar.BaseSugarFragment;

/**
 * @author wobiancao
 * @date 2019/5/15
 * desc : 用于java fragment
 */
public abstract class BaseFragment extends BaseSugarFragment{
    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;



    @Override
    protected void initPresenterProvider(Bundle savedInstanceState) {
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);
        mPresenterDispatch.attachView(getActivity(), this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
    }



    @Override
    protected View getPageView() {
        return null;
    }

    /**
     * 获取viewid
     * @param id
     * @return
     */
    public View findViewById(@IdRes int id) {
        View view;
        if (mRootView != null) {
            view = mRootView.findViewById(id);
            return view;
        }
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenterDispatch.onDestroyPresenter();
    }





}

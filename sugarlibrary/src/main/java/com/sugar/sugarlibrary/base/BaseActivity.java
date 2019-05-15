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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.billy.android.loading.Gloading;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OSUtils;
import com.sugar.sugarlibrary.base.presenter.BasePresenter;
import com.sugar.sugarlibrary.base.presenter.PresenterDispatch;
import com.sugar.sugarlibrary.base.presenter.PresenterProviders;

/**
 * @author wobiancao
 * @date 2019/5/13
 * desc :基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseIView{
    /**
     * 状态页holder类
     */
    protected Gloading.Holder mHolder;
    //软键盘管理
    private InputMethodManager mInputMethodManager;
    protected Context mContext;
    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;

    protected abstract int getContentView();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mContext = this;
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);
        mPresenterDispatch.attachView(this, this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        init(savedInstanceState);
        initImmersionBar();
        loadData();
    }

    public abstract void init(Bundle savedInstanceState);
    public abstract void loadData();
    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }
    protected void initImmersionBar(){
        ImmersionBar.with(this)
                //使用该属性,必须指定状态栏颜色
                .fitsSystemWindows(true)
                //解决软键盘与底部输入框冲突问题
                .keyboardEnable(true)
                //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor("#ffffff")
                .init();
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }


    protected void initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            //bind status view to activity root view by default
            mHolder = Gloading.getDefault().wrap(this).withRetry(new Runnable() {
                @Override
                public void run() {
                    onLoadRetry();
                }
            });
        }
    }


    @Override
    public void showLoading() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoading();
    }

    @Override
    public void showLoadSuccess() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadSuccess();
    }

    @Override
    public void showLoadFailed() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadFailed();
    }

    @Override
    public void showEmpty() {
        initLoadingStatusViewIfNeed();
        mHolder.showEmpty();
    }

    @Override
    public void onLoadRetry() {
        loadData();
    }

    @Override
    public void showDialogLoading() {

    }

    @Override
    public void hideDialogLoading() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterDispatch.detachView();
        mInputMethodManager = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mInputMethodManager != null)) {
            this.mInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 非必加
        // 如果你的app可以横竖屏切换，适配了华为emui3系列系统手机，并且navigationBarWithEMUI3Enable为true，
        // 请在onResume方法里添加这句代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、华为emui3系列系统手机；3、navigationBarWithEMUI3Enable为true）
        // 否则请忽略
        if (OSUtils.isEMUI3_x()) {
            ImmersionBar.with(this).init();
        }
    }

}

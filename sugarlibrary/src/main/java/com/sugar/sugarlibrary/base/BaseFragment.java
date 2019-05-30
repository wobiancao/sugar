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

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billy.android.loading.Gloading;
import com.blankj.utilcode.util.NetworkUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionFragment;
import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.base.presenter.BasePresenter;
import com.sugar.sugarlibrary.base.presenter.PresenterDispatch;
import com.sugar.sugarlibrary.base.presenter.PresenterProviders;
import com.sugar.sugarlibrary.widget.gloading.StatusConstant;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import static com.sugar.sugarlibrary.util.Constant.USE_SELEF_VIEW;

/**
 * @author wobiancao
 * @date 2019/5/15
 * desc :
 */
public abstract class BaseFragment<P extends BasePresenter> extends SimpleImmersionFragment implements BaseIView{

    protected Gloading.Holder mHolder;
    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;
    protected View mRootView;
    protected LayoutInflater inflater;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;
    //是否用了懒加载
    protected boolean isLazy;
    protected Context mContext;
    protected Activity mActivity;
    protected Dialog mDialog;
    public abstract @LayoutRes int getLayoutId();
    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public LifecycleProvider<Lifecycle.Event> getProvider() {
        return AndroidLifecycle.createLifecycleProvider(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null){
                parent.removeView(mRootView);
            }

        } else {
            if (getLayoutId() == USE_SELEF_VIEW){
                mRootView = getPageView();
            }else {
                mRootView = inflater.inflate(getLayoutId(), container, false);
            }

            mActivity = getActivity();
            mContext = mActivity;
            this.inflater = inflater;
        }
        return mRootView;
    }

    /**
     * getLayoutId() == -1的情况
     * @return
     */
    protected View getPageView() {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);

        mPresenterDispatch.attachView(getActivity(), this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        isPrepared = true;
        init(savedInstanceState);
        lazyLoad();
        loadData();
    }

    public abstract void init(Bundle savedInstanceState);

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

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        lazyLoadData();
        isPrepared = false;
    }

    /**
     * 懒加载
     */
    protected void lazyLoadData() {
        isLazy = true;
    }


    protected void loadData() {
        isLazy = false;
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .init();
    }

    protected void initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            //bind status view to activity root view by default
            mHolder = Gloading.getDefault().wrap(getActivity()).withRetry(new Runnable() {
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
        if(NetworkUtils.isConnected()){
            mHolder.showLoadFailed();
        }else {
            showNetWorkError();
        }
    }

    @Override
    public void showNetWorkError() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadingStatus(StatusConstant.STATUS_NETWORK_ERROR);
    }

    @Override
    public void showEmpty() {
        initLoadingStatusViewIfNeed();
        mHolder.showEmpty();
    }

    @Override
    public void onLoadRetry() {
        if (isLazy){
            lazyLoadData();
        }else {
            loadData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenterDispatch.detachView();
    }

    @Override
    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }


    @Override
    public void showDialogLoading() {
        showDialogLoading("");
    }

    @Override
    public void showDialogLoading(String msg) {
        if (null == mDialog){
            mDialog = AppConfig.INSTANCE.getLoadingDialog(mContext, msg);
        }
        if (mDialog != null){
            mDialog.show();
        }
    }

    @Override
    public void hideDialogLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}

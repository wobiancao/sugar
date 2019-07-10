package com.sugar.sugarlibrary.base.sugar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.billy.android.loading.Gloading;
import com.blankj.utilcode.util.NetworkUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.sugar.sugarlibrary.base.BaseIView;
import com.sugar.sugarlibrary.base.config.AppConfig;
import com.sugar.sugarlibrary.widget.gloading.StatusConstant;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import static com.sugar.sugarlibrary.util.Constant.USE_SELEF_VIEW;

/**
 * @author wobiancao
 * @date 2019-06-14
 * desc :
 */
public abstract class BaseSugarFragment extends ImmersionFragment implements BaseIView {
    /**
     * 状态页holder类
     */
    protected Gloading.Holder mHolder;
    /**
     * 标志位 标志已经初始化完成。
     */
    protected boolean isPrepared;
    /**
     * 标志位 fragment是否可见
     */
    protected boolean isVisible;

    /**
     * 是否用了懒加载
     */
    protected boolean isLazy;
    /**
     * rootView
     */
    protected View mRootView;
    protected LayoutInflater inflater;
    protected Context mContext;
    protected Dialog mDialog;


    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
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
            // 返回-1 用自己的view 否则用layout id
            if (getLayoutId() == USE_SELEF_VIEW){
                if (getPageView() == null){
                    throw new IllegalStateException("pageView == null must getPageView() != nll or getLayoutId() != -1");
                }
                mRootView = getPageView();
            }else {
                mRootView = inflater.inflate(getLayoutId(), container, false);
            }

            this.inflater = inflater;
        }
        return mRootView;
    }

    /**
     * 返回xml layout
     * @return
     */
    public abstract @LayoutRes int getLayoutId();
    /**
     * getLayoutId() == -1的情况 否则为空
     * @return
     */
    protected abstract View getPageView();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenterProvider(savedInstanceState);
        isPrepared = true;
        init(savedInstanceState);
        lazyLoad();
        loadData();
    }

    /**
     * 初始化属性
     * @param savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);

    /**
     * 必须在baseFragment初始化presenter相关provider
     * @param savedInstanceState
     */
    protected abstract void initPresenterProvider(Bundle savedInstanceState);


    @Override
    public void initImmersionBar() {
        AppConfig.INSTANCE.getSugarConfigure()
                .getImmersionBar(ImmersionBar.with(this))
                .init();
    }

    @Override
    public void initLoadingStatusViewIfNeed() {
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


    /**
     * 懒加载
     */
    protected void lazyLoadData() {
        isLazy = true;
    }


    protected void loadData() {
        isLazy = false;
    }

    @Override
    public void onVisible() {
        lazyLoad();
    }

    @Override
    public void onInvisible() {
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
    public LifecycleProvider<Lifecycle.Event> getProvider() {
        return AndroidLifecycle.createLifecycleProvider(this);
    }
}

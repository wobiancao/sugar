package com.sugar.sugarlibrary.base.sugar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.billy.android.loading.Gloading;
import com.blankj.utilcode.util.NetworkUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OSUtils;
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
public abstract class BaseSugarActivity extends AppCompatActivity implements BaseIView {

    protected Context mContext;
    /**
     * 软键盘管理
     */
    private InputMethodManager mInputMethodManager;
    /**
     *  dialog loading
     */
    protected Dialog mDialog;
    /**
     * 状态页holder类
     */
    protected Gloading.Holder mHolder;
    /**
     * 获取layout id
     * @return
     */
    protected abstract int getContentView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        if (getContentView() != USE_SELEF_VIEW){
            setContentView(getContentView());
        }

        preInit(savedInstanceState);
        initPresenterProvider(savedInstanceState);
        init(savedInstanceState);
        initImmersionBar();
        loadData();

    }

    /**
     * 必须在baseActivity初始化presenter相关provider
     * @param savedInstanceState
     */
    protected abstract void initPresenterProvider(Bundle savedInstanceState);

    /**
     * 在init方法之前 用于getContentView == -1的情况
     * @param savedInstanceState
     */
    protected void preInit(Bundle savedInstanceState) {

    }

    /**
     * 初始化相关属性
     * @param savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);

    /**
     * 装载数据
     */
    public abstract void loadData();

    /**
     * 初始化状态栏
     */
    protected void initImmersionBar(){
        AppConfig.INSTANCE.getSugarConfigure()
                .getImmersionBar(ImmersionBar.with(this))
                .init();
    }


    @Override
    public LifecycleProvider<Lifecycle.Event> getProvider() {
        return AndroidLifecycle.createLifecycleProvider(this);
    }




    @Override
    public void initLoadingStatusViewIfNeed() {
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
        loadData();
    }

    @Override
    public void showDialogLoading() {
        showDialogLoading("");
    }

    @Override
    public void showDialogLoading(String msg) {
        if (null == mDialog){
            mDialog = AppConfig.INSTANCE.getLoadingDialog(this, msg);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInputMethodManager = null;
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
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
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

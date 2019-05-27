package com.sugar.sugarlibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout;

import com.sugar.sugarlibrary.R;

/**
 * 等待框demo
 */
public class BaseLoadingDialog {

    public BaseLoadingDialog() {
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public Dialog createLoadingDialog(Context context, String msg) {
        LoadingStatusView loadingStatusView = new LoadingStatusView(context, msg);
        // 创建自定义样式的Dialog
        Dialog loadingDialog = new Dialog(context, R.style.lib_loading_dialog);
        // 设置返回键返回
        loadingDialog.setCancelable(true);
        //点击空白取消
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(loadingStatusView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;
    }
}

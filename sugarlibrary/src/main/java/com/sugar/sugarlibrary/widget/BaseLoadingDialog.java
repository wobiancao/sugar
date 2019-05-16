package com.sugar.sugarlibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(R.layout.lib_loading_dialog_view, null);
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.lib_dialog_view);
        // 页面中的Img
        ImageView img = (ImageView) view.findViewById(R.id.lib_dialog_img);
        // 页面中显示文本
        TextView tipText = (TextView) view.findViewById(R.id.lib_dialog_tv);

        // 加载动画，动画用户使img图片不停的旋转
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.lib_dialog_load_animation);
        // 显示动画
        img.startAnimation(animation);
        // 显示文本
        tipText.setText(msg);
        // 创建自定义样式的Dialog
        Dialog loadingDialog = new Dialog(context, R.style.lib_loading_dialog);
        // 设置返回键返回
        loadingDialog.setCancelable(true);
        //点击空白取消
        loadingDialog.setCanceledOnTouchOutside(true);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }
}

package com.sugar.sugarlibrary.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sugar.sugarlibrary.R;

/**
 * @author wobiancao
 * @date 2019-05-24
 * desc :
 */
public class LoadingStatusView extends LinearLayout {
    public LoadingStatusView(Context context, String msg) {
        super(context);
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(R.layout.lib_loading_dialog_view, this, true);
        TextView mMsgView = view.findViewById(R.id.lib_loading_msg);
        mMsgView.setText(msg);
    }
}

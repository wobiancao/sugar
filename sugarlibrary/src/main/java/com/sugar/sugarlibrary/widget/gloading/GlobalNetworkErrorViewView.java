package com.sugar.sugarlibrary.widget.gloading;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
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
public class GlobalNetworkErrorViewView extends LinearLayout {
    public GlobalNetworkErrorViewView(Context context, Runnable retryRunable) {
        super(context);
       View view =  LayoutInflater.from(context).inflate(R.layout.lib_offilne_page_view, this, true);
        TextView textView = view.findViewById(R.id.error_retry);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
    }
}

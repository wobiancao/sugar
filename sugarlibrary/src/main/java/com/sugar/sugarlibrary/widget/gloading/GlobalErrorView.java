package com.sugar.sugarlibrary.widget.gloading;

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
public class GlobalErrorView extends LinearLayout {
    public GlobalErrorView(Context context, Runnable retryRunable) {
        super(context);
       View view = LayoutInflater.from(context).inflate(R.layout.lib_error_page_view, this, true);
        TextView textView = view.findViewById(R.id.error_retry);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                retryRunable.run();
            }
        });
    }
}

package com.sugar.sugarlibrary.widget.gloading;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.sugar.sugarlibrary.R;

/**
 * @author wobiancao
 * @date 2019-05-24
 * desc :
 */
public class GlobalSuccessViewView extends LinearLayout {
    public GlobalSuccessViewView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.lib_success_page_view, this, true);
        setVisibility(GONE);
    }
}

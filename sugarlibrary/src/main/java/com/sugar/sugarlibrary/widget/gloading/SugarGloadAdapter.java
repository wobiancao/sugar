package com.sugar.sugarlibrary.widget.gloading;

import android.view.View;

import com.billy.android.loading.Gloading;

/**
 * @author wobiancao
 * @date 2019-05-24
 * desc :
 */
public class SugarGloadAdapter implements Gloading.Adapter {
    @Override
    public View getView(Gloading.Holder holder, View convertView, int status) {
        switch (status) {
            case StatusConstant.STATUS_LOADING:
                convertView = new GlobalLoadingView(holder.getContext());
                break;
            case StatusConstant.STATUS_LOAD_FAILED:
                convertView = new GlobalErrorView(holder.getContext(), holder.getRetryTask());
                break;
            case StatusConstant.STATUS_EMPTY_DATA:
                convertView = new GlobalEmptyView(holder.getContext());
                break;
            case StatusConstant.STATUS_LOAD_SUCCESS:
                convertView = new GlobalSuccessViewView(holder.getContext());
                break;
            case StatusConstant.STATUS_NETWORK_ERROR:
                convertView = new GlobalNetworkErrorViewView(holder.getContext(), holder.getRetryTask());
                break;

        }
        return convertView;
    }
}

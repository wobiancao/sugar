package com.sugar.sugarlibrary.rx.errorhandler;

import android.content.Context;

/**
 * https://github.com/JessYanCoding
 */
public interface ResponseErrorListener {
    void handleResponseError(Context context, Throwable t);

    ResponseErrorListener EMPTY = new ResponseErrorListener() {
        @Override
        public void handleResponseError(Context context, Throwable t) {


        }
    };
}

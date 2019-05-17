package com.sugar.sugarlibrary.util;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

/**
 * author by wobiancao
 * on 2019/4/18
 * desc :
 */
public class CrashReportingTree extends Timber.Tree{
    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }
        if (t != null) {
            if (priority == Log.ERROR) {

            } else if (priority == Log.WARN) {

            } else {

            }
        }

    }
}

package com.sugar.sugarlibrary.imageload.fresco;

import android.app.ActivityManager;
import android.os.Build;

import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;

/**
 * Created by ${wuzhao} on 2017/5/22 0022.
 */

public class BitmapMemoryCacheParamsSupplier implements Supplier<com.facebook.imagepipeline.cache.MemoryCacheParams> {
    private static final int MAX_CACHE_ENTRIES = 56;
    private static final int MAX_CACHE_ASHM_ENTRIES = 128;
    private static final int MAX_CACHE_EVICTION_SIZE = 5;
    private static final int MAX_CACHE_EVICTION_ENTRIES = 5;
    private final ActivityManager mActivityManager;

    public BitmapMemoryCacheParamsSupplier(ActivityManager activityManager) {
        mActivityManager = activityManager;
    }

    @Override
    public com.facebook.imagepipeline.cache.MemoryCacheParams get() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new com.facebook.imagepipeline.cache.MemoryCacheParams(getMaxCacheSize(), MAX_CACHE_ENTRIES, MAX_CACHE_EVICTION_SIZE, MAX_CACHE_EVICTION_ENTRIES, 1);
        } else {
            return new com.facebook.imagepipeline.cache.MemoryCacheParams(
                    getMaxCacheSize(),
                    MAX_CACHE_ASHM_ENTRIES,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
        }
    }
    private int getMaxCacheSize() {
        final int maxMemory =
                Math.min(mActivityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);
        if (maxMemory < 32 * ByteConstants.MB) {
            return 4 * ByteConstants.MB;
        } else if (maxMemory < 64 * ByteConstants.MB) {
            return 6 * ByteConstants.MB;
        } else {
            return maxMemory / 6;
        }
    }
}

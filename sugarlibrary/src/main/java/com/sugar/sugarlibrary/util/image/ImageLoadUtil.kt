/*
 * Copyright 2019 wobiancao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sugar.sugarlibrary.util.image

import android.widget.ImageView
import com.sugar.sugarlibrary.base.config.AppConfig
import com.sugar.sugarlibrary.imageload.framework.ImageLoaderManager
import com.sugar.sugarlibrary.imageload.framework.ImageLoaderOptions

/**
 * @author wobiancao
 * @date 2019-09-06
 * desc :图片加载
 */
class ImageLoadUtil {
    companion object {
        /**
         * 加载普通图片
         */
        fun loadImage(image: ImageView, url: String) {
            ImageLoaderManager.getInstance().showImage(raduisOptions(image, url, 0))
        }

        /**
         * 加载圆角
         */
        fun loadImage(image: ImageView, url: String, raduisDp: Int) {
            ImageLoaderManager.getInstance().showImage(raduisOptions(image, url, raduisDp))
        }

        /**
         * 部分圆角
         */
        fun loadImage(image: ImageView, url: String, tlRaduis: Int, trRaduis: Int, blRaduis: Int, brRaduis: Int) {
            ImageLoaderManager.getInstance().showImage(raduisOptions(image, url, tlRaduis, trRaduis, blRaduis, brRaduis))
        }

        fun loadImageCircle(image: ImageView, url: String) {
            ImageLoaderManager.getInstance().showImage(circleOptions(image, url))
        }

        /**
         * 加载gif
         */
        fun loadImageGif(image: ImageView, url: String, raduisDp: Int) {
            ImageLoaderManager.getInstance().showImage(gifOptions(image, url, raduisDp))
        }

        /**
         * 加载圆形
         */

        private fun circleOptions(image: ImageView, url: String): ImageLoaderOptions {
            val loadSetting = AppConfig.INSTANCE.appSetting.appImageLoadSetting
            return ImageLoaderOptions.Builder(image, url)
                    .placeholder(loadSetting.placeholder)
                    .error(loadSetting.errorholder)
                    .isCrossFade(loadSetting.isCrossFade)
                    .isCircle
                    .build()

        }

        /**
         * 带圆角
         */
        private fun raduisOptions(image: ImageView, url: String, raduisDp: Int): ImageLoaderOptions {
            val loadSetting = AppConfig.INSTANCE.appSetting.appImageLoadSetting
            return ImageLoaderOptions.Builder(image, url)
                    .placeholder(loadSetting.placeholder)
                    .error(loadSetting.errorholder)
                    .isCrossFade(loadSetting.isCrossFade)
                    .imageRadiusDp(raduisDp)
                    .build()
        }

        /**
         * 带圆角
         */
        private fun raduisOptions(image: ImageView, url: String, tlRaduis: Int, trRaduis: Int, blRaduis: Int, brRaduis: Int): ImageLoaderOptions {
            val loadSetting = AppConfig.INSTANCE.appSetting.appImageLoadSetting
            return ImageLoaderOptions.Builder(image, url)
                    .placeholder(loadSetting.placeholder)
                    .error(loadSetting.errorholder)
                    .isCrossFade(loadSetting.isCrossFade)
                    .imageRadiusDp(tlRaduis)
                    .isAllRadius(false)
                    .setRadiusDp(tlRaduis, trRaduis, blRaduis, brRaduis)
                    .build()
        }

        /**
         * gif
         */
        private fun gifOptions(image: ImageView, url: String, raduisDp: Int): ImageLoaderOptions {
            val loadSetting = AppConfig.INSTANCE.appSetting.appImageLoadSetting
            return ImageLoaderOptions.Builder(image, url)
                    .placeholder(loadSetting.placeholder)
                    .error(loadSetting.errorholder)
                    .isCrossFade(loadSetting.isCrossFade)
                    .imageRadiusDp(raduisDp)
                    .asGif(true)
                    .build()
        }
    }
}
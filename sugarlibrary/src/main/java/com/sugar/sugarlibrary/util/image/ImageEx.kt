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

/**
 * @author wobiancao
 * @date 2019-09-06
 * desc :image扩展函数
 */

/**
 * 普通加载图片
 */
fun ImageView.load(url: String){
    ImageLoadUtil.loadImage(this, url)
}

/**
 * 加载圆角
 */
fun ImageView.load(url: String, raduisDp: Int){
    ImageLoadUtil.loadImage(this, url, raduisDp)
}

/**
 * 加载部分圆角
 */
fun ImageView.load(url: String, tlRaduis: Int, trRaduis: Int, blRaduis: Int, brRaduis: Int){
    ImageLoadUtil.loadImage(this, url, tlRaduis, trRaduis, blRaduis, brRaduis)
}

/**
 * 加载圆形
 */
fun ImageView.loadAsCircle(url: String){
    ImageLoadUtil.loadImageCircle(this, url)
}

/**
 * 加载gif
 */
fun ImageView.loadAsGif(url: String, raduisDp: Int){
    ImageLoadUtil.loadImageGif(this, url, raduisDp)
}
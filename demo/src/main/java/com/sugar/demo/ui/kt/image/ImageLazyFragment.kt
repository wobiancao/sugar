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
package com.sugar.demo.ui.kt.image

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.sugar.demo.R
import com.sugar.sugarlibrary.base.BaseLazyFragment
import com.sugar.sugarlibrary.util.image.load
import com.sugar.sugarlibrary.util.image.loadAsCircle
import com.sugar.sugarlibrary.util.image.loadAsGif
import kotlinx.android.synthetic.main.fragment_image.*

/**
 * @author wobiancao
 * @date 2019-09-06
 * desc :
 */
class ImageLazyFragment : BaseLazyFragment(){
    val imageUrl = "https://cdn.pixabay.com/photo/2016/12/18/22/32/desert-1916882_1280.jpg"
    val gifUrl = "https://freshdesign.io/img/inspiration/tramastudio.gif"

    override fun getLayoutId(): Int {
        return R.layout.fragment_image
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun loadData() {
        super.loadData()
        LogUtils.d("loadData----no do here")
    }

    override fun fetchData() {
        //do here
        LogUtils.d("fetchData----do here")
        iv_normal.load(imageUrl)
        iv_raduis.load(imageUrl, 8)
        iv_circle.loadAsCircle(imageUrl)
        iv_gif.loadAsGif(gifUrl, 0)
    }
}
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

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.sugar.demo.R
import com.sugar.demo.pager.FragmentAdapter
import com.sugar.demo.router.RouterPageContant
import com.sugar.sugarlibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_imageload.*
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem
import me.majiajie.pagerbottomtabstrip.item.NormalItemView

/**
 * @author wobiancao
 * @date 2019-09-06
 * desc :
 */
@Route(path = RouterPageContant.KT_IMAGE)
class ImageLoadActivity : BaseActivity(){
    override fun getContentView(): Int {
        return R.layout.activity_imageload
    }

    override fun init(savedInstanceState: Bundle?) {
        val navigationController = pv.custom()
                .addItem(newItem(R.mipmap.app_t_home_n, R.mipmap.app_t_home, "直加"))
                .addItem(newItem(R.mipmap.app_t_next_n, R.mipmap.app_t_next, "懒加"))
                .build()
        vp.adapter = FragmentAdapter(supportFragmentManager, getFragmentList())
        navigationController.setupWithViewPager(vp)
    }

    private fun getFragmentList(): MutableList<Fragment> {
        var fragmentList = mutableListOf<Fragment>()
        fragmentList.add(ImageFragment())
        fragmentList.add(ImageLazyFragment())
        return fragmentList
    }

    override fun loadData() {
    }


    private fun newItem(drawable: Int, checkedDrawable: Int, tabName: String): BaseTabItem {
        val normalItemView = NormalItemView(this)
        normalItemView.initialize(drawable, checkedDrawable, tabName)
        normalItemView.setTextDefaultColor(Color.GRAY)
        normalItemView.setTextCheckedColor(resources.getColor(R.color.colorPrimary))
        return normalItemView
    }
}
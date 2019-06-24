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
package com.sugar.demo.ui.kt.anko

import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import com.sugar.demo.R
import com.sugar.demo.bean.gank.GirlsData
import com.sugar.demo.router.RouterPageContant
import com.sugar.demo.ui.kt.TestGankPresenter
import com.sugar.demo.ui.kt.anko.ui.GankActivityUi
import com.sugar.demo.ui.kt.anko.ui.GankFragmentUi
import com.sugar.demo.ui.mvp.gank.GankContract
import com.sugar.sugarlibrary.anko.anno.AnkoInject
import com.sugar.sugarlibrary.anko.anno.AnkoVariable
import com.sugar.sugarlibrary.anko.base.BaseAnkoFragment
import com.sugar.sugarlibrary.base.BaseFragment
import com.sugar.sugarlibrary.base.anno.CreatePresenter
import com.sugar.sugarlibrary.base.anno.PresenterVariable

/**
 * @author wobiancao
 * @date 2019-06-14
 * desc :
 */
@AnkoInject(ui = GankFragmentUi::class)
@CreatePresenter(presenter = [TestGankPresenter::class])
class GankAnkoFragment: BaseAnkoFragment(),  GankContract.IView {

    @PresenterVariable
    internal var mPresenter: TestGankPresenter? = null
    @AnkoVariable
    internal var ui : GankFragmentUi? = null

    override fun init(savedInstanceState: Bundle?) {
//        mInfoView = findViewById(R.id.tv_info) as TextView
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true, 0.2f)
                .init()
    }



    override fun loadData() {
        mPresenter?.getFuliDataRepository("10", "1")
    }

    override fun bindData(data: MutableList<GirlsData>?) {
        val jsonStr = JSON.toJSONString(data)
        ui?.textInfo?.text = jsonStr
    }
}
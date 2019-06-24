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

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.sugar.demo.bean.gank.GirlsData
import com.sugar.demo.router.RouterPageContant
import com.sugar.demo.ui.kt.anko.ui.GankActivityUi
import com.sugar.demo.ui.mvp.gank.GankContract
import com.sugar.demo.ui.mvp.gank.GankPresenter
import com.sugar.sugarlibrary.anko.base.BaseAnkoActivity
import com.sugar.sugarlibrary.anko.anno.AnkoInject
import com.sugar.sugarlibrary.anko.anno.AnkoVariable
import com.sugar.sugarlibrary.base.anno.CreatePresenter
import com.sugar.sugarlibrary.base.anno.PresenterVariable

/**
 * @author wobiancao
 * @date 2019-06-21
 * desc :
 */
@AnkoInject(ui = GankActivityUi::class)
@Route(path = RouterPageContant.KT_ANKO)
@CreatePresenter(presenter = [GankPresenter::class])
class GankAnkoActivity : BaseAnkoActivity(), GankContract.IView {

    @PresenterVariable
    internal var mPresenter: GankPresenter? = null
    @AnkoVariable
    internal var ui : GankActivityUi? = null

    @SuppressLint("NewApi")
    override fun init(savedInstanceState: Bundle?) {
        setSupportActionBar(ui?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "KtGankActivity"
    }

    override fun loadData() {
        mPresenter?.getFuliDataRepository("20", "1")
    }

    override fun bindData(data: MutableList<GirlsData>?) {
        val jsonStr = JSON.toJSONString(data)
        ui?.textInfo?.text = jsonStr
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
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
package com.sugar.demo.ui.kt

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.sugar.demo.R
import com.sugar.demo.bean.gank.GirlsData
import com.sugar.demo.router.RouterPageContant
import com.sugar.demo.ui.mvp.gank.GankContract
import com.sugar.demo.ui.mvp.gank.GankPresenter
import com.sugar.sugarlibrary.base.BaseActivity
import com.sugar.sugarlibrary.base.anno.CreatePresenter
import com.sugar.sugarlibrary.base.anno.PresenterVariable

/**
 * @author wobiancao
 * @date 2019-06-14
 * desc :
 */
@Route(path = RouterPageContant.KT_GANK)
@CreatePresenter(presenter = [GankPresenter::class])
class KtGankActivity : BaseActivity(), GankContract.IView{
    @PresenterVariable
    internal var mPresenter: GankPresenter? = null
     lateinit var mInfoView: TextView
     lateinit var mToolbar: Toolbar

    override fun bindData(data: MutableList<GirlsData>?) {
        val jsonStr = Gson().toJson(data)
        mInfoView.text = jsonStr
    }

    override fun getContentView(): Int {
        return R.layout.gank_activity_list
    }

    override fun init(savedInstanceState: Bundle?) {
        mInfoView = findViewById(R.id.tv_info)
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "KtGankActivity"



    }

    override fun loadData() {
        mPresenter?.getFuliDataRepository("10", "1")
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
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
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.sugar.demo.R
import com.sugar.demo.router.RouterPageContant
import com.sugar.demo.ui.kt.anko.GankAnkoFragment
import com.sugar.sugarlibrary.base.BaseActivity

/**
 * @author wobiancao
 * @date 2019-06-14
 * desc :
 */
@Route(path = RouterPageContant.KT_CONTAINER)
class KtContainerActivity : BaseActivity(){

    val mLayoutId by lazy{
        R.id.test_frame_layout
    }

    lateinit var mToolbar: Toolbar


    override fun getContentView(): Int {
        return R.layout.container_activity
    }

    override fun init(savedInstanceState: Bundle?) {
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "AnkoContainerActivity"
        supportFragmentManager.beginTransaction()
                .add(mLayoutId, GankAnkoFragment())
                .commit()

    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .init()
    }

    override fun loadData() {

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
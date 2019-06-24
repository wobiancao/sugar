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
package com.sugar.demo.ui.kt.anko.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.sugar.demo.R
import com.sugar.demo.ui.kt.anko.common.toolbarx
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout

/**
 * @author wobiancao
 * @date 2019-06-21
 * desc :
 */
class GankActivityUi : AnkoComponent<Context>{
    lateinit var toolbar: Toolbar
    lateinit var textInfo: TextView

    @SuppressLint("NewApi")
    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        verticalLayout {
            lparams(matchParent, matchParent)
            appBarLayout {
                toolbar =  toolbarx(R.style.ToolbarStyle) {
                    navigationIcon = context.getDrawable(R.drawable.ic_back_material_white)
                }
            }.lparams(matchParent, wrapContent)

            textInfo = textView {
                gravity = Gravity.CENTER
            }.lparams(wrapContent, wrapContent)

        }
    }
}
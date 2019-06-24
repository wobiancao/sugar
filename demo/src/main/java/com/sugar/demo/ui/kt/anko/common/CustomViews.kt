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
package com.sugar.demo.ui.kt.anko.common

import android.view.ViewManager
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.custom.ankoView

/**
 * @author wobiancao
 * @date 2019-06-24
 * desc :
 */
/**
 * toolbar-x 扩展
 */
inline fun ViewManager.toolbarx(theme: Int = 0, init:  Toolbar.() -> Unit): Toolbar {
    return ankoView({ Toolbar(it) }, theme, init)
}
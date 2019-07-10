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
package com.sugar.sugarlibrary.anko.base

import android.view.View
import com.sugar.sugarlibrary.anko.inject.AnkoInjectTools
import com.sugar.sugarlibrary.base.BaseFragment
import com.sugar.sugarlibrary.util.Constant.USE_SELEF_VIEW

/**
 * @author wobiancao
 * @date 2019-06-24
 * desc :
 */
abstract class BaseAnkoFragment: BaseFragment() {

    /**
     *  返回-1 用自己的view 否则用layout id
     */
    override fun getLayoutId(): Int {
        return USE_SELEF_VIEW
    }

    override fun getPageView(): View {
        return AnkoInjectTools.autoInjectAnkoUi(this)
    }

}
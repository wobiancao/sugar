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
package com.sugar.sugarlibrary.base

import android.os.Bundle
import com.sugar.sugarlibrary.base.BaseFragment

/**
 * @author wobiancao
 * @date 2019-09-02
 * desc : 懒加载fragment
 */
abstract class  BaseLazyFragment : BaseFragment(){
     var isViewInitiated: Boolean = false
     var isVisibleToUser: Boolean = false
     var isDataInitiated: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    fun prepareFetchData(forceUpdate: Boolean) : Boolean{
        if (isViewInitiated && isVisibleToUser  && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }

    override fun loadData() {
        super.loadData()
    }

    abstract fun fetchData()

}
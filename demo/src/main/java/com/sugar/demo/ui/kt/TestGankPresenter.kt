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

import com.sugar.demo.http.repository.GankRepository
import com.sugar.demo.ui.mvp.gank.GankContract
import com.sugar.sugarlibrary.base.presenter.BasePresenter

/**
 * @author wobiancao
 * @date 2019-06-14
 * desc :
 */
class TestGankPresenter: BasePresenter<GankContract.IView, GankRepository>(), GankContract.PView {
    override fun getFuliDataRepository(size: String?, index: String?) {


        mModel.getFuliDataRepository(size, index)
                .subscribe({
                    mView.bindData(it)
                },{

                })

    }

    override fun initRepository() {
        mModel = GankRepository(mView)
    }
}
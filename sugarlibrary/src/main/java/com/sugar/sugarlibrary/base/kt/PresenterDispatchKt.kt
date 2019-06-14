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
package com.sugar.sugarlibrary.base.kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sugar.sugarlibrary.base.BaseIView
import com.sugar.sugarlibrary.base.presenter.BasePresenter
import com.sugar.sugarlibrary.base.presenter.PresenterProviders

/**
 * @author wobiancao
 * @date 2019-06-14
 * desc :
 */
class PresenterDispatchKt(private val mProviders :PresenterProviders ) {

    fun attachView(activity: AppCompatActivity, view: BaseIView){
       var store = mProviders.presenterStore
       var map = store.map
       for (entry in map){
           var presenter  =  entry.value as BasePresenter
           presenter?.attachView( activity, view )
       }

   }


   fun onCreatePresenter(savedState: Bundle?) {
       var store = mProviders.presenterStore
       var map = store.map
       for (entry in map){
           var presenter  =  entry.value as BasePresenter
           presenter?.onCreatePresenter(savedState)
       }
   }

    fun onDestroyPresenter() {
        var store = mProviders.presenterStore
        var map = store.map
        for (entry in map){
            var presenter  =  entry.value as BasePresenter
            presenter?.onDestroyPresenter()
        }
    }

    fun onSaveInstanceState( savedState: Bundle) {
        var store = mProviders.presenterStore
        var map = store.map
        for (entry in map){
            var presenter  =  entry.value as BasePresenter
            presenter?.onSaveInstanceState(savedState)
        }
    }
}
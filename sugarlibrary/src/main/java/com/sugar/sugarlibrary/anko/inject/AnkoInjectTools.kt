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
package com.sugar.sugarlibrary.anko.inject

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.sugar.sugarlibrary.anko.anno.AnkoInject
import com.sugar.sugarlibrary.anko.anno.AnkoVariable
import com.sugar.sugarlibrary.anko.base.BaseAnkoActivity
import com.sugar.sugarlibrary.anko.base.BaseAnkoFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.ctx

/**
 * @author wobiancao
 * @date 2019-06-24
 * desc :
 */
class AnkoInjectTools {
    companion object {
        /**
         * 注解activity
         */
        @SuppressWarnings("unchecked")
        fun autoInjectAnkoUi(activity: BaseAnkoActivity) {
            var clazz = activity.javaClass
            var ankoInject = clazz.getAnnotation(AnkoInject::class.java)
            var classes = ankoInject?.ui
            for (field in clazz.declaredFields) {
                var anns = field.declaredAnnotations
                if (anns.size < 1) {
                    continue
                }
                if (anns[0] is AnkoVariable) {

                    var uiInstance = classes?.java?.newInstance()
                    field.isAccessible = true
                    field.set(activity, uiInstance)
                    uiInstance as AnkoComponent<Context>?
                    if (uiInstance != null) {
                        uiInstance.setContentView(activity)
                    }

                }


            }
        }
        /**
         * 注解 fragment
         */
        @SuppressWarnings("unchecked")
        fun autoInjectAnkoUi(fragment: BaseAnkoFragment): View {
            var view: View = LinearLayout(fragment.context)
            var clazz = fragment.javaClass
            var ankoInject = clazz.getAnnotation(AnkoInject::class.java)
            var classes = ankoInject?.ui
            for (field in clazz.declaredFields) {
                var anns = field.declaredAnnotations
                if (anns.size < 1) {
                    continue
                }
                if (anns[0] is AnkoVariable) {

                    var uiInstance = classes?.java?.newInstance()
                    field.isAccessible = true
                    field.set(fragment, uiInstance)
                    uiInstance as AnkoComponent<Context>?
                    if (uiInstance != null) {
                        view = uiInstance.createView(AnkoContext.Companion.create(fragment.ctx, fragment.ctx))
                    }
                    return view

                }


            }
            return view
        }
    }




}

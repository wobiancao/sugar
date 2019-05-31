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
package com.sugar.sugarlibrary.base.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import androidx.fragment.app.Fragment;

import com.sugar.sugarlibrary.base.anno.CreatePresenter;
import com.sugar.sugarlibrary.base.anno.PresenterVariable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * author by wobiancao
 * on 2019/4/10
 * desc :
 */
public class PresenterProviders {

    private PresenterStore mPresenterStore = new PresenterStore<>();
    private Activity mActivity;
    private Fragment mFragment;
    private Class<?> mClass;

    public static PresenterProviders inject(Activity activity) {
        return new PresenterProviders(activity, null);
    }

    public static PresenterProviders inject(Fragment fragment) {
        return new PresenterProviders(null, fragment);
    }

    private PresenterProviders(Activity activity, Fragment fragment) {
        if (activity != null) {
            this.mActivity = activity;
            mClass = this.mActivity.getClass();
        }
        if (fragment != null) {
            this.mFragment = fragment;
            mClass = this.mFragment.getClass();
        }
        resolveCreatePresenter();
        resolvePresenterVariable();
    }

    private static Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request PresenterProviders before onCreate call.");
        }
        return application;
    }

    private static Activity checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create PresenterProviders for detached fragment");
        }
        return activity;
    }

    private static Context checkContext(Context context) {
        Context resultContent = null;
        if (context instanceof Activity) {
            resultContent = context;
        }
        if (resultContent == null) {
            throw new IllegalStateException("Context must Activity Context");
        }
        return resultContent;
    }

    private <P extends BasePresenter> PresenterProviders resolveCreatePresenter() {
        CreatePresenter createPresenter = mClass.getAnnotation(CreatePresenter.class);
        if (createPresenter != null) {

            Class<P>[] classes = (Class<P>[]) createPresenter.presenter();
            for (Class<P> clazz : classes) {
                String canonicalName = clazz.getCanonicalName();
                try {
                    mPresenterStore.put(canonicalName, clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    private <P extends BasePresenter> PresenterProviders resolvePresenterVariable() {
        for (Field field : mClass.getDeclaredFields()) {
            //获取字段上的注解
            Annotation[] anns = field.getDeclaredAnnotations();
            if (anns.length < 1) {
                continue;
            }
            if (anns[0] instanceof PresenterVariable) {
                String canonicalName = field.getType().getName();
                P presenterInstance = (P) mPresenterStore.get(canonicalName);
                if (presenterInstance != null) {
                    try {
                        field.setAccessible(true);
                        field.set(mFragment != null ? mFragment : mActivity, presenterInstance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }


    public <P extends BasePresenter> P getPresenter(int index) {
        CreatePresenter createPresenter = mClass.getAnnotation(CreatePresenter.class);
        if (createPresenter == null) {
            return null;
        }
        if (createPresenter.presenter().length == 0) {
            return null;
        }
        if (index >= 0 && index < createPresenter.presenter().length) {
            String key = createPresenter.presenter()[index].getCanonicalName();
            BasePresenter presenter = mPresenterStore.get(key);
            if (presenter != null) {
                return (P) presenter;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public PresenterStore getPresenterStore() {
        return mPresenterStore;
    }
}

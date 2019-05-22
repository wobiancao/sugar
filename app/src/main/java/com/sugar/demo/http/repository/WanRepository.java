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
package com.sugar.demo.http.repository;

import android.arch.lifecycle.Lifecycle;

import com.sugar.demo.bean.wan.WanArticle;
import com.sugar.demo.bean.wan.WanData;
import com.sugar.demo.bean.wan.WanResult;
import com.sugar.demo.core.HttpException;
import com.sugar.demo.http.api.Wan;
import com.sugar.sugarlibrary.http.AppHttpClient;
import com.sugar.sugarlibrary.http.SugarRepository;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @author wobiancao
 * @date 2019-05-21
 * desc :
 */
public class WanRepository extends SugarRepository implements RepositoryContract.WanModel {
    public WanRepository(LifecycleProvider<Lifecycle.Event> provider) {
        super(provider);
    }

    @Override
    public Wan getService() {
        return AppHttpClient.getInstance().initService(Wan.class);
    }

    @Override
    public <T> ObservableTransformer<WanResult<T>, T> wanTransformer() {
        return upstream -> upstream
                .flatMap((Function<WanResult<T>, ObservableSource<T>>) tWanResult -> {
                    if (tWanResult == null) {
                        return Observable.error(new HttpException("返回值为null"));
                    }
                    if (tWanResult.errorCode == 0) {
                        return Observable.just(tWanResult.data);
                    } else {
                        return Observable.error(new HttpException("接口异常"));
                    }
                });
    }

    @Override
    public Observable<WanData> getWanArticleList(String index) {
        return setUpObservable(getService()
                .getWanArticleList(index)
                .compose(wanTransformer()));
    }


}

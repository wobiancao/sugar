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
package com.sugar.demo.ui.mvp;

import android.arch.lifecycle.Lifecycle;

import com.sugar.demo.bean.GirlsData;
import com.sugar.demo.bean.GirlsResult;
import com.sugar.demo.core.HttpException;
import com.sugar.demo.http.api.Gank;
import com.sugar.sugarlibrary.http.AppHttpClient;
import com.sugar.sugarlibrary.http.SugarModel;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @author wobiancao
 * @date 2019/5/20
 * desc :
 */
public class GankModel extends SugarModel<Gank> implements GankContract.Model {


    public GankModel(LifecycleProvider<Lifecycle.Event> provider) {
        super(provider);
    }

    @Override
    protected Gank getService() {
        return AppHttpClient.initService(Gank.class);
    }

    @Override
    public <T> ObservableTransformer<GirlsResult<T>, T> gankTransformer() {

        return new ObservableTransformer<GirlsResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<GirlsResult<T>> upstream) {
                return upstream.flatMap(new Function<GirlsResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(GirlsResult<T> tGirlsResult) throws Exception {
                        if (tGirlsResult == null){
                            return Observable.error(new HttpException("返回值为null"));
                        }
                        if (!tGirlsResult.error){
                            return Observable.just(tGirlsResult.results);
                        }else {
                            return Observable.error(new HttpException("接口异常"));
                        }
                    }
                });
            }


        };

    }

    @Override
    public Observable<List<GirlsData>> getFuliDataRepository(String size, String index){
        return setUpObservable(getService()
                .getFuliData(size, index)
                .compose(gankTransformer())

        );
    }

}

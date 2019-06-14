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

import com.sugar.demo.bean.gank.GirlsData;
import com.sugar.demo.bean.gank.GirlsResult;
import com.sugar.demo.core.HttpException;
import com.sugar.demo.http.api.Gank;
import com.sugar.sugarlibrary.base.BaseIView;
import com.sugar.sugarlibrary.http.AppHttpClient;
import com.sugar.sugarlibrary.http.SugarRepository;

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
public class GankRepository extends SugarRepository implements RepositoryContract.GankModel {


    public GankRepository(BaseIView IView) {
        super(IView);
    }

    @Override
    public Gank getService() {
        return AppHttpClient.getInstance().initService(Gank.class);
    }


    @Override
    public <T> ObservableTransformer<GirlsResult<T>, T> gankTransformer() {

        return upstream -> upstream
                .flatMap((Function<GirlsResult<T>, ObservableSource<T>>) tGirlsResult -> {
                    if (tGirlsResult == null) {
                        return Observable.error(new HttpException("返回值为null"));
                    }
                    if (!tGirlsResult.error) {
                        return Observable.just(tGirlsResult.results);
                    } else {
                        return Observable.error(new HttpException("接口异常"));
                    }
                })

                ;

    }

    @Override
    public Observable<List<GirlsData>> getFuliDataRepository(String size, String index) {
        return addObservable(getService()
                .getFuliData(size, index)
                .compose(gankTransformer()), LOADING_TYPE_PAGE);
    }

}

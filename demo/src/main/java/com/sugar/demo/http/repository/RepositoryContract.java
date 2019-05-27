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
import com.sugar.demo.bean.wan.WanArticle;
import com.sugar.demo.bean.wan.WanData;
import com.sugar.demo.bean.wan.WanResult;
import com.sugar.demo.http.api.Gank;
import com.sugar.demo.http.api.Wan;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * @author wobiancao
 * @date 2019-05-21
 * desc :
 */
public class RepositoryContract {

    /**
     * gank.io
     */
    public interface GankModel  {
        Gank getService();
        /**
         * Transformer 需要处理api返回值包装的加上即可
         * @param <T>
         * @return
         */
        <T> ObservableTransformer<GirlsResult<T>, T> gankTransformer();

        Observable<List<GirlsData>> getFuliDataRepository(String size, String index);
    }

    /**
     * wanandroid
     */
    public interface WanModel{
        Wan getService();
        /**
         * Transformer 需要处理api返回值包装的加上即可
         * @param <T>
         * @return
         */
        <T> ObservableTransformer<WanResult<T>, T> wanTransformer();


        Observable<WanData> getWanArticleList(String index);
    }
}

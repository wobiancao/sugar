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
package com.sugar.demo.http.api;

import com.sugar.demo.bean.GirlsData;
import com.sugar.demo.bean.GirlsResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author wobiancao
 * @date 2019/5/20
 * desc :
 */
public interface Gank {
    String HOST = "http://gank.io/";

    @GET("api/data/福利/{size}/{index}")
    Observable<GirlsResult<List<GirlsData>>> getFuliData(@Path("size") String size, @Path("index") String index);
}

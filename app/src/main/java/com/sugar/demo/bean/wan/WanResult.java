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
package com.sugar.demo.bean.wan;

/**
 * @author wobiancao
 * @date 2019-05-21
 * desc :
 */
public class WanResult<T> {
    public T data;
    public int errorCode;
    public String errorMsg;
}

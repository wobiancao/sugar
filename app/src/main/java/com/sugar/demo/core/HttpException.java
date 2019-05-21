package com.sugar.demo.core;

/**
 * @author wobiancao
 * @date 2019/5/21
 * desc :
 */
public class HttpException extends RuntimeException{
    String errorMsg;

    public HttpException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
}

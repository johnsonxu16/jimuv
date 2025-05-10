package com.jimuv.common.domain.http;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HttpResult<T> {

    private int code;
    private String msg;
    private T data;

    private HttpResult(HttpCode apiCodeEnum, T data) {
        this.code = apiCodeEnum.getCode();
        this.msg = apiCodeEnum.getMsg();
        this.data = data;
    }

    private HttpResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static <T> HttpResult<T> success(T data) {
        return new HttpResult<>(HttpCode.SUCCESS, data);
    }

    public static <T> HttpResult<T> error(T data) {
        return new HttpResult<>(HttpCode.ERROR, data);
    }

}


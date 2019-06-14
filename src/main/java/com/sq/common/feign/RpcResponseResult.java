package com.sq.common.feign;

import com.alibaba.fastjson.JSON;

/**
 * rpc 调用返回值
 * @param <T>
 */
public class RpcResponseResult<T> {
    private Integer code = 0;
    private String message ;
    private String msg;

    private T data;

    public RpcResponseResult() {
    }

    public RpcResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.msg = message;
    }

    public RpcResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

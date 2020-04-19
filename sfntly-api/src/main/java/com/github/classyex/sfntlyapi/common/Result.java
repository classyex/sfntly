/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2019, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.github.classyex.sfntlyapi.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.classyex.sfntlyapi.common.exception.ExceptionEnum;

import java.io.Serializable;

import static com.github.classyex.sfntlyapi.common.ResponseKeys.MSG_OK;
import static com.github.classyex.sfntlyapi.common.ResponseKeys.SUCCESS;


public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(T data) {
        this.code = SUCCESS;
        this.msg = MSG_OK;
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result(SUCCESS, MSG_OK);
    }

    public static <T> Result<T> ok(T data) {
        return new Result(SUCCESS, MSG_OK, data);
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result(SUCCESS, msg, data);
    }

    public static Result<Void> ok(Integer code) {
        return new Result(code, MSG_OK);
    }

    public static <T> Result<T> okWithData(T data) {
        return new Result(SUCCESS, MSG_OK, data);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg);
    }

    public static Result error(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result error(ExceptionEnum ee, Object data) {
        return new Result(ee.getCode(), ee.getMsg(), data);
    }

    public static Result error(ExceptionEnum ee) {
        return new Result(ee.getCode(), ee.getMsg());
    }

    public String toJSONString() {
        return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
    }
}

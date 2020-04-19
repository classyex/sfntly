/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2019, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.github.classyex.sfntlyapi.common.exception;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = 7908947273437963987L;

    private Integer code;

    public AppException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public AppException(ExceptionEnum exceptionEnum, Throwable throwable) {
        super(exceptionEnum.getMsg(), throwable);
        this.code = exceptionEnum.getCode();
    }

    public AppException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public AppException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public AppException(ExceptionEnum exceptionEnum, String msg) {
        super(exceptionEnum.getMsg() + "(" + msg + ")");
        this.code = exceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

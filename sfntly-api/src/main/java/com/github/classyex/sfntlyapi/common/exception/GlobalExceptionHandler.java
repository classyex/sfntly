/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2019, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.github.classyex.sfntlyapi.common.exception;

import com.github.classyex.sfntlyapi.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

import static com.github.classyex.sfntlyapi.common.exception.ExceptionEnum.*;


/**
 * 统一异常捕获类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errHandler(HttpServletRequest req, Exception e) {

        logger.error("GlobalException,Url:{}", req.getRequestURL().toString(), e);

        if (e instanceof AppException) {
            AppException ae = (AppException) e;
            return Result.error(ae.getCode(), ae.getMessage());
        } else if (e instanceof HttpMessageNotReadableException) {
            return Result.error(WRONG_REQ);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return Result.error(ERR_REQ_TYPE);
        } else if (e instanceof MultipartException) {
            return Result.error(NOT_A_MULTIPART_REQUEST);
        } else if (e instanceof IllegalArgumentException) {
            return Result.error(PARAM_INVALID.getCode(), e.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            return Result.error(PARAM_INVALID.getCode(), ((MethodArgumentNotValidException) e)
                    .getBindingResult().getFieldError().getDefaultMessage());
        }
        return Result.error(INTERNAL_ERROR);
    }
}

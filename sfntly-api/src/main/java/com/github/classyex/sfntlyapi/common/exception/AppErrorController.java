/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2019, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.github.classyex.sfntlyapi.common.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AppErrorController implements ErrorController {
    private final static String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(ERROR_PATH)
    public void error() {
        throw new AppException(ExceptionEnum.RES_NOT_FOUND);
    }
}

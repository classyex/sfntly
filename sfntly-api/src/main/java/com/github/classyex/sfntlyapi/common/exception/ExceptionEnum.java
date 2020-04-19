/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2019, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.github.classyex.sfntlyapi.common.exception;

public enum ExceptionEnum {
    /** 系统繁忙，请稍后再试 */
    INTERNAL_ERROR(10001, "系统繁忙，请稍后再试"),
    /** 资源不存在 */
    RES_NOT_FOUND(10002, "资源不存在"),
    /** 登录信息过期，请重新登录 */
    UNAUTHORIZED(10003, "登录信息过期，请重新登录"),
    /** 参数非法 */
    PARAM_INVALID(10004, "参数非法"),
    /** 请求参数错误 */
    WRONG_REQ(10005, "请求参数错误"),
    /** 微信繁忙，请稍后重试 */
    ERR_WX_API(10006, "微信繁忙，请稍后重试"),
    /** 文件下载失败 */
    DOWNLOAD_FAILED(10007, "文件下载失败"),
    /** 您的内容不符合规范 */
    CONTENT_WRONG_REQ(10008, "您的内容不符合规范"),
    /** IM-API调用异常 */
    IM_API_REQ_WRONG(10009, "IM-API调用异常"),
    /** 获取网络图片详情信息失败 */
    GET_REMOTE_IMAGEINFO_FAILED(10010, "获取网络图片详情信息失败"),
    /** 请求类型错误 */
    ERR_REQ_TYPE(10012, "请求类型错误"),
    /** 上传失败，请确保网络通畅后重试 */
    FILE_UPLOAD_FAILED(10013, "上传失败，请确保网络通畅后重试"),
    /** 媒体类型不对 */
    NOT_A_MULTIPART_REQUEST(10014, "current request is not a multipart request"),
    /** 该操作已失效，请刷新后重试 */
    OPERATE_FAIL(10015, "该操作已失效，请刷新后重试"),

    ;

    private Integer code;
    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

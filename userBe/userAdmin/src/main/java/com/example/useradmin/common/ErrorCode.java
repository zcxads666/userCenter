package com.example.useradmin.common;

import lombok.Data;

/**
 * 错误码
 */


public enum ErrorCode {

    SUCCESS(0, "ok", ""),
    PARAMS_NULL(40000, "请求参数为空", ""),
    PARAMS_ERROR(40001, "请求参数错误", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");


    private final int code;//状态码

    private final String message;//错误信息

    private final String description;//错误描述

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}

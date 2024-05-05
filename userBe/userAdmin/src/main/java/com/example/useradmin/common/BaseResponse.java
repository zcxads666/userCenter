package com.example.useradmin.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类型
 * @param <T>
 */

@Data
public class BaseResponse<T> implements Serializable {

    private int code;//状态码

    private T data;//数据

    private String message;//提示

    private String description;//描述

    public BaseResponse(int code, T data, String message ,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String description) {
        this(code, data,"" ,"");
    }


    public BaseResponse(int code, T data) {
        this(code, data,"" ,"");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());

    }

    }


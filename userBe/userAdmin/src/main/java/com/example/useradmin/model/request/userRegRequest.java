package com.example.useradmin.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 */
@Data
public class userRegRequest implements Serializable {//Serializable 序列化

    private static final long serialVersionUID = -4704164665212131647L;//指定序列号id

    private String userAccount;

    private String password;

    private String checkPassword;

}

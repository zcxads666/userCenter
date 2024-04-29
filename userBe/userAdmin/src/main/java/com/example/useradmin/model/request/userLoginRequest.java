package com.example.useradmin.model.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 用户登录请求体
 *
 */
@Data
public class userLoginRequest implements Serializable {


    private static final long serialVersionUID = 6495964955315330908L;//指定序列号id

    private String userAccount;

    private String password;



}

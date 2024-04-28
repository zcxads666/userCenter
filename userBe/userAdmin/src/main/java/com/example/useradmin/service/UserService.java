package com.example.useradmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.useradmin.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * 用户服务
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2024-04-27 15:55:38
*/

@Service
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount 账号
     * @param password 密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String password, String checkPassword);


    /**
     * 用户登录
     * @param userAccount 账号
     * @param password 密码
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String password, HttpServletRequest httpReq);
}

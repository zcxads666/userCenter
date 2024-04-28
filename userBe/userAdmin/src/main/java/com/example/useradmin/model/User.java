package com.example.useradmin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户序列号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户账户
     */
    private String accountnumber;

    /**
     * 电话号码
     */
    private Integer phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册时间
     */
    private Date restime;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 拥有权限 0-普通用户  1-管理员
     */
    private Integer permissions;

    /**
     * 是否删除 为删除0 删除1
     */
    private Integer isdelete;

    /**
     * 性别 男0 女1 保密 3
     */
    private Integer gender;

    /**
     * 状态 正常为0，封禁为1
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
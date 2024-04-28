package com.example.useradmin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.useradmin.mapper.UserMapper;
import com.example.useradmin.model.User;
import com.example.useradmin.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-04-27 15:55:38
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {



    private static final String ACCOUNT_PATTERN = "^[^\\W_]+$";
    final String  noise ="adminproject"; //混淆噪声

    @Autowired
    private UserMapper userMapper;



    public long userRegister(String userAccount, String password, String checkPassword) {

        //if(StreamUtils.isAllNotEmpty(userAccount,password,checkPassword))
        //if(ObjectUtils.isEmpty(userAccount||password||checkPassword))
        if(!StrUtil.isAllNotEmpty(userAccount,password,checkPassword)){
            return -1;}//判空}

        if(!password.equals(checkPassword)){return -1;}//密码一致校验

        if(userAccount.length()<4||userAccount.length()>16){return -1;}//账号长度校验

        if(password.length()<6||password.length()>16){return -1;}//密码长度校验

        Pattern pattern = Pattern.compile(ACCOUNT_PATTERN);
        Matcher matcher = pattern.matcher(userAccount);
        if(!matcher.matches()){return -1;}//账号格式校验

        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("AccountNumber",userAccount);//eq相当于==
        if(userMapper.exists(wrapper)){return -1;}//重复账户检验



        String Ciphertext= SecureUtil.sha256(noise+password);//密文
        User newUser = new User();




        Timestamp d= new Timestamp(System.currentTimeMillis());
        newUser.setRestime(d);

        newUser.setPermissions(0);
        newUser.setIsdelete(0);
        newUser.setGender(3);
        newUser.setStatus(0);

        newUser.setPassword(Ciphertext);
        newUser.setAccountnumber(userAccount);

        //userMapper.insert(newUser);
        boolean res = this.save(newUser);//插入
        if(!res){System.out.println(6);return -1;}
        return newUser.getId();
    }



   public User userLogin(String userAccount, String password, HttpServletRequest httpReq)
   {
       if(!StrUtil.isAllNotEmpty(userAccount,password)){
           return null;}//判空}


       if(userAccount.length()<4||userAccount.length()>16){return null;}//账号长度校验

       if(password.length()<6||password.length()>16){return null;}//密码长度校验

       Pattern pattern = Pattern.compile(ACCOUNT_PATTERN);
       Matcher matcher = pattern.matcher(userAccount);
       if(!matcher.matches()){return null;}//账号格式校验

       //System.out.println(userMapper.exists(new QueryWrapper<User>().eq("AccountNumber",userAccount)));
       if(userMapper.exists(new QueryWrapper<User>().eq("AccountNumber",userAccount)))
       {//检验账户是否存在
           System.out.println(1);
           QueryWrapper Wrapper=new QueryWrapper<User>().eq("AccountNumber",userAccount);

           if(userMapper.selectOne(Wrapper).getPassword().equals(SecureUtil.sha256(noise+password)) && userMapper.selectOne(Wrapper).getIsdelete().equals(0))
           {//密码检验及逻辑删除检验
               System.out.println(2);
               User user = new User();
              // System.out.println(1);

               user.setId(userMapper.selectOne(Wrapper).getId());//获取id
               user.setStatus(userMapper.selectOne(Wrapper).getStatus());//获取状态
               user.setPermissions(userMapper.selectOne(Wrapper).getPermissions());//获取权限
               user.setAvatar(userMapper.selectOne(Wrapper).getAvatar());//获取头像
               user.setNickname(userMapper.selectOne(Wrapper).getNickname());//获取昵称
               user.setAccountnumber(userMapper.selectOne(Wrapper).getAccountnumber());//获取账户
               user.setGender(userMapper.selectOne(Wrapper).getGender());//获取性别


               log.info("userLogin file,login success ");//打印日志

               httpReq.getSession().setAttribute("user",user);//设置状态


               return user;
           }
       }
       System.out.println(3);
       return null;
   }
}





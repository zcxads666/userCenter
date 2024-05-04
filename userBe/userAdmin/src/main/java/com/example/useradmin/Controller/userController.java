package com.example.useradmin.Controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.useradmin.mapper.UserMapper;
import com.example.useradmin.model.User;
import com.example.useradmin.model.request.userLoginRequest;
import com.example.useradmin.model.request.userRegRequest;
import com.example.useradmin.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.useradmin.contant.userContant.USER_LOGIN_STATE;

/** 控制类
 *
 *  注册
 *
 *  登录
 *
 *  删除
 *
 *  退出
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    UserServiceImpl userService = new UserServiceImpl();

    @Autowired
    UserMapper userMapper ;

    @PostMapping("/register")//注册
    public Long userRegister(@RequestBody userRegRequest userRegReq)
        {
            if(userRegReq == null)return null;

            String userAccount = userRegReq.getUserAccount();
            String password = userRegReq.getPassword();
            String checkPassword = userRegReq.getCheckPassword();

            if(!StrUtil.isAllNotEmpty(userAccount,password,checkPassword)){
                return null;}//判空}


          Long id =  userService.userRegister(userAccount,password,checkPassword);

            return id;
        }




    @PostMapping("/login")//登录
    public User userLogin(@RequestBody userLoginRequest userLoginReq, HttpServletRequest request)
    {
        if(userLoginReq == null)return null;

        String userAccount = userLoginReq.getUserAccount();
        String password = userLoginReq.getPassword();


        if(!StrUtil.isAllNotEmpty(userAccount,password)){
            return null;
        }//判空


        return  userService.userLogin(userAccount,password,request);


    }


    @GetMapping("/current")//获取用户登录状态
    public User getCurrentUser(HttpServletRequest request)
    {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null)
        {
            return null;
        }

        long userId = currentUser.getId();//重新查询，防止数据库数据被修改，缓存更新缓慢导致数据展现不及时

        User byId = userService.getById(userId);

//        if(byId.getStatus()==1)
//        {
//            byId.setStatus(0);
//            return ;
//        }

        User user = userService.getuserSafe(byId);

        return user;
    }



    @GetMapping("/search")//查询
    public List<User> searchUser( String username,HttpServletRequest request)
    {

        if(!isAdmin(request))
        {
            return new ArrayList<>();//失败返回空表
        }

        QueryWrapper<User> Wrapper = new QueryWrapper();
        log.info("username:{}:{}",username,StrUtil.isNotBlank(username));
        if(!StrUtil.isNotBlank(username))
        {

              //Wrapper.like("username",username);
            List<User> userList =userMapper.selectList(Wrapper);
            log.info("true:{},userList:{}",username,userList);
            return userList.stream().map(user -> userService.getuserSafe(user)).collect(Collectors.toList());//如果沒有輸入userMapper查询表中所有内容并返回一个object列表
        }
        else {
            Wrapper.like("userName", username);//模糊查詢


            List<User> userList = userService.list(Wrapper);
            log.info("else:{},userList:{}",username,userList);
            //遍历userList將關鍵字段置空，重新返回一个脫敏列表
            return userList.stream().map(user -> userService.getuserSafe(user)).collect(Collectors.toList());
        }
    }



    @PostMapping("/delect")//删除
    public boolean userDelect(@RequestBody long id,HttpServletRequest request)
    {

        if(!isAdmin(request)||id<0)
        {
            return false;
        }

        return userService.removeById(id);//自带的逻辑删除方法
    }


    @PostMapping("/logout")//登出
    public boolean userLogout(HttpServletRequest request)
    {

        if(request==null) return false;

        HttpSession session = request.getSession(false);
        if(session==null){
            return false;
        }
        session.removeAttribute(USER_LOGIN_STATE);
        //从定向到index.jsp
       // HttpServletResponse response. ;
       // response.sendRedirect("/session/index.jsp");

        //if(request.getSession().getAttribute(USER_LOGIN_STATE)!=null) {
            request.getSession().invalidate();//使HttpSession失效
            return true;
        //}
       // return false;
    }


    private boolean isAdmin(HttpServletRequest request)//判断是否是管理员
    {
        Object ojuser  = request.getSession().getAttribute(USER_LOGIN_STATE);

        User user = (User) ojuser;
        if(user == null || user.getPermissions()!=1)
        {
            return false;
        }
        return true;
    }

}

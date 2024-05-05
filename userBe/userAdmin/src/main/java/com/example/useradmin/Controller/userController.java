package com.example.useradmin.Controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.useradmin.common.BaseResponse;
import com.example.useradmin.common.ErrorCode;
import com.example.useradmin.common.ResutUtils;
import com.example.useradmin.exception.BusinessException;
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
    public BaseResponse<Long> userRegister(@RequestBody userRegRequest userRegReq)
        {
            if(userRegReq == null)return null;

            String userAccount = userRegReq.getUserAccount();
            String password = userRegReq.getPassword();
            String checkPassword = userRegReq.getCheckPassword();

            if(!StrUtil.isAllNotEmpty(userAccount,password,checkPassword)){
                //return ResutUtils.error(ErrorCode.PARAMS_NULL);}//判空}
                throw new BusinessException(ErrorCode.PARAMS_NULL);
            }


          Long id =  userService.userRegister(userAccount,password,checkPassword);

            return ResutUtils.success(id);
        }




    @PostMapping("/login")//登录
    public BaseResponse<User> userLogin(@RequestBody userLoginRequest userLoginReq, HttpServletRequest request)
    {
        if(userLoginReq == null)return null;

        String userAccount = userLoginReq.getUserAccount();
        String password = userLoginReq.getPassword();


        if(!StrUtil.isAllNotEmpty(userAccount,password)){
            throw new BusinessException(ErrorCode.PARAMS_NULL);
        }//判空


       User user =   userService.userLogin(userAccount,password,request);

        return ResutUtils.success(user);
    }


    @GetMapping("/current")//获取用户登录状态
    public BaseResponse<User> getCurrentUser(HttpServletRequest request)
    {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null)
        {
            //throw new BusinessException(ErrorCode.NOT_LOGIN,"请先登录");
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

        return ResutUtils.success(user);
    }



    @GetMapping("/search")//查询
    public BaseResponse<List<User>> searchUser( String username,HttpServletRequest request)
    {

        if(!isAdmin(request))
        {
            //List<User> nullList =  new ArrayList<>();//失败返回空表
           // return ResutUtils.success(nullList);
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        QueryWrapper<User> Wrapper = new QueryWrapper();
        log.info("username:{}:{}",username,StrUtil.isNotBlank(username));
        if(!StrUtil.isNotBlank(username))
        {

              //Wrapper.like("username",username);
            List<User> userList =userMapper.selectList(Wrapper);
            log.info("true:{},userList:{}",username,userList);
            List<User> UserList = userList.stream().map(user -> userService.getuserSafe(user)).collect(Collectors.toList());//如果沒有輸入userMapper查询表中所有内容并返回一个object列表

            return ResutUtils.success(UserList);
        }
        else {
            Wrapper.like("userName", username);//模糊查詢


            List<User> userList = userService.list(Wrapper);
            log.info("else:{},userList:{}",username,userList);
            //遍历userList將關鍵字段置空，重新返回一个脫敏列表
            List<User> UserList = userList.stream().map(user -> userService.getuserSafe(user)).collect(Collectors.toList());
            return ResutUtils.success(UserList);
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
    public BaseResponse<Boolean> userLogout(HttpServletRequest request)
    {

        if(request==null){
            //Boolean bl = false;
             throw new BusinessException(ErrorCode.NO_AUTH,"请先登录");
        }

        HttpSession session = request.getSession(false);
        if(session==null){
            throw new BusinessException(ErrorCode.NO_AUTH,"请先登录");
        }
        session.removeAttribute(USER_LOGIN_STATE);
        //从定向到index.jsp
       // HttpServletResponse response. ;
       // response.sendRedirect("/session/index.jsp");

        //if(request.getSession().getAttribute(USER_LOGIN_STATE)!=null) {
            request.getSession().invalidate();//使HttpSession失效

        Boolean bl = true;

            return ResutUtils.success(bl);
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

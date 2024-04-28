package com.example.useradmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.useradmin.model.User;
import org.springframework.stereotype.Repository;


import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-04-27 15:55:38
* @Entity generator.domain.User
*/

@Repository

public interface UserMapper extends BaseMapper<User> {

}





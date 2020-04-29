package com.security.study.dao;

import org.apache.ibatis.annotations.*;
import com.security.study.bean.User;


@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
	User findByUserName(String username);

}

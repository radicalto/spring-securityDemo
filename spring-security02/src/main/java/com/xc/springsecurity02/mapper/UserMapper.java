package com.xc.springsecurity02.mapper;
import com.xc.springsecurity02.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username=#{username}")
    User findByUserName(@Param("username") String username);

    @Select("SELECT * FROM user")
    List<User> selectAll();
}

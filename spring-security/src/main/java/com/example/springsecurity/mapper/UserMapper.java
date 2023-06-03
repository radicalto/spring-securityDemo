package com.example.springsecurity.mapper;

import com.example.springsecurity.entity.User;
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

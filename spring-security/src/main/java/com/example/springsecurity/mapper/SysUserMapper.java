package com.example.springsecurity.mapper;

import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserMapper {

    @Select("SELECT * FROM user WHERE username=#{username}")
    SysUser findByUserName(@Param("username") String username);

    @Select("SELECT * FROM user")
    List<SysUser> selectAll();
}

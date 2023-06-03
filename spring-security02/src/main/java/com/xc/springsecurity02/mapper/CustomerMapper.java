package com.xc.springsecurity02.mapper;

import com.xc.springsecurity02.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper {

    /**
     * 查询所有客户
     */
    @Select("select * from customer")
    List<Customer> selectAll();
}

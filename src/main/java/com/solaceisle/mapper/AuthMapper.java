package com.solaceisle.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthMapper {


    @Select("SELECT student_id FROM user WHERE email = #{account} AND password = #{password}")
    Long loginByEmail(String account, String password);
    @Select("SELECT student_id FROM user WHERE student_id = #{account} AND password = #{password}")
    Long loginByUsername(String account, String password);
}

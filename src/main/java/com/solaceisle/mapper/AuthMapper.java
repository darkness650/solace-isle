package com.solaceisle.mapper;

import com.solaceisle.pojo.dto.FindPasswordDTO;
import com.solaceisle.pojo.dto.RegisteDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthMapper {


    @Select("SELECT student_id FROM \"user\" WHERE email = #{account} AND password = #{password}")
    String loginByEmail(String account, String password);
    @Select("SELECT student_id FROM \"user\" WHERE student_id = #{account} AND password = #{password}")
    String loginByUsername(String account, String password);

    void register(RegisteDTO registeDTO);

    void findPassword(FindPasswordDTO findPasswordDTO);
}

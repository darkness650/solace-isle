package com.solaceisle.mapper;

import com.solaceisle.pojo.dto.FindPasswordDTO;
import com.solaceisle.pojo.dto.RegisteDTO;
import com.solaceisle.pojo.dto.SettingDTO;
import com.solaceisle.pojo.entity.Setting;
import com.solaceisle.pojo.entity.User;
import com.solaceisle.pojo.vo.UserChangeVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    Setting getHabits(String studentId);

    void setHabits(SettingDTO settingDTO);

    User getUserProfile(String studentId);

    void changeProfile(UserChangeVO userChangeVO);

    void updatePassword(String studentId, String newPassword);

    void updateEmail(String studentId, String email);

    List<String> listAllUserId();

    @Select("SELECT student_id FROM \"user\" WHERE email = #{account} AND password = #{password}")
    String loginByEmail(String account, String password);
    @Select("SELECT student_id FROM \"user\" WHERE student_id = #{account} AND password = #{password}")
    String loginByUsername(String account, String password);

    void register(RegisteDTO registeDTO);

    void findPassword(FindPasswordDTO findPasswordDTO);
    @Insert("insert into setting(student_id) values (#{studentId})")
    void setSetting(String studentId);
}


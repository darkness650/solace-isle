package com.solaceisle.mapper;

import com.solaceisle.pojo.dto.SettingDTO;
import com.solaceisle.pojo.entity.Setting;
import com.solaceisle.pojo.entity.User;
import com.solaceisle.pojo.po.SettingVO;
import com.solaceisle.pojo.po.UserChangeVO;
import org.apache.ibatis.annotations.Mapper;

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
}


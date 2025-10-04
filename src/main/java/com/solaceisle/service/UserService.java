package com.solaceisle.service;

import com.solaceisle.pojo.dto.SettingDTO;
import com.solaceisle.pojo.dto.UpdateEmailDTO;
import com.solaceisle.pojo.dto.UpdatePasswordDTO;
import com.solaceisle.pojo.vo.SettingVO;
import com.solaceisle.pojo.vo.UserChangeVO;
import com.solaceisle.pojo.vo.UserProfileVO;

public interface UserService {
    SettingVO getHabits();

    void setHabits(SettingDTO settingDTO);

    UserProfileVO getProfile();

    void changeProfile(UserChangeVO userChangeVO);

    void updatePassword(UpdatePasswordDTO updatePasswordDTO);

    void updateEmail(UpdateEmailDTO updateEmailDTO);
}


package com.solaceisle.service.impl;

import com.solaceisle.constant.AuthConstant;
import com.solaceisle.context.BaseContext;
import com.solaceisle.exception.IllegalRegisteMsgException;
import com.solaceisle.exception.UnCorrectPasswordException;
import com.solaceisle.mapper.UserMapper;
import com.solaceisle.pojo.dto.SettingDTO;
import com.solaceisle.pojo.dto.UpdateEmailDTO;
import com.solaceisle.pojo.dto.UpdatePasswordDTO;
import com.solaceisle.pojo.entity.Setting;
import com.solaceisle.pojo.entity.User;
import com.solaceisle.pojo.vo.SettingVO;
import com.solaceisle.pojo.vo.UserChangeVO;
import com.solaceisle.pojo.vo.UserProfileVO;
import com.solaceisle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RedisTemplate<String,String> redisTemplate;
    @Override
    public SettingVO getHabits() {
        String studentId= BaseContext.getCurrentId();
        Setting setting=userMapper.getHabits(studentId);
        SettingVO settingVO=new SettingVO();
        BeanUtils.copyProperties(setting,settingVO);
        return(settingVO);
    }

    @Override
    public void setHabits(SettingDTO settingDTO) {
        String studentId= BaseContext.getCurrentId();
        settingDTO.setStudentId(studentId);
        userMapper.setHabits(settingDTO);
    }

    @Override
    public UserProfileVO getProfile() {
        String studentId=BaseContext.getCurrentId();
        User user=userMapper.getUserProfile(studentId);
        UserProfileVO userProfileVO=new UserProfileVO();
        BeanUtils.copyProperties(user,userProfileVO);
        return userProfileVO;
    }

    @Override
    public void changeProfile(UserChangeVO userChangeVO) {
        String studentId=BaseContext.getCurrentId();
        userChangeVO.setStudentId(studentId);
        userMapper.changeProfile(userChangeVO);
    }

    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        String studentId=BaseContext.getCurrentId();
        String password=userMapper.getUserProfile(studentId).getPassword();
        if(!password.equals(updatePasswordDTO.getOldPassword())){
            throw new UnCorrectPasswordException(AuthConstant.ERROR_PASSWORD);
        }
        userMapper.updatePassword(studentId,updatePasswordDTO.getNewPassword());
//        redisTemplate.opsForHash().put(studentId,"sessionId",null);
        redisTemplate.opsForHash().delete(studentId,"sessionId");
        log.info("用户{}修改密码，清除sessionId",studentId);
    }

    @Override
    public void updateEmail(UpdateEmailDTO updateEmailDTO) {
        String studentId=BaseContext.getCurrentId();
        String code= redisTemplate.opsForValue().get(updateEmailDTO.getEmail());
        if(code==null || !code.equals(updateEmailDTO.getCode()))
        {
            log.info("验证码校验失败，code:{},realcode:{}",updateEmailDTO.getCode(),code);
            throw new IllegalRegisteMsgException(AuthConstant.ILLEGAL_CODE);
        }
        log.info("验证码校验通过");
        redisTemplate.delete(updateEmailDTO.getEmail());
        userMapper.updateEmail(studentId,updateEmailDTO.getEmail());
    }
}

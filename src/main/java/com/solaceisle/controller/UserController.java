package com.solaceisle.controller;

import com.solaceisle.pojo.dto.SettingDTO;
import com.solaceisle.pojo.dto.UpdateEmailDTO;
import com.solaceisle.pojo.dto.UpdatePasswordDTO;
import com.solaceisle.pojo.vo.SettingVO;
import com.solaceisle.pojo.vo.UserChangeVO;
import com.solaceisle.pojo.vo.UserProfileVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/settings")
    public Result<SettingVO> getHabits(){
        SettingVO settingVO = userService.getHabits();
        return Result.success(settingVO);
    }
    @PutMapping("/settings")
    public Result updateHabits(@RequestBody SettingDTO settingDTO){
        userService.setHabits(settingDTO);
        return Result.success();
    }
    @GetMapping("/profile")
    public Result<UserProfileVO> getProfile(){
        UserProfileVO userProfileVO = userService.getProfile();
        return Result.success(userProfileVO);
    }
    @PutMapping("/profile")
    public Result updateProfile(@RequestBody UserChangeVO userChangeVO){
        userService.changeProfile(userChangeVO);
        return Result.success();
    }
    @PutMapping("/profile/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO){
        userService.updatePassword(updatePasswordDTO);
        return Result.success();
    }
    @PutMapping("profile/updateEmail")
    public Result updateEmail(@RequestBody UpdateEmailDTO updateEmailDTO){
        userService.updateEmail(updateEmailDTO);
        return Result.success();
    }
}

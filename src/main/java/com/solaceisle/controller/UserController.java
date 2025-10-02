package com.solaceisle.controller;

import com.solaceisle.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/settings")
    public Result getHabits(){
        return Result.success("用户设置");
    }
}

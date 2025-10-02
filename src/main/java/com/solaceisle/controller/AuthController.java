package com.solaceisle.controller;

import com.solaceisle.pojo.dto.LoginDTO;
import com.solaceisle.result.Result;
import com.solaceisle.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        String jwt=authService.login(loginDTO);
        return Result.success(jwt);
    }
}

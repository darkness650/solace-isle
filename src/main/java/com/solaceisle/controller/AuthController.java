package com.solaceisle.controller;

import com.solaceisle.pojo.dto.LoginDTO;
import com.solaceisle.result.Result;
import com.solaceisle.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Tag(name="授权接口")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(description = "登录接口")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        String jwt=authService.login(loginDTO);
        return Result.success(jwt);
    }
}

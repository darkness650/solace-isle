package com.solaceisle.controller;

import com.solaceisle.pojo.dto.FindPasswordDTO;
import com.solaceisle.pojo.dto.LoginDTO;
import com.solaceisle.pojo.dto.RegisteDTO;
import com.solaceisle.result.Result;
import com.solaceisle.service.AuthService;
import com.solaceisle.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Tag(name="授权接口")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Operation(description = "登录接口")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        String jwt=authService.login(loginDTO);
        return Result.success(jwt);
    }

    @GetMapping("/sendCode")
    public Result sendCode(@RequestParam String email) {
        authService.sendCode(email);
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisteDTO registeDTO) {
        authService.register(registeDTO);
        return Result.success();
    }
    @PostMapping("/findPassword")
    public Result findPassword(@RequestBody FindPasswordDTO findPasswordDTO) {
        authService.findPassword(findPasswordDTO);
        return Result.success();
    }
}

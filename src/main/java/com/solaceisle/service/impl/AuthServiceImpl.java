package com.solaceisle.service.impl;

import com.solaceisle.constant.AuthConstant;
import com.solaceisle.exception.IllegalLoginMessageException;
import com.solaceisle.exception.IllegalRegisteMsgException;
import com.solaceisle.mapper.AuthMapper;
import com.solaceisle.pojo.dto.FindPasswordDTO;
import com.solaceisle.pojo.dto.LoginDTO;
import com.solaceisle.pojo.dto.RegisteDTO;
import com.solaceisle.properties.JwtProperties;
import com.solaceisle.service.AuthService;
import com.solaceisle.service.EmailService;
import com.solaceisle.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;
    private final JwtProperties jwtProperties;
    private final RedisTemplate<String,String> redisTemplate;
    private final EmailService emailService;
    @Override
    public String login(LoginDTO loginDTO) {
        String account=loginDTO.getAccount();
        String password=loginDTO.getPassword();
        if(account==null || password==null) {
            throw new IllegalLoginMessageException(AuthConstant.ILLEGAL_ACCOUNT);
        }
        String id=null;
        if(account.contains("@"))
        {
            id= authMapper.loginByEmail(account,password);
            if(id==null)
                throw new IllegalLoginMessageException(AuthConstant.ACCOUNT_PASSWARD_ERROR);
        }
        else
        {
            id= authMapper.loginByUsername(account,password);
            if(id==null)
                throw new IllegalLoginMessageException(AuthConstant.ACCOUNT_PASSWARD_ERROR);
        }
        if(id==null) {
            throw new RuntimeException("Invalid credentials");
        }
        UUID sessionId= UUID.randomUUID();
        Map<String,Object> claims= Map.of("id",id,"sessionId",sessionId.toString());
        String jwt= JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claims);
        redisTemplate.opsForValue().set(id,sessionId.toString(),jwtProperties.getUserTtl());
        return jwt;
    }

    @Override
    public void sendCode(String email) {
        String code= UUID.randomUUID().toString().substring(0,6);
        emailService.sendTextEmail(email,"SolaceIsle验证码","您的验证码是："+code+"，请勿泄露给他人,验证码在"+jwtProperties.getUserTtl()/60+"分钟内有效");
        redisTemplate.opsForValue().set(email,code,jwtProperties.getUserTtl());

    }

    @Override
    public void register(RegisteDTO registeDTO) {
        String code=redisTemplate.opsForValue().get(registeDTO.getEmail());
        if(code==null || !code.equals(registeDTO.getCode()))
        {
           // log.info("验证码校验失败，code:{},realcode:{}",registeDTO.getCode(),code);
            throw new IllegalRegisteMsgException(AuthConstant.ILLEGAL_CODE);
        }
        log.info("验证码校验通过");
        redisTemplate.delete(registeDTO.getEmail());
        authMapper.register(registeDTO);
    }

    @Override
    public void findPassword(FindPasswordDTO findPasswordDTO) {
        String code=redisTemplate.opsForValue().get(findPasswordDTO.getEmail());
        if(code==null || !code.equals(findPasswordDTO.getCode()))
        {
            // log.info("验证码校验失败，code:{},realcode:{}",registeDTO.getCode(),code);
            throw new IllegalRegisteMsgException(AuthConstant.ILLEGAL_CODE);
        }
        log.info("验证码校验通过");
        redisTemplate.delete(findPasswordDTO.getEmail());
        authMapper.findPassword(findPasswordDTO);
    }


}

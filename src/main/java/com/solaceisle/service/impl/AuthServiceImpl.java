package com.solaceisle.service.impl;

import com.solaceisle.constant.AuthConstant;
import com.solaceisle.exception.IllegalLoginMessageException;
import com.solaceisle.mapper.AuthMapper;
import com.solaceisle.pojo.dto.LoginDTO;
import com.solaceisle.properties.JwtProperties;
import com.solaceisle.service.AuthService;
import com.solaceisle.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;
    private final JwtProperties jwtProperties;
    private final RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(LoginDTO loginDTO) {
        String account=loginDTO.getAccount();
        String password=loginDTO.getPassword();
        if(account==null || password==null) {
            throw new IllegalLoginMessageException(AuthConstant.ILLGAL_ACCOUNT);
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
        redisTemplate.opsForValue().set(id,sessionId.toString());
        return jwt;
    }
}

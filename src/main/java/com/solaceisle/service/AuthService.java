package com.solaceisle.service;

import com.solaceisle.pojo.dto.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
}

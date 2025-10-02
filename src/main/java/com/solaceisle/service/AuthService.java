package com.solaceisle.service;

import com.solaceisle.pojo.dto.FindPasswordDTO;
import com.solaceisle.pojo.dto.LoginDTO;
import com.solaceisle.pojo.dto.RegisteDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);


    void sendCode(String email);


    void register(RegisteDTO registeDTO);

    void findPassword(FindPasswordDTO findPasswordDTO);
}

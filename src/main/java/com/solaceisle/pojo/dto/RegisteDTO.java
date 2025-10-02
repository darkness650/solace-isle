package com.solaceisle.pojo.dto;

import lombok.Data;

@Data
public class RegisteDTO {
    private String studentId;
    private String email;
    private String nickname;
    private String password;
    private String code;
}

package com.solaceisle.pojo.dto;

import lombok.Data;

@Data
public class FindPasswordDTO {
    private String email;
    private String password;
    private String code;
}

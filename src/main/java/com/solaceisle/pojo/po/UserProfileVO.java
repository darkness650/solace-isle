package com.solaceisle.pojo.po;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserProfileVO {

    private String studentId;

    private String email;

    private String nickname;

    private String avatar;

    private String motto;

}

package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Table(name = "user_safe_space_liked")
public class UserSafeSpaceLiked {

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "safe_space_id")
    private Integer safeSpaceId;


}

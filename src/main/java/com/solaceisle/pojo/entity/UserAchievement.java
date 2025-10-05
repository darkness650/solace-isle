package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Table(name = "user_achievement")
public class UserAchievement {

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "achievement_id")
    private String achievementId;

    @Column(name = "finish_time")
    private String finishTime;

    @Column(name = "progress")
    private Integer progress;



}

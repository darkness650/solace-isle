package com.solaceisle.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "user_achievement")
public class UserAchievement {

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "achievement_id")
    private String achievementId;

    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    @Column(name = "progress")
    private Integer progress;



}

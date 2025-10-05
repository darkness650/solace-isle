package com.solaceisle.pojo.vo;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AchievementsVO {

    private String name;

    private String description;

    private String icon;

    private LocalDateTime achievedAt;
}

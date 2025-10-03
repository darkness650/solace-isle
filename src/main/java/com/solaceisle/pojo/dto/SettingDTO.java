package com.solaceisle.pojo.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SettingDTO {
    @Column(name = "share_statistics")
    private boolean shareStatistics;

    @Column(name = "record_remind")
    private boolean recordRemind;

    @Column(name = "exercise_notice")
    private boolean exerciseNotice;

    private String studentId;
}

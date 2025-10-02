package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "setting")
public class Setting {

    @Id
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "share_statistics")
    private String shareStatistics;

    @Column(name = "record_remind")
    private String recordRemind;

    @Column(name = "exercise_notice")
    private String exerciseNotice;


}

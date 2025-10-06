package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Builder;
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
    private Boolean shareStatistics;

    @Column(name = "record_remind")
    private Boolean recordRemind;

    @Column(name = "exercise_notice")
    private Boolean exerciseNotice;


}

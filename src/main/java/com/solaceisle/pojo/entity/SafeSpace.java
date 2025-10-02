package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "safe_space")
public class SafeSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "emoji")
    private String emoji;

    @Column(name = "text")
    private String text;

    @Column(name = "like")
    private Integer like;

    @Column(name = "create_time")
    private String createTime;



}

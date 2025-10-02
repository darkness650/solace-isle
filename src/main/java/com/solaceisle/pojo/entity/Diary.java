package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "diary")
@Data
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "emoji")
    private String emoji;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "text")
    private String text;

    @Column(name = "image")
    private String image;

    @Column(name = "tags")
    private String tags;

    @Column(name = "create_time")
    private String createTime;


}

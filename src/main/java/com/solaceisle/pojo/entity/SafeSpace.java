package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "create_time")
    private LocalDateTime createTime;



}

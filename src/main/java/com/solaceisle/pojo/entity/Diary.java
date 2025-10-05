package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "diary")
@Data
@Builder
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private LocalDate createTime;

    @Column(name ="consecutive_days")
    private int consecutiveDays;

    /**
     * 分数，0~5分
     */
    @Column(name = "score")
    private int score;

    public Diary() {

    }

    public Diary(Long id, String studentId, String emoji, String emotion, String text, String image, String tags, LocalDate createTime, int consecutiveDays, int score) {
        this.id = id;
        this.studentId = studentId;
        this.emoji = emoji;
        this.emotion = emotion;
        this.text = text;
        this.image = image;
        this.tags = tags;
        this.createTime = createTime;
        this.consecutiveDays = consecutiveDays;
        this.score = score;
    }
}

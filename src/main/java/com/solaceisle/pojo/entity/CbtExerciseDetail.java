package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "cbt_exercise_detail")
public class CbtExerciseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cbt_exercise_id")
    private Integer cbtExerciseId;

    @Column(name = "title")
    private String title;

    @Column(name = "prompt")
    private String prompt;

    @Column(name = "type")
    private String type;

    @Column(name = "options")
    private String options;

    @Column(name = "placeholder")
    private String placeholder;

    @Column(name = "support")
    private String support;

    @Column(name = "against")
    private String against;



}

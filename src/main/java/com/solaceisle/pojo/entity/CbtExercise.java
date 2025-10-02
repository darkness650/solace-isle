package com.solaceisle.pojo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "cbt_exercise")
public class CbtExercise {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Integer difficulty;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String durationLabel;

    @Column
    private String tags;

    @Column
    private String coverColor;



}

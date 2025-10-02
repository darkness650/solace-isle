package com.solaceisle.pojo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class CbtExercise {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer difficulty;

    private String title;

    private String description;

    private String durationLabel;

    private String tags;

    private String coverColor;



}

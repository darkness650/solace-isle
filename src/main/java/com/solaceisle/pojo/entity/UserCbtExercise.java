package com.solaceisle.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Table(name = "user_cbt_exercise")
public class UserCbtExercise {

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "cbt_exercise_id")
    private Integer cbtExerciseId;

    @Column(name = "finish_time")
    private String finishTime;



}

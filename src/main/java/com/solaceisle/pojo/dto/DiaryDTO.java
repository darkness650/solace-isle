package com.solaceisle.pojo.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DiaryDTO {


    @Column(name = "emoji")
    private String emoji;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "text")
    private String text;

    @Column(name = "image")
    private String image;

    @Column(name = "tags")
    private List<String> tags;

}

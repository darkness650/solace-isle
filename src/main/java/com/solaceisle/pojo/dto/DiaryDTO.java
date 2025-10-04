package com.solaceisle.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class DiaryDTO {

    private String moodEmoji;

    private String moodLabel;

    private String content;

    private String image;

    private List<String> tags;

}

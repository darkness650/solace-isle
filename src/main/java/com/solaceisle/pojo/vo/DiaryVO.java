package com.solaceisle.pojo.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class DiaryVO {

    private String emoji;

    private String emotion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    private String text;

    private String image;

    private List<String> tags;
}

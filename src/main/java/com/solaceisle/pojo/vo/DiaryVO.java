package com.solaceisle.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DiaryVO {

    private String moodEmoji;

    private String moodLabel;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String content;

    private String image;

    private List<String> tags;
}

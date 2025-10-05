package com.solaceisle.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SafeSpacePageVO {
    private Long id;

    private String studentId;

    private String emoji;

    private String text;

    private Integer like;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datetime;

    private boolean liked;
}

package com.solaceisle.pojo.vo;

import lombok.Data;

@Data
public class SafeSpacePageVO {
    private Long id;

    private String studentId;

    private String emoji;

    private String text;

    private Integer like;

    private String createTime;

    private boolean isliked;
}

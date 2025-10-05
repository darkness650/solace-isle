package com.solaceisle.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class CBTVO {
    private Long id;

    private Integer difficulty;

    private String title;

    private String description;

    private String durationLabel;

    private List<String> tags;

    private String coverColor;

    private boolean finished;
}

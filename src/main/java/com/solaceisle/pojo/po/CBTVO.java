package com.solaceisle.pojo.po;

import lombok.Data;

@Data
public class CBTVO {
    private Long id;

    private Integer difficulty;

    private String title;

    private String description;

    private String durationLabel;

    private String tags;

    private String coverColor;

    private boolean finished;
}

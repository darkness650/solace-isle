package com.solaceisle.pojo.entity;

import com.solaceisle.constant.DiaryConstant;
import lombok.Data;

@Data
public class Track {
    private String day= DiaryConstant.UnRecord;
    private Integer score= DiaryConstant.DEFAULT_SCORE;
    private String label= DiaryConstant.UnRecord;
}

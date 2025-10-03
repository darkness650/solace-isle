package com.solaceisle.pojo.entity;

import com.solaceisle.constant.DiaryConstant;
import lombok.Data;

@Data
public class Track {
    private String day= DiaryConstant.UnRecord;
    private String score= DiaryConstant.UnRecord;
    private String label= DiaryConstant.UnRecord;
}

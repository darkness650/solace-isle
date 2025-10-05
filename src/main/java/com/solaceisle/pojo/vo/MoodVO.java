package com.solaceisle.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoodVO {
    private String emoji;
    private String label;
    private String description;
}

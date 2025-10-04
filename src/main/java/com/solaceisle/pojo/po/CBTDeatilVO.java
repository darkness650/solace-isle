package com.solaceisle.pojo.po;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CBTDeatilVO {
    private String type;

    private String title;

    private String prompt;

    private List<String> options;

    private String placeholder;

    private Map<String,String> placeholders;

}

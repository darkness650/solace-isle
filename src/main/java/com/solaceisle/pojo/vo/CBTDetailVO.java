package com.solaceisle.pojo.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CBTDetailVO {

    private String type;

    private String title;

    private String prompt;

    private List<String> options;

    private String placeholder;

    private Placeholders placeholders;

    @Data
    public static class Placeholders{

        private String support;

        private String against;
    }

}

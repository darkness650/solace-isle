package com.solaceisle.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CBTDTO {

    private List<String> options;

    private String text;

    private Evidence evidence;

    @Data
    public static class Evidence {

        private String support;

        private String against;
    }
}

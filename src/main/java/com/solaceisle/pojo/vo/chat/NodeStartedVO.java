package com.solaceisle.pojo.vo.chat;

import lombok.Data;

@Data
public class NodeStartedVO {

    private String taskId;

    private String event;

    private Data data;

    @lombok.Data
    public static class Data {
        private String nodeType;
        private String title;
    }
}

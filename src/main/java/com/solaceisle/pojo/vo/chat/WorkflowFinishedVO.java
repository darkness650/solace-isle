package com.solaceisle.pojo.vo.chat;

import lombok.Data;

@Data
public class WorkflowFinishedVO {

    private String taskId;

    private String messageId;

    private String event;
}

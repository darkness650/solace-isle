package com.solaceisle.pojo.vo.chat;

import lombok.Data;

@Data
public class WorkflowStartedVO {

    private String taskId;

    private String messageId;

    private String event;
}

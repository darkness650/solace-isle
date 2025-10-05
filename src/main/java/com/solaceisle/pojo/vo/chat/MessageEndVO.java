package com.solaceisle.pojo.vo.chat;

import lombok.Data;

@Data
public class MessageEndVO {

    private String taskId;

    private String messageId;

    private String conversationId;

    private String event;
}

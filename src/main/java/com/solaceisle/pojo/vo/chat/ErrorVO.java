package com.solaceisle.pojo.vo.chat;

import lombok.Data;

@Data
public class ErrorVO{

    private String taskId;

    private String event;

    private String messageId;

    private String status;

    private String code;

    private String message;
}

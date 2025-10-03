package com.solaceisle.pojo.vo.chat;

import lombok.Data;

@Data
public class MessageVO{

    private String taskId;

    private String event;

    private String answer;
}

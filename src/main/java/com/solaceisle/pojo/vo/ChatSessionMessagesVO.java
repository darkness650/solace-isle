package com.solaceisle.pojo.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatSessionMessagesVO {

    private Boolean hasMore;

    private Integer limit;

    private List<MessageVO> messages;

    @Data
    public static class MessageVO{

        private String query;

        private String answer;

        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        private LocalDateTime datetime;
    }
}

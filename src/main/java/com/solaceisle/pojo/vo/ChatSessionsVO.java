package com.solaceisle.pojo.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatSessionsVO {

    private Boolean hasMore;

    private Integer limit;

    private List<SessionVO> sessions;

    @Data
    public static class SessionVO{

        private String id;

        private String title;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime datetime;
    }
}

package com.solaceisle.pojo.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebsocketVO {

    private String type;

    private Object content;

    public static WebsocketVO remind(String content) {
        return WebsocketVO.builder()
                .type("remind")
                .content(content)
                .build();
    }

    public static WebsocketVO achievement(AchievementVO achievementVO) {
        return WebsocketVO.builder()
                .type("achievement")
                .content(achievementVO)
                .build();
    }
}

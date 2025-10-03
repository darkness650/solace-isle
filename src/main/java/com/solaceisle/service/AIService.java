package com.solaceisle.service;

import org.springframework.ai.tool.annotation.Tool;

public interface AIService {

    @Tool(description = "获取心理健康中心热线电话")
    String getHotline();
}

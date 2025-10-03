package com.solaceisle.config;

import com.solaceisle.service.AIService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class McpToolConfiguration {

    @Bean
    public List<ToolCallback> mcpTools(AIService aiService) {
        return List.of(ToolCallbacks.from(aiService));
    }
}

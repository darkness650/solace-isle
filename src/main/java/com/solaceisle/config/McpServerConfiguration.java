package com.solaceisle.config;

import com.solaceisle.service.AIService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfiguration {

    @Bean
    public ToolCallbackProvider mcpTools(AIService aiService) {
        return MethodToolCallbackProvider.builder().toolObjects(aiService).build();
    }
}

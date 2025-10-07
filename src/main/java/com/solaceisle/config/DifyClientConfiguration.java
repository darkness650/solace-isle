package com.solaceisle.config;

import com.solaceisle.properties.DifyProperties;
import io.github.imfangs.dify.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DifyClientConfiguration {

    @Bean
    public DifyChatClient tagGeneratorClient(DifyProperties difyProperties) {
        return DifyClientFactory.createChatClient(
                difyProperties.getUrl(), difyProperties.getApiKey().getTagGenerator());
    }

    @Bean
    public DifyChatflowClient chatPartnerClient(DifyProperties difyProperties) {
        return DifyClientFactory.createChatWorkflowClient(
                difyProperties.getUrl(), difyProperties.getApiKey().getChatPartner());
    }

    @Bean
    public DifyCompletionClient diaryEmotionRankClient(DifyProperties difyProperties) {
        return DifyClientFactory.createCompletionClient(
                difyProperties.getUrl(), difyProperties.getApiKey().getDiaryEmotionRank());
    }

    @Bean
    public DifyWorkflowClient suggestionGeneratorClient(DifyProperties difyProperties) {
        return DifyClientFactory.createWorkflowClient(
                difyProperties.getUrl(), difyProperties.getApiKey().getSuggestionGenerator());
    }

    @Bean
    public DifyChatClient cbtAnalyzerClient(DifyProperties difyProperties) {
        return DifyClientFactory.createChatClient(
                difyProperties.getUrl(), difyProperties.getApiKey().getCbtAnalyzer());
    }
}

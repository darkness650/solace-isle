package com.solaceisle.config;

import com.solaceisle.properties.DifyProperties;
import io.github.imfangs.dify.client.DifyChatClient;
import io.github.imfangs.dify.client.DifyChatflowClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.DifyCompletionClient;
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
}

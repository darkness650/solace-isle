package com.solaceisle.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "solace.dify")
@Data
public class DifyProperties {

    private String url;

    private DifyApiKeyProperties apiKey;
}

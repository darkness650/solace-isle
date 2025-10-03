package com.solaceisle.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "solace.dify.api-key")
@Data
public class DifyApiKeyProperties {

    private String tagGenerator;

    private String chatPartner;
}

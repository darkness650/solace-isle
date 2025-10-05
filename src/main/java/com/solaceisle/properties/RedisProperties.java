package com.solaceisle.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "solace.redis")
@Data
public class RedisProperties {

    /**
     * 过期时间，单位为秒
     */
    private Integer expirationTime;
}

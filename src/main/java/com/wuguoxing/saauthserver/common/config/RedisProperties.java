package com.wuguoxing.saauthserver.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private String streamVideoParse;

    private String streamVideoConvert;

    private String streamVideoConclusion;

    private String streamConsumerGroup;
}

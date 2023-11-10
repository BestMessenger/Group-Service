package com.messenger.groupservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "storage-property")
public class PropertyConfig {
    private String storageDirectory;
}

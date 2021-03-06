package com.evbx.product.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for 'Api Client'
 */
@Configuration
@Data
@ConfigurationProperties
public class ServiceConfig {

    @Value("${service.resource.baseUrl}")
    private String baseUrl;
    @Value("${service.resource.username}")
    private String userName;
    @Value("${service.resource.password}")
    private String password;

}

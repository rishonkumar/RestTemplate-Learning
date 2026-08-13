package com.rishon.orderintegration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.rishon.orderintegration.interceptor.LoggingInterceptor;

/**
 * Spring configuration for RestTemplate, HTTP request factory, and timeouts.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new LoggingInterceptor());
        return restTemplate;
    }
}

package com.liceman.application.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {

    // Always need this inyection when restTemplate has to be used
    @Bean
    public RestTemplate restTemplate() {
        // Uses BufferingClientHttpRequestFactory to let multiple reading of the request body
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()));

        // Add the interceptor to log requests and responses
        restTemplate.getInterceptors().add(new LoggingRequestResponseInterceptor());

        return restTemplate;
    }
}
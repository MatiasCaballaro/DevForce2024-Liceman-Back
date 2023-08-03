package com.liceman.application.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {

    // Siempre que vaya a usar el restTemplate tengo que inyectarle este bean si quiero la respuesta del LoggingRequestResponseInterceptor
    @Bean
    public RestTemplate restTemplate() {
        // Utiliza BufferingClientHttpRequestFactory para permitir la lectura múltiple del cuerpo de solicitud
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()));

        // Agregar el interceptor para logging de solicitudes y respuestas
        restTemplate.getInterceptors().add(new LoggingRequestResponseInterceptor());

        return restTemplate;
    }
}
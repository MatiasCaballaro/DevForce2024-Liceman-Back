package com.liceman.application.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoggingRequestResponseInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingRequestResponseInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        logger.info(new StringBuilder()
                .append("\n\t===========================request begin============================================")
                .append("\n\tURI          : ").append(request.getURI())
                .append("\n\tMethod       : ").append(request.getMethod())
                .append("\n\tHeaders      : ").append(request.getHeaders())
                .append("\n\tRequest body : ").append(new String(body, StandardCharsets.UTF_8))
                .append("\n\t===========================request end==============================================")
        .toString());
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }

            logger.info(new StringBuilder()
                    .append("\n\t============================response begin==========================================")
                    .append("\n\tStatus code  : ").append(response.getStatusCode())
                    .append("\n\tStatus text  : ").append(response.getStatusText())
                    .append("\n\tHeaders      : ").append(response.getHeaders())
                    .append("\n\tResponse body: ").append(responseBody)
                    .append("\n\t============================response end============================================")
                    .toString());
        }
    }

}

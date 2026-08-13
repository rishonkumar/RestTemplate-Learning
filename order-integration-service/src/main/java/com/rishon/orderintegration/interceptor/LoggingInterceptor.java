package com.rishon.orderintegration.interceptor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * RestTemplate ClientHttpRequestInterceptor for request/response logging (to be implemented later).
 */
@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor{

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

                // Before the request goes to the downstream API
                log.info("Outgoing Request: {} {}", request.getMethod(), request.getURI());

                // Actually execute the HTTP request
                ClientHttpResponse response = execution.execute(request, body);

                // After the downstream API responds

                log.info("Downstream response status: {}",

                response.getStatusCode());
                return response;

    }
}

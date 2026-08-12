package com.rishon.orderintegration.auth;

import com.rishon.orderintegration.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Authenticates with the third-party system (OAuth token acquisition).
 */
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final RestTemplate restTemplate;

    public String fetchAccessToken() {
        String url = "http://localhost:8081/oauth/token";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String>body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", "order-client");
        body.add("client_secret", "secret");
        body.add("scope", "orders");

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, headers);
        ResponseEntity<TokenResponse> response =
                restTemplate.exchange(url, HttpMethod.POST, entity,TokenResponse.class);

        return response.getBody().getAccessToken();

    }

}

package com.rishon.fastship.service;

import com.rishon.fastship.dto.response.TokenResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuthService {

    private static final String EXPECTED_GRANT_TYPE = "client_credentials";
    private static final String EXPECTED_CLIENT_ID = "order-client";
    private static final String EXPECTED_CLIENT_SECRET = "secret";
    private static final String EXPECTED_SCOPE = "orders";
    private static final String ACCESS_TOKEN = "fastship-access-token";

    public Optional<TokenResponse> issueToken(String grantType, String clientId, String clientSecret, String scope) {
        if (EXPECTED_GRANT_TYPE.equals(grantType)
                && EXPECTED_CLIENT_ID.equals(clientId)
                && EXPECTED_CLIENT_SECRET.equals(clientSecret)
                && EXPECTED_SCOPE.equals(scope)) {
            return Optional.of(new TokenResponse(ACCESS_TOKEN, "Bearer", 3600L));
        }
        return Optional.empty();
    }
}

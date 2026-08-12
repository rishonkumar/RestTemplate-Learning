package com.rishon.orderintegration.dto.response;

import lombok.Data;

/**
 * OAuth token response from the FastShip auth API.
 */
@Data
public class TokenResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

}

/*
{

    "access_token": "abc123",

    "token_type": "Bearer",

    "expires_in": 3600

}
 */
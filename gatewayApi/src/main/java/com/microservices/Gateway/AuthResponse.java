package com.microservices.Gateway;

import java.util.Collection;

public record AuthResponse
        (String userId, String accessToken,String phone, String refreshToken,
         Long expireAt, Collection<String> authList) {
}

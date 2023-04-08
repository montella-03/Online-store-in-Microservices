package com.microservices.OrderService.external.interceptor;

import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class RequestInterceptor implements feign.RequestInterceptor {
    @Autowired
    private OAuth2AuthorizedClientManager clientManager;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization","Bearer"+
                clientManager.authorize(OAuth2AuthorizeRequest.
                        withClientRegistrationId("internal-client")
                                .principal("internal")
                        .build())
                        .getAccessToken().getTokenValue());


    }
}

package com.productdock.library.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


@Component
public class JwtGatewayFilterFactory extends
        AbstractGatewayFilterFactory<Object> {

    private UserProfileTokenExchanger userProfileTokenExchanger;

    public JwtGatewayFilterFactory(UserProfileTokenExchanger userProfileTokenExchanger) {
        super(Object.class);
        this.userProfileTokenExchanger = userProfileTokenExchanger;
    }

    public GatewayFilter apply() {
        return this.apply((Object) null);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            var serverWebExchangeMono = exchange.getPrincipal()
                    .filter(OAuth2AuthenticationToken.class::isInstance)
                    .cast(OAuth2AuthenticationToken.class)
                    .flatMap(openId -> userProfileTokenExchanger.exchangeForUserProfileToken(getOpenIdTokenValue(openId)))
                    .map(userProfileJwt -> mutateRequestWithUserProfileToken(exchange, userProfileJwt))
                    .defaultIfEmpty(exchange);
            return serverWebExchangeMono.flatMap(chain::filter);
        };
    }

    private String getOpenIdTokenValue(OAuth2AuthenticationToken openIdToken) {
        var idToken = ((DefaultOidcUser) openIdToken.getPrincipal()).getIdToken();
        return idToken.getTokenValue();
    }

    private ServerWebExchange mutateRequestWithUserProfileToken(ServerWebExchange exchange, String userProfileJwt) {
        return exchange.mutate().request(
                r -> r.headers(headers -> headers.setBearerAuth(userProfileJwt))).build();
    }

}

package com.productdock.library.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class JwtGatewayFilterFactory extends
        AbstractGatewayFilterFactory<Object> {

    public JwtGatewayFilterFactory() {
        super(Object.class);
    }

    public GatewayFilter apply() {
        return this.apply((Object) null);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            Mono<ServerWebExchange> var10000 = exchange.getPrincipal()
                    .filter((principal) -> principal instanceof OAuth2AuthenticationToken).cast(OAuth2AuthenticationToken.class)
                    .map((authentication) -> withBearerAuth(exchange, authentication))
                    .defaultIfEmpty(exchange);
            return var10000.flatMap(chain::filter);
        };
    }

    private ServerWebExchange withBearerAuth(ServerWebExchange exchange, OAuth2AuthenticationToken authenticationToken) {
        var idToken = ((DefaultOidcUser) authenticationToken.getPrincipal()).getIdToken();
        return exchange.mutate().request((r) -> {
            r.headers((headers) -> {
                headers.setBearerAuth(idToken.getTokenValue());
            });
        }).build();
    }

}

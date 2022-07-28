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

    private TokenExchanger tokenExchanger;

    public JwtGatewayFilterFactory(TokenExchanger tokenExchanger) {
        super(Object.class);
        this.tokenExchanger = tokenExchanger;
    }

    public GatewayFilter apply() {
        return this.apply((Object) null);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            Mono<ServerWebExchange> var10000 = exchange.getPrincipal()
                    .filter((principal) -> principal instanceof OAuth2AuthenticationToken).cast(OAuth2AuthenticationToken.class)
                    .flatMap((openId) -> tokenExchanger.exchangeToken(getOpenIdTokenValue(openId)))
                    .map((jwtToken) -> withBearerAuth(exchange, jwtToken))
                    .defaultIfEmpty(exchange);
            return var10000.flatMap(chain::filter);
        };
    }

    private String getOpenIdTokenValue(OAuth2AuthenticationToken authenticationToken) {
        var idToken = ((DefaultOidcUser) authenticationToken.getPrincipal()).getIdToken();
        return idToken.getTokenValue();
    }

    private ServerWebExchange withBearerAuth(ServerWebExchange exchange, String jwtToken) {
        return exchange.mutate().request((r) -> {
            r.headers((headers) -> {
                headers.setBearerAuth(jwtToken);
            });
        }).build();
    }

}

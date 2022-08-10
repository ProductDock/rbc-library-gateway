package com.productdock.library.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.*;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Value("${cors.allowed.origins}")
    private String corsAllowedOrigins;

    @Value("${security.front-to-gateway.redirect-uri}")
    private String frontRedirectUri;

    @Autowired
    private ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        ServerAuthenticationSuccessHandler ss;
        return http
                .cors(withDefaults())
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeExchange()
                    .pathMatchers("/auth/**", "/oauth2/**").permitAll()
                    .anyExchange().authenticated().and()
                    .oauth2Login()
                        .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler(frontRedirectUri)).and()
                //configure endpoints respond with a 401 instead of redirecting to login, since it can't be shown when invoked from JavaScript
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout()
                .logoutUrl("/api/logout")
                .logoutHandler(logoutHandler())
                .logoutSuccessHandler(statusLogoutSuccessHandler())
                .and()
                .build();
    }

    private ServerLogoutHandler logoutHandler() {
        return new DelegatingServerLogoutHandler(new WebSessionServerLogoutHandler());
    }

    private ServerLogoutSuccessHandler statusLogoutSuccessHandler() {
        var success = new HttpStatusReturningServerLogoutSuccessHandler();
        return success;
//        var success = new RedirectServerLogoutSuccessHandler();
//        success.setLogoutSuccessUrl(URI.create(frontRedirectUri));
//        return success;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedOrigin(corsAllowedOrigins);
        configuration.addAllowedOrigin("http://localhost:8080");
        allowInvocationWithSessionCookie(configuration);
        configuration.setAllowedMethods(asList("GET", "POST", "OPTIONS", "PUT", "PATCH", "HEAD", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void allowInvocationWithSessionCookie(CorsConfiguration configuration) {
        configuration.setAllowCredentials(true);
    }
}

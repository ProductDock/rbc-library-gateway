package com.productdock.library.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

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
        return new HttpStatusReturningServerLogoutSuccessHandler();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedOrigin(corsAllowedOrigins);
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

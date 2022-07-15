package com.productdock.library.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import static java.util.Arrays.asList;
import static org.springframework.security.config.Customizer.withDefaults;

// @EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Value("${cors.allowed.origins}")
    private String corsAllowedOrigins;

//    @Bean
//    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http.csrf().disable()
//                .authorizeExchange()
//                .pathMatchers("/**").authenticated()
//                .anyExchange().authenticated()
//                .and().cors(withDefaults())
//                .oauth2ResourceServer()
//                .jwt();
//        return http.build();
//    }

    @Autowired
    private ReactiveClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private ReactiveOAuth2AuthorizedClientService oAuth2AuthorizedClient;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
       return http
                .csrf().disable()
                .authorizeExchange(exchange -> exchange.pathMatchers( "/login").permitAll()
                        .anyExchange().authenticated())
                .cors(withDefaults())
                .oauth2Login()
               .authorizedClientService(oAuth2AuthorizedClient)
               .clientRegistrationRepository(clientRegistrationRepository).and()
               .formLogin().and()
               .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler())).build();
    }

    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(this.clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
        return oidcLogoutSuccessHandler;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin(corsAllowedOrigins);
        configuration.setAllowedMethods(asList("GET", "POST", "OPTIONS", "PUT", "PATCH", "HEAD", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

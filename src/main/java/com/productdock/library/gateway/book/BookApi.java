package com.productdock.library.gateway.book;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/books")
public record BookApi(BookService bookService) {

    @GetMapping("/{bookId}")
    public JsonNode getBook(@PathVariable("bookId") String bookId,
                            OAuth2AuthenticationToken authenticationToken){
                            // @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient){
                            // OAuth2AccessToken accessToken) {
//        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("google").principal(authentication).build();
//        ReactiveOAuth2AuthorizedClientManager clientManager = clientManagerProvider.getIfAvailable();
//        System.out.println(clientManager == null);
//        var a = clientManager.authorize(request);
//        System.out.println(a.block().getAccessToken());
//        Mono var10000 = authentication..flatMap((authentication) -> {
//            return this.authorizedClient(exchange, authentication);
//        }).map(OAuth2AuthorizedClient::getAccessToken).map((token) -> {
//            return this.withBearerAuth(exchange, token);
//        })
    // @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {
        // registrationId of the Client that was authorized during the oauth2Login() flow
        // OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
//        System.out.println(authentication.getAuthorizedClientRegistrationId());
//        Mono<OAuth2AuthorizedClient> client =
//                clientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
//        var at = client.block().getAccessToken().getTokenValue();
        // OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        var usr = (DefaultOidcUser) authenticationToken.getPrincipal();
        // var t = usr.getIdToken();
        System.out.println(usr.getIdToken().getTokenValue());
        var jwtToken = "Bearer " + usr.getIdToken().getTokenValue(); //((Jwt) authentication.getCredentials()).getTokenValue();
        return bookService.getBookDetails(bookId, jwtToken);
    }
}

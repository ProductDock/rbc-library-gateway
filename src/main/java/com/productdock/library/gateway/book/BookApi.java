package com.productdock.library.gateway.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.productdock.library.gateway.config.UserProfileTokenExchanger;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public record BookApi(BookService bookService,
                      UserProfileTokenExchanger userProfilesClient) {

    @SneakyThrows
    @GetMapping("/{bookId}")
    public JsonNode getBook(@PathVariable("bookId") String bookId,
                            OAuth2AuthenticationToken authenticationToken) {
        var user = (DefaultOidcUser) authenticationToken.getPrincipal();
        var exchangedJwtToken = userProfilesClient.exchangeForUserProfileToken(user.getIdToken().getTokenValue()).toFuture().get();

        return bookService.getBookDetailsById(bookId, exchangedJwtToken);
    }

    @SneakyThrows
    @GetMapping
    public JsonNode getBookByTitleAndAuthor(@RequestParam String title, @RequestParam String author,
                                            OAuth2AuthenticationToken authenticationToken) {
        var user = (DefaultOidcUser) authenticationToken.getPrincipal();
        var exchangedJwtToken = userProfilesClient.exchangeForUserProfileToken(user.getIdToken().getTokenValue()).toFuture().get();

        return bookService.getBookDetailsByTitleAndAuthor(title, author, exchangedJwtToken);
    }
}

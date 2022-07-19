package com.productdock.library.gateway.book;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public record BookApi(BookService bookService) {

    @GetMapping("/{bookId}")
    public JsonNode getBook(@PathVariable("bookId") String bookId,
                            OAuth2AuthenticationToken authenticationToken){
        var user = (DefaultOidcUser) authenticationToken.getPrincipal();
        var jwtToken = "Bearer " + user.getIdToken().getTokenValue();
        return bookService.getBookDetails(bookId, jwtToken);
    }
}

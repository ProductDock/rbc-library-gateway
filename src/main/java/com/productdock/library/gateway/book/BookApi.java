package com.productdock.library.gateway.book;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public record BookApi(BookService bookService) {

    @GetMapping("/{bookId}")
    public JsonNode getBookById(@PathVariable("bookId") String bookId, Authentication authentication) {
        var jwtToken = "Bearer " + ((Jwt) authentication.getCredentials()).getTokenValue();
        return bookService.getBookDetailsById(bookId, jwtToken);
    }

    @GetMapping("/title-author")
    public JsonNode getBookByTitleAndAuthor(@RequestParam String title, @RequestParam String author, Authentication authentication) {
        var jwtToken = "Bearer " + ((Jwt) authentication.getCredentials()).getTokenValue();
        return bookService.getBookDetailsByTitleAndAuthor(title, author, jwtToken);
    }
}

package com.productdock.library.gateway.book;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public record BookApi(BookService bookService) {

    @GetMapping("/{bookId}")
    public BookDetailsDto getBook(@PathVariable("bookId") String bookId, Authentication authentication) {
        var jwtToken = "Bearer " + ((Jwt) authentication.getCredentials()).getTokenValue();
        return bookService.getBookDetails(bookId, jwtToken);
    }
}

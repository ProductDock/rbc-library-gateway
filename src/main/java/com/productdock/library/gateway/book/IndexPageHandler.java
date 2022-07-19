package com.productdock.library.gateway.book;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexPageHandler {

    private static final String STATIC_DIR = "/static";
    private Resource landingPage = new ClassPathResource(STATIC_DIR  + "/landing/landing.html");
    private Resource indexPage = new ClassPathResource(STATIC_DIR + "/" + "index.html");

    @GetMapping
    public Object getBookDetails(Authentication authentication) {
        if (authentication == null) return landingPage;
        return indexPage;
    }
}

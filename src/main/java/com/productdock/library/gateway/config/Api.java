package com.productdock.library.gateway.config;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class Api {

    @GetMapping
    public String test() {
        System.out.println("ss");
        return "test";
    }
}

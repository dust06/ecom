package com.ecom.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @RequestMapping("/auth")
    public Mono<String> fallback() {
        return Mono.just("Auth Service is currently unavailable. Please try again later.");
    }
}

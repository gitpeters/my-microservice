package com.peters.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {
    private Logger log = LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")
    //Implement retry feature
    //@Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
    //@CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
    //@RateLimiter(name = "default")
    @Bulkhead(name = "sample-api")
    public String sampleApi(){
        log.info("Sample Api call received");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("localhost:8080/some-dummy-api",
//                String.class);
//        return forEntity.getBody();
        return "sample api";
    }

    public String hardCodedResponse(Exception ex){
        return "fallback-response";
    }
}


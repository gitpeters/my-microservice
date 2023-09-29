package com.techiebros.limitsservice.controller;

import com.techiebros.limitsservice.configuration.LimitsConfig;
import com.techiebros.limitsservice.entity.Limits;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/limits")
@RequiredArgsConstructor
public class LimitsController {
    private final LimitsConfig config;
    @GetMapping
    public ResponseEntity<Limits> retrieveLimites(){
        return ResponseEntity.ok(new Limits(config.getMinimum(), config.getMaximum()));
    }
}

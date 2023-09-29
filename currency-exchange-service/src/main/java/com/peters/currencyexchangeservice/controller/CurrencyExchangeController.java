package com.peters.currencyexchangeservice.controller;

import com.peters.currencyexchangeservice.entity.CurrencyExchange;
import com.peters.currencyexchangeservice.repository.CurrencyExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency-exchange")
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final Environment environment;
    private final CurrencyExchangeRepository exchangeRepository;
    @GetMapping("/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
        CurrencyExchange currencyExchange = exchangeRepository.findByFromAndTo(from, to);
        if(currencyExchange==null){
            throw new RuntimeException("Unable to find data for "+from +" and "+to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}

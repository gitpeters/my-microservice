package com.peters.currencyexchangeservice.controller;

import com.peters.currencyexchangeservice.entity.CurrencyExchange;
import com.peters.currencyexchangeservice.repository.CurrencyExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency-exchange")
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {
    private final Environment environment;
    private final CurrencyExchangeRepository exchangeRepository;
    @GetMapping("/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
        log.info("retrieveExchangeValue from {} to {}", from, to);
        CurrencyExchange currencyExchange = exchangeRepository.findByFromAndTo(from, to);
        if(currencyExchange==null){
            throw new RuntimeException("Unable to find data for "+from +" and "+to);
        }
        String port = environment.getProperty("local.server.port");
        //CHANGE-KUBERNETES
        String host = environment.getProperty("HOSTNAME");
        String version = "v2";

        currencyExchange.setEnvironment(port + " " + version + " " + host);
        return currencyExchange;
    }
}

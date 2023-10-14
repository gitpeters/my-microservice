package com.peters.currencyconversionservice.controller;

import com.peters.currencyconversionservice.model.CurrencyConversion;
import com.peters.currencyconversionservice.proxy.CurrencyExchangeProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency-conversion")
public class CurrencyConversionController {
    private final CurrencyExchangeProxy proxy;
    private final RestTemplate restTemplate;

    @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariables);

        CurrencyConversion retrievedCurrencyConversion = responseEntity.getBody();

        CurrencyConversion currencyConversion = new CurrencyConversion(
                retrievedCurrencyConversion.getId(), from, to,
                quantity,
                retrievedCurrencyConversion.getConversionMultiple(),
                quantity.multiply(retrievedCurrencyConversion.getConversionMultiple()),
                retrievedCurrencyConversion.getEnvironment()+" - restTemplate"
        );
        return currencyConversion;
    }
    @GetMapping("/feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        CurrencyConversion retrievedCurrencyConversion = proxy.retrieveExchangeValue(from,to);

        CurrencyConversion currencyConversion = new CurrencyConversion(
                retrievedCurrencyConversion.getId(), from, to,
                quantity,
                retrievedCurrencyConversion.getConversionMultiple(),
                quantity.multiply(retrievedCurrencyConversion.getConversionMultiple()),
                retrievedCurrencyConversion.getEnvironment()+" - feign"
        );
        return currencyConversion;
    }
}

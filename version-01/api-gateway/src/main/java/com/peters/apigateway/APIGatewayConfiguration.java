package com.peters.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){

        return builder.routes()
                .route(p -> p.path("/get")
                .filters(f-> f
                        .addRequestHeader("MyHeader", "MyURI")
                        .addRequestParameter("MyParams", "ParamValue")
                )
                .uri("http://httpbin.org:80"))
                .route(p->p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p->p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion/feign/**")
                        .uri("lb://currency-conversion"))
                // if you want to reroute an uri to point to another path
                .route(p->p.path("/currency-conversion-new/**")
                        .filters(f-> f.rewritePath(
                                // the path you want to reroute
                                "/currency-conversion-new/(?<segment>.*)",
                                // the path you want to reroute to
                                "/currency-conversion/feign/${segment}"
                        ))
                        .uri("lb://currency-conversion"))

                .build();
    }
}

package com.assignment.aggregation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

@SpringBootApplication
public class AggregationServiceApplication {
    @Bean
    public ResourceResolver resourceResolver() {
        return new WebJarsResourceResolver();
    }
    public static void main(String[] args) {
        SpringApplication.run(AggregationServiceApplication.class, args);
    }

}

package com.assignment.aggregation.controller;


import com.assignment.aggregation.common.ServiceUrlBuilder;
import com.assignment.aggregation.model.ServiceContext;
import com.assignment.aggregation.service.ServiceExecutor;
import com.assignment.aggregation.transformer.ServiceContextToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class AggregationController {

    @Autowired
    ServiceUrlBuilder serviceUrlBuilder;

    @Autowired
    ServiceExecutor serviceExecutor;

    @Autowired
    ServiceContextToJson serviceContextToJson;

    @GetMapping(path = "/aggregation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggregate(@RequestParam(name = "shipmentsOrderNumbers", required = false) String shipmentsOrderNumbers,
                                            @RequestParam(name = "trackOrderNumbers", required = false) String trackOrderNumbers,
                                            @RequestParam(name = "pricingCountryCodes", required = false) String pricingCountryCodes) {
        List<ServiceContext> serviceContext = new ArrayList<>();
        serviceContext.addAll(serviceUrlBuilder.buildShipmentsUrl(shipmentsOrderNumbers));
        serviceContext.addAll(serviceUrlBuilder.buildTrackUrl(trackOrderNumbers));
        serviceContext.addAll(serviceUrlBuilder.buildPricingUrl(pricingCountryCodes));

        serviceContext = serviceContext.parallelStream()
                .map(context -> {
                    context.setResponse(serviceExecutor.getResponse(context.getUrl()));
                    return context;
                })
                .filter(v -> Objects.nonNull(v.getResponse()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(serviceContextToJson.transform(serviceContext));
    }
}

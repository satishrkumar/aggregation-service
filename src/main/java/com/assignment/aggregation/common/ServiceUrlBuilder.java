package com.assignment.aggregation.common;

import com.assignment.aggregation.model.ServiceContext;
import com.assignment.aggregation.model.ServiceType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ServiceUrlBuilder {

    public static final String COMMA = ",";

    @Value("${pricing}")
    private String pricing;
    @Value("${track}")
    private String track;
    @Value("${shipments}")
    private String shipments;

    public List<ServiceContext> buildPricingUrl(String commaSeparatedCountryCode) {

        return Optional.ofNullable(commaSeparatedCountryCode)
                .map(codes -> Arrays.stream(commaSeparatedCountryCode.split(COMMA))
                        .map(v -> v.trim())
                        .map(v -> ServiceContext.builder()
                                .url(String.format(pricing, v))
                                .originalValue(v)
                                .serviceType(ServiceType.PRICING)
                                .build())
                        .collect(Collectors.toList())
                ).orElse(new ArrayList<>());
    }

    public List<ServiceContext> buildTrackUrl(String commaSeparatedTrackOrderNumbers) {
        return Optional.ofNullable(commaSeparatedTrackOrderNumbers)
                .map(codes -> Arrays.stream(commaSeparatedTrackOrderNumbers.split(COMMA))
                        .map(v -> v.trim())
                        .map(v -> ServiceContext.builder()
                                .url(String.format(track, v))
                                .originalValue(v)
                                .serviceType(ServiceType.TRACK)
                                .build())
                        .collect(Collectors.toList())
                ).orElse(new ArrayList<>());
    }

    public List<ServiceContext> buildShipmentsUrl(String commaSeparatedShipmentsOrderNumbers) {
        return Optional.ofNullable(commaSeparatedShipmentsOrderNumbers)
                .map(codes -> Arrays.stream(commaSeparatedShipmentsOrderNumbers.split(COMMA))
                        .map(v -> v.trim())
                        .map(v -> ServiceContext.builder()
                                .url(String.format(shipments, v))
                                .originalValue(v)
                                .serviceType(ServiceType.SHIPMENTS)
                                .build())
                        .collect(Collectors.toList())
                ).orElse(new ArrayList<>());
    }
}

package com.assignment.aggregation.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@Data
@EqualsAndHashCode
@ToString
public class ServiceContext {
    private String url;
    private String originalValue;
    private String response;
    private ServiceType serviceType;
}

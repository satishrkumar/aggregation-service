package com.assignment.aggregation.model;


public enum ServiceType {
    PRICING("pricing"),
    SHIPMENTS("shipments"),
    TRACK("track");

    private final String name;

    ServiceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

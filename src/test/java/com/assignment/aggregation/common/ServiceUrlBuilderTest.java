package com.assignment.aggregation.common;

import com.assignment.aggregation.model.ServiceContext;
import com.assignment.aggregation.model.ServiceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ServiceUrlBuilderTest {

    @Autowired
    private ServiceUrlBuilder objectUnderTest;


    @Test
    void buildPricingUrlWhenCodeNull() {
        List<ServiceContext> expected = new ArrayList<>();
        List<ServiceContext> actual = objectUnderTest.buildPricingUrl(null);
        assertEquals(expected, actual);
    }

    @Test
    void buildPricingUrlWhenOneCodeProvided() {
        List<ServiceContext> expected = Arrays.asList(
                ServiceContext.builder()
                        .serviceType(ServiceType.PRICING)
                        .url("http://127.0.0.1:4000/pricing?countryCode=one")
                        .originalValue("one")
                        .build());
        List<ServiceContext> actual = objectUnderTest.buildPricingUrl("one");
        assertEquals(expected, actual);
    }

    @Test
    void buildPricingUrlWhenTwoCodesProvided() {
        List<ServiceContext> expected = Arrays.asList(
                ServiceContext.builder()
                        .serviceType(ServiceType.PRICING)
                        .url("http://127.0.0.1:4000/pricing?countryCode=one")
                        .originalValue("one")
                        .build(),
                ServiceContext.builder()
                        .serviceType(ServiceType.PRICING)
                        .url("http://127.0.0.1:4000/pricing?countryCode=two")
                        .originalValue("two")
                        .build());
        List<ServiceContext> actual = objectUnderTest.buildPricingUrl("one,two");
        assertEquals(expected, actual);
    }

    @Test
    void buildTrackUrlWhenCodeNull() {
        List<ServiceContext> expected = new ArrayList<>();
        List<ServiceContext> actual = objectUnderTest.buildTrackUrl(null);
        assertEquals(expected, actual);
    }

    @Test
    void buildTrackUrlWhenOneCodeProvided() {
        List<ServiceContext> expected = Arrays.asList(
                ServiceContext.builder()
                        .serviceType(ServiceType.TRACK)
                        .url("http://127.0.0.1:4000/track-status?orderNumber=one")
                        .originalValue("one")
                        .build());
        List<ServiceContext> actual = objectUnderTest.buildTrackUrl("one");
        assertEquals(expected, actual);
    }

    @Test
    void buildTrakUrlWhenTwoCodesProvided() {
        List<ServiceContext> expected = Arrays.asList(
                ServiceContext.builder()
                        .serviceType(ServiceType.TRACK)
                        .url("http://127.0.0.1:4000/track-status?orderNumber=one")
                        .originalValue("one")
                        .build(),
                ServiceContext.builder()
                        .serviceType(ServiceType.TRACK)
                        .url("http://127.0.0.1:4000/track-status?orderNumber=two")
                        .originalValue("two")
                        .build());
        List<ServiceContext> actual = objectUnderTest.buildTrackUrl("one,two");
        assertEquals(expected, actual);
    }


    @Test
    void buildShipmentsUrlWhenCodeNull() {
        List<ServiceContext> expected = new ArrayList<>();
        List<ServiceContext> actual = objectUnderTest.buildShipmentsUrl(null);
        assertEquals(expected, actual);
    }

    @Test
    void buildShipmentsUrlWhenOneCodeProvided() {
        List<ServiceContext> expected = Arrays.asList(
                ServiceContext.builder()
                        .serviceType(ServiceType.SHIPMENTS)
                        .url("http://127.0.0.1:4000/shipment-products?orderNumber=one")
                        .originalValue("one")
                        .build());
        List<ServiceContext> actual = objectUnderTest.buildShipmentsUrl("one");
        assertEquals(expected, actual);
    }

    @Test
    void buildShipmentsUrlWhenTwoCodesProvided() {
        List<ServiceContext> expected = Arrays.asList(
                ServiceContext.builder()
                        .serviceType(ServiceType.SHIPMENTS)
                        .url("http://127.0.0.1:4000/shipment-products?orderNumber=one")
                        .originalValue("one")
                        .build(),
                ServiceContext.builder()
                        .serviceType(ServiceType.SHIPMENTS)
                        .url("http://127.0.0.1:4000/shipment-products?orderNumber=two")
                        .originalValue("two")
                        .build());
        List<ServiceContext> actual = objectUnderTest.buildShipmentsUrl("one,two");
        assertEquals(expected, actual);
    }


}
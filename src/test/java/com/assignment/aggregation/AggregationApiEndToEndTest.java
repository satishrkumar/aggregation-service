package com.assignment.aggregation;


import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.json.Json;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AggregationApiEndToEndTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    void testPort() {
        assertThat(port).isEqualTo(8080);
    }

    @Test
    public void testAggregationApiWithoutAnyParameter() throws IOException, JSONException {
        String url = "http://127.0.0.1:8080/aggregation";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        String response = "";
        Scanner scanner = new Scanner(connection.getInputStream());
        while (scanner.hasNext()) {
            response += scanner.nextLine();
        }
        scanner.close();


        String expected = "{\"shipments\":{},\"track\":{},\"pricing\":{}}";
        JSONAssert.assertEquals(expected, response, true);
    }

    @Test
    public void testAggregationApiWithoutTrackParameter() throws IOException, JSONException {
        String url = "http://127.0.0.1:8080/aggregation?shipmentsOrderNumbers=987654321,123456789&pricingCountryCodes=NL,CN";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        String response = "";
        Scanner scanner = new Scanner(connection.getInputStream());
        while (scanner.hasNext()) {
            response += scanner.nextLine();
        }
        scanner.close();


        String expected = "{}";
        JSONAssert.assertEquals(expected, Json.createReader(new StringReader(response)).readObject().getJsonObject("track").toString(), true);
    }

    @Test
    public void testAggregationApiWithoutShipmentsParameter() throws IOException, JSONException {
        String url = "http://127.0.0.1:8080/aggregation?pricingCountryCodes=NL,CN&trackOrderNumbers=987654321,123456789";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        String response = "";
        Scanner scanner = new Scanner(connection.getInputStream());
        while (scanner.hasNext()) {
            response += scanner.nextLine();
        }
        scanner.close();


        String expected = "{}";
        JSONAssert.assertEquals(expected, Json.createReader(new StringReader(response)).readObject().getJsonObject("shipments").toString(), true);
    }

    @Test
    public void testAggregationApiWithoutPricingParameter() throws IOException, JSONException {
        String url = "http://127.0.0.1:8080/aggregation?trackOrderNumbers=987654321,123456789&shipmentsOrderNumbers=987654321,123456789";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        String response = "";
        Scanner scanner = new Scanner(connection.getInputStream());
        while (scanner.hasNext()) {
            response += scanner.nextLine();
        }
        scanner.close();


        String expected = "{}";
        JSONAssert.assertEquals(expected, Json.createReader(new StringReader(response)).readObject().getJsonObject("pricing").toString(), true);
    }

    @Test
    public void testApiResponseTimeWithIn5Second() throws Exception {


        // Iterating 10 times to make sure getting within 5 second
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();

            String url = "http://127.0.0.1:8080/aggregation?shipmentsOrderNumbers=987654321,123456789&trackOrderNumbers=987654321,123456789&pricingCountryCodes=NL,CN";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            String response = "";
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();
            System.out.println(response);


            // Record the end time
            long endTime = System.currentTimeMillis();

            // Assert that the response time is less than 5 seconds
            String message = "API response time exceeds 5 seconds, value is " + (endTime - startTime);
            assertTrue(endTime - startTime <= 5000, message);
        }
    }
}
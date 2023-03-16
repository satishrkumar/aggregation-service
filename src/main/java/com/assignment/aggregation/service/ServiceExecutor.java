package com.assignment.aggregation.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ServiceExecutor {
    RestTemplate restTemplate = new RestTemplate();
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

    public ServiceExecutor() {
        requestFactory.setConnectTimeout(4990);
        requestFactory.setReadTimeout(4990);
        restTemplate.setRequestFactory(requestFactory);
    }


    public String getResponse(final String url) {
        String response = null;
        try {
            response = Optional.ofNullable(restTemplate.getForEntity(url, String.class).getBody())
                    .orElse(null);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                // handle 500 error response
            } else {
                // handle other error response
            }
        } catch (RestClientException ex) {
            // handle other RestClientException
        }
        return response;
    }
}

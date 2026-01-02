package com.example.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateExample {

    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateExample(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getExample() {
        String url = "http://example.com/api/resource";
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            // エラーハンドリング
            e.printStackTrace();
            return null;
        }
    }
}


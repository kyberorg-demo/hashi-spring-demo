package com.example.demo.consul;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.Optional;

@RestController
public class DiscoveryClientController {

    private final DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate = new RestTemplate();

    public DiscoveryClientController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/discoveryClient")
    public String discoveryPing() throws RestClientException,
            ServiceUnavailableException {
        URI service = serviceUrl()
                .map(s -> s.resolve("/ping"))
                .orElseThrow(ServiceUnavailableException::new);
        return restTemplate.getForEntity(service, String.class)
                .getBody();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    public Optional<URI> serviceUrl() {
        return discoveryClient.getInstances("demo-app")
                .stream()
                .findFirst()
                .map(ServiceInstance::getUri);
    }
}

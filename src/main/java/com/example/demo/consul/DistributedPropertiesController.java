package com.example.demo.consul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistributedPropertiesController {
    @Value("${keys.key}")
    String value;
    private final MyConsulKeys properties;

    public DistributedPropertiesController(MyConsulKeys properties) {
        this.properties = properties;
    }

    @GetMapping("/getConfigFromValue")
    public String getConfigFromValue() {
        return value;
    }

    @GetMapping("/getConfigFromProperty")
    public String getConfigFromProperty() {
        return properties.getKey();
    }

    @GetMapping("/getSecondKeyFromConsul")
    public String getSecondKeyFromConsul() {
        return properties.getSecondKey();
    }
}

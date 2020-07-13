package com.example.demo.consul;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/my-health")
    public ResponseEntity<String> myCustomCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

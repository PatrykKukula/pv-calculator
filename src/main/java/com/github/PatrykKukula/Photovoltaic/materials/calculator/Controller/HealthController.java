package com.github.PatrykKukula.Photovoltaic.materials.calculator.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<String> checkStatus(){
        return ResponseEntity.ok("ok");
    }
}

package com.novaraspace.web;

import com.novaraspace.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResponseEntity<Void> testGet() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("x-successful-error-lol", "interesting")
                .build();
    }
}

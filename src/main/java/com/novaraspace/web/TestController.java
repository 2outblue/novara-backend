package com.novaraspace.web;

import com.novaraspace.init.TestingComponent;
import com.novaraspace.init.data.TestingDTO;
import com.novaraspace.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestingComponent testingComponent;

    public TestController(TestingComponent testingComponent) {
        this.testingComponent = testingComponent;
    }

    @GetMapping
    public ResponseEntity<Void> testGet() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("x-successful-error-lol", "interesting")
                .build();
    }

    @PostMapping("/delete-payment")
    public ResponseEntity<Void> deletePayment(@RequestBody TestingDTO tDto) {
        testingComponent.deletePayment(tDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-refresh-token")
    public ResponseEntity<Void> deleteRefreshToken(@RequestBody TestingDTO tDto) {
        testingComponent.deleteRefreshToken(tDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-booking")
    public ResponseEntity<Void> deleteBooking(@RequestBody TestingDTO tDto) {
        testingComponent.deleteBooking(tDto);
        return ResponseEntity.ok().build();
    }
}

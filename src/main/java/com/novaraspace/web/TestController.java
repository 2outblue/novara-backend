package com.novaraspace.web;

import com.novaraspace.init.TestingComponent;
import com.novaraspace.init.data.TestingDTO;
import com.novaraspace.model.dto.flight.AvailableFlightDTO;
import com.novaraspace.model.dto.flight.FlightInstanceGenerationParams;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.service.FlightGenerationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestingComponent testingComponent;
    private final FlightGenerationService flightGenerationService;

    public TestController(TestingComponent testingComponent, FlightGenerationService flightGenerationService) {
        this.testingComponent = testingComponent;
        this.flightGenerationService = flightGenerationService;
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

    @PostMapping("/delete-flights")
    public ResponseEntity<Void> deleteFlights(@RequestBody TestingDTO tDto) {
        testingComponent.deleteFlights(tDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-total-flights")
    public ResponseEntity<Void> deleteBookedFlights(@RequestBody TestingDTO tDto) {
        testingComponent.deleteBookedFlights(tDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/generate-flights")
    public ResponseEntity<Integer> generateFlights(@RequestBody @Valid FlightInstanceGenerationParams params) {
        Integer count = flightGenerationService.generateForAllTemplates(params);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/latest-flight")
    public ResponseEntity<AvailableFlightDTO> getLatestFlight() {
        AvailableFlightDTO fi = testingComponent.getLatestFlight();
        return ResponseEntity.ok(fi);
    }
}

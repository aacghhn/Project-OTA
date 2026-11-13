package com.example.api;

import com.example.api.dto.response.*;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/reservations")
@Validated
public class CustomerReservationController implements CustomerReservationApi {
    private final CustomerReservationService service;

    public CustomerReservationController(CustomerReservationService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<CustomerReservationListResponse> listCustomerReservations(
        Principal principal,
        @RequestParam(name = "status", required = false) ReservationStatus status,
        @RequestParam(name = "cursor", required = false) String cursor,
        @RequestParam(name = "limit", defaultValue = "20") int limit) {
        return ResponseEntity.ok(service.listCustomerReservations(principal.getName(), status, cursor, limit));
    }

    @Override
    @GetMapping("/{reservationId}")
    public ResponseEntity<CustomerReservationDetailResponse> getCustomerReservation(
        Principal principal,
        @PathVariable("reservationId") String reservationId) {
        return ResponseEntity.ok(service.getCustomerReservation(principal.getName(), reservationId));
    }
}

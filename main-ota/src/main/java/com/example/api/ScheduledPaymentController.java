package com.example.api;

import com.example.api.dto.response.ScheduledPaymentResponse;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/customer/reservations")
public class ScheduledPaymentController implements ScheduledPaymentApi {
    private final ScheduledPaymentService service;

    public ScheduledPaymentController(ScheduledPaymentService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/{reservationId}/scheduled-payment", produces = "application/json")
    public ResponseEntity<ScheduledPaymentResponse> getScheduledPayment(
        Principal principal,
        @PathVariable("reservationId") String reservationId) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "로그인이 필요합니다.");
        }
        return ResponseEntity.ok(service.getScheduledPayment(principal.getName(), reservationId));
    }

}

package com.example.api;

import com.example.api.dto.request.UnavailabilityRequest;
import com.example.api.dto.response.UnavailabilityContextResponse;
import com.example.api.dto.response.CancellationResultResponse;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/partner/properties/{propertyId}/reservations/{reservationId}/unavailability")
public class UnavailabilityController implements UnavailabilityApi {
    private final UnavailabilityService service;

    public UnavailabilityController(UnavailabilityService service) { this.service = service; }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<UnavailabilityContextResponse> getUnavailabilityContext(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @PathVariable("reservationId") String reservationId) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.getUnavailabilityContext(principal.getName(), propertyId, reservationId));
    }

    @Override
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<CancellationResultResponse> reportUnavailability(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @PathVariable("reservationId") String reservationId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody UnavailabilityRequest request) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.reportUnavailability(principal.getName(), propertyId, reservationId, idempotencyKey, request));
    }
}

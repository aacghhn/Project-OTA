package com.example.api;

import com.example.api.dto.request.CancellationRequest;
import com.example.api.dto.response.CancellationPreviewResponse;
import com.example.api.dto.response.CancellationResultResponse;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/customer/reservations")
public class CancellationController implements CancellationApi {
    private final CancellationService service;

    public CancellationController(CancellationService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/{reservationId}/cancellation-preview", produces = "application/json")
    public ResponseEntity<CancellationPreviewResponse> getCancellationPreview(
        Principal principal,
        @PathVariable("reservationId") String reservationId) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "로그인이 필요합니다.");
        }
        return ResponseEntity.ok(service.getCancellationPreview(principal.getName(), reservationId));
    }

    @Override
    @PostMapping(value = "/{reservationId}/cancellation", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CancellationResultResponse> cancelEntireReservation(
        Principal principal,
        @PathVariable("reservationId") String reservationId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CancellationRequest request) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "로그인이 필요합니다.");
        }
        return ResponseEntity.ok(service.cancelEntireReservation(principal.getName(), reservationId, idempotencyKey, request));
    }

    @Override
    @GetMapping(value = "/{reservationId}/cancellation", produces = "application/json")
    public ResponseEntity<CancellationResultResponse> getCancellationResult(
        Principal principal,
        @PathVariable("reservationId") String reservationId) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "로그인이 필요합니다.");
        }
        return ResponseEntity.ok(service.getCancellationResult(principal.getName(), reservationId));
    }
}

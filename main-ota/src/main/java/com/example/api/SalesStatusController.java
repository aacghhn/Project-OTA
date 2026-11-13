package com.example.api;

import com.example.api.dto.response.SalesStatusResponse.SalesStatusScope;
import com.example.api.dto.request.SalesStatusChangeRequest;
import com.example.api.dto.response.*;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/partner/properties/{propertyId}/sales-status")
public class SalesStatusController implements SalesStatusApi {
    private final SalesStatusService service;

    public SalesStatusController(SalesStatusService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<SalesStatusResponse> getSalesStatus(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestParam("scope") SalesStatusScope scope,
        @RequestParam("targetId") String targetId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.getSalesStatus(principal.getName(), propertyId, scope, targetId, from, to));
    }

    @Override
    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<SalesStatusMutationResponse> setSalesStatus(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody SalesStatusChangeRequest request) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.setSalesStatus(principal.getName(), propertyId, idempotencyKey, request));
    }
}

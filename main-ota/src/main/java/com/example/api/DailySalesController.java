package com.example.api;

import com.example.api.dto.request.*;
import com.example.api.dto.response.*;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/partner/properties/{propertyId}")
public class DailySalesController implements DailySalesApi {
    private final DailySalesService service;

    public DailySalesController(DailySalesService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/daily-prices", produces = "application/json")
    public ResponseEntity<DailySalesPricesResponse> getDailySalesPrices(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestParam("productId") String productId,
        @RequestParam("from")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam("to")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.getDailySalesPrices(principal.getName(), propertyId, productId, from, to));
    }

    @Override
    @PutMapping(value = "/daily-prices", produces = "application/json", consumes = "application/json")
    public ResponseEntity<DailySalesPricesResponse> updateDailySalesPrices(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody DailySalesPriceUpdateRequest request) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.updateDailySalesPrices(principal.getName(), propertyId, idempotencyKey, request));
    }

    @Override
    @GetMapping(value = "/daily-inventory", produces = "application/json")
    public ResponseEntity<DailySalesInventoryResponse> getDailySalesInventory(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestParam("roomTypeId") String roomTypeId,
        @RequestParam("from")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam("to")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.getDailySalesInventory(principal.getName(), propertyId, roomTypeId, from, to));
    }

    @Override
    @PutMapping(value = "/daily-inventory", produces = "application/json", consumes = "application/json")
    public ResponseEntity<DailySalesInventoryResponse> updateDailySalesLimits(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody DailySalesLimitUpdateRequest request) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.updateDailySalesLimits(principal.getName(), propertyId, idempotencyKey, request));
    }

}

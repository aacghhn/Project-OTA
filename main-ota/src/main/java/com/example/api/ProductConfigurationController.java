package com.example.api;

import com.example.api.dto.request.*;
import com.example.api.dto.response.*;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class ProductConfigurationController implements ProductConfigurationApi {
    private final ProductConfigurationService service;
    public ProductConfigurationController(ProductConfigurationService service) { this.service = service; }

    @Override
    @GetMapping(value = "/api/partner/properties", produces = "application/json")
    public ResponseEntity<CatalogManagedPropertiesResponse> listManagedProperties(
        Principal principal,
        @RequestParam(value = "limit", defaultValue = "20") Integer limit,
        @RequestParam(value = "cursor", required = false) String cursor) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(200).body(service.listManagedProperties(principal.getName(), limit, cursor));
    }

    @Override
    @GetMapping(value = "/api/partner/properties/{propertyId}/configuration", produces = "application/json")
    public ResponseEntity<CatalogConfigurationResponse> getProductConfiguration(
        Principal principal,
        @PathVariable("propertyId") String propertyId) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(200).body(service.getProductConfiguration(principal.getName(), propertyId));
    }

    @Override
    @GetMapping(value = "/api/partner/properties/{propertyId}/configuration/options", produces = "application/json")
    public ResponseEntity<CatalogConfigurationOptionsResponse> getProductConfigurationOptions(
        Principal principal,
        @PathVariable("propertyId") String propertyId) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(200).body(service.getProductConfigurationOptions(principal.getName(), propertyId));
    }

    @Override
    @GetMapping(value = "/api/partner/properties/{propertyId}/configuration/location-suggestions", produces = "application/json")
    public ResponseEntity<CatalogLocationSuggestionsResponse> getCatalogLocationSuggestions(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestParam("query") String query) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(200).body(service.getCatalogLocationSuggestions(principal.getName(), propertyId, query));
    }

    @Override
    @PutMapping(value = "/api/partner/properties/{propertyId}/configuration/property", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogPropertyResponse> updateCatalogProperty(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CatalogPropertyUpdateRequest request) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(200).body(service.updateCatalogProperty(principal.getName(), propertyId, idempotencyKey, request));
    }

    @Override
    @PostMapping(value = "/api/partner/properties/{propertyId}/configuration/photos", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogUploadedPhotoResponse> uploadCatalogPhoto(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CatalogPhotoUploadRequest request) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(201).body(service.uploadCatalogPhoto(principal.getName(), propertyId, idempotencyKey, request));
    }

    @Override
    @PostMapping(value = "/api/partner/properties/{propertyId}/configuration/room-types", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogRoomResponse> createCatalogRoomType(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CatalogRoomCreateRequest request) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(201).body(service.createCatalogRoomType(principal.getName(), propertyId, idempotencyKey, request));
    }

    @Override
    @PutMapping(value = "/api/partner/properties/{propertyId}/configuration/room-types/{roomTypeId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogRoomResponse> updateCatalogRoomType(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @PathVariable("roomTypeId") String roomTypeId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CatalogRoomUpdateRequest request) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(200).body(service.updateCatalogRoomType(principal.getName(), propertyId, roomTypeId, idempotencyKey, request));
    }

    @Override
    @PostMapping(value = "/api/partner/properties/{propertyId}/configuration/rate-plans", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogRatePlanResponse> createCatalogRatePlan(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CatalogRatePlanCreateRequest request) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(201).body(service.createCatalogRatePlan(principal.getName(), propertyId, idempotencyKey, request));
    }

    @Override
    @PutMapping(value = "/api/partner/properties/{propertyId}/configuration/rate-plans/{ratePlanId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogRatePlanResponse> updateCatalogRatePlan(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @PathVariable("ratePlanId") String ratePlanId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CatalogRatePlanUpdateRequest request) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(200).body(service.updateCatalogRatePlan(principal.getName(), propertyId, ratePlanId, idempotencyKey, request));
    }

    @Override
    @PostMapping(value = "/api/partner/properties/{propertyId}/configuration/products", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogProductResponse> createCatalogProduct(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CatalogProductCreateRequest request) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(201).body(service.createCatalogProduct(principal.getName(), propertyId, idempotencyKey, request));
    }

    @Override
    @PutMapping(value = "/api/partner/properties/{propertyId}/configuration/products/{productId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogProductResponse> updateCatalogProduct(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @PathVariable("productId") String productId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody CatalogProductUpdateRequest request) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.status(200).body(service.updateCatalogProduct(principal.getName(), propertyId, productId, idempotencyKey, request));
    }
}

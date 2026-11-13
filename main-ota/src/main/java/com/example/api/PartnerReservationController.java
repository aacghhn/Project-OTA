package com.example.api;

import com.example.api.dto.response.*;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/partner/properties/{propertyId}/reservations")
public class PartnerReservationController implements PartnerReservationApi {
    private final PartnerReservationService service;

    public PartnerReservationController(PartnerReservationService service) { this.service = service; }

    @Override
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<PartnerReservationListResponse> listPartnerReservations(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = "reservationId", required = false) String reservationId,
        @RequestParam(value = "referenceCode", required = false) String referenceCode,
        @RequestParam(value = "guestFirstName", required = false) String guestFirstName,
        @RequestParam(value = "guestLastName", required = false) String guestLastName,
        @RequestParam(value = "checkInFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInFrom,
        @RequestParam(value = "checkInTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInTo,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "cursor", required = false) String cursor) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.ok(service.listPartnerReservations(principal.getName(), propertyId, status == null ? "CONFIRMED" : status, reservationId, referenceCode, guestFirstName, guestLastName, checkInFrom, checkInTo, limit == null ? 20 : limit, cursor));
    }

    @Override
    @GetMapping(value = "/{reservationId}", produces = "application/json")
    public ResponseEntity<PartnerReservationDetailResponse> getPartnerReservation(
        Principal principal,
        @PathVariable("propertyId") String propertyId,
        @PathVariable("reservationId") String reservationId) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        return ResponseEntity.ok(service.getPartnerReservation(principal.getName(), propertyId, reservationId));
    }
}

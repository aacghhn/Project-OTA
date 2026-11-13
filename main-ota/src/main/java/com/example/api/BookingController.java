package com.example.api;

import com.example.api.dto.request.*;
import com.example.api.dto.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@Validated
public class BookingController implements BookingApi {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }

    @Override
    @GetMapping(value = "/api/public/search-options", produces = "application/json")
    public ResponseEntity<SearchOptionsResponse> getSearchOptions() {
        return ResponseEntity.status(200).body(service.getSearchOptions());
    }

    @Override
    @PostMapping(value = "/api/public/property-searches", produces = "application/json", consumes = "application/json")
    public ResponseEntity<SearchResultResponse> searchProperties(
        @RequestBody SearchCriteriaRequest request) {
        return ResponseEntity.status(200).body(service.searchProperties(request));
    }

    @Override
    @PostMapping(value = "/api/public/properties/{propertyId}/offers", produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookingPropertyOffersResponse> getPropertyOffers(
        @PathVariable("propertyId") String propertyId,
        @RequestBody SearchStayRequest request) {
        return ResponseEntity.status(200).body(service.getPropertyOffers(propertyId, request));
    }

    @Override
    @PostMapping(value = "/api/customer/booking-quotes", produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookingQuoteResponse> getBookingQuote(
        Principal principal,
        @RequestBody BookingQuoteRequest request) {
        return ResponseEntity.status(200).body(service.getBookingQuote(principal.getName(), request));
    }

    @Override
    @PostMapping(value = "/api/customer/booking-payment-options", produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookingPaymentOptionsResponse> getBookingPaymentOptions(
        Principal principal,
        @RequestBody BookingPaymentOptionsRequest request) {
        return ResponseEntity.status(200).body(service.getBookingPaymentOptions(principal.getName(), request));
    }

    @Override
    @PostMapping(value = "/api/customer/reservations", produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookingAttemptResponse> createBooking(
        Principal principal,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody BookingCreateRequest request) {
        return ResponseEntity.status(202).body(service.createBooking(principal.getName(), idempotencyKey, request));
    }

    @Override
    @GetMapping(value = "/api/customer/reservation-attempts/{reservationId}", produces = "application/json")
    public ResponseEntity<BookingAttemptResponse> getBookingAttempt(
        Principal principal,
        @PathVariable("reservationId") String reservationId) {
        return ResponseEntity.status(200).body(service.getBookingAttempt(principal.getName(), reservationId));
    }

    @Override
    @PostMapping(value = "/api/customer/reservation-attempts/{reservationId}/payment-attempts", produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookingAttemptResponse> retryBookingPayment(
        Principal principal,
        @PathVariable("reservationId") String reservationId,
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody BookingRetryPaymentRequest request) {
        return ResponseEntity.status(202).body(service.retryBookingPayment(principal.getName(), reservationId, idempotencyKey, request));
    }

}

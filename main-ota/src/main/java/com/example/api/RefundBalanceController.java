package com.example.api;

import com.example.api.dto.response.RefundBalanceResponse;
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
public class RefundBalanceController implements RefundBalanceApi {
    private final RefundBalanceService service;

    public RefundBalanceController(RefundBalanceService service) { this.service = service; }

    @Override
    @GetMapping(value = "/{reservationId}/financial-outcome", produces = "application/json")
    public ResponseEntity<RefundBalanceResponse> getRefundBalance(
        Principal principal,
        @PathVariable("reservationId") String reservationId) {
        if (principal == null) throw new UnauthenticatedException("UNAUTHENTICATED", "고객 인증이 필요합니다.");
        return ResponseEntity.ok(service.getRefundBalance(principal.getName(), reservationId));
    }

}

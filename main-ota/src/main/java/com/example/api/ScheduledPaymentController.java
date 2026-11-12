package com.example.api;

import com.example.api.dto.response.ErrorResponse;
import com.example.api.dto.response.ScheduledPaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 인증된 고객에게 자신의 예약 당시 예정 청구와 현재 예약·결제 결과를 제공한다. */
@RestController
@Validated
@RequestMapping("/api/customer/reservations")
@Tag(name = "Scheduled payment", description = "나중 결제의 예정 의무와 현재 결과 조회")
public class ScheduledPaymentController {
    private final ScheduledPaymentService service;

    public ScheduledPaymentController(ScheduledPaymentService service) {
        this.service = service;
    }

    /**
     * 본인 예약의 예정 청구를 조회한다. 조회가 자동 청구나 새 결제를 시작하지는 않는다.
     * 가정: 예정 청구가 없는 즉시 결제 예약은 404 SCHEDULED_PAYMENT_NOT_FOUND로 구분해 비어 있는 청구를 만들지 않는다.
     */
    @GetMapping(value = "/{reservationId}/scheduled-payment", produces = "application/json")
    @Operation(operationId = "getScheduledPayment", summary = "예약의 예정 결제와 결과 조회",
        description = "인증된 고객이 자신의 예약에 준비된 예정 금액·시각·수단과 현재 결과를 조회한다. 예약 상태, 결제 상태, 남은 숙박대금 의무를 분리한다. 예정 결제 실패가 확정되면 예약을 전체 취소하고 고객 비용은 0원이며, 결과 불명에서는 예약을 유지하고 기존 시도를 확인한다. 이미 취소된 예약은 최초 취소 사유·비용을 유지한다. 조회 자체는 새 결제·수단 변경·환불을 실행하지 않는다. 가정: 예정 청구가 없는 예약은 404 SCHEDULED_PAYMENT_NOT_FOUND로 응답해 존재하지 않는 청구를 가공하지 않는다.",
        security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "예정 또는 처리 중·성공·실패·결과 불명·실행 전 중단 상태를 조회했다. 실패나 불명도 유효한 조회 결과이며 HTTP 성공은 결제 성공을 뜻하지 않는다.",
            content = @Content(schema = @Schema(implementation = ScheduledPaymentResponse.class), examples = @ExampleObject(name = "Scheduled", value = SCHEDULED_EXAMPLE))),
        @ApiResponse(responseCode = "400", description = "예약 식별자가 비었거나 64자를 초과한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"예약 식별자는 1~64자여야 합니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[{\"field\":\"reservationId\",\"message\":\"1~64자여야 합니다.\"}]}"))),
        @ApiResponse(responseCode = "401", description = "Bearer 인증이 없거나 유효하지 않다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"UNAUTHENTICATED\",\"message\":\"로그인이 필요합니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "403", description = "인증된 계정이 고객 역할이 아니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"CUSTOMER_ROLE_REQUIRED\",\"message\":\"고객 계정만 예정 결제를 조회할 수 있습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "예약이 없거나 본인 소유가 아니거나 해당 예약의 예정 청구가 없다. 타인 예약의 존재 여부를 구분해 노출하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SCHEDULED_PAYMENT_NOT_FOUND\",\"message\":\"요청한 예정 결제를 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "저장소 장애로 현재 조회 결과를 제공할 수 없다. 외부 결제 결과 불명 상태는 이 오류가 아니라 정상 본문의 UNKNOWN으로 반환한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"예정 결제 내역을 잠시 조회할 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}")))
    })
    public ResponseEntity<ScheduledPaymentResponse> getScheduledPayment(
        @Parameter(hidden = true) Principal principal,
        // 가정: 경로 이름 reservationId와 1~64자 문자열 제약을 선택해 내부 저장소 식별 형식을 노출하지 않는다.
        @Parameter(description = "예약 식별자. 가정: reservationId라는 필수 1~64자 opaque 문자열로 받아 저장소 형식을 노출하지 않는다.", required = true,
            schema = @Schema(type = "string", minLength = 1, maxLength = 64), example = "rsv_01")
        @PathVariable("reservationId") @NotBlank @Size(min = 1, max = 64) String reservationId) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "로그인이 필요합니다.");
        }
        return ResponseEntity.ok(service.getScheduledPayment(principal.getName(), reservationId));
    }

    private static final String SCHEDULED_EXAMPLE = "{\"reservationId\":\"rsv_01\",\"reservationStatus\":\"CONFIRMED\",\"chargeId\":\"chg_01\",\"scheduledAmount\":220000,\"scheduledCurrency\":\"KRW\",\"scheduledAt\":\"2026-12-17T00:00:00+09:00\",\"timeZone\":\"Asia/Seoul\",\"paymentMethodId\":\"pm_01\",\"paymentMethodTypeLabel\":\"카드\",\"paymentMethodMaskedIdentifier\":\"**** 1234\",\"paymentStatus\":\"SCHEDULED\",\"attemptId\":null,\"attemptStartedAt\":null,\"resultConfirmedAt\":null,\"failureReason\":null,\"successfulCollectionAmount\":0,\"successfulCollectionCurrency\":\"KRW\",\"remainingScheduledObligationAmount\":220000,\"remainingScheduledObligationCurrency\":\"KRW\",\"scheduledCollectionStopped\":false,\"resultCheckPending\":false,\"cancellationReason\":null,\"cancellationCancelledAt\":null,\"cancellationCustomerCostAmount\":null,\"cancellationCustomerCostCurrency\":null,\"observedAt\":\"2026-11-11T15:00:00+09:00\"}";
}

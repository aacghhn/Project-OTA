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

/** 인증된 고객에게 자신의 예약 당시 예정 청구와 현재 예약·결제 결과를 제공한다. */
@Tag(name = "Scheduled payment", description = "나중 결제의 예정 의무와 현재 결과 조회")
public interface ScheduledPaymentApi {

    /**
     * 본인 예약의 예정 청구를 조회한다. 조회가 자동 청구나 새 결제를 시작하지는 않는다.
     * 가정: 예정 청구가 없는 즉시 결제 예약은 404 SCHEDULED_PAYMENT_NOT_FOUND로 구분해 비어 있는 청구를 만들지 않는다.
     */
    @Operation(operationId = "getScheduledPayment", summary = "예약의 예정 결제와 결과 조회", description = "본인 예약의 예정 결제 금액·시각·수단과 현재 결과를 조회한다. 예정 청구가 없는 예약은 404를 반환한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "예정 또는 처리 중·성공·실패·결과 불명·실행 전 중단 상태를 조회했다. 실패나 불명도 유효한 조회 결과이며 HTTP 성공은 결제 성공을 뜻하지 않는다.", content = @Content(schema = @Schema(implementation = ScheduledPaymentResponse.class), examples = @ExampleObject(name = "Scheduled", value = SCHEDULED_EXAMPLE))),
        @ApiResponse(responseCode = "400", description = "예약 식별자가 비었거나 64자를 초과한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"예약 식별자는 1~64자여야 합니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[{\"field\":\"reservationId\",\"message\":\"1~64자여야 합니다.\"}]}"))),
        @ApiResponse(responseCode = "404", description = "예약이 없거나 본인 소유가 아니거나 해당 예약의 예정 청구가 없다. 타인 예약의 존재 여부를 구분해 노출하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SCHEDULED_PAYMENT_NOT_FOUND\",\"message\":\"요청한 예정 결제를 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<ScheduledPaymentResponse> getScheduledPayment(
        @Parameter(hidden = true) Principal principal,
        // 가정: 경로 이름 reservationId와 1~64자 문자열 제약을 선택해 내부 저장소 식별 형식을 노출하지 않는다.
        @Parameter(description = "예약 식별자. 가정: reservationId라는 필수 1~64자 opaque 문자열로 받아 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string"), example = "rsv_01")
        @NotBlank @Size(min = 1, max = 64) String reservationId);

    String SCHEDULED_EXAMPLE = "{\"reservationId\":\"rsv_01\",\"reservationStatus\":\"CONFIRMED\",\"chargeId\":\"chg_01\",\"scheduledAmount\":220000,\"scheduledCurrency\":\"KRW\",\"scheduledAt\":\"2026-12-17T00:00:00+09:00\",\"timeZone\":\"Asia/Seoul\",\"paymentMethodId\":\"pm_01\",\"paymentMethodTypeLabel\":\"카드\",\"paymentMethodMaskedIdentifier\":\"**** 1234\",\"paymentStatus\":\"SCHEDULED\",\"attemptId\":null,\"attemptStartedAt\":null,\"resultConfirmedAt\":null,\"failureReason\":null,\"successfulCollectionAmount\":0,\"successfulCollectionCurrency\":\"KRW\",\"remainingScheduledObligationAmount\":220000,\"remainingScheduledObligationCurrency\":\"KRW\",\"scheduledCollectionStopped\":false,\"resultCheckPending\":false,\"cancellationReason\":null,\"cancellationCancelledAt\":null,\"cancellationCustomerCostAmount\":null,\"cancellationCustomerCostCurrency\":null,\"observedAt\":\"2026-11-11T15:00:00+09:00\"}";
}

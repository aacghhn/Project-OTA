package com.example.api;

import com.example.api.dto.response.ErrorResponse;
import com.example.api.dto.response.RefundBalanceResponse;
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

/** 고객 자신의 예약에 남은 환불·추가 비용과 기존 금전 작업 결과를 조회한다. */
@Tag(name = "Refund and balance", description = "환불 및 잔여 금전 결과 조회")
public interface RefundBalanceApi {

    /** 예약 종료 뒤의 금전 결과를 읽는다. 조회와 새 환불·청구 생성은 분리한다. */
    @Operation(operationId = "getRefundBalance", summary = "예약의 환불과 잔여 금전 결과 조회", description = "종료된 본인 예약의 환불 내역과 남은 금전 의무를 조회한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공. 처리 중·실패·결과 불명도 정상 조회이며 금전 처리 완료를 뜻하지 않는다.", content = @Content(schema = @Schema(implementation = RefundBalanceResponse.class), examples = @ExampleObject(name = "refundPending", value = SUCCESS_EXAMPLE))),
        @ApiResponse(responseCode = "400", description = "예약 식별자는 1~64자여야 합니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "조회 가능한 본인 예약이 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "409", description = "아직 예약 종료나 후속 환불·취소 비용 처리 대상이 아닙니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"FINANCIAL_OUTCOME_NOT_AVAILABLE\",\"message\":\"현재 상태에서는 처리할 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<RefundBalanceResponse> getRefundBalance(
        @Parameter(hidden = true) Principal principal,
        // 가정: reservationId라는 필수 경로 값을 1~64자 opaque 문자열로 선택했다. 고객 본인의 예약을 식별하며 저장소 형식을 클라이언트에 노출하지 않는다.
        @Parameter(description = "가정: reservationId라는 필수 경로 값을 1~64자 opaque 문자열로 선택했다. 고객 본인의 예약을 식별하며 저장소 형식을 클라이언트에 노출하지 않는다.", required = true, schema = @Schema(type = "string"), example = "rsv_01")
        @NotBlank @Size(min = 1, max = 64) String reservationId);

    String SUCCESS_EXAMPLE = "{\"reservationId\":\"rsv_01\",\"reservationStatus\":\"CANCELLED\",\"reservationEndReason\":\"고객 요청으로 전체 취소\",\"cancellationReason\":\"CUSTOMER_REQUEST\",\"cancellationReasonDetail\":null,\"cancellationDecidedAt\":\"2026-12-16T12:00:00+09:00\",\"cancellationCustomerFeeAmount\":0,\"cancellationCustomerFeeCurrency\":\"KRW\",\"targetCustomerCostAmount\":0,\"targetCustomerCostCurrency\":\"KRW\",\"confirmedNetCollectionAmount\":240000,\"confirmedNetCollectionCurrency\":\"KRW\",\"financialImpactSuccessfulCollectionAmount\":240000,\"financialImpactSuccessfulCollectionCurrency\":\"KRW\",\"financialImpactSuccessfulRefundAmount\":0,\"financialImpactSuccessfulRefundCurrency\":\"KRW\",\"financialImpactRefundOutstandingAmount\":240000,\"financialImpactRefundOutstandingCurrency\":\"KRW\",\"financialImpactCustomerOutstandingAmount\":0,\"financialImpactCustomerOutstandingCurrency\":\"KRW\",\"financialImpactProcessingPaymentAmount\":0,\"financialImpactProcessingPaymentCurrency\":\"KRW\",\"financialImpactUnknownPaymentAmount\":0,\"financialImpactUnknownPaymentCurrency\":\"KRW\",\"financialImpactProcessingRefundAmount\":0,\"financialImpactProcessingRefundCurrency\":\"KRW\",\"financialImpactUnknownRefundAmount\":0,\"financialImpactUnknownRefundCurrency\":\"KRW\",\"financialImpactAmountsProvisional\":false,\"financialImpactPayments\":[{\"chargeId\":\"chg_1\",\"attemptId\":\"pay_1\",\"purpose\":\"STAY\",\"status\":\"SUCCEEDED\",\"amount\":240000,\"currency\":\"KRW\",\"updatedAt\":\"2026-12-16T12:00:00+09:00\",\"failureReason\":null}],\"financialImpactRefunds\":[{\"refundId\":\"rfd_1\",\"originalPaymentId\":\"pay_1\",\"status\":\"PENDING\",\"amount\":240000,\"currency\":\"KRW\",\"updatedAt\":\"2026-12-16T12:00:00+09:00\",\"failureReason\":null}],\"financialImpactRemainingWork\":[\"ISSUE_OR_RECOVER_REFUND\"],\"reconciliationStatus\":\"ACTION_REQUIRED\",\"charges\":[{\"chargeId\":\"chg_1\",\"purpose\":\"STAY\",\"amount\":240000,\"currency\":\"KRW\",\"dueAt\":\"2026-12-16T12:00:00+09:00\",\"collectionStopped\":true,\"attempts\":[{\"attemptId\":\"pay_1\",\"retryOfAttemptId\":null,\"status\":\"SUCCEEDED\",\"amount\":240000,\"currency\":\"KRW\",\"paymentMethodLabel\":\"카드 ****1234\",\"createdAt\":\"2026-12-16T12:00:00+09:00\",\"startedAt\":\"2026-12-16T12:00:00+09:00\",\"resultConfirmedAt\":\"2026-12-16T12:00:00+09:00\",\"lastCheckedAt\":null,\"nextCheckAt\":null,\"failureReason\":null}]}],\"refunds\":[{\"refundId\":\"rfd_1\",\"originalPaymentId\":\"pay_1\",\"retryOfRefundId\":null,\"status\":\"PENDING\",\"amount\":240000,\"currency\":\"KRW\",\"reason\":\"전체 취소에 따른 원결제 반환\",\"createdAt\":\"2026-12-16T12:00:00+09:00\",\"requestedAt\":null,\"resultConfirmedAt\":null,\"lastCheckedAt\":null,\"nextCheckAt\":null,\"failureReason\":null}],\"timeZone\":\"Asia/Seoul\",\"observedAt\":\"2026-12-16T12:00:00+09:00\"}";
}

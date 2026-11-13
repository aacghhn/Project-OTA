package com.example.api.dto.response;
import com.example.api.dto.response.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CancellationResultResponse", description = "전체 취소의 최초 사유·판정 시각·고객 비용과 현재 금전 후속 상태. 최초 취소 결과는 반복 요청으로 바뀌지 않지만 금전 진행은 이후 조회에서 갱신될 수 있다.")
public record CancellationResultResponse(
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "취소한 예약의 1~64자 opaque 식별자이다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "rsv_1")
    @NotNull 
    @Size(min = 1, max = 64)
    String reservationId,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 응답에서는 CANCELLED이며 결제·환불 완료를 뜻하지 않는다. 가정: 응답 필드명을 reservationStatus, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"CANCELLED"}, example = "CANCELLED")
    @NotNull 
    ReservationStatus reservationStatus,
    /** 최초 전체 취소 사유. 반복 요청으로 변경하지 않는다. 가정: 응답 필드명을 cancellationReason, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "최초 취소의 사유·자유문장·판정 시각·고객 부담 비용을 보존한다. 나중에 다른 사유로 요청해도 바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"CUSTOMER_REQUEST", "SCHEDULED_PAYMENT_FAILED", "PROPERTY_UNAVAILABLE"}, example = "CUSTOMER_REQUEST")
    @NotNull
    CancellationReason cancellationReason,
    /** 최초 취소 때 남긴 구체 사유. 가정: 사유 입력이 없으면 null, 있으면 1~500자로 보존한다. 가정: 응답 필드명을 cancellationReasonDetail, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "최초 취소의 사유·자유문장·판정 시각·고객 부담 비용을 보존한다. 나중에 다른 사유로 요청해도 바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    @Size(min = 1, max = 500)
    String cancellationReasonDetail,
    /** 최초 취소 비용을 판단한 시각. 반복 조회·반복 취소로 갱신하지 않는다. 가정: 응답 필드명을 cancellationDecidedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "최초 취소의 사유·자유문장·판정 시각·고객 부담 비용을 보존한다. 나중에 다른 사유로 요청해도 바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-12-18T10:00:00+09:00")
    @NotNull
    OffsetDateTime cancellationDecidedAt,
    /** 확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationCustomerFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "최초 취소의 사유·자유문장·판정 시각·고객 부담 비용을 보존한다. 나중에 다른 사유로 요청해도 바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long cancellationCustomerFeeAmount,
    /** 가정: 응답 필드명을 cancellationCustomerFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 cancellationCustomerFeeAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String cancellationCustomerFeeCurrency,
    /** 가정: 응답 필드명을 occupancyReleased, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "전체 숙박일 점유 해제가 완료되면 true를 반환한다. 로컬 취소 완료는 환불 성공을 기다리지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"true"}, example = "true")
    @NotNull 
    @AssertTrue 
    Boolean occupancyReleased,
    /** 반복 결과 통지는 다시 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactSuccessfulCollectionAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. 성공이 확인된 서로 다른 논리 결제만 합산한 P이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactSuccessfulCollectionAmount,
    /** 가정: 응답 필드명을 financialImpactSuccessfulCollectionCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 financialImpactSuccessfulCollectionAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactSuccessfulCollectionCurrency,
    /** 성공 수금액을 초과하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactSuccessfulRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. 성공이 확인된 서로 다른 환불만 합산한 R이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactSuccessfulRefundAmount,
    /** 가정: 응답 필드명을 financialImpactSuccessfulRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 financialImpactSuccessfulRefundAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactSuccessfulRefundCurrency,
    /** 진행·결과 불명인 결제나 환불이 있으면 financialImpactAmountsProvisional=true이며 최종 처리액으로 단정하지 않는다. 기존 결과 확인 전 중복 수금·환불 작업을 만들지 않는다. 가정: Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactRefundOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "현재 확인된 환불 잔액 max(P-R-F,0)이다. P는 financialImpactSuccessfulCollectionAmount에 기록한 성공 수금 합계, R은 financialImpactSuccessfulRefundAmount에 기록한 성공 환불 합계, F는 cancellationCustomerFeeAmount에 기록한 최초 확정 고객 취소 비용이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactRefundOutstandingAmount,
    /** 가정: 응답 필드명을 financialImpactRefundOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 financialImpactRefundOutstandingAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactRefundOutstandingCurrency,
    /** P는 financialImpactSuccessfulCollectionAmount에 기록한 성공 수금 합계, R은 financialImpactSuccessfulRefundAmount에 기록한 성공 환불 합계, F는 cancellationCustomerFeeAmount에 기록한 최초 확정 고객 취소 비용이다. 진행·결과 불명인 결제나 환불이 있으면 financialImpactAmountsProvisional=true이며 최종 처리액으로 단정하지 않는다. 기존 결과 확인 전 중복 수금·환불 작업을 만들지 않는다. 가정: Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactCustomerOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. 미결제 취소에도 비용 의무가 남으며 수금 실패가 의무를 없애지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactCustomerOutstandingAmount,
    /** 가정: 응답 필드명을 financialImpactCustomerOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 financialImpactCustomerOutstandingAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactCustomerOutstandingCurrency,
    /** 성공 수금액에 더하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactProcessingPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. PROCESSING 상태의 결제 대상액 합계이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactProcessingPaymentAmount,
    /** 가정: 응답 필드명을 financialImpactProcessingPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 financialImpactProcessingPaymentAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactProcessingPaymentCurrency,
    /** 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactUnknownPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. UNKNOWN 상태의 결제 대상액 합계이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactUnknownPaymentAmount,
    /** 가정: 응답 필드명을 financialImpactUnknownPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 financialImpactUnknownPaymentAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactUnknownPaymentCurrency,
    /** 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactProcessingRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. PROCESSING 상태의 환불 대상액 합계이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactProcessingRefundAmount,
    /** 가정: 응답 필드명을 financialImpactProcessingRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 financialImpactProcessingRefundAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactProcessingRefundCurrency,
    /** 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactUnknownRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. UNKNOWN 상태의 환불 대상액 합계이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactUnknownRefundAmount,
    /** 가정: 응답 필드명을 financialImpactUnknownRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
    @Schema(description = "이 응답의 financialImpactUnknownRefundAmount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactUnknownRefundCurrency,
    /** false여도 미수 비용·미완료 환불이 있으면 금전 처리는 끝나지 않았다. 가정: 응답 필드명을 financialImpactAmountsProvisional, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. 결제 또는 환불에 PROCESSING/UNKNOWN이 있으면 true이다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull 
    Boolean financialImpactAmountsProvisional,
    /** 없으면 빈 배열이며 조회가 새 청구를 만들지 않는다. 가정: 응답 필드명을 financialImpactPayments, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. 관련 숙박대금·취소 비용 청구를 생성 순서로 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = FinancialImpactPaymentsItem.class))
    @NotNull 
    @Valid
    List<@NotNull FinancialImpactPaymentsItem> financialImpactPayments,
    /** 없으면 빈 배열이며 조회가 새 환불을 만들지 않는다. 가정: 응답 필드명을 financialImpactRefunds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. 원결제별 환불 작업을 생성 순서로 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = FinancialImpactRefundsItem.class))
    @NotNull 
    @Valid
    List<@NotNull FinancialImpactRefundsItem> financialImpactRefunds,
    /** PROCESSING/UNKNOWN 작업의 금액은 기존 결과 확인 전에 새 작업으로 중복 배정하지 않는다. 예약 취소가 이루어진 뒤 이 배열이 비어 있고 financialImpactCustomerOutstandingAmount와 financialImpactRefundOutstandingAmount도 0인 경우에 금전 처리가 완료된 상태다. 아직 취소하지 않은 예상 조회의 작업 목록은 실행 시 필요한 작업이며 취소·금전 처리 완료를 뜻하지 않는다. 가정: 응답 필드명을 financialImpactRemainingWork, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "확인된 수금·환불, 처리 중/불명 금액과 남은 금전 작업을 분리한다. 필요한 시스템 후속 작업을 중복 없이 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = CancellationRemainingWork.class, description = "가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다.", allowableValues = {"WAIT_PAYMENT_RESULT", "WAIT_REFUND_RESULT", "ISSUE_OR_RECOVER_REFUND", "COLLECT_CANCELLATION_FEE"}))
    @NotNull 
    @Valid
    List<@NotNull CancellationRemainingWork> financialImpactRemainingWork,
    /** 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "금전 작업과 예약 취소를 함께 관측한 RFC3339 시각이다. 최초 취소 판정 시각과 다르다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-12-16T12:00:00+09:00")
    @NotNull 
    OffsetDateTime observedAt
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CancellationResultResponseFinancialImpactPaymentsItem", description = "예약 취소·선점 만료·해제에 따른 후속 금전 판단에 포함되는 결제 시도. 숙박대금과 취소 비용 청구를 구분하고 기존 시도 결과만 기록한다.")
    public record FinancialImpactPaymentsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "내부 청구 식별자는 저장소 형식을 노출하지 않는 1~64자로 정했다. 가정: 응답 필드명을 chargeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "chg_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String chargeId,
        /** 청구의 기존 논리 결제 시도를 식별한다. 수금 시작 전에는 null이다. 가정: 응답 필드명을 attemptId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 필드명 attemptId, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 청구가 아직 결제를 시작하지 않았음을 뜻한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 64)
        String attemptId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "숙박대금과 취소 비용을 같은 의무로 합산하지 않기 위한 수금 목적이다. 가정: 응답 필드명을 purpose, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"STAY", "CANCELLATION_FEE"})
        @NotNull 
        CancellationPaymentPurpose purpose,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "SCHEDULED는 아직 시작 전, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 결과 확인 대기, CANCELLED는 실행 전 청구 중단이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"})
        @NotNull 
        PaymentStatus status,
        /** 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
        @Schema(description = "같은 배열 항목의 amount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "서버에 이 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-12-16T12:00:00+09:00")
        @NotNull 
        OffsetDateTime updatedAt,
        /** 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명을 실패로 채우지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 실패 사유가 없음을 뜻한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CancellationResultResponseFinancialImpactRefundsItem", description = "예약 취소·선점 만료·해제에 따라 원결제에 연결된 환불 작업. 외부 환불 성공은 은행 입금 시각을 보장하지 않는다.")
    public record FinancialImpactRefundsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "환불 작업의 1~64자 opaque 식별자로 중복 작업을 구분한다. 가정: 응답 필드명을 refundId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "rfd_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String refundId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "성공한 원결제 시도의 1~64자 식별자이며 환불의 귀속을 보존한다. 가정: 응답 필드명을 originalPaymentId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "pay_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String originalPaymentId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "PENDING은 실행 대기, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 기존 작업 확인 대기이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"PENDING", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN"})
        @NotNull 
        RefundStatus status,
        /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 독립적으로 보존하기 위한 선택이다. */
        @Schema(description = "같은 배열 항목의 amount에 기록한 금액의 통화이며 원화 KRW만 허용한다. 가정: 통화를 문자열 KRW로 명시하여 금액의 원 단위를 독립적으로 전달한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "서버에 환불 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-12-16T12:00:00+09:00")
        @NotNull 
        OffsetDateTime updatedAt,
        /** 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명에는 실패 사유를 만들지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 환불 실패 사유가 없음을 뜻한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
        @Schema(name = "CancellationPaymentPurpose", description = "가정: CancellationPaymentPurpose 문자열 코드를 선택하여 같은 결제라도 수금 목적을 구분한다. STAY: 숙박대금 청구; CANCELLATION_FEE: 취소 후 남은 고객 취소 비용의 수금.")
    public enum CancellationPaymentPurpose {
/** 숙박대금 청구. */
    STAY,
    /** 취소 후 남은 고객 취소 비용의 수금. */
    CANCELLATION_FEE
    }
        @Schema(name = "CancellationRemainingWork", description = "가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다.")
    public enum CancellationRemainingWork {
/** 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다. */
    WAIT_PAYMENT_RESULT,
    /** 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다. */
    WAIT_REFUND_RESULT,
    /** 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다. */
    ISSUE_OR_RECOVER_REFUND,
    /** 확인된 미수 취소 비용의 수금이 남아 있다. */
    COLLECT_CANCELLATION_FEE
    }
}

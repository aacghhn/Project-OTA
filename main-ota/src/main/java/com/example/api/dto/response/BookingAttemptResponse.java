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
@Schema(name = "BookingAttemptResponse", description = "예약 요청 결과와 독립적인 결제·환불 후속 상태.")
public record BookingAttemptResponse(
    /** 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "reservation-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String reservationId,
    /** 가정: 응답 필드명을 reservationStatus, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선점·확정·만료·해제·취소 상태. 결제 상태와 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"HELD", "CONFIRMED", "EXPIRED", "RELEASED", "CANCELLED"})
    @NotNull
    ReservationStatus reservationStatus,
    /** 확정 후에도 최초 만료 시각을 보존한다. 가정: 응답 필드명을 heldUntil, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선점 만료 시각. 기본 선점 기간 10분이며 현재 시각이 이 값과 같으면 만료다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T14:11:00+09:00")
    @NotNull
    OffsetDateTime heldUntil,
    /** 가정: 응답 필드명을 confirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확정된 시각. 확정 이력이 없으면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
    OffsetDateTime confirmedAt,
    /** 가정: 응답 필드명을 reasonCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 확인된 미확정 사유 코드이다. PAYMENT_FAILED, HOLD_EXPIRED, CONDITIONS_CHANGED, HOLD_RELEASED 중 하나이며 정상 또는 확인 중이면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"PAYMENT_FAILED", "HOLD_EXPIRED", "CONDITIONS_CHANGED", "HOLD_RELEASED"})
    @Pattern(regexp = "^(?:PAYMENT_FAILED|HOLD_EXPIRED|CONDITIONS_CHANGED|HOLD_RELEASED)$")
    String reasonCode,
    /** 가정: 응답 필드명을 reasonMessage, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 예약 사유 설명. 해당 사유가 없으면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    String reasonMessage,
    /** 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 paymentChargeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 받을 금액과 시점을 정의한 청구 식별자.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "charge-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String paymentChargeId,
    /** 청구와 시도를 구분한다. 가정: 응답 필드명을 paymentAttemptId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 가정: 실제 논리적 결제 시도 ID는 1~64자이며 나중 결제의 예정 상태에서는 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "payment-1")
    @Size(min = 1, max = 64)
    String paymentAttemptId,
    /** 가정: 응답 필드명을 paymentTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 예약에 선택한 결제 시점.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"IMMEDIATE", "LATER"}, example = "IMMEDIATE")
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?:IMMEDIATE|LATER)$")
    String paymentTiming,
    /** UNKNOWN은 실패 확정이 아니다. 가정: 응답 필드명을 paymentStatus, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 예약 상태와 독립된 결제 상태.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"})
    @NotNull
    PaymentStatus paymentStatus,
    /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 이 청구가 이행할 금액.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long paymentAmount,
    /** 가정: paymentCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 paymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 이 청구가 이행할 금액.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String paymentCurrency,
    /** 즉시 결제는 요청 수락 시각이다. 가정: 응답 필드명을 paymentDueAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 나중 결제 예정 시각.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T14:01:00+09:00")
    @NotNull
    OffsetDateTime paymentDueAt,
    /** 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 paymentMethodId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 대상을 구분하는 불투명 식별자.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "paymentMethod-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String paymentMethodId,
    /** 가정: 응답 필드명을 paymentMaskedIdentifier, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 선택 수단의 마스킹된 식별 정보.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "**** 1234")
    @NotBlank
    @Size(min = 1)
    String paymentMaskedIdentifier,
    /** 실패가 아니거나 이유를 확인하지 못했으면 null이다. 가정: 응답 필드명을 paymentFailureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결제 또는 예정 의무의 상태. 확정된 실패 이유.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    String paymentFailureReason,
    /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 remainingPaymentObligationAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "서버가 확인한 남은 청구 금액. 확정된 나중 결제에도 총액의 의무가 남는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long remainingPaymentObligationAmount,
    /** 가정: remainingPaymentObligationCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 remainingPaymentObligationCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "서버가 확인한 남은 청구 금액. 확정된 나중 결제에도 총액의 의무가 남는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String remainingPaymentObligationCurrency,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선점 만료·해제 후 성공한 결제는 예약을 부활시키지 않고 환불 대상으로 처리한다. 가정: 응답 필드명을 refundRequired, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull
    Boolean refundRequired,
    /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundTargetAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 성공 결제 중 환불 대상으로 연결한 금액. 미확인 성공은 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long refundTargetAmount,
    /** 가정: refundTargetCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 refundTargetCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 성공 결제 중 환불 대상으로 연결한 금액. 미확인 성공은 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String refundTargetCurrency,
    /** RETRY_PAYMENT는 reservationStatus=HELD, asOf가 heldUntil보다 이른 시각, paymentTiming=IMMEDIATE, paymentStatus=FAILED를 모두 만족할 때만 반환하며, POST /api/customer/reservation-attempts/{reservationId}/payment-attempts로 자신의 유효한 수단을 다시 검증받아 새 결제 시도를 요청한다. START_NEW_RESERVATION은 reservationStatus가 EXPIRED/RELEASED이고 기존 paymentStatus가 PROCESSING/UNKNOWN이 아닐 때 반환하며, 숙소 검색과 새 상품 견적 확인 단계로 이동해 새 조건에 동의한 별도 예약을 시작한다. 기존 예약을 부활시키는 동작이 아니다. VIEW_RESERVATION은 reservationStatus=CONFIRMED 또는 confirmedAt이 있는 CANCELLED에서 반환하며, GET /api/customer/reservations/{reservationId}로 확정 당시 약속과 현재 예약 상태를 확인한다. VIEW_REFUNDS는 reservationStatus가 EXPIRED/RELEASED/CANCELLED이면 반환하며, GET /api/customer/reservations/{reservationId}/financial-outcome으로 확인된 환불 대상·처리 상태·잔여 금전 의무를 읽는다. 환불이 없거나 금액이 0인 결과도 확인할 수 있고 조회 자체는 환불을 생성하지 않는다. 각 조건을 독립적으로 평가하여 필요한 코드를 중복 없이 함께 반환한다. 예를 들어 만료 후 결제 성공이 확인되어 환불 대상이 생겼으면 START_NEW_RESERVATION과 VIEW_REFUNDS를 함께 반환한다. 만료 후 결제 결과가 UNKNOWN이면 VIEW_RESULT와 VIEW_REFUNDS만 반환한다. paymentStatus가 PROCESSING/UNKNOWN이면 RETRY_PAYMENT와 START_NEW_RESERVATION은 반환하지 않아 기존 결과를 확인하기 전 새 결제를 안내하지 않는다. 상태 코드만 보고 고객이 잘못된 실행을 하지 않도록 행동의 조건과 대상을 명시하는 방식을 선택했다. 가정: 응답 필드명을 allowedActions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 현재 결과에서 가능한 후속 행동을 필수 문자열 배열로 제공하며 허용 코드는 VIEW_RESULT, RETRY_PAYMENT, START_NEW_RESERVATION, VIEW_RESERVATION, VIEW_REFUNDS이다. VIEW_RESULT는 reservationStatus=HELD이거나 EXPIRED/RELEASED 상태에서 paymentStatus가 PROCESSING/UNKNOWN인 경우 반환하며, GET /api/customer/reservation-attempts/{reservationId}로 기존 예약 요청과 결제 결과를 다시 읽는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"VIEW_RESULT\"]"), schema = @Schema(implementation = String.class, allowableValues = {"VIEW_RESULT", "RETRY_PAYMENT", "START_NEW_RESERVATION", "VIEW_RESERVATION", "VIEW_REFUNDS"}))
    @NotNull
    @Valid
    List<@NotNull  @Pattern(regexp = "^(?:VIEW_RESULT|RETRY_PAYMENT|START_NEW_RESERVATION|VIEW_RESERVATION|VIEW_REFUNDS)$") String> allowedActions,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "결과를 평가한 기준 시각. 가정: 응답 필드명을 asOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T14:01:00+09:00")
    @NotNull
    OffsetDateTime asOf
) {
}

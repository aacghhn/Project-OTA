package com.example.api.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;

/** 예약 당시 예정 청구 약속과 현재 예약·결제 결과를 분리해 제공한다. 모든 응답 키는 항상 포함한다. reservationStatus가 CANCELLED가 아니면 cancellationReason·cancellationCancelledAt·cancellationCustomerCostAmount·cancellationCustomerCostCurrency는 모두 null이다. CANCELLED이면 네 값 모두 필수이며 최초 취소 사유·판정 시각·0 이상 원화 비용을 보존한다. 결과 불명에서는 기존 시도를 확인하고 유효한 확정 예약을 유지하며, 확정 실패로 새로 취소할 때에는 유예나 자동 신규 시도 없이 전체 취소하고 고객 비용을 0원으로 한다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "ScheduledPaymentResponse", description = "예약 당시 예정 청구 약속과 현재 예약·결제 결과를 분리해 제공한다. 모든 응답 키는 항상 포함한다. reservationStatus가 CANCELLED가 아니면 cancellationReason·cancellationCancelledAt·cancellationCustomerCostAmount·cancellationCustomerCostCurrency는 모두 null이다. CANCELLED이면 네 값 모두 필수이며 최초 취소 사유·판정 시각·0 이상 원화 비용을 보존한다. 결과 불명에서는 기존 시도를 확인하고 유효한 확정 예약을 유지하며, 확정 실패로 새로 취소할 때에는 유예나 자동 신규 시도 없이 전체 취소하고 고객 비용을 0원으로 한다.")
public record ScheduledPaymentResponse(
    /** 예약 식별자. 가정: 필드명 reservationId와 필수 1~64자 opaque 문자열을 선택해 내부 저장소 형식을 노출하지 않는다. */
    @Schema(description = "예약 식별자. 가정: 필드명 reservationId와 필수 1~64자 opaque 문자열을 선택해 내부 저장소 형식을 노출하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "rsv_01")
    @NotBlank
    @Size(min = 1, max = 64)
    String reservationId,
    /** 현재 예약 상태. 가정: reservationStatus라는 필수 enum으로 제공해 결제 상태와 구분한다. 예정 결제 결과가 불명인 동안 확정 예약을 유지한다. 실패가 확정되면 아직 확정 상태인 예약을 전체 취소하며, 이미 취소된 예약은 최초 취소 결과를 유지한다. */
    @Schema(description = "현재 예약 상태. 가정: reservationStatus라는 필수 enum으로 제공해 결제 상태와 구분한다. 예정 결제 결과가 불명인 동안 확정 예약을 유지한다. 실패가 확정되면 아직 확정 상태인 예약을 전체 취소하며, 이미 취소된 예약은 최초 취소 결과를 유지한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"HELD", "CONFIRMED", "EXPIRED", "RELEASED", "CANCELLED"}, example = "CONFIRMED")
    @NotNull
    ReservationStatus reservationStatus,
    /** 예약 확정 때 준비한 예정 청구 식별자. 가정: chargeId라는 필수 1~64자 opaque 문자열로 정해 중복 실행을 같은 청구에 묶는다. */
    @Schema(description = "예약 확정 때 준비한 예정 청구 식별자. 가정: chargeId라는 필수 1~64자 opaque 문자열로 정해 중복 실행을 같은 청구에 묶는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "chg_01")
    @NotBlank
    @Size(min = 1, max = 64)
    String chargeId,
    /** 예약 당시 약속한 예정 숙박대금이며 이후 상품 가격 변경으로 다시 계산하지 않는다. 가정: scheduledAmount라는 필수 Long으로 원화 정수 원을 제공하고 0 이상으로 제한한다. 32비트 초과 합산을 보존하며 성공 수금액과 취소 비용을 구분하기 위한 선택이다. */
    @Schema(description = "예약 당시 약속한 예정 숙박대금이며 이후 상품 가격 변경으로 다시 계산하지 않는다. 가정: scheduledAmount라는 필수 Long으로 원화 정수 원을 제공하고 0 이상으로 제한한다. 32비트 초과 합산을 보존하며 성공 수금액과 취소 비용을 구분하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long scheduledAmount,
    /** 예약 당시 예정 숙박대금 scheduledAmount의 통화. 가정: scheduledCurrency라는 필수 문자열에 KRW만 허용하여 예정 금액의 원 단위를 명시한다. */
    @Schema(description = "예약 당시 예정 숙박대금 scheduledAmount의 통화. 가정: scheduledCurrency라는 필수 문자열에 KRW만 허용하여 예정 금액의 원 단위를 명시한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String scheduledCurrency,
    /** 예약 당시 청구 예정 시각. 체크인 날짜에서 결제 설정 일수를 뺀 날의 Asia/Seoul 00:00이며 무료 취소 기한 설정과 독립된다. 가정: scheduledAt을 필수 RFC3339 오프셋 날짜·시각으로 제공해 절대 시점을 식별한다. */
    @Schema(description = "예약 당시 청구 예정 시각. 체크인 날짜에서 결제 설정 일수를 뺀 날의 Asia/Seoul 00:00이며 무료 취소 기한 설정과 독립된다. 가정: scheduledAt을 필수 RFC3339 오프셋 날짜·시각으로 제공해 절대 시점을 식별한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-12-17T00:00:00+09:00")
    @NotNull
    OffsetDateTime scheduledAt,
    /** 숙소의 날짜·예정 시각을 해석하는 기준 시간대. 가정: timeZone이라는 필수 문자열을 사용하고 Asia/Seoul만 허용하여 현지 날짜 해석을 명확히 한다. */
    @Schema(description = "숙소의 날짜·예정 시각을 해석하는 기준 시간대. 가정: timeZone이라는 필수 문자열을 사용하고 Asia/Seoul만 허용하여 현지 날짜 해석을 명확히 한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotNull
    @Pattern(regexp = "Asia/Seoul")
    String timeZone,
    /** 예약 확정 당시 미래 청구에 연결한 본인 소유 결제수단의 식별자. 가정: paymentMethodId를 필수 1~64자 opaque 문자열로 제공해 청구용 비밀값과 분리한다. 원본 카드번호나 비밀 토큰을 반환하지 않는다. */
    @Schema(description = "예약 확정 당시 미래 청구에 연결한 본인 소유 결제수단의 식별자. 가정: paymentMethodId를 필수 1~64자 opaque 문자열로 제공해 청구용 비밀값과 분리한다. 원본 카드번호나 비밀 토큰을 반환하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "pm_01")
    @NotBlank
    @Size(min = 1, max = 64)
    String paymentMethodId,
    /** 예약 당시 미래 청구에 준비한 결제수단 종류의 표시용 이름. 가정: paymentMethodTypeLabel을 필수 1~50자 문자열로 제공한다. 확인되지 않은 카드·은행 선택지 전체를 enum으로 고정하지 않고 실제 등록된 수단 종류를 설명하기 위한 선택이다. */
    @Schema(description = "예약 당시 미래 청구에 준비한 결제수단 종류의 표시용 이름. 가정: paymentMethodTypeLabel을 필수 1~50자 문자열로 제공한다. 확인되지 않은 카드·은행 선택지 전체를 enum으로 고정하지 않고 실제 등록된 수단 종류를 설명하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 50, example = "카드")
    @NotBlank
    @Size(min = 1, max = 50)
    String paymentMethodTypeLabel,
    /** 예약 당시 미래 청구에 준비한 결제수단을 구분하는 마스킹 표시값. 가정: paymentMethodMaskedIdentifier를 필수 1~64자 문자열로 제공해 고객이 수단을 식별할 수 있게 하되 원본 계좌·카드 번호는 노출하지 않는다. 이 조회는 수단 변경이나 새 결제를 실행하지 않는다. */
    @Schema(description = "예약 당시 미래 청구에 준비한 결제수단을 구분하는 마스킹 표시값. 가정: paymentMethodMaskedIdentifier를 필수 1~64자 문자열로 제공해 고객이 수단을 식별할 수 있게 하되 원본 계좌·카드 번호는 노출하지 않는다. 이 조회는 수단 변경이나 새 결제를 실행하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "**** 1234")
    @NotBlank
    @Size(min = 1, max = 64)
    String paymentMethodMaskedIdentifier,
    /** 예정 청구의 현재 결제 상태. SCHEDULED는 예정, PROCESSING은 처리 중, SUCCEEDED는 성공 확인, FAILED는 실패 확인, UNKNOWN은 결과 불명, CANCELLED는 실행 전 중단이다. 가정: paymentStatus라는 필수 enum으로 반환해 예약 상태와 별개로 판단하게 한다. */
    @Schema(description = "예정 청구의 현재 결제 상태. SCHEDULED는 예정, PROCESSING은 처리 중, SUCCEEDED는 성공 확인, FAILED는 실패 확인, UNKNOWN은 결과 불명, CANCELLED는 실행 전 중단이다. 가정: paymentStatus라는 필수 enum으로 반환해 예약 상태와 별개로 판단하게 한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"}, example = "SCHEDULED")
    @NotNull
    PaymentStatus paymentStatus,
    /** 논리적 결제 시도 식별자. 가정: attemptId는 항상 포함하는 nullable 1~64자 문자열로 정했다. 아직 실행하지 않은 SCHEDULED 또는 실행 전 CANCELLED에서는 null이며, 결과 불명을 재확인할 때는 같은 식별자를 유지한다. */
    @Schema(description = "논리적 결제 시도 식별자. 가정: attemptId는 항상 포함하는 nullable 1~64자 문자열로 정했다. 아직 실행하지 않은 SCHEDULED 또는 실행 전 CANCELLED에서는 null이며, 결과 불명을 재확인할 때는 같은 식별자를 유지한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64)
    @Size(min = 1, max = 64)
    String attemptId,
    /** 현재 논리적 결제 시도를 처음 시작한 시각. 가정: attemptStartedAt을 항상 포함하는 nullable RFC3339 오프셋 시각으로 제공하며 시도 생성 전에는 null이다. 기존 시도의 재확인을 새 시도로 혼동하지 않도록 시작 시각을 바꾸지 않는다. */
    @Schema(description = "현재 논리적 결제 시도를 처음 시작한 시각. 가정: attemptStartedAt을 항상 포함하는 nullable RFC3339 오프셋 시각으로 제공하며 시도 생성 전에는 null이다. 기존 시도의 재확인을 새 시도로 혼동하지 않도록 시작 시각을 바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
    OffsetDateTime attemptStartedAt,
    /** 외부 결제의 성공 또는 실패가 확인된 시각. 가정: resultConfirmedAt을 항상 포함하는 nullable RFC3339 오프셋 시각으로 제공한다. SCHEDULED·PROCESSING·UNKNOWN·실행 전 CANCELLED에서는 null이며 시간 초과만으로 값을 채우지 않는다. */
    @Schema(description = "외부 결제의 성공 또는 실패가 확인된 시각. 가정: resultConfirmedAt을 항상 포함하는 nullable RFC3339 오프셋 시각으로 제공한다. SCHEDULED·PROCESSING·UNKNOWN·실행 전 CANCELLED에서는 null이며 시간 초과만으로 값을 채우지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
    OffsetDateTime resultConfirmedAt,
    /** 실패가 확인된 결제의 고객 공개 사유. 가정: failureReason을 항상 포함하는 nullable 1~500자 문자열로 정했다. FAILED에서만 값을 제공하고 나머지 상태에서는 null이며 읽을 수 있는 사유를 제공하되 외부 민감 정보는 숨긴다. */
    @Schema(description = "실패가 확인된 결제의 고객 공개 사유. 가정: failureReason을 항상 포함하는 nullable 1~500자 문자열로 정했다. FAILED에서만 값을 제공하고 나머지 상태에서는 null이며 읽을 수 있는 사유를 제공하되 외부 민감 정보는 숨긴다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
    @Size(min = 1, max = 500)
    String failureReason,
    /** 이 예정 청구에 대해 성공이 확인된 수금액만 합산한 값. 처리 중·결과 불명·실패 금액은 포함하지 않으며 중복 통지를 재합산하지 않는다. 가정: successfulCollectionAmount를 필수 0 이상 Long 원화 정수 원으로 제공해 32비트 초과 합산을 보존하고 예정 금액과 구분한다. */
    @Schema(description = "이 예정 청구에 대해 성공이 확인된 수금액만 합산한 값. 처리 중·결과 불명·실패 금액은 포함하지 않으며 중복 통지를 재합산하지 않는다. 가정: successfulCollectionAmount를 필수 0 이상 Long 원화 정수 원으로 제공해 32비트 초과 합산을 보존하고 예정 금액과 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long successfulCollectionAmount,
    /** 성공이 확인된 예정 청구 수금액 successfulCollectionAmount의 통화. 가정: successfulCollectionCurrency라는 필수 문자열에 KRW만 허용하여 수금액의 원 단위를 명시한다. */
    @Schema(description = "성공이 확인된 예정 청구 수금액 successfulCollectionAmount의 통화. 가정: successfulCollectionCurrency라는 필수 문자열에 KRW만 허용하여 수금액의 원 단위를 명시한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String successfulCollectionCurrency,
    /** 유지 중인 예약의 예정 숙박대금에서 성공 수금액을 뺀 남은 의무이며 예약 취소 후에는 0원이다. 별도로 발생한 취소 비용 의무와 환불은 예약 금전 결과에서 조회한다. 가정: remainingScheduledObligationAmount를 필수 0 이상 Long 원화 정수 원으로 제공해 32비트 초과 금액을 보존하고 남은 숙박대금과 취소 비용을 구분한다. */
    @Schema(description = "유지 중인 예약의 예정 숙박대금에서 성공 수금액을 뺀 남은 의무이며 예약 취소 후에는 0원이다. 별도로 발생한 취소 비용 의무와 환불은 예약 금전 결과에서 조회한다. 가정: remainingScheduledObligationAmount를 필수 0 이상 Long 원화 정수 원으로 제공해 32비트 초과 금액을 보존하고 남은 숙박대금과 취소 비용을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long remainingScheduledObligationAmount,
    /** 남은 예정 숙박대금 의무 remainingScheduledObligationAmount의 통화. 가정: remainingScheduledObligationCurrency라는 필수 문자열에 KRW만 허용하여 남은 의무의 원 단위를 명시한다. */
    @Schema(description = "남은 예정 숙박대금 의무 remainingScheduledObligationAmount의 통화. 가정: remainingScheduledObligationCurrency라는 필수 문자열에 KRW만 허용하여 남은 의무의 원 단위를 명시한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String remainingScheduledObligationCurrency,
    /** 예약 취소로 신규 예정 숙박대금 수금을 중단했는지 나타낸다. 가정: scheduledCollectionStopped는 필수 boolean이며 취소된 예약에서 true다. 이미 진행 중인 시도의 결과 확인은 계속하므로 resultCheckPending과 동시에 true일 수 있다. */
    @Schema(description = "예약 취소로 신규 예정 숙박대금 수금을 중단했는지 나타낸다. 가정: scheduledCollectionStopped는 필수 boolean이며 취소된 예약에서 true다. 이미 진행 중인 시도의 결과 확인은 계속하므로 resultCheckPending과 동시에 true일 수 있다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull
    Boolean scheduledCollectionStopped,
    /** 기존 결제 시도의 결과 확인이 남았는지 나타낸다. 가정: resultCheckPending은 필수 boolean이며 PROCESSING 또는 UNKNOWN에서 true다. 기존 결과 확인과 새 결제 실행을 구분하기 위한 값이며 조회 자체는 새 시도나 수단 변경을 실행하지 않는다. */
    @Schema(description = "기존 결제 시도의 결과 확인이 남았는지 나타낸다. 가정: resultCheckPending은 필수 boolean이며 PROCESSING 또는 UNKNOWN에서 true다. 기존 결과 확인과 새 결제 실행을 구분하기 위한 값이며 조회 자체는 새 시도나 수단 변경을 실행하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull
    Boolean resultCheckPending,
    /** 최초 예약 취소의 업무 사유. 가정: cancellationReason은 항상 포함하는 nullable enum으로 제공한다. reservationStatus가 CANCELLED가 아니면 취소의 네 필드 모두 null이고 CANCELLED이면 네 필드 모두 null이 아니어야 한다. 예정 결제 실패로 새로 취소하면 SCHEDULED_PAYMENT_FAILED이며 이미 취소된 예약의 기존 사유는 유지한다. 허용값은 CUSTOMER_REQUEST·SCHEDULED_PAYMENT_FAILED·PROPERTY_UNAVAILABLE로 정해 고객 요청·시스템 실패·숙소 제공 불가를 구분한다. */
    @Schema(description = "최초 예약 취소의 업무 사유. 가정: cancellationReason은 항상 포함하는 nullable enum으로 제공한다. reservationStatus가 CANCELLED가 아니면 취소의 네 필드 모두 null이고 CANCELLED이면 네 필드 모두 null이 아니어야 한다. 예정 결제 실패로 새로 취소하면 SCHEDULED_PAYMENT_FAILED이며 이미 취소된 예약의 기존 사유는 유지한다. 허용값은 CUSTOMER_REQUEST·SCHEDULED_PAYMENT_FAILED·PROPERTY_UNAVAILABLE로 정해 고객 요청·시스템 실패·숙소 제공 불가를 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"CUSTOMER_REQUEST", "SCHEDULED_PAYMENT_FAILED", "PROPERTY_UNAVAILABLE"}, example = "SCHEDULED_PAYMENT_FAILED")
    CancellationReason cancellationReason,
    /** 최초 예약 취소를 판정한 시각. 가정: cancellationCancelledAt은 항상 포함하는 nullable RFC3339 오프셋 시각으로 제공한다. reservationStatus가 CANCELLED가 아니면 null이고 CANCELLED이면 반드시 값을 제공한다. 반복 조회나 후속 결제 실패로 최초 판정 시각을 바꾸지 않기 위한 결정이다. */
    @Schema(description = "최초 예약 취소를 판정한 시각. 가정: cancellationCancelledAt은 항상 포함하는 nullable RFC3339 오프셋 시각으로 제공한다. reservationStatus가 CANCELLED가 아니면 null이고 CANCELLED이면 반드시 값을 제공한다. 반복 조회나 후속 결제 실패로 최초 판정 시각을 바꾸지 않기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-17T00:00:02+09:00")
    OffsetDateTime cancellationCancelledAt,
    /** 최초 예약 취소에서 확정한 고객 부담 비용. 예정 결제 실패 때문에 새로 취소한 경우 0원이며 이미 취소된 예약은 최초 비용을 유지한다. 가정: cancellationCustomerCostAmount는 항상 포함하는 nullable Long 원화 정수 원으로 제공한다. reservationStatus가 CANCELLED가 아니면 null이고 CANCELLED이면 0 이상의 값을 반드시 제공한다. 32비트 초과 합산을 보존하고 환불 금액과 구분하기 위한 선택이다. */
    @Schema(description = "최초 예약 취소에서 확정한 고객 부담 비용. 예정 결제 실패 때문에 새로 취소한 경우 0원이며 이미 취소된 예약은 최초 비용을 유지한다. 가정: cancellationCustomerCostAmount는 항상 포함하는 nullable Long 원화 정수 원으로 제공한다. reservationStatus가 CANCELLED가 아니면 null이고 CANCELLED이면 0 이상의 값을 반드시 제공한다. 32비트 초과 합산을 보존하고 환불 금액과 구분하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationCustomerCostAmount,
    /** 최초 예약 취소의 고객 부담 비용 cancellationCustomerCostAmount의 통화. 가정: cancellationCustomerCostCurrency는 항상 포함하는 nullable 문자열로 제공한다. reservationStatus가 CANCELLED가 아니면 null이고 CANCELLED이면 KRW를 반드시 제공하여 취소 비용의 원 단위를 명시한다. */
    @Schema(description = "최초 예약 취소의 고객 부담 비용 cancellationCustomerCostAmount의 통화. 가정: cancellationCustomerCostCurrency는 항상 포함하는 nullable 문자열로 제공한다. reservationStatus가 CANCELLED가 아니면 null이고 CANCELLED이면 KRW를 반드시 제공하여 취소 비용의 원 단위를 명시한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationCustomerCostCurrency,
    /** 반환한 예약·결제·금액을 함께 관측한 조회 기준 시각. 가정: observedAt을 필수 RFC3339 오프셋 시각으로 제공해 서로 다른 시점의 상태를 하나의 결과로 오인하지 않게 한다. */
    @Schema(description = "반환한 예약·결제·금액을 함께 관측한 조회 기준 시각. 가정: observedAt을 필수 RFC3339 오프셋 시각으로 제공해 서로 다른 시점의 상태를 하나의 결과로 오인하지 않게 한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull
    OffsetDateTime observedAt
) {
}

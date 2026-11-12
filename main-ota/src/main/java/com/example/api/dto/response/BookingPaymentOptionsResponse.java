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

/** 결제 시점과 수단 선택지. 결제나 예약을 실행하지 않는다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "BookingPaymentOptionsResponse", description = "결제 시점과 수단 선택지. 결제나 예약을 실행하지 않는다.")
public record BookingPaymentOptionsResponse(
    /** 지금 결제와 나중에 결제 두 항목 및 비활성 이유. 가정: 응답 필드명을 timings, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "지금 결제와 나중에 결제 두 항목 및 비활성 이유. 가정: 응답 필드명을 timings, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = TimingsItem.class), minItems = 2, maxItems = 2)
    @NotNull
    @Size(min = 2, max = 2)
    @Valid
    List<@NotNull  TimingsItem> timings,
    /** 고객 자신의 사용할 수단. 없으면 빈 배열로 반환하고 예약 생성 시 수단 없는 요청은 거절한다. 가정: 응답 필드명을 methods, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "고객 자신의 사용할 수단. 없으면 빈 배열로 반환하고 예약 생성 시 수단 없는 요청은 거절한다. 가정: 응답 필드명을 methods, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = MethodsItem.class))
    @NotNull
    @Valid
    List<@NotNull  MethodsItem> methods
) {
    /** 지금 결제 또는 나중에 결제의 조건. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPaymentOptionsResponseTimingsItem", description = "지금 결제 또는 나중에 결제의 조건.")
    public record TimingsItem(
        /** 결제 시점 코드. IMMEDIATE는 지금 결제, LATER는 나중에 결제이다. 가정: 응답 필드명을 timing, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "결제 시점 코드. IMMEDIATE는 지금 결제, LATER는 나중에 결제이다. 가정: 응답 필드명을 timing, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, allowableValues = {"IMMEDIATE", "LATER"}, example = "IMMEDIATE")
        @NotBlank
        @Size(min = 1)
        @Pattern(regexp = "^(?:IMMEDIATE|LATER)$")
        String timing,
        /** 선택 문구. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "선택 문구. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, allowableValues = {"지금 결제", "나중에 결제"}, example = "지금 결제")
        @NotBlank
        @Size(min = 1)
        @Pattern(regexp = "^(?:지금\\ 결제|나중에\\ 결제)$")
        String label,
        /** 상품 허용 시점이고 나중 결제 예정 시각이 미래이면 선택할 수 있다. 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "상품 허용 시점이고 나중 결제 예정 시각이 미래이면 선택할 수 있다. 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
        Boolean enabled,
        /** 상품에서 허용하지 않거나 예정 시각이 이미 지났으면 그 이유를 반환한다. 사용 가능이면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "상품에서 허용하지 않거나 예정 시각이 이미 지났으면 그 이유를 반환한다. 사용 가능이면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        String disabledReason,
        /** 가정: 초기 선택은 false이며 고객이 선택한다. 가정: 응답 필드명을 initiallySelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 초기 선택은 false이며 고객이 선택한다. 가정: 응답 필드명을 initiallySelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"false"}, example = "false")
        @NotNull
        @AssertFalse
        Boolean initiallySelected,
        /** 지금 결제의 총액 또는 나중 결제의 0원. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amountNowAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "지금 결제의 총액 또는 나중 결제의 0원. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amountNowAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amountNowAmount,
        /** 지금 결제의 총액 또는 나중 결제의 0원. 가정: amountNowCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 amountNowCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "지금 결제의 총액 또는 나중 결제의 0원. 가정: amountNowCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 amountNowCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String amountNowCurrency,
        /** 나중 결제의 총액 또는 지금 결제의 0원. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 scheduledAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "나중 결제의 총액 또는 지금 결제의 0원. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 scheduledAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long scheduledAmount,
        /** 나중 결제의 총액 또는 지금 결제의 0원. 가정: scheduledCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 scheduledCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "나중 결제의 총액 또는 지금 결제의 0원. 가정: scheduledCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 scheduledCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String scheduledCurrency,
        /** 나중 결제 예정 시각. 지금 결제는 null이다. 날짜를 고객이 선택할 수는 없다. 가정: 응답 필드명을 scheduledAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "나중 결제 예정 시각. 지금 결제는 null이다. 날짜를 고객이 선택할 수는 없다. 가정: 응답 필드명을 scheduledAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime scheduledAt
    ) {
    }
    /** 소유권을 확인한 저장 결제수단. 새 수단 등록은 별도 결제수단 기능에 속한다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPaymentOptionsResponseMethodsItem", description = "소유권을 확인한 저장 결제수단. 새 수단 등록은 별도 결제수단 기능에 속한다.")
    public record MethodsItem(
        /** 대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 paymentMethodId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 paymentMethodId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "paymentMethod-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String paymentMethodId,
        /** 가정: 초기 결제수단은 토큰화된 CARD만 지원한다. 원문에 수단 종류가 없어 저장된 결제 참조로 한정했다. 가정: 응답 필드명을 type, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 초기 결제수단은 토큰화된 CARD만 지원한다. 원문에 수단 종류가 없어 저장된 결제 참조로 한정했다. 가정: 응답 필드명을 type, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, allowableValues = {"CARD"}, example = "CARD")
        @NotBlank
        @Size(min = 1)
        @Pattern(regexp = "^(?:CARD)$")
        String type,
        /** 가정: 카드 원문 없이 마스킹된 식별 문자열만 제공한다. 소유자 확인과 선택에 사용한다. 가정: 응답 필드명을 maskedIdentifier, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 카드 원문 없이 마스킹된 식별 문자열만 제공한다. 소유자 확인과 선택에 사용한다. 가정: 응답 필드명을 maskedIdentifier, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "**** 1234")
        @NotBlank
        @Size(min = 1)
        String maskedIdentifier,
        /** 미래 자동 청구에 유효한 준비가 되어 있는지 나타낸다. 가정: 응답 필드명을 billingReady, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "미래 자동 청구에 유효한 준비가 되어 있는지 나타낸다. 가정: 응답 필드명을 billingReady, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
        Boolean billingReady,
        /** 현재 이 고객이 이 견적에 사용할 수 있는 수단인지 표시한다. 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "현재 이 고객이 이 견적에 사용할 수 있는 수단인지 표시한다. 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
        Boolean enabled,
        /** 사용 불가 이유. enabled=true이면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "사용 불가 이유. enabled=true이면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        String disabledReason,
        /** 가정: 수단은 자동 선택하지 않고 false로 제공한다. 고객이 사용할 수단을 명시하게 한다. 가정: 응답 필드명을 initiallySelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 수단은 자동 선택하지 않고 false로 제공한다. 고객이 사용할 수단을 명시하게 한다. 가정: 응답 필드명을 initiallySelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"false"}, example = "false")
        @NotNull
        @AssertFalse
        Boolean initiallySelected
    ) {
    }
}

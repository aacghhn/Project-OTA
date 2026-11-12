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

/** 요금제의 현재 혜택·취소·결제 구성. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CatalogRatePlanResponse", description = "요금제의 현재 혜택·취소·결제 구성.")
public record CatalogRatePlanResponse(
    /** 생성된 요금제 식별자. 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "생성된 요금제 식별자. 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "rate_1")
    @NotBlank
    
    @Size(min = 1, max = 64)
    String ratePlanId,
    /** 가정: 저장 성공마다 증가하는 요금제 버전. 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 저장 성공마다 증가하는 요금제 버전. 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "1")
    @NotNull
    
    @Min(0)
    Long version,
    /** 고객이 비교할 요금제 이름. 가정: 1~200자로 내부 이름과 별도 입력한다. 가정: 응답 필드명을 publicName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "고객이 비교할 요금제 이름. 가정: 1~200자로 내부 이름과 별도 입력한다. 가정: 응답 필드명을 publicName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "조식 포함 요금")
    @NotBlank
    
    @Size(min = 1, max = 200)
    String publicName,
    /** 운영자가 식별할 내부 이름. 가정: 필수 1~200자이며 고객에게 노출하지 않는다. 가정: 응답 필드명을 internalName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "운영자가 식별할 내부 이름. 가정: 필수 1~200자이며 고객에게 노출하지 않는다. 가정: 응답 필드명을 internalName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "조식 후불")
    @NotBlank
    
    @Size(min = 1, max = 200)
    String internalName,
    /** 포함 혜택 각각의 이름·설명·조건·표시 수량. 가정: 최대 50개이며 빈 배열은 포함 혜택 없음이다. 가정: 응답 필드명을 benefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "포함 혜택 각각의 이름·설명·조건·표시 수량. 가정: 최대 50개이며 빈 배열은 포함 혜택 없음이다. 가정: 응답 필드명을 benefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BenefitsItem.class), minItems = 0, maxItems = 50)
    @NotNull
    
    @Size(min = 0, max = 50)
    @Valid
    List<@NotNull  BenefitsItem> benefits,
    /** 요금제의 취소 정책과 무료 취소 설정 일수. 가정: 자체 정책 코드를 FREE_CANCELLATION과 NON_REFUNDABLE로 정해 두 조건을 구분한다. 가정: 응답 필드명을 cancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "요금제의 취소 정책과 무료 취소 설정 일수. 가정: 자체 정책 코드를 FREE_CANCELLATION과 NON_REFUNDABLE로 정해 두 조건을 구분한다. 가정: 응답 필드명을 cancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^(FREE_CANCELLATION|NON_REFUNDABLE)$", minLength = 1, maxLength = 32, allowableValues = {"FREE_CANCELLATION", "NON_REFUNDABLE"}, example = "FREE_CANCELLATION")
    @NotBlank
    
    @Size(min = 1, max = 32)
    @Pattern(regexp = "^(FREE_CANCELLATION|NON_REFUNDABLE)$")
    String cancellationPolicyKind,
    /** 요금제의 취소 정책과 무료 취소 설정 일수. cancellationPolicyKind가 FREE_CANCELLATION이면 필수인 음이 아닌 일수 N. 체크인 N일 전 Asia/Seoul 00:00 전은 비용 0, 같거나 이후는 총액 전액이다. cancellationPolicyKind가 NON_REFUNDABLE이면 null이며 고객 취소 비용은 항상 총액 전액이다. 가정: 응답 필드명을 cancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: paymentTermsScheduledDaysBeforeCheckIn과 독립된 설정이며 두 일수를 같게 제한하지 않는다. */
    @Schema(description = "요금제의 취소 정책과 무료 취소 설정 일수. cancellationPolicyKind가 FREE_CANCELLATION이면 필수인 음이 아닌 일수 N. 체크인 N일 전 Asia/Seoul 00:00 전은 비용 0, 같거나 이후는 총액 전액이다. cancellationPolicyKind가 NON_REFUNDABLE이면 null이며 고객 취소 비용은 항상 총액 전액이다. 가정: 응답 필드명을 cancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: paymentTermsScheduledDaysBeforeCheckIn과 독립된 설정이며 두 일수를 같게 제한하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", example = "3")
    @Min(0)
    Integer cancellationPolicyFreeCancellationDaysBeforeCheckIn,
    /** 요금제의 결제 시점과 나중 결제 설정 일수. 가정: 자체 결제 시점 코드는 IMMEDIATE와 LATER이다. 가정: 응답 필드명을 paymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "요금제의 결제 시점과 나중 결제 설정 일수. 가정: 자체 결제 시점 코드는 IMMEDIATE와 LATER이다. 가정: 응답 필드명을 paymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^(IMMEDIATE|LATER)$", minLength = 1, maxLength = 16, allowableValues = {"IMMEDIATE", "LATER"}, example = "LATER")
    @NotBlank
    
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^(IMMEDIATE|LATER)$")
    String paymentTermsTiming,
    /** 요금제의 결제 시점과 나중 결제 설정 일수. paymentTermsTiming이 LATER이면 필수인 음이 아닌 일수 N. 예정 시각은 체크인 N일 전 Asia/Seoul 00:00이다. 신규 선점 시 그 시각이 지났거나 같으면 후불 예약을 거절한다. paymentTermsTiming이 IMMEDIATE이면 null이다. 가정: 응답 필드명을 paymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: cancellationPolicyFreeCancellationDaysBeforeCheckIn과 독립된 설정이며 두 일수를 같게 제한하지 않는다. */
    @Schema(description = "요금제의 결제 시점과 나중 결제 설정 일수. paymentTermsTiming이 LATER이면 필수인 음이 아닌 일수 N. 예정 시각은 체크인 N일 전 Asia/Seoul 00:00이다. 신규 선점 시 그 시각이 지났거나 같으면 후불 예약을 거절한다. paymentTermsTiming이 IMMEDIATE이면 null이다. 가정: 응답 필드명을 paymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: cancellationPolicyFreeCancellationDaysBeforeCheckIn과 독립된 설정이며 두 일수를 같게 제한하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", example = "2")
    @Min(0)
    Integer paymentTermsScheduledDaysBeforeCheckIn
) {
    /** 현재 요금제의 개별 혜택과 이용 조건. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogRatePlanResponseBenefitsItem", description = "현재 요금제의 개별 혜택과 이용 조건.")
    public record BenefitsItem(
        /** 가정: 선택지에서 고른 혜택 식별자이다. null이면 파트너가 직접 작성한 포함 서비스다. 가정: 응답 필드명을 optionId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지에서 고른 혜택 식별자이다. null이면 파트너가 직접 작성한 포함 서비스다. 가정: 응답 필드명을 optionId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64, example = "BREAKFAST")
        @Size(min = 1, max = 64)
        String optionId,
        /** 혜택 이름. 가정: 필수 1~200자다. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택 이름. 가정: 필수 1~200자다. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "조식 포함")
        @NotBlank
    
        @Size(min = 1, max = 200)
        String name,
        /** 혜택의 구체적 제공 내용. 가정: 필수 1~1000자다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택의 구체적 제공 내용. 가정: 필수 1~1000자다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 1000, example = "숙박일마다 제공되는 조식")
        @NotBlank
    
        @Size(min = 1, max = 1000)
        String description,
        /** 혜택의 제공 대상·시각·이용 조건. 가정: 필수 0~1000자로 받고 추가 조건이 없으면 빈 문자열이다. 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택의 제공 대상·시각·이용 조건. 가정: 필수 0~1000자로 받고 추가 조건이 없으면 빈 문자열이다. 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 0, maxLength = 1000, example = "예약 객실에 배정된 투숙객에게 제공")
        @NotNull
    
        @Size(min = 0, max = 1000)
        String conditions,
        /** 가정: 표시용 제공 수량은 선택 양수 정수이며 수량으로 표현하지 않는 혜택은 null이다. 요금 계산에 사용하지 않는다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 표시용 제공 수량은 선택 양수 정수이며 수량으로 표현하지 않는 혜택은 null이다. 요금 계산에 사용하지 않는다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "1")
        @Min(1)
        Integer quantity,
        /** 가정: 표시용 수량의 단위는 선택 1~30자다. quantity가 null이면 반드시 null이고 수량이 있으면 필수다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 표시용 수량의 단위는 선택 1~30자다. quantity가 null이면 반드시 null이고 수량이 있으면 필수다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 30)
        @Size(min = 1, max = 30)
        String unit
    ) {
    }
}

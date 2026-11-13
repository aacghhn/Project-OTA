package com.example.api.dto.request;
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
@Schema(name = "CatalogRatePlanUpdateRequest", description = "요금제 현재 설정의 전체 교체. 기존 예약 당시 혜택·취소·결제 약속은 그대로 보존한다.")
public record CatalogRatePlanUpdateRequest(
    /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "가정: 현재 요금제 버전을 필수로 받아 동시 편집 충돌을 검사한다. 가정: 필드명 expectedVersion, integer 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "1")
    @NotNull

    @Min(0)
    Long expectedVersion,
    /** 가정: 필드명 publicName, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "고객이 비교할 요금제 이름. 가정: 1~200자로 내부 이름과 별도 입력한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "조식 포함 요금")
    @NotBlank

    @Size(min = 1, max = 200)
    String publicName,
    /** 가정: 필드명 internalName, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "운영자가 식별할 내부 이름. 가정: 필수 1~200자이며 고객에게 노출하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "조식 후불")
    @NotBlank

    @Size(min = 1, max = 200)
    String internalName,
    /** 가정: 필드명 benefits, array 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "포함 혜택 각각의 이름·설명·조건·표시 수량. 가정: 최대 50개이며 빈 배열은 포함 혜택 없음이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = BenefitsItem.class))
    @NotNull

    @Size(max = 50)
    @Valid
    List<@NotNull  BenefitsItem> benefits,
    /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "요금제의 취소 정책과 무료 취소 설정 일수. 가정: 필드명 cancellationPolicy, 구조 객체 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull

    @Valid
    CancellationPolicy cancellationPolicy,
    /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "요금제의 결제 시점과 나중 결제 설정 일수. 가정: 필드명 paymentTerms, 구조 객체 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull

    @Valid
    PaymentTerms paymentTerms
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogRatePlanUpdateRequestBenefitsItem", description = "요금제에 포함되는 혜택. 숙소·객실 시설과 분리하며 수량은 표시용으로만 사용한다.")
    public record BenefitsItem(
        /** 가정: 필드명 optionId, string 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 선택지에서 고른 혜택 식별자이다. null이면 파트너가 직접 작성한 포함 서비스다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, example = "BREAKFAST")
        @Size(min = 1, max = 64)
        String optionId,
        /** 가정: 필드명 name, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "혜택 이름. 가정: 필수 1~200자다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "조식 포함")
        @NotBlank

        @Size(min = 1, max = 200)
        String name,
        /** 가정: 필드명 description, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "혜택의 구체적 제공 내용. 가정: 필수 1~1000자다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "숙박일마다 제공되는 조식")
        @NotBlank

        @Size(min = 1, max = 1000)
        String description,
        /** 가정: 필드명 conditions, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "혜택의 제공 대상·시각·이용 조건. 가정: 필수 0~1000자로 받고 추가 조건이 없으면 빈 문자열이다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예약 객실에 배정된 투숙객에게 제공")
        @NotNull

        @Size(max = 1000)
        String conditions,
        /** 가정: 필드명 quantity, integer 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 표시용 제공 수량은 선택 양수 정수이며 수량으로 표현하지 않는 혜택은 null이다. 요금 계산에 사용하지 않는다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, format = "int32")
        @Min(1)
        Integer quantity,
        /** 가정: 필드명 unit, string 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 표시용 수량의 단위는 선택 1~30자다. quantity가 null이면 반드시 null이고 수량이 있으면 필수다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        @Size(min = 1, max = 30)
        String unit
    ) {
    }
    /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogRatePlanUpdateRequestCancellationPolicy", description = "요금제의 취소 정책과 무료 취소 설정 일수. 가정: 필드명 cancellationPolicy, 구조 객체 표현과 null 불허를 선택했다.")
    public record CancellationPolicy(
        /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 자체 정책 코드를 FREE_CANCELLATION과 NON_REFUNDABLE로 정해 두 조건을 구분한다. 가정: 필드명 kind, string 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^(FREE_CANCELLATION|NON_REFUNDABLE)$", allowableValues = {"FREE_CANCELLATION", "NON_REFUNDABLE"}, example = "FREE_CANCELLATION")
        @NotBlank

        @Size(min = 1, max = 32)
        @Pattern(regexp = "^(FREE_CANCELLATION|NON_REFUNDABLE)$")
        String kind,
        /** NON_REFUNDABLE이면 null이며 고객 취소 비용은 항상 총액 전액이다. 가정: 필드명 freeCancellationDaysBeforeCheckIn, integer 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "FREE_CANCELLATION이면 필수인 음이 아닌 일수 N. 체크인 N일 전 Asia/Seoul 00:00 전은 비용 0, 같거나 이후는 총액 전액이다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, format = "int32", example = "3")
        @Min(0)
        Integer freeCancellationDaysBeforeCheckIn
    ) {
    }
    /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogRatePlanUpdateRequestPaymentTerms", description = "요금제의 결제 시점과 나중 결제 설정 일수. 가정: 필드명 paymentTerms, 구조 객체 표현과 null 불허를 선택했다.")
    public record PaymentTerms(
        /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 자체 결제 시점 코드는 IMMEDIATE와 LATER이다. 가정: 필드명 timing, string 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^(IMMEDIATE|LATER)$", allowableValues = {"IMMEDIATE", "LATER"}, example = "LATER")
        @NotBlank

        @Size(min = 1, max = 16)
        @Pattern(regexp = "^(IMMEDIATE|LATER)$")
        String timing,
        /** 신규 선점 시 그 시각이 지났거나 같으면 후불 예약을 거절한다. IMMEDIATE이면 null이다. 가정: 필드명 scheduledDaysBeforeCheckIn, integer 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "LATER이면 필수인 음이 아닌 일수 N. 예정 시각은 체크인 N일 전 Asia/Seoul 00:00이다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, format = "int32", example = "2")
        @Min(0)
        Integer scheduledDaysBeforeCheckIn
    ) {
    }
}

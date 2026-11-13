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
@Schema(name = "BookingCreateRequest", description = "최종 조건에 동의한 예약 요청.")
public record BookingCreateRequest(
    /** 가정: 서버 필드명을 quoteToken로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "가정: 동의한 최종 견적을 담은 1~4096자 서명 토큰을 보낸다. 고객이 금액이나 서버 계산 기한을 입력하지 않도록 한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "quote-token-1")
    @NotBlank
    @Size(min = 1, max = 4096)
    String quoteToken,
    /** 가정: 서버 필드명을 representativeGuest로 정하고 이름과 성을 구분하는 객체 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "예약자와 별개일 수 있는 대표 투숙객.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Valid
    RepresentativeGuest representativeGuest,
    /** 가정: 서버 필드명을 consents로 정하고 array 구조 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @ArraySchema(arraySchema = @Schema(description = "이용약관과 개인정보 처리방침의 명시적 동의를 각각 제출한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ConsentsItem.class))
    @NotNull
    @Size(min = 2, max = 2)
    @Valid
    List<@NotNull  ConsentsItem> consents,
    /** 가정: 서버 필드명을 paymentTiming로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "선택한 결제 시점. 나중 결제 부적합 시 즉시 결제로 바꾸지 않고 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"IMMEDIATE", "LATER"}, example = "IMMEDIATE")
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?:IMMEDIATE|LATER)$")
    String paymentTiming,
    /** 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 서버 필드명을 paymentMethodId로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "고객 자신의 유효한 결제수단 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "paymentMethod-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String paymentMethodId
) {
    /** 가정: 서버 필드명을 representativeGuest로 정하고 이름과 성을 구분하는 객체 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingCreateRequestRepresentativeGuest", description = "예약자와 별개일 수 있는 대표 투숙객.")
    public record RepresentativeGuest(
        /** 예약자와 다른 사람을 표현하고 이름/성을 분리하기 위한 결정이다. 가정: 서버 필드명을 firstName로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "대표 투숙객의 이름. 가정: firstName으로 명명하고 공백만 있는 값은 거절하며 길이는 1~100자로 제한한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "Gildong")
        @NotBlank
        @Size(min = 1, max = 100)
        String firstName,
        /** 이름과 별도 필드로 전달한다. 가정: 서버 필드명을 lastName로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "대표 투숙객의 성. 가정: lastName으로 명명하고 공백만 있는 값은 거절하며 길이는 1~100자로 제한한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "Hong")
        @NotBlank
        @Size(min = 1, max = 100)
        String lastName
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingCreateRequestConsentsItem", description = "명시적으로 확인한 동의.")
    public record ConsentsItem(
        /** 가정: 서버 필드명을 type로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "동의한 대상. 두 종류를 각각 한 번씩 제출해야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"TERMS_OF_USE", "PRIVACY_POLICY"}, example = "TERMS_OF_USE")
        @NotBlank
        @Size(min = 1)
        @Pattern(regexp = "^(?:TERMS_OF_USE|PRIVACY_POLICY)$")
        String type,
        /** 가정: 서버 필드명을 version로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "가정: 견적에서 제시한 버전과 정확히 일치하는 1~64자 값을 필수로 보낸다. 다른 버전 동의를 재사용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "2026-11-11")
        @NotBlank
        @Size(min = 1, max = 64)
        String version,
        /** 가정: 서버 필드명을 accepted로 정하고 불리언 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "가정: 필수 동의이므로 true만 수락한다. 동의하지 않은 상태에서 선점과 결제를 시작하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"true"}, example = "true")
        @NotNull
        @AssertTrue
        Boolean accepted
    ) {
    }
}

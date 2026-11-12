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

/** 숙소 검색 입력에 사용할 선택지. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "SearchOptionsResponse", description = "숙소 검색 입력에 사용할 선택지.")
public record SearchOptionsResponse(
    /** 정렬 선택지와 기본 선택 상태. 가정: 응답 필드명을 sortOptions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "정렬 선택지와 기본 선택 상태. 가정: 응답 필드명을 sortOptions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = SortOptionsItem.class))
    @NotNull
    @Valid
    List<@NotNull  SortOptionsItem> sortOptions,
    /** 숙소 유형의 전체 초기 선택지와 상태. 가정: 응답 필드명을 propertyTypeOptions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소 유형의 전체 초기 선택지와 상태. 가정: 응답 필드명을 propertyTypeOptions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PropertyTypeOptionsItem.class))
    @NotNull
    @Valid
    List<@NotNull  PropertyTypeOptionsItem> propertyTypeOptions,
    /** 검색 가격 필터와 표시 금액의 통화. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "검색 가격 필터와 표시 금액의 통화. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, allowableValues = {"KRW"}, example = "KRW")
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?:KRW)$")
    String currency,
    /** 숙박일과 당일 예약 집계의 기준 시간대. 가정: 응답 필드명을 timeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙박일과 당일 예약 집계의 기준 시간대. 가정: 응답 필드명을 timeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?:Asia/Seoul)$")
    String timeZone
) {
    /** 정렬 또는 숙소 유형 선택지. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchOptionsResponseSortOptionsItem", description = "정렬 또는 숙소 유형 선택지.")
    public record SortOptionsItem(
        /** 선택값으로 다시 전송하는 코드. 가정: 정렬 코드는 PRICE_ASC/PRICE_DESC/REVIEW_SCORE_DESC, 숙소 유형 코드는 HOTEL/PRIVATE_HOME/OTHER이며 각각의 목록 안에서 해석한다. 가정: 응답 필드명을 code, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "선택값으로 다시 전송하는 코드. 가정: 정렬 코드는 PRICE_ASC/PRICE_DESC/REVIEW_SCORE_DESC, 숙소 유형 코드는 HOTEL/PRIVATE_HOME/OTHER이며 각각의 목록 안에서 해석한다. 가정: 응답 필드명을 code, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "PRICE_ASC")
        @NotBlank
        @Size(min = 1)
        String code,
        /** 선택지 표시 문구. 가정: 한국어 문구를 제공하며 관찰된 실제 UI 라벨로 주장하지 않는다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "선택지 표시 문구. 가정: 한국어 문구를 제공하며 관찰된 실제 UI 라벨로 주장하지 않는다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "총액 낮은 순")
        @NotBlank
        @Size(min = 1)
        String label,
        /** 가정: 초기 선택 여부를 서버가 명시한다. 기본 정렬만 true이고 숙소 유형 필터는 모두 false이다. 가정: 응답 필드명을 selected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 초기 선택 여부를 서버가 명시한다. 기본 정렬만 true이고 숙소 유형 필터는 모두 false이다. 가정: 응답 필드명을 selected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
        Boolean selected,
        /** 가정: 현재 제공되는 선택지는 true로 반환한다. 비활성 선택지의 상태를 명시적으로 표현하기 위한 필드다. 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 현재 제공되는 선택지는 true로 반환한다. 비활성 선택지의 상태를 명시적으로 표현하기 위한 필드다. 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
        Boolean enabled
    ) {
    }
    /** 정렬 또는 숙소 유형 선택지. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchOptionsResponsePropertyTypeOptionsItem", description = "정렬 또는 숙소 유형 선택지.")
    public record PropertyTypeOptionsItem(
        /** 선택값으로 다시 전송하는 코드. 가정: 정렬 코드는 PRICE_ASC/PRICE_DESC/REVIEW_SCORE_DESC, 숙소 유형 코드는 HOTEL/PRIVATE_HOME/OTHER이며 각각의 목록 안에서 해석한다. 가정: 응답 필드명을 code, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "선택값으로 다시 전송하는 코드. 가정: 정렬 코드는 PRICE_ASC/PRICE_DESC/REVIEW_SCORE_DESC, 숙소 유형 코드는 HOTEL/PRIVATE_HOME/OTHER이며 각각의 목록 안에서 해석한다. 가정: 응답 필드명을 code, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "PRICE_ASC")
        @NotBlank
        @Size(min = 1)
        String code,
        /** 선택지 표시 문구. 가정: 한국어 문구를 제공하며 관찰된 실제 UI 라벨로 주장하지 않는다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "선택지 표시 문구. 가정: 한국어 문구를 제공하며 관찰된 실제 UI 라벨로 주장하지 않는다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "총액 낮은 순")
        @NotBlank
        @Size(min = 1)
        String label,
        /** 가정: 초기 선택 여부를 서버가 명시한다. 기본 정렬만 true이고 숙소 유형 필터는 모두 false이다. 가정: 응답 필드명을 selected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 초기 선택 여부를 서버가 명시한다. 기본 정렬만 true이고 숙소 유형 필터는 모두 false이다. 가정: 응답 필드명을 selected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
        Boolean selected,
        /** 가정: 현재 제공되는 선택지는 true로 반환한다. 비활성 선택지의 상태를 명시적으로 표현하기 위한 필드다. 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 현재 제공되는 선택지는 true로 반환한다. 비활성 선택지의 상태를 명시적으로 표현하기 위한 필드다. 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
        Boolean enabled
    ) {
    }
}

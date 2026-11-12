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

/** 숙소 검색 결과. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "SearchResultResponse", description = "숙소 검색 결과.")
public record SearchResultResponse(
    /** 현재 검색 조건에 맞는 숙소 목록. 없으면 빈 배열. 가정: 응답 필드명을 properties, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "현재 검색 조건에 맞는 숙소 목록. 없으면 빈 배열. 가정: 응답 필드명을 properties, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PropertiesItem.class))
    @NotNull
    @Valid
    List<@NotNull  PropertiesItem> properties,
    /** 0부터 시작하는 반환 페이지. 가정: 응답 필드명을 page, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "0부터 시작하는 반환 페이지. 가정: 응답 필드명을 page, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "0", example = "0")
    @NotNull
    @Min(0)
    Integer page,
    /** 요청에 적용한 페이지 크기. 가정: 응답 필드명을 size, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "요청에 적용한 페이지 크기. 가정: 응답 필드명을 size, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "1", maximum = "100", example = "20")
    @NotNull
    @Min(1)
    @Max(100)
    Integer size,
    /** 다음 페이지가 있는지 나타낸다. 가정: 응답 필드명을 hasNext, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "다음 페이지가 있는지 나타낸다. 가정: 응답 필드명을 hasNext, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull
    Boolean hasNext,
    /** 가격과 목록의 조회 기준 시각. 재고 확보를 의미하지 않는다. 가정: 응답 필드명을 asOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가격과 목록의 조회 기준 시각. 재고 확보를 의미하지 않는다. 가정: 응답 필드명을 asOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T14:00:00+09:00")
    @NotNull
    OffsetDateTime asOf
) {
    /** 숙소 사진 한 장. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchResultResponsePropertiesItemPhotosItem", description = "숙소 사진 한 장.")
    public record PropertiesItemPhotosItem(
        /** 가정: 공개 HTTPS 사진 주소이다. 클라이언트가 사진을 표시할 수 있도록 URL 형태를 선택했다. 가정: 응답 필드명을 url, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 공개 HTTPS 사진 주소이다. 클라이언트가 사진을 표시할 수 있도록 URL 형태를 선택했다. 가정: 응답 필드명을 url, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "uri", pattern = "\\S", minLength = 1, example = "https://example.com/property.jpg")
        @NotBlank
        @Size(min = 1)
        String url,
        /** 가정: 접근성 대체 문구를 필수 문자열로 제공한다. 관찰 자료에 대체 텍스트가 없어 서버 콘텐츠로 정했다. 가정: 응답 필드명을 altText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 접근성 대체 문구를 필수 문자열로 제공한다. 관찰 자료에 대체 텍스트가 없어 서버 콘텐츠로 정했다. 가정: 응답 필드명을 altText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "숙소 정면")
        @NotBlank
        @Size(min = 1)
        String altText,
        /** 사진 설명. 가정: 설명이 없으면 null을 반환한다. 가정: 응답 필드명을 caption, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "사진 설명. 가정: 설명이 없으면 null을 반환한다. 가정: 응답 필드명을 caption, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "숙소 외관")
        String caption,
        /** 가정: 첫 표시용 대표 사진 여부이며 사진이 있으면 정확히 한 장만 true이다. 가정: 응답 필드명을 primary, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 첫 표시용 대표 사진 여부이며 사진이 있으면 정확히 한 장만 true이다. 가정: 응답 필드명을 primary, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
        Boolean primary
    ) {
    }
    /** 공개된 후기 항목. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchResultResponsePropertiesItemReviewsItemsItem", description = "공개된 후기 항목.")
    public record PropertiesItemReviewsItemsItem(
        /** 대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 reviewId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 reviewId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "review-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String reviewId,
        /** 가정: 작성자의 공개 표시명만 제공하고 계정 식별자는 노출하지 않는다. 가정: 응답 필드명을 authorDisplayName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 작성자의 공개 표시명만 제공하고 계정 식별자는 노출하지 않는다. 가정: 응답 필드명을 authorDisplayName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "방문 고객")
        @NotBlank
        @Size(min = 1)
        String authorDisplayName,
        /** 가정: 후기 본문을 제공한다. 후기 관리·작성 기능은 포함하지 않는다. 가정: 응답 필드명을 text, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 후기 본문을 제공한다. 후기 관리·작성 기능은 포함하지 않는다. 가정: 응답 필드명을 text, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "편안하게 이용했습니다.")
        @NotBlank
        @Size(min = 1)
        String text,
        /** 가정: 후기 점수는 0~10이며 숙소 성급과 다른 값이다. 가정: 응답 필드명을 score, JSON 타입을 숫자로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 후기 점수는 0~10이며 숙소 성급과 다른 값이다. 가정: 응답 필드명을 score, JSON 타입을 숫자로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "double", minimum = "0", maximum = "10", example = "8.5")
        @NotNull
        @DecimalMin("0")
        @DecimalMax("10")
        BigDecimal score,
        /** 후기 공개 시각. 가정: 응답 필드명을 publishedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후기 공개 시각. 가정: 응답 필드명을 publishedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-10T09:00:00+09:00")
        @NotNull
        OffsetDateTime publishedAt
    ) {
    }
    /** 주변 편의시설 또는 관광지. 서로 다른 배열에 보존한다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchResultResponsePropertiesItemNearbyAmenitiesItem", description = "주변 편의시설 또는 관광지. 서로 다른 배열에 보존한다.")
    public record PropertiesItemNearbyAmenitiesItem(
        /** 주변 장소 이름. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "주변 장소 이름. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "중앙 공원")
        @NotBlank
        @Size(min = 1)
        String name,
        /** 가정: 숙소로부터 직선거리 미터이며 모르면 null이다. 거리와 이동시간을 혼동하지 않도록 구분한다. 가정: 응답 필드명을 distanceMetres, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 숙소로부터 직선거리 미터이며 모르면 null이다. 거리와 이동시간을 혼동하지 않도록 구분한다. 가정: 응답 필드명을 distanceMetres, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minimum = "0", example = "500")
        @Min(0)
        Integer distanceMetres,
        /** 가정: 도보 예상 분이며 경로 정보가 없으면 null이다. 실측 도착 보장은 아니다. 가정: 응답 필드명을 walkingMinutes, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 도보 예상 분이며 경로 정보가 없으면 null이다. 실측 도착 보장은 아니다. 가정: 응답 필드명을 walkingMinutes, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minimum = "0", example = "8")
        @Min(0)
        Integer walkingMinutes
    ) {
    }
    /** 주변 편의시설 또는 관광지. 서로 다른 배열에 보존한다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchResultResponsePropertiesItemNearbyAttractionsItem", description = "주변 편의시설 또는 관광지. 서로 다른 배열에 보존한다.")
    public record PropertiesItemNearbyAttractionsItem(
        /** 주변 장소 이름. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "주변 장소 이름. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "중앙 공원")
        @NotBlank
        @Size(min = 1)
        String name,
        /** 가정: 숙소로부터 직선거리 미터이며 모르면 null이다. 거리와 이동시간을 혼동하지 않도록 구분한다. 가정: 응답 필드명을 distanceMetres, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 숙소로부터 직선거리 미터이며 모르면 null이다. 거리와 이동시간을 혼동하지 않도록 구분한다. 가정: 응답 필드명을 distanceMetres, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minimum = "0", example = "500")
        @Min(0)
        Integer distanceMetres,
        /** 가정: 도보 예상 분이며 경로 정보가 없으면 null이다. 실측 도착 보장은 아니다. 가정: 응답 필드명을 walkingMinutes, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 도보 예상 분이며 경로 정보가 없으면 null이다. 실측 도착 보장은 아니다. 가정: 응답 필드명을 walkingMinutes, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minimum = "0", example = "8")
        @Min(0)
        Integer walkingMinutes
    ) {
    }
    /** 객실 유형별 기간 전체 판매 여유. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchResultResponsePropertiesItemAvailabilityItem", description = "객실 유형별 기간 전체 판매 여유.")
    public record PropertiesItemAvailabilityItem(
        /** 대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "roomType-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String roomTypeId,
        /** 판매 여유가 귀속되는 객실 유형 이름. 가정: 응답 필드명을 roomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "판매 여유가 귀속되는 객실 유형 이름. 가정: 응답 필드명을 roomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "스탠다드")
        @NotBlank
        @Size(min = 1)
        String roomTypeName,
        /** 가정: 검색한 전체 숙박일에 대해 동일 객실 유형의 OTA 한도-점유량 중 최솟값이다. 실제 보유 객실 수나 여러 요금제 합계가 아니다. 가정: 응답 필드명을 remainingRooms, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검색한 전체 숙박일에 대해 동일 객실 유형의 OTA 한도-점유량 중 최솟값이다. 실제 보유 객실 수나 여러 요금제 합계가 아니다. 가정: 응답 필드명을 remainingRooms, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "0", example = "2")
        @NotNull
        @Min(0)
        Integer remainingRooms,
        /** 가정: 판매 여유 계산의 관측 시각이며 예약 보장이 아니다. 가정: 응답 필드명을 asOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 판매 여유 계산의 관측 시각이며 예약 보장이 아니다. 가정: 응답 필드명을 asOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T14:00:00+09:00")
        @NotNull
        OffsetDateTime asOf
    ) {
    }
    /** 쿠폰 표시 전용 콘텐츠. 발급·적용 API는 제공하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchResultResponsePropertiesItemCouponsItem", description = "쿠폰 표시 전용 콘텐츠. 발급·적용 API는 제공하지 않는다.")
    public record PropertiesItemCouponsItem(
        /** 가정: 검색 결과에서 고객에게 보여 줄 표시용 쿠폰 안내의 제목이다. 제목만으로 어떤 숙소의 어떤 안내인지 구분할 수 있도록 문자열로 제공하며 예시는 실제 운영 혜택이 아닌 가상 콘텐츠다. 가정: 응답 필드명을 title, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검색 결과에서 고객에게 보여 줄 표시용 쿠폰 안내의 제목이다. 제목만으로 어떤 숙소의 어떤 안내인지 구분할 수 있도록 문자열로 제공하며 예시는 실제 운영 혜택이 아닌 가상 콘텐츠다. 가정: 응답 필드명을 title, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "가상 예시: 예시 호텔 2박 예약 할인 안내")
        @NotBlank
        @Size(min = 1)
        String title,
        /** 가정: 표시 대상 쿠폰의 할인 대상·혜택·적용 범위를 완결된 안내 문장으로 제공한다. 이 서비스 견적에 할인액을 적용하지 않는다. 예시의 숙소·혜택·조건은 설명용 가상 값이며 실제 외부 혜택을 확인했다는 뜻이 아니다. 가정: 응답 필드명을 benefitText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 표시 대상 쿠폰의 할인 대상·혜택·적용 범위를 완결된 안내 문장으로 제공한다. 이 서비스 견적에 할인액을 적용하지 않는다. 예시의 숙소·혜택·조건은 설명용 가상 값이며 실제 외부 혜택을 확인했다는 뜻이 아니다. 가정: 응답 필드명을 benefitText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "가상 예시: 예시 호텔의 자체 예약 채널에서 2박 이상 예약하는 고객에게 객실 요금 5,000원 할인을 안내하며, 이 서비스의 예약·견적에는 적용되지 않는다.")
        @NotBlank
        @Size(min = 1)
        String benefitText,
        /** 가정: 적용 조건을 별도 문장으로 제공한다. 확인된 안내가 없으면 쿠폰 항목 자체를 반환하지 않는다. 가정: 응답 필드명을 conditionsText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 적용 조건을 별도 문장으로 제공한다. 확인된 안내가 없으면 쿠폰 항목 자체를 반환하지 않는다. 가정: 응답 필드명을 conditionsText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "가상 예시: 예시 호텔 자체 예약 채널의 2박 이상 예약에 1회 적용하는 안내이며, 이 서비스에서는 쿠폰을 발급하거나 할인액을 적용하지 않는다.")
        @NotBlank
        @Size(min = 1)
        String conditionsText,
        /** 가정: 안내된 유효기간 시작이 없으면 null이다. 가정: 응답 필드명을 startsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 안내된 유효기간 시작이 없으면 null이다. 가정: 응답 필드명을 startsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime startsAt,
        /** 가정: 안내된 유효기간 종료가 없으면 null이다. 가정: 응답 필드명을 endsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 안내된 유효기간 종료가 없으면 null이다. 가정: 응답 필드명을 endsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime endsAt,
        /** 가정: 이 서비스의 실제 할인은 제외되므로 항상 false이다. 표시와 가격 적용을 구분한다. 가정: 응답 필드명을 applicableToBooking, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 이 서비스의 실제 할인은 제외되므로 항상 false이다. 표시와 가격 적용을 구분한다. 가정: 응답 필드명을 applicableToBooking, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"false"}, example = "false")
        @NotNull
        @AssertFalse
        Boolean applicableToBooking
    ) {
    }
    /** 검색한 동일 조건으로 비교하는 숙소. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchResultResponsePropertiesItem", description = "검색한 동일 조건으로 비교하는 숙소.")
    public record PropertiesItem(
        /** 대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "property-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String propertyId,
        /** 숙소 이름. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "숙소 이름. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "예시 호텔")
        @NotBlank
        @Size(min = 1)
        String name,
        /** 숙소 소재 도시. 가정: 응답 필드명을 cityName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "숙소 소재 도시. 가정: 응답 필드명을 cityName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, example = "서울")
        @NotBlank
        @Size(min = 1)
        String cityName,
        /** 가정: HOTEL/PRIVATE_HOME/OTHER 중 하나로 분류한다. 원문의 유형 예시를 구현 가능한 목록으로 정했다. 가정: 응답 필드명을 propertyType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: HOTEL/PRIVATE_HOME/OTHER 중 하나로 분류한다. 원문의 유형 예시를 구현 가능한 목록으로 정했다. 가정: 응답 필드명을 propertyType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, allowableValues = {"HOTEL", "PRIVATE_HOME", "OTHER"}, example = "HOTEL")
        @NotBlank
        @Size(min = 1)
        @Pattern(regexp = "^(?:HOTEL|PRIVATE_HOME|OTHER)$")
        String propertyType,
        /** 숙소 사진. 없는 경우 빈 배열. 가정: 응답 필드명을 photos, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "숙소 사진. 없는 경우 빈 배열. 가정: 응답 필드명을 photos, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PropertiesItemPhotosItem.class))
        @NotNull
        @Valid
        List<@NotNull  PropertiesItemPhotosItem> photos,
        /** 가정: 별점은 숙소 성급 0~5로 정하고 미등록이면 null이다. 후기 평점과 합치지 않는다. 가정: 응답 필드명을 starRating, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 별점은 숙소 성급 0~5로 정하고 미등록이면 null이다. 후기 평점과 합치지 않는다. 가정: 응답 필드명을 starRating, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minimum = "0", maximum = "5", example = "4")
        @Min(0)
        @Max(5)
        Integer starRating,
        /** 후기 평점과 후기 발췌. 가정: 후기 평균은 0~10, 후기가 없으면 null이다. 숙소 성급과 별도 척도를 선택했다. 가정: 응답 필드명을 reviewsScore, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후기 평점과 후기 발췌. 가정: 후기 평균은 0~10, 후기가 없으면 null이다. 숙소 성급과 별도 척도를 선택했다. 가정: 응답 필드명을 reviewsScore, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "double", minimum = "0", maximum = "10", example = "8.5")
        @DecimalMin("0")
        @DecimalMax("10")
        BigDecimal reviewsScore,
        /** 후기 평점과 후기 발췌. 가정: 현재 공개된 후기 수이다. 삭제·비공개 후기는 제외한다. 가정: 응답 필드명을 reviewsCount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후기 평점과 후기 발췌. 가정: 현재 공개된 후기 수이다. 삭제·비공개 후기는 제외한다. 가정: 응답 필드명을 reviewsCount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "12")
        @NotNull
        @Min(0)
        Long reviewsCount,
        /** 후기 평점과 후기 발췌. 가정: 최근 공개 순 최대 3개 발췌를 제공한다. 전체 후기 서비스 확장을 피하면서 본문 표시를 지원한다. 가정: 응답 필드명을 reviewsItems, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "후기 평점과 후기 발췌. 가정: 최근 공개 순 최대 3개 발췌를 제공한다. 전체 후기 서비스 확장을 피하면서 본문 표시를 지원한다. 가정: 응답 필드명을 reviewsItems, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PropertiesItemReviewsItemsItem.class), maxItems = 3)
        @NotNull
        @Size(max = 3)
        @Valid
        List<@NotNull  PropertiesItemReviewsItemsItem> reviewsItems,
        /** 주변 편의시설. 가정: 응답 필드명을 nearbyAmenities, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "주변 편의시설. 가정: 응답 필드명을 nearbyAmenities, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PropertiesItemNearbyAmenitiesItem.class))
        @NotNull
        @Valid
        List<@NotNull  PropertiesItemNearbyAmenitiesItem> nearbyAmenities,
        /** 주변 관광지. 가정: 응답 필드명을 nearbyAttractions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "주변 관광지. 가정: 응답 필드명을 nearbyAttractions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PropertiesItemNearbyAttractionsItem.class))
        @NotNull
        @Valid
        List<@NotNull  PropertiesItemNearbyAttractionsItem> nearbyAttractions,
        /** 객실 유형별 기간 전체 OTA 판매 여유. 가정: 응답 필드명을 availability, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "객실 유형별 기간 전체 OTA 판매 여유. 가정: 응답 필드명을 availability, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PropertiesItemAvailabilityItem.class))
        @NotNull
        @Valid
        List<@NotNull  PropertiesItemAvailabilityItem> availability,
        /** 이 숙소에 귀속되는 예약 통계. 가정: 이 숙소에서 현재 취소되지 않은 예약의 최근 확정 시각이다. 해당 예약이 없으면 null이다. 클라이언트가 상대 시간을 계산한다. 가정: 응답 필드명을 bookingActivityLastConfirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 숙소에 귀속되는 예약 통계. 가정: 이 숙소에서 현재 취소되지 않은 예약의 최근 확정 시각이다. 해당 예약이 없으면 null이다. 클라이언트가 상대 시간을 계산한다. 가정: 응답 필드명을 bookingActivityLastConfirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T13:22:00+09:00")
        OffsetDateTime bookingActivityLastConfirmedAt,
        /** 이 숙소에 귀속되는 예약 통계. 가정: 서울 당일에 확정되고 현재 취소되지 않은 이 숙소 예약 건수이다. 객실 수와 다르다. 가정: 응답 필드명을 bookingActivityConfirmedToday, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 숙소에 귀속되는 예약 통계. 가정: 서울 당일에 확정되고 현재 취소되지 않은 이 숙소 예약 건수이다. 객실 수와 다르다. 가정: 응답 필드명을 bookingActivityConfirmedToday, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "0", example = "10")
        @NotNull
        @Min(0)
        Integer bookingActivityConfirmedToday,
        /** 이 숙소에 귀속되는 예약 통계. 당일 집계 날짜. 가정: 응답 필드명을 bookingActivityDay, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 숙소에 귀속되는 예약 통계. 당일 집계 날짜. 가정: 응답 필드명을 bookingActivityDay, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-11-11")
        @NotNull
        LocalDate bookingActivityDay,
        /** 이 숙소에 귀속되는 예약 통계. 당일의 시간대. 가정: 응답 필드명을 bookingActivityTimeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 숙소에 귀속되는 예약 통계. 당일의 시간대. 가정: 응답 필드명을 bookingActivityTimeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
        @NotBlank
        @Size(min = 1)
        @Pattern(regexp = "^(?:Asia/Seoul)$")
        String bookingActivityTimeZone,
        /** 이 숙소에 귀속되는 예약 통계. 통계 관측 시각. 최근 예약 38분전의 38은 고정값이 아니다. 가정: 응답 필드명을 bookingActivityAsOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 숙소에 귀속되는 예약 통계. 통계 관측 시각. 최근 예약 38분전의 38은 고정값이 아니다. 가정: 응답 필드명을 bookingActivityAsOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T14:00:00+09:00")
        @NotNull
        OffsetDateTime bookingActivityAsOf,
        /** 우선 노출 표시 데이터. 가정: 제공된 우선 노출 메타데이터가 있으면 true이다. 이 조회는 광고 구매·과금·정산을 실행하지 않는다. 가정: 응답 필드명을 promotionSponsored, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "우선 노출 표시 데이터. 가정: 제공된 우선 노출 메타데이터가 있으면 true이다. 이 조회는 광고 구매·과금·정산을 실행하지 않는다. 가정: 응답 필드명을 promotionSponsored, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
        @NotNull
        Boolean promotionSponsored,
        /** 우선 노출 표시 데이터. 가정: 유료 노출에는 광고 문구를 표시하며 promotionSponsored=false이면 null이다. 유료 노출 여부를 식별하기 위해 선택했다. 가정: 응답 필드명을 promotionLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "우선 노출 표시 데이터. 가정: 유료 노출에는 광고 문구를 표시하며 promotionSponsored=false이면 null이다. 유료 노출 여부를 식별하기 위해 선택했다. 가정: 응답 필드명을 promotionLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        String promotionLabel,
        /** 우선 노출 표시 데이터. 가정: 노출 종료 시각이며 유료 노출이 없으면 null이다. 가정: 응답 필드명을 promotionEndsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "우선 노출 표시 데이터. 가정: 노출 종료 시각이며 유료 노출이 없으면 null이다. 가정: 응답 필드명을 promotionEndsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime promotionEndsAt,
        /** 표시용 쿠폰 안내. 할인 적용은 하지 않는다. 가정: 응답 필드명을 coupons, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "표시용 쿠폰 안내. 할인 적용은 하지 않는다. 가정: 응답 필드명을 coupons, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PropertiesItemCouponsItem.class))
        @NotNull
        @Valid
        List<@NotNull  PropertiesItemCouponsItem> coupons,
        /** 검색 기간과 요청 객실 수 전체에 대한 판매 가능한 상품의 최저 총액. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 lowestTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "검색 기간과 요청 객실 수 전체에 대한 판매 가능한 상품의 최저 총액. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 lowestTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long lowestTotalAmount,
        /** 검색 기간과 요청 객실 수 전체에 대한 판매 가능한 상품의 최저 총액. 가정: lowestTotalCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 lowestTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "검색 기간과 요청 객실 수 전체에 대한 판매 가능한 상품의 최저 총액. 가정: lowestTotalCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 lowestTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String lowestTotalCurrency
    ) {
    }
}

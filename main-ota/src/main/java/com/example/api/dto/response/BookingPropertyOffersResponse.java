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
@Schema(name = "BookingPropertyOffersResponse", description = "숙소 상세와 판매 상품 선택지.")
public record BookingPropertyOffersResponse(
    /** 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 대상을 구분하는 불투명 식별자.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "property-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String propertyId,
    /** 가정: 응답 필드명을 propertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 숙소 이름.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "예시 호텔")
    @NotBlank
    @Size(min = 1)
    String propertyName,
    /** 가정: 응답 필드명을 propertyCityName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 숙소 소재 도시.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "서울")
    @NotBlank
    @Size(min = 1)
    String propertyCityName,
    /** 원문의 유형 예시를 구현 가능한 목록으로 정했다. 가정: 응답 필드명을 propertyType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 가정: HOTEL/PRIVATE_HOME/OTHER 중 하나로 분류한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"HOTEL", "PRIVATE_HOME", "OTHER"}, example = "HOTEL")
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?:HOTEL|PRIVATE_HOME|OTHER)$")
    String propertyType,
    /** 없는 경우 빈 배열. 가정: 응답 필드명을 propertyPhotos, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "선택 숙소의 상세 검색 정보. 숙소 사진.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PropertyPhotosItem.class))
    @NotNull
    @Valid
    List<@NotNull  PropertyPhotosItem> propertyPhotos,
    /** 후기 평점과 합치지 않는다. 가정: 응답 필드명을 propertyStarRating, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 가정: 별점은 숙소 성급 0~5로 정하고 미등록이면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "4")
    @Min(0)
    @Max(5)
    Integer propertyStarRating,
    /** 가정: 후기 평균은 0~10, 후기가 없으면 null이다. 숙소 성급과 별도 척도를 선택했다. 가정: 응답 필드명을 propertyReviewsScore, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 후기 평점과 후기 발췌.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "double", example = "8.5")
    @DecimalMin("0")
    @DecimalMax("10")
    BigDecimal propertyReviewsScore,
    /** 가정: 현재 공개된 후기 수이다. 삭제·비공개 후기는 제외한다. 가정: 응답 필드명을 propertyReviewsCount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 후기 평점과 후기 발췌.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "12")
    @NotNull
    @Min(0)
    Long propertyReviewsCount,
    /** 가정: 최근 공개 순 최대 3개 발췌를 제공한다. 전체 후기 서비스 확장을 피하면서 본문 표시를 지원한다. 가정: 응답 필드명을 propertyReviewsItems, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "선택 숙소의 상세 검색 정보. 후기 평점과 후기 발췌.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PropertyReviewsItemsItem.class))
    @NotNull
    @Size(max = 3)
    @Valid
    List<@NotNull  PropertyReviewsItemsItem> propertyReviewsItems,
    /** 가정: 응답 필드명을 propertyNearbyAmenities, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "선택 숙소의 상세 검색 정보. 주변 편의시설.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PropertyNearbyAmenitiesItem.class))
    @NotNull
    @Valid
    List<@NotNull  PropertyNearbyAmenitiesItem> propertyNearbyAmenities,
    /** 가정: 응답 필드명을 propertyNearbyAttractions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "선택 숙소의 상세 검색 정보. 주변 관광지.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PropertyNearbyAttractionsItem.class))
    @NotNull
    @Valid
    List<@NotNull  PropertyNearbyAttractionsItem> propertyNearbyAttractions,
    /** 가정: 응답 필드명을 propertyAvailability, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "선택 숙소의 상세 검색 정보. 객실 유형별 기간 전체 OTA 판매 여유.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PropertyAvailabilityItem.class))
    @NotNull
    @Valid
    List<@NotNull  PropertyAvailabilityItem> propertyAvailability,
    /** 가정: 이 숙소에서 현재 취소되지 않은 예약의 최근 확정 시각이다. 해당 예약이 없으면 null이다. 클라이언트가 상대 시간을 계산한다. 가정: 응답 필드명을 propertyBookingActivityLastConfirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 이 숙소에 귀속되는 예약 통계.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T13:22:00+09:00")
    OffsetDateTime propertyBookingActivityLastConfirmedAt,
    /** 가정: 서울 당일에 확정되고 현재 취소되지 않은 이 숙소 예약 건수이다. 객실 수와 다르다. 가정: 응답 필드명을 propertyBookingActivityConfirmedToday, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 이 숙소에 귀속되는 예약 통계.", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull
    @Min(0)
    Integer propertyBookingActivityConfirmedToday,
    /** 당일 집계 날짜. 가정: 응답 필드명을 propertyBookingActivityDay, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 이 숙소에 귀속되는 예약 통계.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-11-11")
    @NotNull
    LocalDate propertyBookingActivityDay,
    /** 당일의 시간대. 가정: 응답 필드명을 propertyBookingActivityTimeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 이 숙소에 귀속되는 예약 통계.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?:Asia/Seoul)$")
    String propertyBookingActivityTimeZone,
    /** 통계 관측 시각. 최근 예약 38분전의 38은 고정값이 아니다. 가정: 응답 필드명을 propertyBookingActivityAsOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 이 숙소에 귀속되는 예약 통계.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T14:00:00+09:00")
    @NotNull
    OffsetDateTime propertyBookingActivityAsOf,
    /** 가정: 제공된 우선 노출 메타데이터가 있으면 true이다. 이 조회는 광고 구매·과금·정산을 실행하지 않는다. 가정: 응답 필드명을 propertyPromotionSponsored, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 우선 노출 표시 데이터.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull
    Boolean propertyPromotionSponsored,
    /** 가정: 유료 노출에는 광고 문구를 표시하며 propertyPromotionSponsored=false이면 null이다. 유료 노출 여부를 식별하기 위해 선택했다. 가정: 응답 필드명을 propertyPromotionLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 우선 노출 표시 데이터.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    String propertyPromotionLabel,
    /** 가정: 노출 종료 시각이며 유료 노출이 없으면 null이다. 가정: 응답 필드명을 propertyPromotionEndsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 우선 노출 표시 데이터.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
    OffsetDateTime propertyPromotionEndsAt,
    /** 할인 적용은 하지 않는다. 가정: 응답 필드명을 propertyCoupons, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "선택 숙소의 상세 검색 정보. 표시용 쿠폰 안내.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PropertyCouponsItem.class))
    @NotNull
    @Valid
    List<@NotNull  PropertyCouponsItem> propertyCoupons,
    /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 propertyLowestTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 검색 기간과 요청 객실 수 전체에 대한 판매 가능한 상품의 최저 총액.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long propertyLowestTotalAmount,
    /** 가정: propertyLowestTotalCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 propertyLowestTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선택 숙소의 상세 검색 정보. 검색 기간과 요청 객실 수 전체에 대한 판매 가능한 상품의 최저 총액.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String propertyLowestTotalCurrency,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 상세 설명. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "도심 숙소")
    @NotBlank
    @Size(min = 1)
    String description,
    /** 가정: 응답 필드명을 address, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 숙소 주소를 필수 문자열로 제공한다. 상세 정보가 부족하여 숙소를 식별할 최소 위치 정보를 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "서울특별시 예시로 1")
    @NotBlank
    @Size(min = 1)
    String address,
    /** 가정: 응답 필드명을 facilities, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 숙소 시설은 표시 이름 목록으로 제공한다. 확인되지 않은 시설을 기본값으로 추가하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"편의시설\"]"), schema = @Schema(implementation = String.class))
    @NotNull
    @Valid
    List<@NotNull  String> facilities,
    /** 가정: 응답 필드명을 products, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "동일 기간·인원·수량에서 가능한 객실·요금제 조합. 없으면 빈 배열.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ProductsItem.class))
    @NotNull
    @Valid
    List<@NotNull  ProductsItem> products,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "이 조회에서 가격·판매 여유를 계산한 시각. 가정: 응답 필드명을 asOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T14:00:00+09:00")
    @NotNull
    OffsetDateTime asOf
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponsePropertyPhotosItem", description = "숙소 사진 한 장.")
    public record PropertyPhotosItem(
        /** 가정: 응답 필드명을 url, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 공개 HTTPS 사진 주소이다. 클라이언트가 사진을 표시할 수 있도록 URL 형태를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "uri", pattern = "\\S", example = "https://example.com/property.jpg")
        @NotBlank
        @Size(min = 1)
        String url,
        /** 가정: 응답 필드명을 altText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 접근성 대체 문구를 필수 문자열로 제공한다. 관찰 자료에 대체 텍스트가 없어 서버 콘텐츠로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "숙소 정면")
        @NotBlank
        @Size(min = 1)
        String altText,
        /** 가정: 응답 필드명을 caption, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "사진 설명. 가정: 설명이 없으면 null을 반환한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "숙소 외관")
        String caption,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 첫 표시용 대표 사진 여부이며 사진이 있으면 정확히 한 장만 true이다. 가정: 응답 필드명을 primary, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull
        Boolean primary
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponsePropertyReviewsItemsItem", description = "공개된 후기 항목.")
    public record PropertyReviewsItemsItem(
        /** 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 reviewId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "review-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String reviewId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 작성자의 공개 표시명만 제공하고 계정 식별자는 노출하지 않는다. 가정: 응답 필드명을 authorDisplayName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "방문 고객")
        @NotBlank
        @Size(min = 1)
        String authorDisplayName,
        /** 가정: 응답 필드명을 text, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 후기 본문을 제공한다. 후기 관리·작성 기능은 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "편안하게 이용했습니다.")
        @NotBlank
        @Size(min = 1)
        String text,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 후기 점수는 0~10이며 숙소 성급과 다른 값이다. 가정: 응답 필드명을 score, JSON 타입을 숫자로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "double", example = "8.5")
        @NotNull
        @DecimalMin("0")
        @DecimalMax("10")
        BigDecimal score,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후기 공개 시각. 가정: 응답 필드명을 publishedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-10T09:00:00+09:00")
        @NotNull
        OffsetDateTime publishedAt
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponsePropertyNearbyAmenitiesItem", description = "주변 편의시설 또는 관광지. 서로 다른 배열에 보존한다.")
    public record PropertyNearbyAmenitiesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "주변 장소 이름. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "중앙 공원")
        @NotBlank
        @Size(min = 1)
        String name,
        /** 가정: 응답 필드명을 distanceMetres, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 숙소로부터 직선거리 미터이며 모르면 null이다. 거리와 이동시간을 혼동하지 않도록 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "500")
        @Min(0)
        Integer distanceMetres,
        /** 가정: 응답 필드명을 walkingMinutes, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 도보 예상 분이며 경로 정보가 없으면 null이다. 실측 도착 보장은 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "8")
        @Min(0)
        Integer walkingMinutes
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponsePropertyNearbyAttractionsItem", description = "주변 편의시설 또는 관광지. 서로 다른 배열에 보존한다.")
    public record PropertyNearbyAttractionsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "주변 장소 이름. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "중앙 공원")
        @NotBlank
        @Size(min = 1)
        String name,
        /** 가정: 응답 필드명을 distanceMetres, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 숙소로부터 직선거리 미터이며 모르면 null이다. 거리와 이동시간을 혼동하지 않도록 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "500")
        @Min(0)
        Integer distanceMetres,
        /** 가정: 응답 필드명을 walkingMinutes, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 도보 예상 분이며 경로 정보가 없으면 null이다. 실측 도착 보장은 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "8")
        @Min(0)
        Integer walkingMinutes
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponsePropertyAvailabilityItem", description = "객실 유형별 기간 전체 판매 여유.")
    public record PropertyAvailabilityItem(
        /** 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "roomType-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String roomTypeId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "판매 여유가 귀속되는 객실 유형 이름. 가정: 응답 필드명을 roomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "스탠다드")
        @NotBlank
        @Size(min = 1)
        String roomTypeName,
        /** 가정: 응답 필드명을 remainingRooms, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검색한 전체 숙박일에 대해 동일 객실 유형의 OTA 한도-점유량 중 최솟값이다. 실제 보유 객실 수나 여러 요금제 합계가 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
        @NotNull
        @Min(0)
        Integer remainingRooms,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 판매 여유 계산의 관측 시각이며 예약 보장이 아니다. 가정: 응답 필드명을 asOf, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T14:00:00+09:00")
        @NotNull
        OffsetDateTime asOf
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponsePropertyCouponsItem", description = "쿠폰 표시 전용 콘텐츠. 발급·적용 API는 제공하지 않는다.")
    public record PropertyCouponsItem(
        /** 가정: 응답 필드명을 title, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검색 결과에서 고객에게 보여 줄 표시용 쿠폰 안내의 제목이다. 제목만으로 어떤 숙소의 어떤 안내인지 구분할 수 있도록 문자열로 제공하며 예시는 실제 운영 혜택이 아닌 가상 콘텐츠다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "가상 예시: 예시 호텔 2박 예약 할인 안내")
        @NotBlank
        @Size(min = 1)
        String title,
        /** 예시의 숙소·혜택·조건은 설명용 가상 값이며 실제 외부 혜택을 확인했다는 뜻이 아니다. 가정: 응답 필드명을 benefitText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 표시 대상 쿠폰의 할인 대상·혜택·적용 범위를 완결된 안내 문장으로 제공한다. 이 서비스 견적에 할인액을 적용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "가상 예시: 예시 호텔의 자체 예약 채널에서 2박 이상 예약하는 고객에게 객실 요금 5,000원 할인을 안내하며, 이 서비스의 예약·견적에는 적용되지 않는다.")
        @NotBlank
        @Size(min = 1)
        String benefitText,
        /** 가정: 응답 필드명을 conditionsText, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 적용 조건을 별도 문장으로 제공한다. 확인된 안내가 없으면 쿠폰 항목 자체를 반환하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "가상 예시: 예시 호텔 자체 예약 채널의 2박 이상 예약에 1회 적용하는 안내이며, 이 서비스에서는 쿠폰을 발급하거나 할인액을 적용하지 않는다.")
        @NotBlank
        @Size(min = 1)
        String conditionsText,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 안내된 유효기간 시작이 없으면 null이다. 가정: 응답 필드명을 startsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime startsAt,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 안내된 유효기간 종료가 없으면 null이다. 가정: 응답 필드명을 endsAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime endsAt,
        /** 가정: 응답 필드명을 applicableToBooking, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 이 서비스의 실제 할인은 제외되므로 항상 false이다. 표시와 가격 적용을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"false"}, example = "false")
        @NotNull
        @AssertFalse
        Boolean applicableToBooking
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponseProductsItemBenefitsItem", description = "판매 상품에 포함된 고정 혜택.")
    public record ProductsItemBenefitsItem(
        /** 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "상품에 미리 포함된 서비스·혜택 이름. 특정 서비스를 기본 제공으로 추정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "포함 서비스")
        @NotBlank
        @Size(min = 1)
        String name,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택의 구체적 제공 내용. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "상품에서 약속한 서비스")
        @NotBlank
        @Size(min = 1)
        String description,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 제공 수량은 양수이며 수량으로 표현할 수 없으면 null이다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "1")
        @Min(1)
        Integer quantity,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 제공 수량의 단위이며 quantity가 null이면 null이다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "회")
        String unit,
        /** 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 대상·시간·이용 조건을 독립 문자열로 보존한다. 추가 조건이 없으면 빈 문자열이다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "객실당 제공")
        @NotNull
        String conditions
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponseProductsItemPricesItem", description = "숙박일별 필수 요금 포함 가격 구성.")
    public record ProductsItemPricesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 가격이 귀속되는 숙박일. 가정: 응답 필드명을 stayDate, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull
        LocalDate stayDate,
        /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 unitAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 하나·해당 숙박일의 필수 요금 포함 양수 가격. 금액은 1원 이상이어야 하며 0원 판매가격을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
        @NotNull 
        @Min(0)
        Long unitAmount,
        /** 가정: unitCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 unitCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 하나·해당 숙박일의 필수 요금 포함 양수 가격. 금액은 1원 이상이어야 하며 0원 판매가격을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String unitCurrency,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 숙박일에 요청한 객실 수. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull
        @Min(1)
        Integer quantity,
        /** 가정: 응답 필드명을 lineTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "서버가 계산한 unitAmount × quantity 금액. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
        @NotNull 
        @Min(0)
        Long lineTotalAmount,
        /** 가정: 응답 필드명을 lineTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "서버가 계산한 unitAmount × quantity 금액. 가정: lineTotalCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String lineTotalCurrency
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingPropertyOffersResponseProductsItem", description = "동일 기간·인원·객실 수로 계산한 판매 상품.")
    public record ProductsItem(
        /** 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 productId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "product-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String productId,
        /** 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "roomType-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String roomTypeId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 유형 이름. 가정: 응답 필드명을 roomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "스탠다드")
        @NotBlank
        @Size(min = 1)
        String roomTypeName,
        /** 객실 설정과 예약 당시 설명을 손실 없이 교환하면서 입력 크기를 제한하기 위해 정한 이름과 검증 조건이다. 가정: 응답 필드명을 roomDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "제공 객실의 설명. 가정: 필드명은 roomDescription으로 선택하고 공백만 있는 값 없이 필수 1~5000자 문자열로 제한한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "객실 유형 설명")
        @NotBlank
        @Size(min = 1, max = 5000)
        String roomDescription,
        /** 가정: 응답 필드명을 capacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 한 개의 총정원. 성인과 무료 아동을 모두 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
        @NotNull
        @Min(1)
        Integer capacity,
        /** 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "ratePlan-1")
        @NotBlank
        @Size(min = 1, max = 64)
        String ratePlanId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "적용 요금제 이름. 가정: 응답 필드명을 ratePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "기본 요금제")
        @NotBlank
        @Size(min = 1)
        String ratePlanName,
        /** 상품 설정과 예약 당시 설명을 손실 없이 교환하면서 입력 크기를 제한하기 위해 정한 이름과 검증 조건이다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실과 요금제를 조합한 상품 설명. 가정: 필드명은 description으로 선택하고 공백만 있는 값 없이 필수 1~5000자 문자열로 제한한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "숙박 상품")
        @NotBlank
        @Size(min = 1, max = 5000)
        String description,
        /** 가정: 응답 필드명을 benefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "상품에 미리 묶인 혜택. 고객별 추가·제거는 제공하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ProductsItemBenefitsItem.class))
        @NotNull
        @Valid
        List<@NotNull  ProductsItemBenefitsItem> benefits,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "체크인 포함 체크아웃 제외의 모든 숙박일 가격. 가정: 응답 필드명을 prices, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ProductsItemPricesItem.class))
        @NotNull
        @Size(min = 1)
        @Valid
        List<@NotNull  ProductsItemPricesItem> prices,
        /** 가정: 응답 필드명을 totalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "전체 기간·요청 수량의 필수 요금 포함 총액이며 가격 행 합계와 같다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
        @NotNull 
        @Min(0)
        Long totalAmount,
        /** 가정: 응답 필드명을 totalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "전체 기간·요청 수량의 필수 요금 포함 총액이며 가격 행 합계와 같다. 가정: totalCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String totalCurrency,
        /** 가정: 응답 필드명을 cancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "현재 견적의 취소 정책. 무료 취소가 가능한 정책인지 환불 불가인지 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"FREE_CANCELLATION", "NON_REFUNDABLE"}, example = "FREE_CANCELLATION")
        @NotBlank
        @Size(min = 1)
        @Pattern(regexp = "^(?:FREE_CANCELLATION|NON_REFUNDABLE)$")
        String cancellationPolicyKind,
        /** 환불 불가이면 null이다. 예정 청구 N과 독립이다. 가정: 응답 필드명을 cancellationPolicyDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "현재 견적의 취소 정책. 무료 취소는 체크인 N일 전 00:00 Asia/Seoul을 기한으로 한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "3")
        @Min(0)
        Integer cancellationPolicyDaysBeforeCheckIn,
        /** 환불 불가이면 null이고 항상 총액 전액이다. 가정: 응답 필드명을 cancellationPolicyFreeCancellationDeadline, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "현재 견적의 취소 정책. 기한 전 비용 0, 같은 시각부터 총액 전액.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-17T00:00:00+09:00")
        OffsetDateTime cancellationPolicyFreeCancellationDeadline,
        /** 가정: 응답 필드명을 allowedPaymentTimings, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "가정: 요금제에서 허용한 IMMEDIATE/LATER만 반환한다. 고객은 임의 청구 시각을 입력하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"IMMEDIATE\", \"LATER\"]"), schema = @Schema(implementation = String.class, allowableValues = {"IMMEDIATE", "LATER"}))
        @NotNull
        @Size(min = 1)
        @Valid
        List<@NotNull  @Pattern(regexp = "^(?:IMMEDIATE|LATER)$") String> allowedPaymentTimings,
        /** 가정: 응답 필드명을 scheduledPaymentDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "나중 결제는 체크인 N일 전 00:00 Asia/Seoul에 청구한다. 나중 결제를 제공하지 않으면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "1")
        @Min(0)
        Integer scheduledPaymentDaysBeforeCheckIn,
        /** 가정: 응답 필드명을 remainingRooms, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "동일 객실 유형의 모든 요금제가 공유하는 전체 기간의 OTA 판매 여유. 선택 자체가 선점은 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
        @NotNull
        @Min(0)
        Integer remainingRooms
    ) {
    }
}

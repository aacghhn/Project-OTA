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

/** 숙박 검색 조건. POST는 복합 배열 입력을 전달하는 읽기이며 예약·선점·결제를 만들지 않는다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "SearchCriteriaRequest", description = "숙박 검색 조건. POST는 복합 배열 입력을 전달하는 읽기이며 예약·선점·결제를 만들지 않는다.")
public record SearchCriteriaRequest(
    /** 체크인 날짜를 포함한다. Asia/Seoul 기준이며 체크아웃보다 앞서야 한다. 가정: 서버 필드명을 checkIn로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "체크인 날짜를 포함한다. Asia/Seoul 기준이며 체크아웃보다 앞서야 한다. 가정: 서버 필드명을 checkIn로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
    @NotNull
    LocalDate checkIn,
    /** 체크아웃 날짜는 숙박 가격과 재고 계산에서 제외한다. 가정: 서버 필드명을 checkOut로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "체크아웃 날짜는 숙박 가격과 재고 계산에서 제외한다. 가정: 서버 필드명을 checkOut로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-22")
    @NotNull
    LocalDate checkOut,
    /** 요청 객실 수이며 rooms 항목 수와 같아야 한다. 가정: 서버 필드명을 roomCount로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "요청 객실 수이며 rooms 항목 수와 같아야 한다. 가정: 서버 필드명을 roomCount로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "1", example = "1")
    @NotNull
    @Min(1)
    Integer roomCount,
    /** 객실마다 성인과 아동을 따로 보낸다. 총정원 검증은 서버에서 한다. 가정: 서버 필드명을 rooms로 정하고 array 구조 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @ArraySchema(arraySchema = @Schema(description = "객실마다 성인과 아동을 따로 보낸다. 총정원 검증은 서버에서 한다. 가정: 서버 필드명을 rooms로 정하고 array 구조 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = RoomsItem.class), minItems = 1)
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull  RoomsItem> rooms,
    /** 숙소 이름 부분 검색. 가정: null 또는 누락은 조건 없음이고 빈 문자열은 거절하며 최대 200자이다. 도시와 함께 지정하면 AND로 적용해 범위를 명확히 한다. 가정: 서버 필드명을 propertyName로 정하고 문자열 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다. */
    @Schema(description = "숙소 이름 부분 검색. 가정: null 또는 누락은 조건 없음이고 빈 문자열은 거절하며 최대 200자이다. 도시와 함께 지정하면 AND로 적용해 범위를 명확히 한다. 가정: 서버 필드명을 propertyName로 정하고 문자열 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, minLength = 1, maxLength = 200, example = "예시 호텔")
    @Size(min = 1, max = 200)
    String propertyName,
    /** 도시 이름 부분 검색. 가정: null 또는 누락은 조건 없음이며 최대 100자이다. 숙소명과 독립된 필드로 정했다. 가정: 서버 필드명을 cityName로 정하고 문자열 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다. */
    @Schema(description = "도시 이름 부분 검색. 가정: null 또는 누락은 조건 없음이며 최대 100자이다. 숙소명과 독립된 필드로 정했다. 가정: 서버 필드명을 cityName로 정하고 문자열 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, minLength = 1, maxLength = 100, example = "서울")
    @Size(min = 1, max = 100)
    String cityName,
    /** 가정: PRICE_ASC를 기본값으로 하고 PRICE_DESC와 REVIEW_SCORE_DESC를 허용한다. 기간·수량 전체 금액 또는 후기 평점으로 비교하며 동점은 숙소 식별자 순이다. 정렬 선택지가 확인되지 않아 구현 가능한 기준을 정했다. 가정: 서버 필드명을 sort로 정하고 문자열 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다. */
    @Schema(description = "가정: PRICE_ASC를 기본값으로 하고 PRICE_DESC와 REVIEW_SCORE_DESC를 허용한다. 기간·수량 전체 금액 또는 후기 평점으로 비교하며 동점은 숙소 식별자 순이다. 정렬 선택지가 확인되지 않아 구현 가능한 기준을 정했다. 가정: 서버 필드명을 sort로 정하고 문자열 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, allowableValues = {"PRICE_ASC", "PRICE_DESC", "REVIEW_SCORE_DESC"}, example = "PRICE_ASC", defaultValue = "PRICE_ASC")
    @Pattern(regexp = "^(?:PRICE_ASC|PRICE_DESC|REVIEW_SCORE_DESC)$")
    String sort,
    /** 가정: HOTEL, PRIVATE_HOME, OTHER를 복수 선택하며 빈 목록/누락은 전체 유형이다. 호텔과 프라이빗 하우스는 원문의 예시이며 전체 선택지는 이 계약의 결정이다. 가정: 서버 필드명을 propertyTypes로 정하고 array 구조 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: HOTEL, PRIVATE_HOME, OTHER를 복수 선택하며 빈 목록/누락은 전체 유형이다. 호텔과 프라이빗 하우스는 원문의 예시이며 전체 선택지는 이 계약의 결정이다. 가정: 서버 필드명을 propertyTypes로 정하고 array 구조 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, example = "[\"HOTEL\"]"), schema = @Schema(implementation = String.class, allowableValues = {"HOTEL", "PRIVATE_HOME", "OTHER"}))
    @Valid
    List<@NotNull  @Pattern(regexp = "^(?:HOTEL|PRIVATE_HOME|OTHER)$") String> propertyTypes,
    /** 전체 기간·객실 수의 필수 요금 포함 총액 상한(원). 가정: null/누락은 가격 제한 없음으로 두고 클라이언트 가격 필터에 사용한다. 가정: 서버 필드명을 maximumTotalAmount로 정하고 64비트 정수 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다. */
    @Schema(description = "전체 기간·객실 수의 필수 요금 포함 총액 상한(원). 가정: null/누락은 가격 제한 없음으로 두고 클라이언트 가격 필터에 사용한다. 가정: 서버 필드명을 maximumTotalAmount로 정하고 64비트 정수 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, format = "int64", minimum = "0", example = "300000")
    @Min(0)
    Long maximumTotalAmount,
    /** 가정: 0부터 시작하는 페이지이며 기본 0이다. 목록 전송량을 제한하기 위한 결정이다. 가정: 서버 필드명을 page로 정하고 정수 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다. */
    @Schema(description = "가정: 0부터 시작하는 페이지이며 기본 0이다. 목록 전송량을 제한하기 위한 결정이다. 가정: 서버 필드명을 page로 정하고 정수 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, minimum = "0", example = "0", defaultValue = "0")
    @Min(0)
    Integer page,
    /** 가정: 페이지 크기 1~100, 기본 20이다. 단일 조회량을 제한하기 위한 결정이다. 가정: 서버 필드명을 size로 정하고 정수 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다. */
    @Schema(description = "가정: 페이지 크기 1~100, 기본 20이다. 단일 조회량을 제한하기 위한 결정이다. 가정: 서버 필드명을 size로 정하고 정수 선택값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. null 또는 누락은 필터 미적용 또는 명시한 기본값을 뜻한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, minimum = "1", maximum = "100", example = "20", defaultValue = "20")
    @Min(1)
    @Max(100)
    Integer size
) {
    /** 한 객실의 인원 배분. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchCriteriaRequestRoomsItem", description = "한 객실의 인원 배분.")
    public record RoomsItem(
        /** 객실별 인원 배분의 순서. 가정: 1부터 시작하며 동일 요청 안에서 중복이 없어야 한다. 물리적 호실 번호와 구별하기 위한 순번이다. 가정: 서버 필드명을 roomIndex로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "객실별 인원 배분의 순서. 가정: 1부터 시작하며 동일 요청 안에서 중복이 없어야 한다. 물리적 호실 번호와 구별하기 위한 순번이다. 가정: 서버 필드명을 roomIndex로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "1", example = "1")
        @NotNull
        @Min(1)
        Integer roomIndex,
        /** 해당 객실의 만 18세 이상 성인 수. 별도의 최소 성인 인원은 두지 않으며 성인과 아동 합계가 1 이상이고 객실 정원 이하여야 한다. 가정: 서버 필드명을 adults로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "해당 객실의 만 18세 이상 성인 수. 별도의 최소 성인 인원은 두지 않으며 성인과 아동 합계가 1 이상이고 객실 정원 이하여야 한다. 가정: 서버 필드명을 adults로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "0", example = "2")
        @NotNull
        @Min(0)
        Integer adults,
        /** 체크인 시점 만 0~17세 아동 수. 무료 투숙 아동도 총정원에 포함한다. 가정: 서버 필드명을 children로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "체크인 시점 만 0~17세 아동 수. 무료 투숙 아동도 총정원에 포함한다. 가정: 서버 필드명을 children로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "0", example = "0")
        @NotNull
        @Min(0)
        Integer children
    ) {
    }
}

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

/** 인증된 파트너가 소유한 숙소의 예약 목록. 빈 결과도 정상 응답이며 조회로 예약이나 금전 상태를 변경하지 않는다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "PartnerReservationListResponse", description = "인증된 파트너가 소유한 숙소의 예약 목록. 빈 결과도 정상 응답이며 조회로 예약이나 금전 상태를 변경하지 않는다.")
public record PartnerReservationListResponse(
    /** 목록의 모든 예약이 귀속되는 소유 숙소 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "목록의 모든 예약이 귀속되는 소유 숙소 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "prop_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*")
    String propertyId,
    /** 가정: 조건에 맞는 예약을 checkIn 오름차순, 같은 날이면 reservationId 오름차순으로 제공한다. 결과가 없으면 빈 배열이다. 가정: 응답 필드명을 items, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 조건에 맞는 예약을 checkIn 오름차순, 같은 날이면 reservationId 오름차순으로 제공한다. 결과가 없으면 빈 배열이다. 가정: 응답 필드명을 items, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = ItemsItem.class), maxItems = 100)
    @NotNull 
    @Size(max = 100)
    @Valid
    List<@NotNull ItemsItem> items,
    /** 가정: 다음 페이지가 없으면 null, 있으면 1~1024자의 opaque 문자열이다. 숙소·상태·검색 조건과 마지막 정렬 키에 결합하여 다른 검색에 재사용하지 않는다. 가정: 응답 필드명을 nextCursor, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 다음 페이지가 없으면 null, 있으면 1~1024자의 opaque 문자열이다. 숙소·상태·검색 조건과 마지막 정렬 키에 결합하여 다른 검색에 재사용하지 않는다. 가정: 응답 필드명을 nextCursor, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 1024)
    @Size(min = 1, max = 1024)
    String nextCursor,
    /** 현재 페이지의 예약 상태와 금전 정보를 읽은 동일 기준 시각. 가정: 페이지 간 고정 스냅샷은 보장하지 않고 각 응답의 관측 시각을 표시한다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "현재 페이지의 예약 상태와 금전 정보를 읽은 동일 기준 시각. 가정: 페이지 간 고정 스냅샷은 보장하지 않고 각 응답의 관측 시각을 표시한다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:02:00+09:00")
    @NotNull 
    OffsetDateTime observedAt
) {
    /** 소유 숙소의 확정·취소 예약을 선택하기 위한 목록 요약. 열 구성은 자체 API의 결정이며 예약 당시 값만 사용한다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "PartnerReservationListResponseItemsItem", description = "소유 숙소의 확정·취소 예약을 선택하기 위한 목록 요약. 열 구성은 자체 API의 결정이며 예약 당시 값만 사용한다.")
    public record ItemsItem(
        /** 상세 조회에 사용할 예약 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "상세 조회에 사용할 예약 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "res_1")
        @NotNull 
        @Size(min = 1, max = 64)
        @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*")
        String reservationId,
        /** 예약 번호와 별개인 숙소 참조 코드. 가정: 별도 코드가 없으면 null, 있으면 1~128자로 표시한다. 가정: 응답 필드명을 referenceCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 번호와 별개인 숙소 참조 코드. 가정: 별도 코드가 없으면 null, 있으면 1~128자로 표시한다. 가정: 응답 필드명을 referenceCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 128, example = "ref_1001")
        @Size(min = 1, max = 128)
        String referenceCode,
        /** 예약 당시 객실 유형의 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 객실 유형의 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "room_1")
        @NotNull 
        @Size(min = 1, max = 64)
        @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*")
        String roomTypeId,
        /** 예약 당시 객실 유형 이름. 가정: 고객이 동의한 이름을 1~200자로 보존한다. 가정: 응답 필드명을 roomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 객실 유형 이름. 가정: 고객이 동의한 이름을 1~200자로 보존한다. 가정: 응답 필드명을 roomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "스탠더드")
        @NotNull 
        @Size(min = 1, max = 200)
        String roomTypeName,
        /** 예약 당시 요금제의 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 요금제의 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "rate_1")
        @NotNull 
        @Size(min = 1, max = 64)
        @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*")
        String ratePlanId,
        /** 예약 당시 고객에게 표시한 요금제 이름. 가정: 1~200자로 보존하고 현재 내부 운영 이름으로 대체하지 않는다. 가정: 응답 필드명을 ratePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 고객에게 표시한 요금제 이름. 가정: 1~200자로 보존하고 현재 내부 운영 이름으로 대체하지 않는다. 가정: 응답 필드명을 ratePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "유연 요금")
        @NotNull 
        @Size(min = 1, max = 200)
        String ratePlanName,
        /** 포함되는 체크인 날짜. 숙소 시간대 Asia/Seoul 기준이며 날짜 자체를 시각으로 변환하지 않는다. 가정: 응답 필드명을 checkIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "포함되는 체크인 날짜. 숙소 시간대 Asia/Seoul 기준이며 날짜 자체를 시각으로 변환하지 않는다. 가정: 응답 필드명을 checkIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
        @NotNull 
        LocalDate checkIn,
        /** 포함되지 않는 체크아웃 날짜. checkIn 이후 날짜이며 숙소 시간대는 Asia/Seoul이다. 가정: 응답 필드명을 checkOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "포함되지 않는 체크아웃 날짜. checkIn 이후 날짜이며 숙소 시간대는 Asia/Seoul이다. 가정: 응답 필드명을 checkOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-22")
        @NotNull 
        LocalDate checkOut,
        /** 예약한 같은 객실 유형의 수량. 가정: 1 이상의 정수로 제공한다. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약한 같은 객실 유형의 수량. 가정: 1 이상의 정수로 제공한다. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
        @NotNull 
        @Min(1)
        Integer roomCount,
        /** 예약자가 아닐 수도 있는 대표 투숙객의 이름·성을 분리해 제공한다. 연락처나 예약자 계정 정보는 포함하지 않는다. 대표 투숙객의 이름 부분. 성과 분리하여 숙소 예약 확인에 사용한다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 representativeGuestFirstName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약자가 아닐 수도 있는 대표 투숙객의 이름·성을 분리해 제공한다. 연락처나 예약자 계정 정보는 포함하지 않는다. 대표 투숙객의 이름 부분. 성과 분리하여 숙소 예약 확인에 사용한다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 representativeGuestFirstName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "Minjun")
        @NotNull
        @Size(min = 1, max = 100)
        String representativeGuestFirstName,
        /** 예약자가 아닐 수도 있는 대표 투숙객의 이름·성을 분리해 제공한다. 연락처나 예약자 계정 정보는 포함하지 않는다. 대표 투숙객의 성 부분. 이름과 합치지 않는다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 representativeGuestLastName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약자가 아닐 수도 있는 대표 투숙객의 이름·성을 분리해 제공한다. 연락처나 예약자 계정 정보는 포함하지 않는다. 대표 투숙객의 성 부분. 이름과 합치지 않는다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 representativeGuestLastName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "Kim")
        @NotNull
        @Size(min = 1, max = 100)
        String representativeGuestLastName,
        /** 필수 요금을 포함한 예약 당시 전체 숙박대금. 현재 가격이나 취소 결과로 0원으로 덮어쓰지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 totalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "필수 요금을 포함한 예약 당시 전체 숙박대금. 현재 가격이나 취소 결과로 0원으로 덮어쓰지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 totalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long totalAmount,
        /** 필수 요금을 포함한 예약 당시 전체 숙박대금. 현재 가격이나 취소 결과로 0원으로 덮어쓰지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 totalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "필수 요금을 포함한 예약 당시 전체 숙박대금. 현재 가격이나 취소 결과로 0원으로 덮어쓰지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 totalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String totalCurrency,
        /** 현재 예약 상태. 가정: 업무상 이행 대상과 취소 대상만 제공하므로 CONFIRMED 또는 CANCELLED로 제한한다. 확정은 결제 성공과 같지 않다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "현재 예약 상태. 가정: 업무상 이행 대상과 취소 대상만 제공하므로 CONFIRMED 또는 CANCELLED로 제한한다. 확정은 결제 성공과 같지 않다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"CONFIRMED", "CANCELLED"}, example = "CONFIRMED")
        @NotNull 
        ReservationStatus status,
        /** 최초 예약 확정 시각. 취소 이후에도 보존하며 RFC3339 오프셋을 포함한다. 가정: 응답 필드명을 confirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "최초 예약 확정 시각. 취소 이후에도 보존하며 RFC3339 오프셋을 포함한다. 가정: 응답 필드명을 confirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:01:00+09:00")
        @NotNull 
        OffsetDateTime confirmedAt,
        /** 최초 전체 취소 사유. 가정: CANCELLED인 경우에만 존재하고 CONFIRMED이면 null이다. 반복 요청으로 사유를 바꾸지 않는다. 가정: 응답 필드명을 cancellationReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "최초 전체 취소 사유. 가정: CANCELLED인 경우에만 존재하고 CONFIRMED이면 null이다. 반복 요청으로 사유를 바꾸지 않는다. 가정: 응답 필드명을 cancellationReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"CUSTOMER_REQUEST", "SCHEDULED_PAYMENT_FAILED", "PROPERTY_UNAVAILABLE"})
        CancellationReason cancellationReason,
        /** 최초 전체 취소 비용 판정 시각. 가정: CANCELLED인 경우에만 존재하고 CONFIRMED이면 null이다. 가정: 응답 필드명을 cancellationDecidedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "최초 전체 취소 비용 판정 시각. 가정: CANCELLED인 경우에만 존재하고 CONFIRMED이면 null이다. 가정: 응답 필드명을 cancellationDecidedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime cancellationDecidedAt
    ) {
    }
}

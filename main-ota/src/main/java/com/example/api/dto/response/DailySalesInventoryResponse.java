package com.example.api.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "DailySalesInventoryResponse", description = "한 객실 유형의 날짜별 OTA 한도와 도출 수량 조회 또는 원자적 한도 변경 결과.")
public record DailySalesInventoryResponse(
    /** 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @Schema(description = "소유 숙소 식별자. 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "property_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = ".*\\S.*")
    String propertyId,
    /** 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @Schema(description = "조회 또는 변경한 객실 유형 식별자. 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "room_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = ".*\\S.*")
    String roomTypeId,
    /** 가정: 비어 있지 않은 최대 366개 배열로 한 번의 조회·변경 범위를 명확히 한다. 가정: 응답 필드명을 days, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙박일 오름차순 결과. 조회는 from 포함·to 제외 모든 날짜를 빠짐없이 제공하고, 변경 응답은 요청한 날짜만 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = DaysItem.class))
    @NotNull 
    @Size(min = 1, max = 366)
    @Valid
    List<@NotNull  DaysItem> days,
    /** 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @Schema(description = "가격·한도·모든 점유·여유·차단을 함께 관측한 기준 시각. 가정: 하나의 일관된 스냅샷을 RFC3339 오프셋 시각으로 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull 
    OffsetDateTime observedAt,
    /** 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @Schema(description = "숙박일 및 변경 가능한 오늘 날짜의 기준 시간대. 가정: 응답 필드명을 timeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^Asia/Seoul$", allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotNull 
    @Pattern(regexp = "^Asia/Seoul$")
    String timeZone
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "DailySalesInventoryResponseDaysItemSalesBlocksItem", description = "신규 판매를 차단하는 범위 하나. 배열에 존재하면 현재 차단 중이며 수량 부족이나 가격 누락과는 다른 상태다.")
    public record DaysItemSalesBlocksItem(
        /** PROPERTY는 숙소 전체, ROOM_TYPE은 객실 유형, RATE_PLAN은 요금제, PRODUCT는 판매 상품, ROOM_TYPE_DATE는 객실 유형의 하루이다. 가정: 응답 필드명을 scope, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
        @Schema(description = "차단 범위. PARTNER는 파트너 정지 조회 정보이며 파트너가 직접 변경하는 판매 범위가 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^(PARTNER|PROPERTY|ROOM_TYPE|RATE_PLAN|PRODUCT|ROOM_TYPE_DATE)$", allowableValues = {"PARTNER", "PROPERTY", "ROOM_TYPE", "RATE_PLAN", "PRODUCT", "ROOM_TYPE_DATE"}, example = "ROOM_TYPE_DATE")
        @NotNull 
        @Pattern(regexp = "^(PARTNER|PROPERTY|ROOM_TYPE|RATE_PLAN|PRODUCT|ROOM_TYPE_DATE)$")
        String scope,
        /** 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다. 가정: 응답 필드명을 targetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
        @Schema(description = "차단 대상 식별자. ROOM_TYPE_DATE에서는 객실 유형 식별자이며 날짜는 stayDate로 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "room_1")
        @NotNull 
        @Size(min = 1, max = 64)
        @Pattern(regexp = ".*\\S.*")
        String targetId,
        /** null은 차단이 없다는 뜻이 아니라 날짜 하나에 한정되지 않는 차단임을 뜻한다. 가정: 필드명을 stayDate로 정하고 필수 키의 값은 ISO 8601 날짜 문자열 또는 명시적 null로 제공해 차단 범위와 적용 날짜를 구분한다. */
        @Schema(description = "현재 차단의 대상 숙박일. scope가 ROOM_TYPE_DATE이면 반드시 Asia/Seoul 현지 날짜를 제공하며, PARTNER·PROPERTY·ROOM_TYPE·RATE_PLAN·PRODUCT이면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date", example = "2026-12-20")
        LocalDate stayDate,
        /** 가정: 응답 필드명을 reason, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
        @Schema(description = "해당 범위가 신규 판매를 막는 이유를 표시하는 안전한 설명. 가정: 필수 1~500자로 제한해 화면에 표시할 충분한 사유를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "객실 유형의 해당 숙박일 판매가 중단되었습니다.")
        @NotNull 
        @Size(min = 1, max = 500)
        String reason
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "DailySalesInventoryResponseDaysItem", description = "한 객실 유형·숙박일의 OTA 한도와 같은 기준 시각의 계산 점유·여유. 실제 보유 객실 수를 나타내지 않는다.")
    public record DaysItem(
        /** 가정: 응답 필드명을 stayDate, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
        @Schema(description = "수량이 귀속되는 숙박일. Asia/Seoul 현지 날짜다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull 
        LocalDate stayDate,
        /** 한도가 미설정이면 같은 days 항목의 otaLimit과 remaining은 null이고 confirmedOccupancy·heldOccupancy·occupied는 0이며 stayDate와 salesBlocks는 계속 제공한다. 0은 설정된 판매 한도이고 null과 다르다. 가정: 필드명을 otaLimit로 정하고 필수 키에 0 이상의 32비트 정수 또는 한도 미설정의 명시적 null을 제공해 파트너 설정값과 서버 계산값을 구분한다. */
        @Schema(description = "파트너가 설정한 해당 객실 유형·숙박일의 OTA 판매 한도이며 모든 요금제가 공유한다. 실제 보유 수량이 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "4")
        @Min(0)
        Integer otaLimit,
        /** 가정: 응답 필드명을 confirmedOccupancy, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
        @Schema(description = "해당 객실 유형·숙박일의 확정 예약 객실 수 합계. 취소된 예약은 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "4")
        @NotNull 
        @Min(0)
        Integer confirmedOccupancy,
        /** 가정: 응답 필드명을 heldOccupancy, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
        @Schema(description = "응답 observedAt이 선점 만료 시각보다 작은 미확정 선점 객실 수 합계. 만료 시각과 같으면 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "4")
        @NotNull 
        @Min(0)
        Integer heldOccupancy,
        /** 가정: 응답 필드명을 occupied, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
        @Schema(description = "confirmedOccupancy + heldOccupancy로 서버가 계산한 점유량. 파트너가 독립 입력하거나 수정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "4")
        @NotNull 
        @Min(0)
        Integer occupied,
        /** 한도가 설정되면 0 이상의 정수이며 여유가 있어도 가격·판매 차단 등에 따라 예약이 거절될 수 있다. 파트너의 편집 입력이 아니다. 가정: 필드명을 remaining으로 정하고 필수 키에 32비트 정수 또는 한도 미설정의 명시적 null을 제공해 한도 설정 여부와 계산 결과를 구분한다. */
        @Schema(description = "같은 days 항목에서 응답 observedAt의 otaLimit - occupied로 서버가 계산한 판매 여유 객실 수. otaLimit이 null인 한도 미설정 날짜에만 remaining도 null이며 0으로 대체하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "4")
        @Min(0)
        Integer remaining,
        /** 한도가 미설정이어도 이 목록은 제공한다. 가정: 필드명을 salesBlocks로 정하고 필수 배열로 제공한다. null과 null 항목은 허용하지 않으며 차단이 없으면 빈 배열 []로 제공해 판매 중단과 수량 부족을 구분한다. */
        @ArraySchema(arraySchema = @Schema(description = "해당 객실 유형·숙박일에 적용되는 PARTNER·PROPERTY·ROOM_TYPE·ROOM_TYPE_DATE 차단 목록. 요금제·판매 상품 차단은 상품별 가격 조회의 productSalesBlocks에서 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = DaysItemSalesBlocksItem.class))
        @NotNull 
        @Valid
        List<@NotNull  DaysItemSalesBlocksItem> salesBlocks
    ) {
    }
}

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
@Schema(name = "DailySalesPricesResponse", description = "한 판매 상품의 날짜별 가격과 공유 재고 조회 또는 원자적 가격 변경 결과.")
public record DailySalesPricesResponse(
    /** 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @Schema(description = "소유 숙소 식별자. 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "property_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = ".*\\S.*")
    String propertyId,
    /** 가정: 응답 필드명을 productId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @Schema(description = "가격의 판매 상품 식별자. 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "product_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = ".*\\S.*")
    String productId,
    /** 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @Schema(description = "판매 한도를 공유하는 객실 유형 식별자. 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "room_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = ".*\\S.*")
    String roomTypeId,
    /** 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 업무 대상과 서버 계산값을 명확히 구분해 전달하기 위한 결정이다. */
    @Schema(description = "이 판매 상품을 구성하는 요금제 식별자. 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "rate_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = ".*\\S.*")
    String ratePlanId,
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
    @Schema(name = "DailySalesPricesResponseDaysItemInventorySalesBlocksItem", description = "신규 판매를 차단하는 범위 하나. 배열에 존재하면 현재 차단 중이며 수량 부족이나 가격 누락과는 다른 상태다.")
    public record DaysItemInventorySalesBlocksItem(
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
    @Schema(name = "DailySalesPricesResponseDaysItemProductSalesBlocksItem", description = "신규 판매를 차단하는 범위 하나. 배열에 존재하면 현재 차단 중이며 수량 부족이나 가격 누락과는 다른 상태다.")
    public record DaysItemProductSalesBlocksItem(
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
    @Schema(name = "DailySalesPricesResponseDaysItem", description = "한 판매 상품·숙박일 가격과 공유 재고 및 판매 차단을 같은 시점으로 보여준다.")
    public record DaysItem(
        /** 가정: 필드명 stayDate를 필수 ISO 8601 날짜 문자열로 정하고 null을 허용하지 않아 상품 가격과 객실 수량의 날짜를 대조할 수 있게 한다. */
        @Schema(description = "판매 상품의 가격이 귀속되는 Asia/Seoul 현지 숙박일. 같은 days 항목의 inventoryStayDate와 반드시 같으며, 가격 미설정이어도 날짜는 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull 
        LocalDate stayDate,
        /** 가격이 설정되면 priceAmount는 1 이상의 정수이고 priceCurrency는 KRW이다. 가정: 필드명을 priceAmount로 정하고 32비트를 넘는 금액도 보존하도록 Java Long·JSON int64를 사용한다. 필수 키이며 가격 미설정인 경우에만 null을 허용한다. priceAmount를 가격 설정 여부의 기준값으로 삼아 금액과 통화 중 하나만 존재하는 상태를 허용하지 않는다. */
        @Schema(description = "객실 하나·같은 days 항목의 stayDate 하루에 대한 필수 요금 포함 양수 원화 판매가격. 가격이 미설정이면 priceAmount와 priceCurrency를 모두 null로 제공하며 0원으로 대체하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", example = "120000")
        @Min(0)
        Long priceAmount,
        /** 가정: 필드명을 priceCurrency로 정하고 필수 문자열 키에 KRW 또는 명시적 null만 제공해 금액의 원 단위와 가격 미설정을 함께 구분한다. */
        @Schema(description = "같은 days 항목의 priceAmount에 적용되는 통화. 가격이 설정되면 KRW이고 priceAmount도 반드시 양수이며, 가격이 미설정이면 priceCurrency와 priceAmount를 모두 null로 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @Pattern(regexp = "^KRW$")
        String priceCurrency,
        /** 가정: 필드명을 inventoryStayDate로 정하고 필수 ISO 8601 날짜 문자열로 제공하며 null을 허용하지 않아 상품 가격 날짜와 객실 수량 날짜를 각각 보존한다. */
        @Schema(description = "이 판매 상품이 속한 객실 유형의 공유 한도·점유·여유·객실 수준 차단이 귀속되는 Asia/Seoul 현지 숙박일. 같은 days 항목의 stayDate와 반드시 같으며 가격이나 한도가 미설정이어도 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull 
        LocalDate inventoryStayDate,
        /** 한도가 미설정이면 inventoryOtaLimit과 inventoryRemaining만 null이며 inventoryConfirmedOccupancy·inventoryHeldOccupancy·inventoryOccupied는 0, inventoryStayDate와 inventorySalesBlocks는 계속 제공한다. 0은 설정된 판매 한도이고 null과 다르다. 가정: 필드명을 inventoryOtaLimit로 정하고 필수 키에 0 이상의 32비트 정수 또는 한도 미설정을 나타내는 명시적 null을 제공해 파트너 설정값과 서버 계산값을 구분한다. */
        @Schema(description = "같은 days 항목의 inventoryStayDate에 대해 파트너가 설정한 객실 유형의 OTA 판매 한도. 단위는 객실 개수이며 모든 요금제가 공유하고 실제 보유 수량이 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "4")
        @Min(0)
        Integer inventoryOtaLimit,
        /** 파트너의 편집 입력이 아니다. 가정: 필드명을 inventoryConfirmedOccupancy로 정하고 필수 0 이상 32비트 정수로 제공하며 null을 허용하지 않아 확정 예약 점유를 유효 선점 점유와 구분한다. */
        @Schema(description = "같은 days 항목의 inventoryStayDate에 대한 해당 객실 유형의 확정 예약 객실 수 합계를 서버가 계산한 값. 취소된 예약은 제외하며 한도 미설정 날짜에는 0이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "4")
        @NotNull 
        @Min(0)
        Integer inventoryConfirmedOccupancy,
        /** 한도 미설정 날짜에는 0이며 파트너의 편집 입력이 아니다. 가정: 필드명을 inventoryHeldOccupancy로 정하고 필수 0 이상 32비트 정수로 제공하며 null을 허용하지 않아 실제 유효한 선점 점유를 구분한다. */
        @Schema(description = "같은 days 항목의 inventoryStayDate에 대한 미확정 선점 객실 수 합계를 서버가 계산한 값. 응답 observedAt이 선점 만료 시각보다 작은 선점만 포함하고 만료 시각과 같으면 제외한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "4")
        @NotNull 
        @Min(0)
        Integer inventoryHeldOccupancy,
        /** 파트너가 독립 입력하거나 수정하지 않는다. 가정: 필드명을 inventoryOccupied로 정하고 필수 0 이상 32비트 정수로 제공하며 null을 허용하지 않아 판매 한도와 계산된 점유량을 구분한다. */
        @Schema(description = "같은 days 항목의 inventoryConfirmedOccupancy + inventoryHeldOccupancy로 서버가 계산한 객실 유형의 점유량. 모두 응답 observedAt의 값이며 한도 미설정 날짜에는 0이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "4")
        @NotNull 
        @Min(0)
        Integer inventoryOccupied,
        /** 한도가 설정되면 0 이상의 정수이며 여유가 있어도 가격·판매 차단 등에 따라 예약이 거절될 수 있다. 파트너의 편집 입력이 아니다. 가정: 필드명을 inventoryRemaining으로 정하고 필수 키에 32비트 정수 또는 한도 미설정의 명시적 null을 제공해 한도 설정 여부와 계산 결과를 구분한다. */
        @Schema(description = "같은 days 항목에서 응답 observedAt의 inventoryOtaLimit - inventoryOccupied로 서버가 계산한 판매 여유 객실 수. inventoryOtaLimit이 null인 한도 미설정 날짜에만 inventoryRemaining도 null이며 0으로 대체하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "4")
        @Min(0)
        Integer inventoryRemaining,
        /** 가격이나 한도가 미설정이어도 객실 수준 차단 목록은 제공한다. 가정: 필드명을 inventorySalesBlocks로 정하고 필수 배열로 제공한다. null과 null 항목은 허용하지 않으며 차단이 없으면 빈 배열 []로 제공해 미설정 가격·한도나 수량 부족과 구분한다. */
        @ArraySchema(arraySchema = @Schema(description = "같은 days 항목의 inventoryStayDate에 적용되는 PARTNER·PROPERTY·ROOM_TYPE·ROOM_TYPE_DATE 차단 목록. 요금제·판매 상품 차단은 같은 항목의 productSalesBlocks에 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = DaysItemInventorySalesBlocksItem.class))
        @NotNull 
        @Valid
        List<@NotNull  DaysItemInventorySalesBlocksItem> inventorySalesBlocks,
        /** null과 null 항목은 허용하지 않으며 차단이 없으면 빈 배열 []로 제공해 상품 판매 중단과 수량 소진을 구분한다. */
        @ArraySchema(arraySchema = @Schema(description = "이 판매 상품에 적용되는 RATE_PLAN·PRODUCT 차단 목록이며 같은 days 항목의 inventorySalesBlocks와 함께 확인한다. 가정: 필드명을 productSalesBlocks로 정하고 필수 배열로 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = DaysItemProductSalesBlocksItem.class))
        @NotNull 
        @Valid
        List<@NotNull  DaysItemProductSalesBlocksItem> productSalesBlocks,
        /** 가정: 필드명을 salePrerequisitesMet로 정하고 필수 boolean으로 제공하며 null을 허용하지 않아 조회 시점의 하루 판매 기초조건 충족 여부를 명확히 구분한다. */
        @Schema(description = "같은 days 항목의 priceAmount와 priceCurrency가 설정되고 inventoryOtaLimit이 설정되며 inventoryRemaining이 1 이상이고 inventorySalesBlocks와 productSalesBlocks가 모두 빈 배열일 때만 true이다. 이 값은 하루의 기초 판매 조건만 나타내며 전체 숙박 기간·인원·후불 시각·현재 조건 재검증을 통과한 예약 가능 보장이 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull 
        Boolean salePrerequisitesMet
    ) {
    }
}

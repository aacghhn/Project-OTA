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
@Schema(name = "SalesStatusResponse", description = "대상 자체 상태와 영향을 받는 상품의 현재 판매 조건을 분리한 조회 결과.")
public record SalesStatusResponse(
    /** 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "파트너 소유 숙소 식별자. 가정: 문자열 길이는 1~64자로 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "prop_1")
    @NotBlank 
    @Size(min = 1, max = 64)
    String propertyId,
    /** 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "이번 조회가 식별한 판매 상태 범위. 가정: 응답 필드명을 scope, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"PROPERTY", "ROOM_TYPE", "RATE_PLAN", "PRODUCT", "ROOM_TYPE_DATE"})
    @NotNull 
    SalesStatusScope scope,
    /** 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 targetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "선택한 범위의 대상 식별자. 가정: 문자열 길이는 1~64자로 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "room_1")
    @NotBlank 
    @Size(min = 1, max = 64)
    String targetId,
    /** 가정: 문자열 길이는 1~200자로 선택했다. 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 targetName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "현재 대상 이름. 숙소·객실 유형·요금제·판매 상품 중 scope에 맞는 이름이며 ROOM_TYPE_DATE는 객실 유형 이름이다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "스탠더드")
    @NotBlank 
    @Size(min = 1, max = 200)
    String targetName,
    /** 가정: 응답 필드명을 appliesToAllDates, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "ROOM_TYPE_DATE이면 false, 다른 범위이면 true이다. true일 때 from/to는 응답 평가기간일 뿐 상태 적용 범위를 제한하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull 
    Boolean appliesToAllDates,
    /** 가정: 응답 필드명을 from, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "응답 평가기간의 포함 시작 날짜. Asia/Seoul 기준이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
    @NotNull 
    LocalDate from,
    /** 가정: 응답 필드명을 to, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "응답 평가기간의 제외 종료 날짜. from보다 뒤이며 1~366일 기간이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-22")
    @NotNull 
    LocalDate to,
    /** 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "이 숙소의 판매 상태 설정 전체 버전. 가정: 최초 0에서 실제 변경 요청 한 건마다 1 증가하며 모든 대상·날짜에 공유하여 동시 변경 손실을 막는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "8")
    @NotNull 
    @Min(0)
    Long version,
    /** 가정: 응답 필드명을 localStates, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "ROOM_TYPE_DATE이면 각 숙박일 상태를 날짜 오름차순으로 반환하고, 다른 범위이면 stayDate=null인 단일 상태를 반환한다. 가정: 배열 크기는 1~366개로 선택하여 평가기간의 응답 크기를 제한한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = LocalStatesItem.class))
    @NotNull 
    @Size(min = 1, max = 366)
    @Valid
    List<@NotNull LocalStatesItem> localStates,
    /** 가정: 응답 필드명을 affectedProducts, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소·객실·요금제·상품 범위에 연결된 판매 상품 전체이다. 아직 연결된 상품이 없으면 빈 배열이며 이를 판매 성공으로 표시하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = AffectedProductsItem.class))
    @NotNull 
    @Valid
    List<@NotNull AffectedProductsItem> affectedProducts,
    /** 가정: 응답 필드명을 timeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "숙박일 경계의 시간대는 Asia/Seoul이다. 가정: 허용 문자열은 Asia/Seoul로 한정하여 해석을 통일한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^Asia/Seoul$", allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotBlank 
    @Pattern(regexp = "^Asia/Seoul$")
    String timeZone,
    /** 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
    @Schema(description = "상태·가격·한도·점유·여유를 함께 읽은 기준 시각. 모든 계산값은 이 시각의 같은 일관된 관측을 사용한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T16:00:00+09:00")
    @NotNull 
    OffsetDateTime observedAt
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SalesStatusResponseLocalStatesItem", description = "선택한 대상 자체의 중단·재개 상태. 상위나 다른 범위의 차단을 합친 상태가 아니다.")
    public record LocalStatesItem(
        /** 가정: 필드명 stayDate와 ISO 8601 날짜 문자열(LocalDate)을 선택하고 키를 항상 포함한다. 날짜별 설정과 전체 날짜 설정을 구분하도록 ROOM_TYPE_DATE 이외의 범위에서만 null을 허용한다. */
        @Schema(description = "선택 범위가 ROOM_TYPE_DATE이면 해당 숙박일을 반환한다. 나머지 범위는 전체 날짜에 적용되므로 null이며 상태 배열에는 한 항목만 있다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date", example = "2026-12-20")
        LocalDate stayDate,
        /** 신규 상품의 가격·한도 미설정을 판매 가능으로 추정하지 않는다. 가정: 응답 필드명을 state, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "이 대상 자체의 상태. 가정: 별도 중단 설정이 없으면 OPEN으로 표시한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"OPEN", "SUSPENDED"})
        @NotNull 
        SalesStatusValue state,
        /** 사유가 있으면 입력한 1~500자 문장을 보존하며 공백만인 값은 허용하지 않는다. 가정: 필드명을 reason, 타입을 String으로 정하고 키를 항상 포함하며 사유가 없는 상태에는 null을 사용한다. 자유문장 사유를 표시하되 응답 크기를 제한하기 위해 길이를 1~500자로 정했다. */
        @Schema(description = "저장된 중단 사유. OPEN이거나 사유 없이 SUSPENDED로 설정한 경우에만 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "\\S", example = "객실 정비")
        @Size(min = 1, max = 500)
        @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$")
        String reason,
        /** 동일 상태·사유의 재설정으로 시각을 바꾸지 않는다. 가정: 필드명 updatedAt과 오프셋을 포함한 ISO 8601 시각 문자열(OffsetDateTime)을 선택하고 키를 항상 포함한다. 명시적으로 설정한 이력이 없는 기본 상태를 구분하기 위해 그 경우에만 null을 허용한다. */
        @Schema(description = "이 대상의 상태 또는 사유가 실제로 마지막 변경된 시각. 별도 설정이 없는 기본 OPEN이면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T16:00:00+09:00")
        OffsetDateTime updatedAt
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SalesStatusResponseAffectedProductsItemDaysItemRemainingBlocksItem", description = "신규 판매를 차단하는 범위 하나. 배열에 존재하면 현재 차단 중이며 수량 부족이나 가격 누락과는 다른 상태다.")
    public record AffectedProductsItemDaysItemRemainingBlocksItem(
        /** PROPERTY는 숙소 전체, ROOM_TYPE은 객실 유형, RATE_PLAN은 요금제, PRODUCT는 판매 상품, ROOM_TYPE_DATE는 객실 유형의 하루이다. 가정: 응답 필드명을 scope, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "차단 범위. PARTNER는 파트너 정지 조회 정보이며 파트너가 직접 변경하는 판매 범위가 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^(PARTNER|PROPERTY|ROOM_TYPE|RATE_PLAN|PRODUCT|ROOM_TYPE_DATE)$", allowableValues = {"PARTNER", "PROPERTY", "ROOM_TYPE", "RATE_PLAN", "PRODUCT", "ROOM_TYPE_DATE"}, example = "ROOM_TYPE_DATE")
        @NotNull 
        @Pattern(regexp = "^(PARTNER|PROPERTY|ROOM_TYPE|RATE_PLAN|PRODUCT|ROOM_TYPE_DATE)$")
        String scope,
        /** 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다. 가정: 응답 필드명을 targetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "차단 대상 식별자. ROOM_TYPE_DATE에서는 객실 유형 식별자이며 날짜는 stayDate로 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "room_1")
        @NotNull 
        @Size(min = 1, max = 64)
        @Pattern(regexp = ".*\\S.*")
        String targetId,
        /** 가정: 필드명 stayDate와 ISO 8601 날짜 문자열(LocalDate)을 선택하고 키를 항상 포함한다. 날짜별 차단과 전체 날짜 차단을 구분하기 위해 ROOM_TYPE_DATE 이외의 범위에서만 null을 허용한다. */
        @Schema(description = "차단 범위가 ROOM_TYPE_DATE일 때 해당 숙박일을 반환하며 다른 범위에서는 null이다. 날짜는 Asia/Seoul 현지 날짜이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date", example = "2026-12-20")
        LocalDate stayDate,
        /** 가정: 응답 필드명을 reason, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "해당 범위가 신규 판매를 막는 이유를 표시하는 안전한 설명. 가정: 필수 1~500자로 제한해 화면에 표시할 충분한 사유를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "객실 유형의 해당 숙박일 판매가 중단되었습니다.")
        @NotNull 
        @Size(min = 1, max = 500)
        String reason
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SalesStatusResponseAffectedProductsItemDaysItem", description = "한 상품·숙박일의 동일 관측 시점 평가. 수량 소진과 판매 중단을 별도 데이터로 표현한다.")
    public record AffectedProductsItemDaysItem(
        /** 가정: 응답 필드명을 stayDate, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "평가 대상 숙박일. 같은 상품의 배열은 from 포함·to 제외 모든 날짜를 누락·중복 없이 날짜 오름차순으로 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull 
        LocalDate stayDate,
        /** 가격이 미설정이면 priceAmount와 priceCurrency가 모두 null이고, 설정돼 있으면 둘 다 null이 아니다. 한쪽만 null인 쌍과 미설정 가격을 0원으로 대신하는 응답은 허용하지 않는다. 가정: 필드명 priceAmount와 64비트 정수(Long)를 선택해 32비트를 넘는 금액도 보존한다. 키는 항상 포함하고 가격 미설정일 때만 null을 허용한다. 금액의 기본 스키마 하한은 0이며 설정된 판매 가격에는 1원 이상이라는 업무 조건을 함께 적용한다. */
        @Schema(description = "현재 상품·숙박일의 객실 하나 가격이며 필수 요금을 포함한 KRW 정수 원이다. 설정된 판매 가격은 1원 이상이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", example = "120000")
        @Min(0)
        Long priceAmount,
        /** 가격이 미설정이면 두 필드 모두 null이며 한쪽만 null인 쌍은 허용하지 않는다. 가정: 필드명을 priceCurrency, 타입을 String으로 정하고 설정된 가격의 허용값은 KRW로 한정한다. 원 단위를 명확히 표시하기 위한 결정이며 키는 항상 포함하고 가격 미설정일 때만 null을 허용한다. */
        @Schema(description = "현재 상품·숙박일의 객실 하나 가격 통화이다. 가격이 설정돼 있으면 priceCurrency는 KRW이고 priceAmount도 null이 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @Pattern(regexp = "^KRW$")
        String priceCurrency,
        /** 한도를 설정하지 않은 경우에만 null이고 설정한 한도는 0 이상의 정수이다. 가정: 필드명 salesLimit과 Integer 표현을 선택하고 키를 항상 포함한다. 미설정과 명시적인 한도 0을 구분하기 위해 미설정일 때만 null을 허용한다. */
        @Schema(description = "객실 유형·숙박일의 OTA 판매 한도이며 같은 객실 유형에 연결된 요금제들이 공유한다. 실제 보유 객실 수가 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "3")
        @Min(0)
        Integer salesLimit,
        /** 가정: 응답 필드명을 occupied, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "해당 객실 유형·숙박일의 확정 수량과 만료 시각 전인 유효 선점 수량 합계. 서버 계산값으로 파트너가 수정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "1")
        @NotNull 
        @Min(0)
        Integer occupied,
        /** 0은 수량 소진이며 판매 중단 상태와 구분한다. 가정: 필드명 available과 Integer 표현을 선택하고 키를 항상 포함한다. 계산할 한도가 없는 상태를 수량 0으로 오해하지 않도록 한도 미설정일 때만 null을 허용한다. */
        @Schema(description = "같은 기준 시점의 salesLimit에서 occupied를 뺀 서버 계산값이다. salesLimit이 미설정인 경우에만 null이고 설정돼 있으면 0 이상의 정수이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "2")
        @Min(0)
        Integer available,
        /** 차단이 없으면 빈 배열이다. 가정: 응답 필드명을 remainingBlocks, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @ArraySchema(arraySchema = @Schema(description = "이 상품·숙박일에 현재 적용되는 판매 차단 각각이다. 파트너 정지는 읽기만 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = AffectedProductsItemDaysItemRemainingBlocksItem.class))
        @NotNull 
        @Valid
        List<@NotNull AffectedProductsItemDaysItemRemainingBlocksItem> remainingBlocks,
        /** 문제가 없으면 빈 배열이다. 가정: 응답 필드명을 availabilityReasons, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @ArraySchema(arraySchema = @Schema(description = "차단·가격 미설정·한도 미설정·여유 0을 각각 나열한다. 한도 미설정에서는 여유를 0으로 추정하지 않아 NO_AVAILABLE_ROOMS를 추가하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = SalesStatusAvailabilityReason.class, description = "특정 상품·숙박일의 가격·한도·판매 여유 검사에서 확인한 장애. 판매 중단의 원인과 가격·재고 부족을 구분한다.", allowableValues = {"SALES_SUSPENDED", "PRICE_MISSING", "LIMIT_MISSING", "NO_AVAILABLE_ROOMS"}))
        @NotNull 
        @Valid
        List<@NotNull SalesStatusAvailabilityReason> availabilityReasons,
        /** 다른 숙박일·인원·최종 시점 재검증 전에는 예약 수락 보장이 아니다. 가정: 응답 필드명을 readyForOneRoom, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "해당 날짜에 remainingBlocks가 비고 가격·한도가 설정되며 available>=1이면 true이다. 가정: 객실 하나 기준의 조회 판단으로 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull 
        Boolean readyForOneRoom
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SalesStatusResponseAffectedProductsItem", description = "선택한 중단·재개 범위에 연결된 판매 상품과 지정 기간의 날짜별 영향.")
    public record AffectedProductsItem(
        /** 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 productId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "영향받는 판매 상품 식별자. 가정: 문자열 길이는 1~64자로 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "prd_1")
        @NotBlank 
        @Size(min = 1, max = 64)
        String productId,
        /** 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 productName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "영향받는 판매 상품의 현재 고객용 이름. 가정: 문자열 길이는 1~200자로 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "스탠더드 유연 상품")
        @NotBlank 
        @Size(min = 1, max = 200)
        String productName,
        /** 가정: 문자열 길이는 1~64자로 선택했다. 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "이 상품의 객실 유형 식별자. 같은 유형의 상품들은 날짜별 한도를 공유한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "room_1")
        @NotBlank 
        @Size(min = 1, max = 64)
        String roomTypeId,
        /** 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 roomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "이 상품의 현재 객실 유형 이름. 가정: 문자열 길이는 1~200자로 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "스탠더드")
        @NotBlank 
        @Size(min = 1, max = 200)
        String roomTypeName,
        /** 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "이 상품에 연결된 요금제 식별자. 가정: 문자열 길이는 1~64자로 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "rate_1")
        @NotBlank 
        @Size(min = 1, max = 64)
        String ratePlanId,
        /** 가정: 문자열 길이는 1~200자로 선택했다. 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 응답 필드명을 ratePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "파트너가 요금제를 구분하는 현재 내부 표시 이름. 고객용 상품명과 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "유연 요금")
        @NotBlank 
        @Size(min = 1, max = 200)
        String ratePlanName,
        /** 가정: 배열 크기는 1~366개로 선택하여 평가기간의 응답 크기를 제한한다. 가정: 응답 필드명을 days, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @ArraySchema(arraySchema = @Schema(description = "평가기간의 날짜별 가격·한도·점유·여유와 차단이다. 기간의 모든 날짜를 반환한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = AffectedProductsItemDaysItem.class))
        @NotNull 
        @Size(min = 1, max = 366)
        @Valid
        List<@NotNull AffectedProductsItemDaysItem> days,
        /** 가정: 응답 필드명을 allDatesReadyForOneRoom, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 대상과 단위·상태를 응답 필드에서 명확히 구분하기 위한 결정이다. */
        @Schema(description = "days의 모든 readyForOneRoom이 true인 경우에만 true이다. 최종 선점·확정에서는 기간·인원·현재 조건을 다시 검증하므로 예약이 확보됐다는 뜻은 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull 
        Boolean allDatesReadyForOneRoom
    ) {
    }
    /** 가정: SalesStatusAvailabilityReason이라는 enum 이름과 문자열 코드를 선택하여 범위·상태·거절 원인을 구분한다. SALES_SUSPENDED: 한 개 이상의 판매 차단이 남아 있음; PRICE_MISSING: 해당 상품·숙박일 가격이 설정되지 않음; LIMIT_MISSING: 해당 객실 유형·숙박일 판매 한도가 설정되지 않음; NO_AVAILABLE_ROOMS: 설정된 한도에서 점유를 뺀 판매 여유가 0임. */
    @Schema(name = "SalesStatusAvailabilityReason", description = "특정 상품·숙박일의 가격·한도·판매 여유 검사에서 확인한 장애. 판매 중단의 원인과 가격·재고 부족을 구분한다.")
    public enum SalesStatusAvailabilityReason {
/** 한 개 이상의 판매 차단이 남아 있음. */
    SALES_SUSPENDED,
    /** 해당 상품·숙박일 가격이 설정되지 않음. */
    PRICE_MISSING,
    /** 해당 객실 유형·숙박일 판매 한도가 설정되지 않음. */
    LIMIT_MISSING,
    /** 설정된 한도에서 점유를 뺀 판매 여유가 0임. */
    NO_AVAILABLE_ROOMS
    }
    /** 가정: SalesStatusScope이라는 enum 이름과 문자열 코드를 선택하여 범위·상태·거절 원인을 구분한다. PROPERTY: 숙소 전체; ROOM_TYPE: 객실 유형 전체 날짜; RATE_PLAN: 요금제 전체 날짜; PRODUCT: 객실 유형과 요금제를 연결한 판매 상품 전체 날짜; ROOM_TYPE_DATE: 객실 유형의 지정 숙박일. */
    @Schema(name = "SalesStatusScope", description = "파트너가 직접 중단·재개할 범위. 날짜는 객실 유형에만 지정할 수 있고 파트너 계정 정지는 수정 대상이 아니다.")
    public enum SalesStatusScope {
/** 숙소 전체. */
    PROPERTY,
    /** 객실 유형 전체 날짜. */
    ROOM_TYPE,
    /** 요금제 전체 날짜. */
    RATE_PLAN,
    /** 객실 유형과 요금제를 연결한 판매 상품 전체 날짜. */
    PRODUCT,
    /** 객실 유형의 지정 숙박일. */
    ROOM_TYPE_DATE
    }
    /** 가정: SalesStatusValue이라는 enum 이름과 문자열 코드를 선택하여 범위·상태·거절 원인을 구분한다. OPEN: 이 대상 자체의 판매 차단 없음; SUSPENDED: 이 대상의 신규 선점과 미확정 예약 확정 차단. */
    @Schema(name = "SalesStatusValue", description = "대상 자체의 판매 허용 상태. OPEN이어도 다른 범위의 중단·파트너 정지·가격 누락·여유 부족이면 예약을 수락할 수 없다.")
    public enum SalesStatusValue {
/** 이 대상 자체의 판매 차단 없음. */
    OPEN,
    /** 이 대상의 신규 선점과 미확정 예약 확정 차단. */
    SUSPENDED
    }
}

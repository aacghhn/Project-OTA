package com.example.api;

import com.example.api.dto.request.*;
import com.example.api.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;

/** 소유 파트너의 날짜별 가격·OTA 판매 한도 변경과 서버가 계산한 점유·여유 조회를 제공한다. */
@Tag(name = "Daily sales", description = "날짜별 가격과 OTA 한도, 계산된 점유·여유")
public interface DailySalesApi {

    @Operation(operationId = "getDailySalesPrices", summary = "상품 날짜별 가격과 공유 재고 조회", description = "상품의 날짜별 가격과 객실 유형이 공유하는 재고 정보를 조회한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 전체 처리에 성공한 동일 기준 시점의 결과. 변경 응답은 모든 요청 날짜가 반영됐음을 뜻하며 예약 가능 보장은 아니다.", content = @Content(schema = @Schema(implementation = DailySalesPricesResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property_1\",\"productId\":\"product_1\",\"roomTypeId\":\"room_1\",\"ratePlanId\":\"rate_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"priceAmount\":150000,\"priceCurrency\":\"KRW\",\"inventoryStayDate\":\"2026-12-20\",\"inventoryOtaLimit\":5,\"inventoryConfirmedOccupancy\":2,\"inventoryHeldOccupancy\":1,\"inventoryOccupied\":3,\"inventoryRemaining\":2,\"inventorySalesBlocks\":[],\"productSalesBlocks\":[],\"salePrerequisitesMet\":true}],\"observedAt\":\"2026-11-11T15:00:00+09:00\",\"timeZone\":\"Asia/Seoul\"}"))),
        @ApiResponse(responseCode = "400", description = "식별자 또는 조회 날짜 범위가 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<DailySalesPricesResponse> getDailySalesPrices(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*"), example = "property_1")
        @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String propertyId,
        // 조회할 판매 상품 식별자. 가정: 필수 1~64자 opaque 문자열이며 공백만인 값은 거절한다.
        @Parameter(description = "조회할 판매 상품 식별자. 가정: 필수 1~64자 opaque 문자열이며 공백만인 값은 거절한다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*"), example = "product_1")
        @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String productId,
        // 포함되는 조회 시작 숙박일. 가정: 필수 YYYY-MM-DD 현지 날짜로 정한다. 과거 조회도 허용하되 가격·한도 수정 권한과 구분한다.
        @Parameter(description = "포함되는 조회 시작 숙박일. 가정: 필수 YYYY-MM-DD 현지 날짜로 정한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-20")
        @NotNull  LocalDate from,
        // 제외되는 조회 종료 숙박일. from 이후여야 한다. 가정: 한 조회는 1~366일로 제한하여 응답 크기를 제한하며 더 긴 관리 기간은 나누어 조회한다.
        @Parameter(description = "제외되는 조회 종료 숙박일. from 이후여야 한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-21")
        @NotNull  LocalDate to);

    @Operation(operationId = "updateDailySalesPrices", summary = "상품 날짜별 가격 일괄 설정", description = "상품의 선택한 날짜별 가격을 일괄 설정한다. 요청 전체가 함께 적용되거나 거절된다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 전체 처리에 성공한 동일 기준 시점의 결과. 변경 응답은 모든 요청 날짜가 반영됐음을 뜻하며 예약 가능 보장은 아니다.", content = @Content(schema = @Schema(implementation = DailySalesPricesResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property_1\",\"productId\":\"product_1\",\"roomTypeId\":\"room_1\",\"ratePlanId\":\"rate_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"priceAmount\":150000,\"priceCurrency\":\"KRW\",\"inventoryStayDate\":\"2026-12-20\",\"inventoryOtaLimit\":5,\"inventoryConfirmedOccupancy\":2,\"inventoryHeldOccupancy\":1,\"inventoryOccupied\":3,\"inventoryRemaining\":2,\"inventorySalesBlocks\":[],\"productSalesBlocks\":[],\"salePrerequisitesMet\":true}],\"observedAt\":\"2026-11-11T15:00:00+09:00\",\"timeZone\":\"Asia/Seoul\"}"))),
        @ApiResponse(responseCode = "400", description = "식별자·날짜 범위·가격·한도 또는 날짜별 입력 목록이 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "409", description = "같은 멱등 키가 다른 숙소·상품·날짜·가격 요청에 사용됐다. 모든 요청 날짜를 적용하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"IDEMPOTENCY_CONFLICT\",\"message\":\"같은 멱등 키에 다른 숙소 또는 입력 내용이 사용되었습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<DailySalesPricesResponse> updateDailySalesPrices(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*"), example = "property_1")
        @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String propertyId,
        // 가정: 필수 1~128자 문자열로 같은 변경 요청을 식별한다. 파트너·작업·키가 같고 경로 및 정규화한 내용이 같으면 최초 응답을 재생한다. 다른 내용은 409로 거절한다. 중복 변경을 방지하기 위해 정했다.
        @Parameter(description = "가정: 필수 1~128자 문자열로 같은 변경 요청을 식별한다. 파트너·작업·키가 같고 경로 및 정규화한 내용이 같으면 최초 응답을 재생한다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*"), example = "sales-change-20261111-1")
        @NotNull @Size(min = 1, max = 128) @Pattern(regexp = ".*\\S.*") String idempotencyKey,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "변경할 날짜별 값. 점유량·판매 여유·판매 차단은 입력하지 않는다.", content = @Content(schema = @Schema(implementation = DailySalesPriceUpdateRequest.class), examples = @ExampleObject(value = "{\"productId\":\"product_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"amount\":150000,\"currency\":\"KRW\"}]}")))
        @Valid DailySalesPriceUpdateRequest request);

    @Operation(operationId = "getDailySalesInventory", summary = "객실 날짜별 OTA 한도·점유·여유 조회", description = "객실 유형의 날짜별 OTA 판매 한도·점유·판매 여유를 조회한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 전체 처리에 성공한 동일 기준 시점의 결과. 변경 응답은 모든 요청 날짜가 반영됐음을 뜻하며 예약 가능 보장은 아니다.", content = @Content(schema = @Schema(implementation = DailySalesInventoryResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property_1\",\"roomTypeId\":\"room_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"otaLimit\":5,\"confirmedOccupancy\":2,\"heldOccupancy\":1,\"occupied\":3,\"remaining\":2,\"salesBlocks\":[]}],\"observedAt\":\"2026-11-11T15:00:00+09:00\",\"timeZone\":\"Asia/Seoul\"}"))),
        @ApiResponse(responseCode = "400", description = "식별자 또는 조회 날짜 범위가 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<DailySalesInventoryResponse> getDailySalesInventory(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*"), example = "property_1")
        @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String propertyId,
        // 조회할 객실 유형 식별자. 가정: 필수 1~64자 opaque 문자열이며 공백만인 값은 거절한다.
        @Parameter(description = "조회할 객실 유형 식별자. 가정: 필수 1~64자 opaque 문자열이며 공백만인 값은 거절한다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*"), example = "room_1")
        @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String roomTypeId,
        // 포함되는 조회 시작 숙박일. 가정: 필수 YYYY-MM-DD 현지 날짜로 정한다. 과거 조회도 허용하되 가격·한도 수정 권한과 구분한다.
        @Parameter(description = "포함되는 조회 시작 숙박일. 가정: 필수 YYYY-MM-DD 현지 날짜로 정한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-20")
        @NotNull  LocalDate from,
        // 제외되는 조회 종료 숙박일. from 이후여야 한다. 가정: 한 조회는 1~366일로 제한하여 응답 크기를 제한하며 더 긴 관리 기간은 나누어 조회한다.
        @Parameter(description = "제외되는 조회 종료 숙박일. from 이후여야 한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-21")
        @NotNull  LocalDate to);

    @Operation(operationId = "updateDailySalesLimits", summary = "객실 날짜별 OTA 판매 한도 일괄 설정", description = "객실 유형의 날짜별 OTA 판매 한도를 일괄 설정한다. 현재 점유보다 낮은 한도는 거절한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 전체 처리에 성공한 동일 기준 시점의 결과. 변경 응답은 모든 요청 날짜가 반영됐음을 뜻하며 예약 가능 보장은 아니다.", content = @Content(schema = @Schema(implementation = DailySalesInventoryResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property_1\",\"roomTypeId\":\"room_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"otaLimit\":5,\"confirmedOccupancy\":2,\"heldOccupancy\":1,\"occupied\":3,\"remaining\":2,\"salesBlocks\":[]}],\"observedAt\":\"2026-11-11T15:00:00+09:00\",\"timeZone\":\"Asia/Seoul\"}"))),
        @ApiResponse(responseCode = "400", description = "식별자·날짜 범위·가격·한도 또는 날짜별 입력 목록이 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "409", description = "한도가 처리 시점의 점유보다 작거나 같은 멱등 키가 다른 요청에 사용됐다. 모든 요청 날짜를 적용하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVENTORY_LIMIT_BELOW_OCCUPANCY\",\"message\":\"한도가 현재 점유량보다 작아 요청한 모든 날짜를 변경하지 않았습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<DailySalesInventoryResponse> updateDailySalesLimits(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*"), example = "property_1")
        @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String propertyId,
        // 가정: 필수 1~128자 문자열로 같은 변경 요청을 식별한다. 파트너·작업·키가 같고 경로 및 정규화한 내용이 같으면 최초 응답을 재생한다. 다른 내용은 409로 거절한다. 중복 변경을 방지하기 위해 정했다.
        @Parameter(description = "가정: 필수 1~128자 문자열로 같은 변경 요청을 식별한다. 파트너·작업·키가 같고 경로 및 정규화한 내용이 같으면 최초 응답을 재생한다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*"), example = "sales-change-20261111-1")
        @NotNull @Size(min = 1, max = 128) @Pattern(regexp = ".*\\S.*") String idempotencyKey,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "변경할 날짜별 값. 점유량·판매 여유·판매 차단은 입력하지 않는다.", content = @Content(schema = @Schema(implementation = DailySalesLimitUpdateRequest.class), examples = @ExampleObject(value = "{\"roomTypeId\":\"room_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"otaLimit\":5}]}")))
        @Valid DailySalesLimitUpdateRequest request);
}

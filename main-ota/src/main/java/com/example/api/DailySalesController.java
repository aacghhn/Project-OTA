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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** 소유 파트너의 날짜별 가격·OTA 판매 한도 변경과 서버가 계산한 점유·여유 조회를 제공한다. */
@RestController
@Validated
@RequestMapping("/api/partner/properties/{propertyId}")
@Tag(name = "Daily sales", description = "날짜별 가격과 OTA 한도, 계산된 점유·여유")
public class DailySalesController {
    private final DailySalesService service;

    public DailySalesController(DailySalesService service) {
        this.service = service;
    }

    /** 파트너가 소유 숙소의 판매 상품별 가격과 공유 객실 재고 및 차단을 읽는다. 조회는 가격·한도·점유·판매 상태를 변경하지 않는다. 전체 응답의 observedAt을 하나로 정해 확정 예약과 만료되지 않은 선점에서 점유를 도출한다. 남은 수량이 있어도 가격 미설정이나 판매 차단이면 예약 가능으로 단정하지 않는다. 실제 보유 객실 수는 제공하지 않는다. */
    @GetMapping(value = "/daily-prices", produces = "application/json")
    @Operation(operationId = "getDailySalesPrices", summary = "상품 날짜별 가격과 공유 재고 조회", description = "파트너가 소유 숙소의 판매 상품별 가격과 공유 객실 재고 및 차단을 읽는다. 조회는 가격·한도·점유·판매 상태를 변경하지 않는다. 전체 응답의 observedAt을 하나로 정해 확정 예약과 만료되지 않은 선점에서 점유를 도출한다. 남은 수량이 있어도 가격 미설정이나 판매 차단이면 예약 가능으로 단정하지 않는다. 실제 보유 객실 수는 제공하지 않는다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 전체 처리에 성공한 동일 기준 시점의 결과. 변경 응답은 모든 요청 날짜가 반영됐음을 뜻하며 예약 가능 보장은 아니다.", content = @Content(schema = @Schema(implementation = DailySalesPricesResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property_1\",\"productId\":\"product_1\",\"roomTypeId\":\"room_1\",\"ratePlanId\":\"rate_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"priceAmount\":150000,\"priceCurrency\":\"KRW\",\"inventoryStayDate\":\"2026-12-20\",\"inventoryOtaLimit\":5,\"inventoryConfirmedOccupancy\":2,\"inventoryHeldOccupancy\":1,\"inventoryOccupied\":3,\"inventoryRemaining\":2,\"inventorySalesBlocks\":[],\"productSalesBlocks\":[],\"salePrerequisitesMet\":true}],\"observedAt\":\"2026-11-11T15:00:00+09:00\",\"timeZone\":\"Asia/Seoul\"}"))),
        @ApiResponse(responseCode = "400", description = "식별자 또는 조회 날짜 범위가 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"식별자 또는 조회 날짜 범위가 유효하지 않습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "401", description = "파트너 인증이 필요합니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"UNAUTHENTICATED\",\"message\":\"파트너 인증이 필요합니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "403", description = "파트너 역할의 접근만 허용됩니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"ACCESS_DENIED\",\"message\":\"파트너 역할의 접근만 허용됩니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "가격·한도·점유·차단을 일관된 시점으로 조회할 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"가격·한도·점유·차단을 일관된 시점으로 조회할 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}")))
    })
    public ResponseEntity<DailySalesPricesResponse> getDailySalesPrices(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*", minLength = 1, maxLength = 64), example = "property_1")
        @PathVariable("propertyId") @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String propertyId,
        // 조회할 판매 상품 식별자. 가정: 필수 1~64자 opaque 문자열이며 공백만인 값은 거절한다.
        @Parameter(description = "조회할 판매 상품 식별자. 가정: 필수 1~64자 opaque 문자열이며 공백만인 값은 거절한다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*", minLength = 1, maxLength = 64), example = "product_1")
        @RequestParam("productId") @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String productId,
        // 포함되는 조회 시작 숙박일. 가정: 필수 YYYY-MM-DD 현지 날짜로 정한다. 과거 조회도 허용하되 가격·한도 수정 권한과 구분한다.
        @Parameter(description = "포함되는 조회 시작 숙박일. 가정: 필수 YYYY-MM-DD 현지 날짜로 정한다. 과거 조회도 허용하되 가격·한도 수정 권한과 구분한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-20")
        @RequestParam("from") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        // 제외되는 조회 종료 숙박일. from 이후여야 한다. 가정: 한 조회는 1~366일로 제한하여 응답 크기를 제한하며 더 긴 관리 기간은 나누어 조회한다.
        @Parameter(description = "제외되는 조회 종료 숙박일. from 이후여야 한다. 가정: 한 조회는 1~366일로 제한하여 응답 크기를 제한하며 더 긴 관리 기간은 나누어 조회한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-21")
        @RequestParam("to") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.getDailySalesPrices(principal.getName(), propertyId, productId, from, to));
    }

    /** 파트너가 소유 숙소의 한 판매 상품에 날짜별 양수 원화 가격을 설정한다. 같은 요청의 모든 날짜를 원자적으로 반영하며 하나라도 유효하지 않으면 전체를 거절한다. 가격·한도 변경으로 판매 중단을 해제하거나 기존 확정 예약의 약속을 변경하지 않는다. 가정: 멱등 키는 파트너·작업·키 단위로 보존하고 경로·대상·날짜별 값을 비교한다. 날짜 항목은 오름차순으로 정규화하며 중복 날짜는 거절한다. 동일 내용은 최초 응답을 재생하고 다른 내용은 409이다. 최신 계산값은 조회로 확인한다. 가정: 한 요청은 최대 366개 날짜이며 변경일은 Asia/Seoul 오늘 이상만 허용한다. 이 제한은 예약 숙박일수 제한이 아니라 변경 요청 크기와 과거 설정 보호를 위한 것이다. */
    @PutMapping(value = "/daily-prices", produces = "application/json", consumes = "application/json")
    @Operation(operationId = "updateDailySalesPrices", summary = "상품 날짜별 가격 일괄 설정", description = "파트너가 소유 숙소의 한 판매 상품에 날짜별 양수 원화 가격을 설정한다. 같은 요청의 모든 날짜를 원자적으로 반영하며 하나라도 유효하지 않으면 전체를 거절한다. 가격·한도 변경으로 판매 중단을 해제하거나 기존 확정 예약의 약속을 변경하지 않는다. 가정: 멱등 키는 파트너·작업·키 단위로 보존하고 경로·대상·날짜별 값을 비교한다. 날짜 항목은 오름차순으로 정규화하며 중복 날짜는 거절한다. 동일 내용은 최초 응답을 재생하고 다른 내용은 409이다. 최신 계산값은 조회로 확인한다. 가정: 한 요청은 최대 366개 날짜이며 변경일은 Asia/Seoul 오늘 이상만 허용한다. 이 제한은 예약 숙박일수 제한이 아니라 변경 요청 크기와 과거 설정 보호를 위한 것이다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 전체 처리에 성공한 동일 기준 시점의 결과. 변경 응답은 모든 요청 날짜가 반영됐음을 뜻하며 예약 가능 보장은 아니다.", content = @Content(schema = @Schema(implementation = DailySalesPricesResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property_1\",\"productId\":\"product_1\",\"roomTypeId\":\"room_1\",\"ratePlanId\":\"rate_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"priceAmount\":150000,\"priceCurrency\":\"KRW\",\"inventoryStayDate\":\"2026-12-20\",\"inventoryOtaLimit\":5,\"inventoryConfirmedOccupancy\":2,\"inventoryHeldOccupancy\":1,\"inventoryOccupied\":3,\"inventoryRemaining\":2,\"inventorySalesBlocks\":[],\"productSalesBlocks\":[],\"salePrerequisitesMet\":true}],\"observedAt\":\"2026-11-11T15:00:00+09:00\",\"timeZone\":\"Asia/Seoul\"}"))),
        @ApiResponse(responseCode = "400", description = "식별자·날짜 범위·가격·한도 또는 날짜별 입력 목록이 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"식별자·날짜 범위·가격·한도 또는 날짜별 입력 목록이 유효하지 않습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "401", description = "파트너 인증이 필요합니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"UNAUTHENTICATED\",\"message\":\"파트너 인증이 필요합니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "403", description = "파트너 역할의 접근만 허용됩니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"ACCESS_DENIED\",\"message\":\"파트너 역할의 접근만 허용됩니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "409", description = "같은 멱등 키가 다른 숙소·상품·날짜·가격 요청에 사용됐다. 모든 요청 날짜를 적용하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"IDEMPOTENCY_CONFLICT\",\"message\":\"같은 멱등 키에 다른 숙소 또는 입력 내용이 사용되었습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "변경 또는 조회 결과를 안전하게 확인할 수 없습니다. 변경 재요청은 같은 멱등 키를 유지해 주세요.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"변경 또는 조회 결과를 안전하게 확인할 수 없습니다. 변경 재요청은 같은 멱등 키를 유지해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}")))
    })
    public ResponseEntity<DailySalesPricesResponse> updateDailySalesPrices(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*", minLength = 1, maxLength = 64), example = "property_1")
        @PathVariable("propertyId") @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String propertyId,
        // 가정: 필수 1~128자 문자열로 같은 변경 요청을 식별한다. 파트너·작업·키가 같고 경로 및 정규화한 내용이 같으면 최초 응답을 재생한다. 다른 내용은 409로 거절한다. 중복 변경을 방지하기 위해 정했다.
        @Parameter(description = "가정: 필수 1~128자 문자열로 같은 변경 요청을 식별한다. 파트너·작업·키가 같고 경로 및 정규화한 내용이 같으면 최초 응답을 재생한다. 다른 내용은 409로 거절한다. 중복 변경을 방지하기 위해 정했다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*", minLength = 1, maxLength = 128), example = "sales-change-20261111-1")
        @RequestHeader("Idempotency-Key") @NotNull @Size(min = 1, max = 128) @Pattern(regexp = ".*\\S.*") String idempotencyKey,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "변경할 날짜별 값. 점유량·판매 여유·판매 차단은 입력하지 않는다.", content = @Content(schema = @Schema(implementation = DailySalesPriceUpdateRequest.class), examples = @ExampleObject(value = "{\"productId\":\"product_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"amount\":150000,\"currency\":\"KRW\"}]}")))
        @Valid @RequestBody DailySalesPriceUpdateRequest request) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.updateDailySalesPrices(principal.getName(), propertyId, idempotencyKey, request));
    }

    /** 파트너가 소유 숙소의 객실 유형별 OTA 판매 한도와 계산 점유·여유 및 객실 수준 차단을 읽는다. 조회는 가격·한도·점유·판매 상태를 변경하지 않는다. 전체 응답의 observedAt을 하나로 정해 확정 예약과 만료되지 않은 선점에서 점유를 도출한다. 남은 수량이 있어도 가격 미설정이나 판매 차단이면 예약 가능으로 단정하지 않는다. 실제 보유 객실 수는 제공하지 않는다. */
    @GetMapping(value = "/daily-inventory", produces = "application/json")
    @Operation(operationId = "getDailySalesInventory", summary = "객실 날짜별 OTA 한도·점유·여유 조회", description = "파트너가 소유 숙소의 객실 유형별 OTA 판매 한도와 계산 점유·여유 및 객실 수준 차단을 읽는다. 조회는 가격·한도·점유·판매 상태를 변경하지 않는다. 전체 응답의 observedAt을 하나로 정해 확정 예약과 만료되지 않은 선점에서 점유를 도출한다. 남은 수량이 있어도 가격 미설정이나 판매 차단이면 예약 가능으로 단정하지 않는다. 실제 보유 객실 수는 제공하지 않는다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 전체 처리에 성공한 동일 기준 시점의 결과. 변경 응답은 모든 요청 날짜가 반영됐음을 뜻하며 예약 가능 보장은 아니다.", content = @Content(schema = @Schema(implementation = DailySalesInventoryResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property_1\",\"roomTypeId\":\"room_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"otaLimit\":5,\"confirmedOccupancy\":2,\"heldOccupancy\":1,\"occupied\":3,\"remaining\":2,\"salesBlocks\":[]}],\"observedAt\":\"2026-11-11T15:00:00+09:00\",\"timeZone\":\"Asia/Seoul\"}"))),
        @ApiResponse(responseCode = "400", description = "식별자 또는 조회 날짜 범위가 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"식별자 또는 조회 날짜 범위가 유효하지 않습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "401", description = "파트너 인증이 필요합니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"UNAUTHENTICATED\",\"message\":\"파트너 인증이 필요합니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "403", description = "파트너 역할의 접근만 허용됩니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"ACCESS_DENIED\",\"message\":\"파트너 역할의 접근만 허용됩니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "가격·한도·점유·차단을 일관된 시점으로 조회할 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"가격·한도·점유·차단을 일관된 시점으로 조회할 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}")))
    })
    public ResponseEntity<DailySalesInventoryResponse> getDailySalesInventory(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*", minLength = 1, maxLength = 64), example = "property_1")
        @PathVariable("propertyId") @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String propertyId,
        // 조회할 객실 유형 식별자. 가정: 필수 1~64자 opaque 문자열이며 공백만인 값은 거절한다.
        @Parameter(description = "조회할 객실 유형 식별자. 가정: 필수 1~64자 opaque 문자열이며 공백만인 값은 거절한다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*", minLength = 1, maxLength = 64), example = "room_1")
        @RequestParam("roomTypeId") @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String roomTypeId,
        // 포함되는 조회 시작 숙박일. 가정: 필수 YYYY-MM-DD 현지 날짜로 정한다. 과거 조회도 허용하되 가격·한도 수정 권한과 구분한다.
        @Parameter(description = "포함되는 조회 시작 숙박일. 가정: 필수 YYYY-MM-DD 현지 날짜로 정한다. 과거 조회도 허용하되 가격·한도 수정 권한과 구분한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-20")
        @RequestParam("from") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        // 제외되는 조회 종료 숙박일. from 이후여야 한다. 가정: 한 조회는 1~366일로 제한하여 응답 크기를 제한하며 더 긴 관리 기간은 나누어 조회한다.
        @Parameter(description = "제외되는 조회 종료 숙박일. from 이후여야 한다. 가정: 한 조회는 1~366일로 제한하여 응답 크기를 제한하며 더 긴 관리 기간은 나누어 조회한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-21")
        @RequestParam("to") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.getDailySalesInventory(principal.getName(), propertyId, roomTypeId, from, to));
    }

    /** 파트너가 소유 숙소의 한 객실 유형에 날짜별 OTA 판매 한도를 설정한다. 처리 시점의 점유량보다 낮은 한도는 409로 전체 거절하며 예약·선점·만료 처리와 같은 기준으로 순서를 정해 초과 점유를 막는다. 같은 요청의 모든 날짜를 원자적으로 반영하며 하나라도 유효하지 않으면 전체를 거절한다. 가격·한도 변경으로 판매 중단을 해제하거나 기존 확정 예약의 약속을 변경하지 않는다. 가정: 멱등 키는 파트너·작업·키 단위로 보존하고 경로·대상·날짜별 값을 비교한다. 날짜 항목은 오름차순으로 정규화하며 중복 날짜는 거절한다. 동일 내용은 최초 응답을 재생하고 다른 내용은 409이다. 최신 계산값은 조회로 확인한다. 가정: 한 요청은 최대 366개 날짜이며 변경일은 Asia/Seoul 오늘 이상만 허용한다. 이 제한은 예약 숙박일수 제한이 아니라 변경 요청 크기와 과거 설정 보호를 위한 것이다. */
    @PutMapping(value = "/daily-inventory", produces = "application/json", consumes = "application/json")
    @Operation(operationId = "updateDailySalesLimits", summary = "객실 날짜별 OTA 판매 한도 일괄 설정", description = "파트너가 소유 숙소의 한 객실 유형에 날짜별 OTA 판매 한도를 설정한다. 처리 시점의 점유량보다 낮은 한도는 409로 전체 거절하며 예약·선점·만료 처리와 같은 기준으로 순서를 정해 초과 점유를 막는다. 같은 요청의 모든 날짜를 원자적으로 반영하며 하나라도 유효하지 않으면 전체를 거절한다. 가격·한도 변경으로 판매 중단을 해제하거나 기존 확정 예약의 약속을 변경하지 않는다. 가정: 멱등 키는 파트너·작업·키 단위로 보존하고 경로·대상·날짜별 값을 비교한다. 날짜 항목은 오름차순으로 정규화하며 중복 날짜는 거절한다. 동일 내용은 최초 응답을 재생하고 다른 내용은 409이다. 최신 계산값은 조회로 확인한다. 가정: 한 요청은 최대 366개 날짜이며 변경일은 Asia/Seoul 오늘 이상만 허용한다. 이 제한은 예약 숙박일수 제한이 아니라 변경 요청 크기와 과거 설정 보호를 위한 것이다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 전체 처리에 성공한 동일 기준 시점의 결과. 변경 응답은 모든 요청 날짜가 반영됐음을 뜻하며 예약 가능 보장은 아니다.", content = @Content(schema = @Schema(implementation = DailySalesInventoryResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property_1\",\"roomTypeId\":\"room_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"otaLimit\":5,\"confirmedOccupancy\":2,\"heldOccupancy\":1,\"occupied\":3,\"remaining\":2,\"salesBlocks\":[]}],\"observedAt\":\"2026-11-11T15:00:00+09:00\",\"timeZone\":\"Asia/Seoul\"}"))),
        @ApiResponse(responseCode = "400", description = "식별자·날짜 범위·가격·한도 또는 날짜별 입력 목록이 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"식별자·날짜 범위·가격·한도 또는 날짜별 입력 목록이 유효하지 않습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "401", description = "파트너 인증이 필요합니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"UNAUTHENTICATED\",\"message\":\"파트너 인증이 필요합니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "403", description = "파트너 역할의 접근만 허용됩니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"ACCESS_DENIED\",\"message\":\"파트너 역할의 접근만 허용됩니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"소유 범위에서 숙소 또는 판매 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "409", description = "한도가 처리 시점의 점유보다 작거나 같은 멱등 키가 다른 요청에 사용됐다. 모든 요청 날짜를 적용하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVENTORY_LIMIT_BELOW_OCCUPANCY\",\"message\":\"한도가 현재 점유량보다 작아 요청한 모든 날짜를 변경하지 않았습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "변경 또는 조회 결과를 안전하게 확인할 수 없습니다. 변경 재요청은 같은 멱등 키를 유지해 주세요.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"변경 또는 조회 결과를 안전하게 확인할 수 없습니다. 변경 재요청은 같은 멱등 키를 유지해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T06:00:00Z\",\"violations\":[]}")))
    })
    public ResponseEntity<DailySalesInventoryResponse> updateDailySalesLimits(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "소유 숙소 식별자. 가정: 필수 1~64자 opaque 문자열로 정하고 공백만인 값은 거절해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*", minLength = 1, maxLength = 64), example = "property_1")
        @PathVariable("propertyId") @NotNull @Size(min = 1, max = 64) @Pattern(regexp = ".*\\S.*") String propertyId,
        // 가정: 필수 1~128자 문자열로 같은 변경 요청을 식별한다. 파트너·작업·키가 같고 경로 및 정규화한 내용이 같으면 최초 응답을 재생한다. 다른 내용은 409로 거절한다. 중복 변경을 방지하기 위해 정했다.
        @Parameter(description = "가정: 필수 1~128자 문자열로 같은 변경 요청을 식별한다. 파트너·작업·키가 같고 경로 및 정규화한 내용이 같으면 최초 응답을 재생한다. 다른 내용은 409로 거절한다. 중복 변경을 방지하기 위해 정했다.", required = true, schema = @Schema(type = "string", pattern = ".*\\S.*", minLength = 1, maxLength = 128), example = "sales-change-20261111-1")
        @RequestHeader("Idempotency-Key") @NotNull @Size(min = 1, max = 128) @Pattern(regexp = ".*\\S.*") String idempotencyKey,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "변경할 날짜별 값. 점유량·판매 여유·판매 차단은 입력하지 않는다.", content = @Content(schema = @Schema(implementation = DailySalesLimitUpdateRequest.class), examples = @ExampleObject(value = "{\"roomTypeId\":\"room_1\",\"days\":[{\"stayDate\":\"2026-12-20\",\"otaLimit\":5}]}")))
        @Valid @RequestBody DailySalesLimitUpdateRequest request) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.updateDailySalesLimits(principal.getName(), propertyId, idempotencyKey, request));
    }

}

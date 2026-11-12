package com.example.api;

import com.example.api.dto.response.SalesStatusResponse.SalesStatusScope;
import com.example.api.dto.request.SalesStatusChangeRequest;
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

/** 파트너 소유 숙소의 판매 상태를 조회하고 선택 범위의 중단·재개를 요청한다. */
@RestController
@Validated
@RequestMapping("/api/partner/properties/{propertyId}/sales-status")
@Tag(name = "Sales status", description = "판매 중단·재개 범위와 남아 있는 판매 차단 확인")
public class SalesStatusController {
    private final SalesStatusService service;

    public SalesStatusController(SalesStatusService service) {
        this.service = service;
    }

    /** 인증된 파트너가 자기 숙소의 선택 범위와 평가기간을 조회한다. 대상 자체의 OPEN/SUSPENDED와 상품별 날짜에 남아 있는 상위·다른 범위의 차단, 파트너 정지, 가격 누락, 판매 한도 누락, 여유 부족을 구분한다. 가격·한도·점유·여유는 같은 observedAt 기준이다. 조회는 상태·가격·한도·예약을 바꾸지 않는다. 가정: from/to는 Asia/Seoul의 시작 포함·끝 제외 1~366일이며 과거 날짜 조회도 허용한다. ROOM_TYPE_DATE 외의 상태는 전체 날짜에 적용되고 from/to는 응답 평가기간만 제한한다. 객실 하나의 일별 준비 여부는 인원·전체 숙박 조건·최종 시점 재검증 이전의 조회값으로 예약 확보를 뜻하지 않는다. */
    @GetMapping(produces = "application/json")
    @Operation(operationId = "getSalesStatus", summary = "대상 자체의 판매 상태와 남은 차단 조회", description = "인증된 파트너가 자기 숙소의 선택 범위와 평가기간을 조회한다. 대상 자체의 OPEN/SUSPENDED와 상품별 날짜에 남아 있는 상위·다른 범위의 차단, 파트너 정지, 가격 누락, 판매 한도 누락, 여유 부족을 구분한다. 가격·한도·점유·여유는 같은 observedAt 기준이다. 조회는 상태·가격·한도·예약을 바꾸지 않는다. 가정: from/to는 Asia/Seoul의 시작 포함·끝 제외 1~366일이며 과거 날짜 조회도 허용한다. ROOM_TYPE_DATE 외의 상태는 전체 날짜에 적용되고 from/to는 응답 평가기간만 제한한다. 객실 하나의 일별 준비 여부는 인원·전체 숙박 조건·최종 시점 재검증 이전의 조회값으로 예약 확보를 뜻하지 않는다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 범위와 남은 판매 차단을 조회했다. 예약 확정이나 재고 확보 결과가 아니다.", content = @Content(schema = @Schema(implementation = SalesStatusResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"prop_1\",\"scope\":\"ROOM_TYPE\",\"targetId\":\"room_1\",\"targetName\":\"스탠더드\",\"appliesToAllDates\":true,\"from\":\"2026-12-20\",\"to\":\"2026-12-22\",\"version\":7,\"localStates\":[{\"stayDate\":null,\"state\":\"SUSPENDED\",\"reason\":\"객실 정비\",\"updatedAt\":\"2026-11-11T16:00:00+09:00\"}],\"affectedProducts\":[{\"productId\":\"prd_1\",\"productName\":\"스탠더드 유연 상품\",\"roomTypeId\":\"room_1\",\"roomTypeName\":\"스탠더드\",\"ratePlanId\":\"rate_1\",\"ratePlanName\":\"유연 요금\",\"days\":[{\"stayDate\":\"2026-12-20\",\"priceAmount\":120000,\"priceCurrency\":\"KRW\",\"salesLimit\":3,\"occupied\":1,\"available\":2,\"remainingBlocks\":[{\"scope\":\"PROPERTY\",\"targetId\":\"prop_1\",\"stayDate\":null,\"reason\":\"숙소 전체 판매 중단\"},{\"scope\":\"ROOM_TYPE\",\"targetId\":\"room_1\",\"stayDate\":null,\"reason\":\"객실 정비\"}],\"availabilityReasons\":[\"SALES_SUSPENDED\"],\"readyForOneRoom\":false},{\"stayDate\":\"2026-12-21\",\"priceAmount\":130000,\"priceCurrency\":\"KRW\",\"salesLimit\":3,\"occupied\":1,\"available\":2,\"remainingBlocks\":[{\"scope\":\"PROPERTY\",\"targetId\":\"prop_1\",\"stayDate\":null,\"reason\":\"숙소 전체 판매 중단\"},{\"scope\":\"ROOM_TYPE\",\"targetId\":\"room_1\",\"stayDate\":null,\"reason\":\"객실 정비\"}],\"availabilityReasons\":[\"SALES_SUSPENDED\"],\"readyForOneRoom\":false}],\"allDatesReadyForOneRoom\":false}],\"timeZone\":\"Asia/Seoul\",\"observedAt\":\"2026-11-11T16:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "범위·식별자·날짜 입력 조건 위반. 기간은 시작 포함·종료 제외 1~366일이며 과거 조회는 허용한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"범위·식별자·날짜·사유의 입력 조건을 확인해야 한다. 기간은 시작 포함·끝 제외의 1~366일이다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "401", description = "Bearer 인증이 없거나 유효하지 않다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"UNAUTHENTICATED\",\"message\":\"Bearer 인증이 없거나 유효하지 않다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "403", description = "인증 주체가 파트너 역할을 갖지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"ACCESS_DENIED\",\"message\":\"인증 주체가 파트너 역할을 갖지 않는다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "숙소 또는 대상이 없거나 요청한 파트너·숙소의 소유 범위에 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"숙소 또는 대상이 없거나 요청한 파트너·숙소의 소유 범위에 없다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "저장소 장애로 일관된 조회 또는 안전한 상태 저장을 확인할 수 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"저장소 장애로 일관된 조회 또는 안전한 상태 저장을 확인할 수 없다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}")))
    })
    public ResponseEntity<SalesStatusResponse> getSalesStatus(
        @Parameter(hidden = true) Principal principal,
        // 숙소 식별자. 가정: 경로 이름 propertyId와 필수 1~64자 opaque 문자열을 선택하여 내부 저장소 형식을 노출하지 않는다.
        @Parameter(description = "숙소 식별자. 가정: 경로 이름 propertyId와 필수 1~64자 opaque 문자열을 선택하여 내부 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(minLength = 1, maxLength = 64, pattern = "\\S"), example = "prop_1")
        @PathVariable("propertyId") @NotBlank @Size(min = 1, max = 64) String propertyId,
        // 조회할 대상 범위. 가정: 필수 enum PROPERTY/ROOM_TYPE/RATE_PLAN/PRODUCT/ROOM_TYPE_DATE로 구분하여 날짜별 특정 상품이나 파트너 정지를 편집 대상으로 추가하지 않는다.
        @Parameter(description = "조회할 대상 범위. 가정: 필수 enum PROPERTY/ROOM_TYPE/RATE_PLAN/PRODUCT/ROOM_TYPE_DATE로 구분하여 날짜별 특정 상품이나 파트너 정지를 편집 대상으로 추가하지 않는다.", required = true, schema = @Schema(implementation = SalesStatusScope.class), example = "ROOM_TYPE")
        @RequestParam("scope") SalesStatusScope scope,
        // 범위에 맞는 대상 식별자. PROPERTY이면 propertyId와 같고 그 밖에는 같은 숙소의 구성 요소이다. 가정: 필수 1~64자 opaque 문자열을 선택한다.
        @Parameter(description = "범위에 맞는 대상 식별자. PROPERTY이면 propertyId와 같고 그 밖에는 같은 숙소의 구성 요소이다. 가정: 필수 1~64자 opaque 문자열을 선택한다.", required = true, schema = @Schema(minLength = 1, maxLength = 64, pattern = "\\S"), example = "room_1")
        @RequestParam("targetId") @NotBlank @Size(min = 1, max = 64) String targetId,
        // 평가기간의 포함 시작일. 가정: 필수 LocalDate로 받으며 Asia/Seoul 기준 과거 날짜 조회도 허용한다.
        @Parameter(description = "평가기간의 포함 시작일. 가정: 필수 LocalDate로 받으며 Asia/Seoul 기준 과거 날짜 조회도 허용한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-20")
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        // 평가기간의 제외 종료일. 가정: 필수 LocalDate이며 from보다 뒤이고 1~366일로 제한하여 날짜별 응답 크기를 관리한다.
        @Parameter(description = "평가기간의 제외 종료일. 가정: 필수 LocalDate이며 from보다 뒤이고 1~366일로 제한하여 날짜별 응답 크기를 관리한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-22")
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.getSalesStatus(principal.getName(), propertyId, scope, targetId, from, to));
    }

    /** 인증된 파트너가 자기 숙소의 선택한 대상 자체를 중단하거나 재개한다. 숙소 전체·객실 유형·요금제·판매 상품·객실 유형의 숙박일만 허용하며 날짜별 특정 상품과 파트너 계정 정지는 변경하지 않는다. 가격·판매 한도와 기존 확정 예약을 보존한다. 중단 뒤 신규 선점과 미확정 예약 확정은 거절하고, 재개해도 상위·다른 범위의 차단이나 파트너 정지가 남으면 수락하지 않는다. 가정: 숙소 단위 expectedVersion으로 동시 변경 손실을 막고 동일 변경 단위의 모든 날짜를 함께 반영하거나 거절한다. 날짜별 변경은 처리 시점 Asia/Seoul의 오늘 이후만 허용한다. Idempotency-Key는 파트너·작업·키 범위에서 경로와 본문을 비교하며 동일 요청은 최초 응답, 다른 내용은 409이다. 같은 상태·사유를 새 키로 설정하면 changed=false이며 버전·변경 시각을 증가시키지 않는다. 상태 설정 성공은 예약 수락 보장이 아니며 응답의 남은 차단과 평가기간 결과를 함께 확인한다. */
    @PutMapping(produces = "application/json", consumes = "application/json")
    @Operation(operationId = "setSalesStatus", summary = "대상 판매 중단 또는 재개", description = "인증된 파트너가 자기 숙소의 선택한 대상 자체를 중단하거나 재개한다. 숙소 전체·객실 유형·요금제·판매 상품·객실 유형의 숙박일만 허용하며 날짜별 특정 상품과 파트너 계정 정지는 변경하지 않는다. 가격·판매 한도와 기존 확정 예약을 보존한다. 중단 뒤 신규 선점과 미확정 예약 확정은 거절하고, 재개해도 상위·다른 범위의 차단이나 파트너 정지가 남으면 수락하지 않는다. 가정: 숙소 단위 expectedVersion으로 동시 변경 손실을 막고 동일 변경 단위의 모든 날짜를 함께 반영하거나 거절한다. 날짜별 변경은 처리 시점 Asia/Seoul의 오늘 이후만 허용한다. Idempotency-Key는 파트너·작업·키 범위에서 경로와 본문을 비교하며 동일 요청은 최초 응답, 다른 내용은 409이다. 같은 상태·사유를 새 키로 설정하면 changed=false이며 버전·변경 시각을 증가시키지 않는다. 상태 설정 성공은 예약 수락 보장이 아니며 응답의 남은 차단과 평가기간 결과를 함께 확인한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "선택 대상의 상태를 저장하거나 동일 상태임을 확인했다. 다른 차단은 남을 수 있으며 가격·한도와 기존 확정 예약은 보존된다.", content = @Content(schema = @Schema(implementation = SalesStatusMutationResponse.class), examples = @ExampleObject(value = "{\"requestedState\":\"OPEN\",\"changed\":true,\"snapshotPropertyId\":\"prop_1\",\"snapshotScope\":\"ROOM_TYPE\",\"snapshotTargetId\":\"room_1\",\"snapshotTargetName\":\"스탠더드\",\"snapshotAppliesToAllDates\":true,\"snapshotFrom\":\"2026-12-20\",\"snapshotTo\":\"2026-12-22\",\"snapshotVersion\":8,\"snapshotLocalStates\":[{\"stayDate\":null,\"state\":\"OPEN\",\"reason\":null,\"updatedAt\":\"2026-11-11T16:00:00+09:00\"}],\"snapshotAffectedProducts\":[{\"productId\":\"prd_1\",\"productName\":\"스탠더드 유연 상품\",\"roomTypeId\":\"room_1\",\"roomTypeName\":\"스탠더드\",\"ratePlanId\":\"rate_1\",\"ratePlanName\":\"유연 요금\",\"days\":[{\"stayDate\":\"2026-12-20\",\"priceAmount\":120000,\"priceCurrency\":\"KRW\",\"salesLimit\":3,\"occupied\":1,\"available\":2,\"remainingBlocks\":[{\"scope\":\"PROPERTY\",\"targetId\":\"prop_1\",\"stayDate\":null,\"reason\":\"숙소 전체 판매 중단\"}],\"availabilityReasons\":[\"SALES_SUSPENDED\"],\"readyForOneRoom\":false},{\"stayDate\":\"2026-12-21\",\"priceAmount\":130000,\"priceCurrency\":\"KRW\",\"salesLimit\":3,\"occupied\":1,\"available\":2,\"remainingBlocks\":[{\"scope\":\"PROPERTY\",\"targetId\":\"prop_1\",\"stayDate\":null,\"reason\":\"숙소 전체 판매 중단\"}],\"availabilityReasons\":[\"SALES_SUSPENDED\"],\"readyForOneRoom\":false}],\"allDatesReadyForOneRoom\":false}],\"snapshotTimeZone\":\"Asia/Seoul\",\"snapshotObservedAt\":\"2026-11-11T16:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "범위·식별자·날짜·사유의 입력 조건을 확인해야 한다. 기간은 시작 포함·끝 제외의 1~366일이다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"범위·식별자·날짜·사유의 입력 조건을 확인해야 한다. 기간은 시작 포함·끝 제외의 1~366일이다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "401", description = "Bearer 인증이 없거나 유효하지 않다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"UNAUTHENTICATED\",\"message\":\"Bearer 인증이 없거나 유효하지 않다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "403", description = "인증 주체가 파트너 역할을 갖지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"ACCESS_DENIED\",\"message\":\"인증 주체가 파트너 역할을 갖지 않는다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "숙소 또는 대상이 없거나 요청한 파트너·숙소의 소유 범위에 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"숙소 또는 대상이 없거나 요청한 파트너·숙소의 소유 범위에 없다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "409", description = "숙소 판매 상태 버전이 변경되었거나 같은 멱등 키의 요청 내용이 다르다. 이번 변경은 적용하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_VERSION_CONFLICT\",\"message\":\"숙소 판매 상태 버전이 변경되었거나 같은 멱등 키의 요청 내용이 다르다. 이번 변경은 적용하지 않는다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "저장소 장애로 일관된 조회 또는 안전한 상태 저장을 확인할 수 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"저장소 장애로 일관된 조회 또는 안전한 상태 저장을 확인할 수 없다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T16:00:00+09:00\",\"violations\":[]}")))
    })
    public ResponseEntity<SalesStatusMutationResponse> setSalesStatus(
        @Parameter(hidden = true) Principal principal,
        // 숙소 식별자. 가정: 경로 이름 propertyId와 필수 1~64자 opaque 문자열을 선택하여 내부 저장소 형식을 노출하지 않는다.
        @Parameter(description = "숙소 식별자. 가정: 경로 이름 propertyId와 필수 1~64자 opaque 문자열을 선택하여 내부 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(minLength = 1, maxLength = 64, pattern = "\\S"), example = "prop_1")
        @PathVariable("propertyId") @NotBlank @Size(min = 1, max = 64) String propertyId,
        // 가정: 필수 1~128자 문자열로 같은 파트너·작업·키의 경로와 본문을 식별한다. 동일 요청은 최초 응답을 재생하고 다른 내용은 409로 거절하여 반복 변경을 막는다.
        @Parameter(description = "가정: 필수 1~128자 문자열로 같은 파트너·작업·키의 경로와 본문을 식별한다. 동일 요청은 최초 응답을 재생하고 다른 내용은 409로 거절하여 반복 변경을 막는다.", required = true, schema = @Schema(minLength = 1, maxLength = 128, pattern = "\\S"), example = "sales-change-001")
        @RequestHeader("Idempotency-Key") @NotBlank @Size(min = 1, max = 128) String idempotencyKey,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "한 범위의 상태와 평가기간. 날짜별 변경만 기간을 실제 변경 범위로 사용한다.", content = @Content(schema = @Schema(implementation = SalesStatusChangeRequest.class), examples = @ExampleObject(value = "{\"scope\":\"ROOM_TYPE\",\"targetId\":\"room_1\",\"from\":\"2026-12-20\",\"to\":\"2026-12-22\",\"state\":\"OPEN\",\"reason\":null,\"expectedVersion\":7}")))
        @RequestBody @Valid SalesStatusChangeRequest request) {
        if (principal == null) {
            throw new UnauthenticatedException("UNAUTHENTICATED", "파트너 인증이 필요합니다.");
        }
        return ResponseEntity.ok(service.setSalesStatus(principal.getName(), propertyId, idempotencyKey, request));
    }
}

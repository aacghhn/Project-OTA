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
import org.springframework.http.ResponseEntity;

/** 파트너 소유 숙소의 판매 상태를 조회하고 선택 범위의 중단·재개를 요청한다. */
@Tag(name = "Sales status", description = "판매 중단·재개 범위와 남아 있는 판매 차단 확인")
public interface SalesStatusApi {

    @Operation(operationId = "getSalesStatus", summary = "대상 자체의 판매 상태와 남은 차단 조회", description = "선택한 대상의 판매 상태와 상품별 남은 판매 차단을 조회한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 범위와 남은 판매 차단을 조회했다. 예약 확정이나 재고 확보 결과가 아니다.", content = @Content(schema = @Schema(implementation = SalesStatusResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"prop_1\",\"scope\":\"ROOM_TYPE\",\"targetId\":\"room_1\",\"targetName\":\"스탠더드\",\"appliesToAllDates\":true,\"from\":\"2026-12-20\",\"to\":\"2026-12-22\",\"version\":7,\"localStates\":[{\"stayDate\":null,\"state\":\"SUSPENDED\",\"reason\":\"객실 정비\",\"updatedAt\":\"2026-11-11T16:00:00+09:00\"}],\"affectedProducts\":[{\"productId\":\"prd_1\",\"productName\":\"스탠더드 유연 상품\",\"roomTypeId\":\"room_1\",\"roomTypeName\":\"스탠더드\",\"ratePlanId\":\"rate_1\",\"ratePlanName\":\"유연 요금\",\"days\":[{\"stayDate\":\"2026-12-20\",\"priceAmount\":120000,\"priceCurrency\":\"KRW\",\"salesLimit\":3,\"occupied\":1,\"available\":2,\"remainingBlocks\":[{\"scope\":\"PROPERTY\",\"targetId\":\"prop_1\",\"stayDate\":null,\"reason\":\"숙소 전체 판매 중단\"},{\"scope\":\"ROOM_TYPE\",\"targetId\":\"room_1\",\"stayDate\":null,\"reason\":\"객실 정비\"}],\"availabilityReasons\":[\"SALES_SUSPENDED\"],\"readyForOneRoom\":false},{\"stayDate\":\"2026-12-21\",\"priceAmount\":130000,\"priceCurrency\":\"KRW\",\"salesLimit\":3,\"occupied\":1,\"available\":2,\"remainingBlocks\":[{\"scope\":\"PROPERTY\",\"targetId\":\"prop_1\",\"stayDate\":null,\"reason\":\"숙소 전체 판매 중단\"},{\"scope\":\"ROOM_TYPE\",\"targetId\":\"room_1\",\"stayDate\":null,\"reason\":\"객실 정비\"}],\"availabilityReasons\":[\"SALES_SUSPENDED\"],\"readyForOneRoom\":false}],\"allDatesReadyForOneRoom\":false}],\"timeZone\":\"Asia/Seoul\",\"observedAt\":\"2026-11-11T16:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "범위·식별자·날짜 입력 조건 위반. 기간은 시작 포함·종료 제외 1~366일이며 과거 조회는 허용한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"범위·식별자·날짜·사유의 입력 조건을 확인해야 한다. 기간은 시작 포함·끝 제외의 1~366일이다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "숙소 또는 대상이 없거나 요청한 파트너·숙소의 소유 범위에 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<SalesStatusResponse> getSalesStatus(
        @Parameter(hidden = true) Principal principal,
        // 숙소 식별자. 가정: 경로 이름 propertyId와 필수 1~64자 opaque 문자열을 선택하여 내부 저장소 형식을 노출하지 않는다.
        @Parameter(description = "숙소 식별자. 가정: 경로 이름 propertyId와 필수 1~64자 opaque 문자열을 선택하여 내부 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(pattern = "\\S"), example = "prop_1")
        @NotBlank @Size(min = 1, max = 64) String propertyId,
        // 조회할 대상 범위. 가정: 필수 enum PROPERTY/ROOM_TYPE/RATE_PLAN/PRODUCT/ROOM_TYPE_DATE로 구분하여 날짜별 특정 상품이나 파트너 정지를 편집 대상으로 추가하지 않는다.
        @Parameter(description = "조회할 대상 범위. 가정: 필수 enum PROPERTY/ROOM_TYPE/RATE_PLAN/PRODUCT/ROOM_TYPE_DATE로 구분하여 날짜별 특정 상품이나 파트너 정지를 편집 대상으로 추가하지 않는다.", required = true, schema = @Schema(implementation = SalesStatusScope.class), example = "ROOM_TYPE")
         SalesStatusScope scope,
        // 범위에 맞는 대상 식별자. PROPERTY이면 propertyId와 같고 그 밖에는 같은 숙소의 구성 요소이다. 가정: 필수 1~64자 opaque 문자열을 선택한다.
        @Parameter(description = "범위에 맞는 대상 식별자. PROPERTY이면 propertyId와 같고 그 밖에는 같은 숙소의 구성 요소이다.", required = true, schema = @Schema(pattern = "\\S"), example = "room_1")
        @NotBlank @Size(min = 1, max = 64) String targetId,
        // 평가기간의 포함 시작일. 가정: 필수 LocalDate로 받으며 Asia/Seoul 기준 과거 날짜 조회도 허용한다.
        @Parameter(description = "평가기간의 포함 시작일. 가정: 필수 LocalDate로 받으며 Asia/Seoul 기준 과거 날짜 조회도 허용한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-20")
          LocalDate from,
        // 평가기간의 제외 종료일. 가정: 필수 LocalDate이며 from보다 뒤이고 1~366일로 제한하여 날짜별 응답 크기를 관리한다.
        @Parameter(description = "평가기간의 제외 종료일. 가정: 필수 LocalDate이며 from보다 뒤이고 1~366일로 제한하여 날짜별 응답 크기를 관리한다.", required = true, schema = @Schema(type = "string", format = "date"), example = "2026-12-22")
          LocalDate to);

    @Operation(operationId = "setSalesStatus", summary = "대상 판매 중단 또는 재개", description = "선택한 대상의 판매를 중단하거나 재개한다. 다른 차단이나 가격·재고 부족이 남으면 실제 예약은 수락되지 않을 수 있다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "선택 대상의 상태를 저장하거나 동일 상태임을 확인했다. 다른 차단은 남을 수 있으며 가격·한도와 기존 확정 예약은 보존된다.", content = @Content(schema = @Schema(implementation = SalesStatusMutationResponse.class), examples = @ExampleObject(value = "{\"requestedState\":\"OPEN\",\"changed\":true,\"snapshotPropertyId\":\"prop_1\",\"snapshotScope\":\"ROOM_TYPE\",\"snapshotTargetId\":\"room_1\",\"snapshotTargetName\":\"스탠더드\",\"snapshotAppliesToAllDates\":true,\"snapshotFrom\":\"2026-12-20\",\"snapshotTo\":\"2026-12-22\",\"snapshotVersion\":8,\"snapshotLocalStates\":[{\"stayDate\":null,\"state\":\"OPEN\",\"reason\":null,\"updatedAt\":\"2026-11-11T16:00:00+09:00\"}],\"snapshotAffectedProducts\":[{\"productId\":\"prd_1\",\"productName\":\"스탠더드 유연 상품\",\"roomTypeId\":\"room_1\",\"roomTypeName\":\"스탠더드\",\"ratePlanId\":\"rate_1\",\"ratePlanName\":\"유연 요금\",\"days\":[{\"stayDate\":\"2026-12-20\",\"priceAmount\":120000,\"priceCurrency\":\"KRW\",\"salesLimit\":3,\"occupied\":1,\"available\":2,\"remainingBlocks\":[{\"scope\":\"PROPERTY\",\"targetId\":\"prop_1\",\"stayDate\":null,\"reason\":\"숙소 전체 판매 중단\"}],\"availabilityReasons\":[\"SALES_SUSPENDED\"],\"readyForOneRoom\":false},{\"stayDate\":\"2026-12-21\",\"priceAmount\":130000,\"priceCurrency\":\"KRW\",\"salesLimit\":3,\"occupied\":1,\"available\":2,\"remainingBlocks\":[{\"scope\":\"PROPERTY\",\"targetId\":\"prop_1\",\"stayDate\":null,\"reason\":\"숙소 전체 판매 중단\"}],\"availabilityReasons\":[\"SALES_SUSPENDED\"],\"readyForOneRoom\":false}],\"allDatesReadyForOneRoom\":false}],\"snapshotTimeZone\":\"Asia/Seoul\",\"snapshotObservedAt\":\"2026-11-11T16:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "범위·식별자·날짜·사유의 입력 조건을 확인해야 한다. 기간은 시작 포함·끝 제외의 1~366일이다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "숙소 또는 대상이 없거나 요청한 파트너·숙소의 소유 범위에 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_TARGET_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "409", description = "숙소 판매 상태 버전이 변경되었거나 같은 멱등 키의 요청 내용이 다르다. 이번 변경은 적용하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"SALES_VERSION_CONFLICT\",\"message\":\"현재 상태에서는 처리할 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<SalesStatusMutationResponse> setSalesStatus(
        @Parameter(hidden = true) Principal principal,
        // 숙소 식별자. 가정: 경로 이름 propertyId와 필수 1~64자 opaque 문자열을 선택하여 내부 저장소 형식을 노출하지 않는다.
        @Parameter(description = "숙소 식별자. 가정: 경로 이름 propertyId와 필수 1~64자 opaque 문자열을 선택하여 내부 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(pattern = "\\S"), example = "prop_1")
        @NotBlank @Size(min = 1, max = 64) String propertyId,
        // 가정: 필수 1~128자 문자열로 같은 파트너·작업·키의 경로와 본문을 식별한다. 동일 요청은 최초 응답을 재생하고 다른 내용은 409로 거절하여 반복 변경을 막는다.
        @Parameter(description = "가정: 필수 1~128자 문자열로 같은 파트너·작업·키의 경로와 본문을 식별한다. 동일 요청은 최초 응답을 재생하고 다른 내용은 409로 거절하여 반복 변경을 막는다.", required = true, schema = @Schema(pattern = "\\S"), example = "sales-change-001")
        @NotBlank @Size(min = 1, max = 128) String idempotencyKey,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "한 범위의 상태와 평가기간. 날짜별 변경만 기간을 실제 변경 범위로 사용한다.", content = @Content(schema = @Schema(implementation = SalesStatusChangeRequest.class), examples = @ExampleObject(value = "{\"scope\":\"ROOM_TYPE\",\"targetId\":\"room_1\",\"from\":\"2026-12-20\",\"to\":\"2026-12-22\",\"state\":\"OPEN\",\"reason\":null,\"expectedVersion\":7}")))
        @Valid SalesStatusChangeRequest request);
}

package com.example.api;

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
import jakarta.validation.constraints.*;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;

/** 파트너가 소유 숙소의 예약 당시 약속과 현재 취소·금전 결과를 조회하는 읽기 전용 API. */
@Tag(name = "Partner reservations", description = "소유 숙소의 예약 약속과 취소·금전 결과 조회")
public interface PartnerReservationApi {

    @Operation(operationId = "listPartnerReservations", summary = "소유 숙소의 예약 목록 조회", description = "소유 숙소의 확정·취소 예약 목록을 조회한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공. HTTP 성공은 결제·환불·이행 완료를 뜻하지 않는다.", content = @Content(schema = @Schema(implementation = PartnerReservationListResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"prop_1\",\"items\":[{\"reservationId\":\"res_1\",\"referenceCode\":\"ref_1001\",\"roomTypeId\":\"room_1\",\"roomTypeName\":\"스탠더드\",\"ratePlanId\":\"rate_1\",\"ratePlanName\":\"유연 요금\",\"checkIn\":\"2026-12-20\",\"checkOut\":\"2026-12-22\",\"roomCount\":1,\"representativeGuestFirstName\":\"Minjun\",\"representativeGuestLastName\":\"Kim\",\"totalAmount\":240000,\"totalCurrency\":\"KRW\",\"status\":\"CONFIRMED\",\"confirmedAt\":\"2026-11-11T15:01:00+09:00\",\"cancellationReason\":null,\"cancellationDecidedAt\":null}],\"nextCursor\":null,\"observedAt\":\"2026-11-11T15:02:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "검색 값·식별자·페이지 크기의 형식이나 범위가 잘못되었거나 날짜 순서가 뒤바뀌었거나 다른 검색 조건의 cursor이다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"검색 조건의 형식·범위 또는 날짜 순서가 올바르지 않습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "숙소가 없거나 파트너 소유 범위에 없다. 유효한 숙소에서 조건에 맞는 예약이 없는 경우는 200 빈 배열이다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"PROPERTY_NOT_FOUND\",\"message\":\"조회 가능한 소유 숙소가 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<PartnerReservationListResponse> listPartnerReservations(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 필드명 propertyId, String 타입을 선택하고 응답 키를 항상 포함하고 null은 허용하지 않는다. 예약 당시 사실과 현재 조회 사실을 명시적으로 구분하기 위해 정했다.
        @Parameter(description = "소유 숙소 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = "\\S"))
        @NotBlank @Size(min = 1, max = 64) @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*") String propertyId,
        // 가정: 예약 상태 필터는 ALL, CONFIRMED, CANCELLED 중 하나이고 생략하면 CONFIRMED이다. 이행할 예약 목록을 먼저 보여주며 ALL도 확정·취소 두 상태만 포함한다.
        @Parameter(description = "가정: 예약 상태 필터는 ALL, CONFIRMED, CANCELLED 중 하나이고 생략하면 CONFIRMED이다. 이행할 예약 목록을 먼저 보여주며 ALL도 확정·취소 두 상태만 포함한다.", required = false, schema = @Schema(type = "string", allowableValues = {"ALL", "CONFIRMED", "CANCELLED"}, pattern = "^(ALL|CONFIRMED|CANCELLED)$", defaultValue = "CONFIRMED"))
         @Pattern(regexp = "^(ALL|CONFIRMED|CANCELLED)$") String status,
        // 가정: 선택한 예약 번호의 정확한 일치를 1~64자 문자열로 검색한다. 생략하면 이 조건을 적용하지 않으며 빈값·공백만인 값은 거절한다.
        @Parameter(description = "가정: 선택한 예약 번호의 정확한 일치를 1~64자 문자열로 검색한다. 생략하면 이 조건을 적용하지 않으며 빈값·공백만인 값은 거절한다.", required = false, schema = @Schema(type = "string", pattern = "\\S"))
        @Size(min = 1, max = 64) @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*") String reservationId,
        // 가정: 예약 번호와 다른 숙소 참조 코드를 1~128자 문자열의 정확한 일치로 검색한다. 생략하면 필터를 적용하지 않는다. 빈값·공백만인 값은 거절한다.
        @Parameter(description = "가정: 예약 번호와 다른 숙소 참조 코드를 1~128자 문자열의 정확한 일치로 검색한다. 생략하면 필터를 적용하지 않는다.", required = false, schema = @Schema(type = "string", pattern = "\\S"))
        @Size(min = 1, max = 128) @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*") String referenceCode,
        // 가정: 대표 투숙객의 이름을 1~100자 원문 정확한 일치로 검색한다. 성과 별개이며 생략하면 필터를 적용하지 않는다. 대소문자·공백을 임의 정규화하지 않고 공백만인 값은 거절한다.
        @Parameter(description = "가정: 대표 투숙객의 이름을 1~100자 원문 정확한 일치로 검색한다. 성과 별개이며 생략하면 필터를 적용하지 않는다.", required = false, schema = @Schema(type = "string", pattern = "\\S"))
        @Size(min = 1, max = 100) @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*") String guestFirstName,
        // 가정: 대표 투숙객의 성을 1~100자 원문 정확한 일치로 검색한다. 이름과 별개이며 생략하면 필터를 적용하지 않는다. 대소문자·공백을 임의 정규화하지 않고 공백만인 값은 거절한다.
        @Parameter(description = "가정: 대표 투숙객의 성을 1~100자 원문 정확한 일치로 검색한다. 이름과 별개이며 생략하면 필터를 적용하지 않는다.", required = false, schema = @Schema(type = "string", pattern = "\\S"))
        @Size(min = 1, max = 100) @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*") String guestLastName,
        // 가정: 체크인 날짜의 포함 시작일을 YYYY-MM-DD로 검색한다. 생성일·외부 메시지 변경 시각·체류 겹침 조건이 아니다. 생략하면 하한이 없다.
        @Parameter(description = "가정: 체크인 날짜의 포함 시작일을 YYYY-MM-DD로 검색한다. 생성일·외부 메시지 변경 시각·체류 겹침 조건이 아니다.", required = false, schema = @Schema(type = "string", format = "date"))
          LocalDate checkInFrom,
        // 가정: 체크인 날짜의 제외 종료일을 YYYY-MM-DD로 검색한다. 생략하면 상한이 없다. 두 경계가 있으면 checkInFrom < checkInTo여야 한다.
        @Parameter(description = "가정: 체크인 날짜의 제외 종료일을 YYYY-MM-DD로 검색한다. 생략하면 상한이 없다.", required = false, schema = @Schema(type = "string", format = "date"))
          LocalDate checkInTo,
        // 가정: 페이지 크기는 생략 시 20, 허용 범위는 1~100이다. 데이터 크기를 제한하면서 숙소 예약 목록을 단계적으로 읽기 위해 정했다.
        @Parameter(description = "가정: 페이지 크기는 생략 시 20, 허용 범위는 1~100이다. 데이터 크기를 제한하면서 숙소 예약 목록을 단계적으로 읽기 위해 정했다.", required = false, schema = @Schema(type = "integer", format = "int32", defaultValue = "20"))
        @Min(1) @Max(100) Integer limit,
        // 가정: 다음 페이지 토큰은 선택 1~1024자 opaque 문자열이다. 생략하면 첫 페이지이며 다른 숙소·조건의 토큰은 400으로 거절한다. 빈값·공백만인 값도 거절한다.
        @Parameter(description = "가정: 다음 페이지 토큰은 선택 1~1024자 opaque 문자열이다. 생략하면 첫 페이지이며 다른 숙소·조건의 토큰은 400으로 거절한다.", required = false, schema = @Schema(type = "string", pattern = "\\S"))
        @Size(min = 1, max = 1024) @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*") String cursor);

    @Operation(operationId = "getPartnerReservation", summary = "소유 숙소의 예약 약속과 현재 결과 조회", description = "소유 숙소 예약의 예약 당시 약속과 현재 예약·금전 상태를 조회한다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공. HTTP 성공은 결제·환불·이행 완료를 뜻하지 않는다.", content = @Content(schema = @Schema(implementation = PartnerReservationDetailResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"prop_1\",\"reservationId\":\"res_1\",\"referenceCode\":\"ref_1001\",\"bookedProductId\":\"prd_1\",\"bookedProductDescription\":\"숙박 상품\",\"bookedPropertyId\":\"prop_1\",\"bookedPropertyName\":\"서울 스테이\",\"bookedRoomTypeId\":\"room_1\",\"bookedRoomTypeName\":\"스탠더드\",\"bookedRoomTypeDescription\":\"객실 유형 설명\",\"bookedRoomTypeTotalCapacity\":3,\"bookedRatePlanId\":\"rate_1\",\"bookedRatePlanName\":\"유연 요금\",\"bookedCheckIn\":\"2026-12-20\",\"bookedCheckOut\":\"2026-12-22\",\"bookedRoomCount\":1,\"bookedRepresentativeGuestFirstName\":\"Minjun\",\"bookedRepresentativeGuestLastName\":\"Kim\",\"bookedRoomOccupancies\":[{\"roomIndex\":1,\"adults\":2,\"children\":1}],\"bookedPriceTotalAmount\":240000,\"bookedPriceTotalCurrency\":\"KRW\",\"bookedPriceDailyRates\":[{\"stayDate\":\"2026-12-20\",\"unitPriceAmount\":120000,\"unitPriceCurrency\":\"KRW\",\"roomCount\":1,\"subtotalAmount\":120000,\"subtotalCurrency\":\"KRW\"},{\"stayDate\":\"2026-12-21\",\"unitPriceAmount\":120000,\"unitPriceCurrency\":\"KRW\",\"roomCount\":1,\"subtotalAmount\":120000,\"subtotalCurrency\":\"KRW\"}],\"bookedBenefits\":[{\"name\":\"상품 포함 서비스\",\"description\":\"예약 상품에서 약속한 서비스\",\"quantity\":null,\"unit\":null,\"conditions\":\"예약 객실 투숙객에게 숙박 기간 동안 제공\"}],\"bookedCancellationPolicyKind\":\"FREE_CANCELLATION\",\"bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn\":3,\"bookedCancellationPolicyFreeCancellationDeadline\":\"2026-12-17T00:00:00+09:00\",\"bookedCancellationPolicyLateCancellationFeeAmount\":240000,\"bookedCancellationPolicyLateCancellationFeeCurrency\":\"KRW\",\"bookedPaymentTermsTiming\":\"LATER\",\"bookedPaymentTermsScheduledDaysBeforeCheckIn\":5,\"bookedPaymentTermsScheduledAt\":\"2026-12-15T00:00:00+09:00\",\"bookedPaymentTermsAmount\":240000,\"bookedPaymentTermsCurrency\":\"KRW\",\"bookedPaymentTermsMethodLabel\":\"카드 ****1234\",\"perRoomStayTotalAmount\":240000,\"perRoomStayTotalCurrency\":\"KRW\",\"status\":\"CONFIRMED\",\"statusReason\":null,\"createdAt\":\"2026-11-11T15:00:00+09:00\",\"confirmedAt\":\"2026-11-11T15:01:00+09:00\",\"cancellationReason\":null,\"cancellationReasonDetail\":null,\"cancellationDecidedAt\":null,\"cancellationCustomerFeeAmount\":null,\"cancellationCustomerFeeCurrency\":null,\"paymentSummaryLatestStatus\":\"SCHEDULED\",\"paymentSummarySucceededAmount\":0,\"paymentSummarySucceededCurrency\":\"KRW\",\"paymentSummaryOutstandingScheduledAmount\":240000,\"paymentSummaryOutstandingScheduledCurrency\":\"KRW\",\"paymentSummaryHasUnresolvedAttempt\":false,\"refundSummaryLatestStatus\":null,\"refundSummarySucceededAmount\":0,\"refundSummarySucceededCurrency\":\"KRW\",\"refundSummaryRemainingAmount\":0,\"refundSummaryRemainingCurrency\":\"KRW\",\"refundSummaryHasUnresolvedAttempt\":false,\"cancellationFinancialImpactSuccessfulCollectionAmount\":null,\"cancellationFinancialImpactSuccessfulCollectionCurrency\":null,\"cancellationFinancialImpactSuccessfulRefundAmount\":null,\"cancellationFinancialImpactSuccessfulRefundCurrency\":null,\"cancellationFinancialImpactRefundOutstandingAmount\":null,\"cancellationFinancialImpactRefundOutstandingCurrency\":null,\"cancellationFinancialImpactCustomerOutstandingAmount\":null,\"cancellationFinancialImpactCustomerOutstandingCurrency\":null,\"cancellationFinancialImpactProcessingPaymentAmount\":null,\"cancellationFinancialImpactProcessingPaymentCurrency\":null,\"cancellationFinancialImpactUnknownPaymentAmount\":null,\"cancellationFinancialImpactUnknownPaymentCurrency\":null,\"cancellationFinancialImpactProcessingRefundAmount\":null,\"cancellationFinancialImpactProcessingRefundCurrency\":null,\"cancellationFinancialImpactUnknownRefundAmount\":null,\"cancellationFinancialImpactUnknownRefundCurrency\":null,\"cancellationFinancialImpactAmountsProvisional\":null,\"cancellationFinancialImpactPayments\":null,\"cancellationFinancialImpactRefunds\":null,\"cancellationFinancialImpactRemainingWork\":null,\"currentRoomInformationRoomTypeId\":\"room_1\",\"currentRoomInformationName\":\"스탠더드\",\"currentRoomInformationDescription\":\"현재 객실 시설 안내\",\"currentRoomInformationObservedAt\":\"2026-11-11T15:02:00+09:00\",\"canReportUnavailability\":true,\"observedAt\":\"2026-11-11T15:02:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "숙소 또는 예약 식별자가 비었거나 64자를 초과하거나 공백만인 값이다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"숙소 또는 예약 식별자의 형식·길이가 올바르지 않습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "숙소가 없거나 파트너 소유가 아니거나 조회할 예약이 없거나 해당 숙소에 속하지 않거나 확정된 적 없는 예약이다. 존재 여부를 구분해 노출하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"RESERVATION_NOT_FOUND\",\"message\":\"소유 숙소에서 조회 가능한 예약이 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<PartnerReservationDetailResponse> getPartnerReservation(
        @Parameter(hidden = true) Principal principal,
        // 소유 숙소 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 필드명 propertyId, String 타입을 선택하고 응답 키를 항상 포함하고 null은 허용하지 않는다. 예약 당시 사실과 현재 조회 사실을 명시적으로 구분하기 위해 정했다.
        @Parameter(description = "소유 숙소 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = "\\S"))
        @NotBlank @Size(min = 1, max = 64) @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*") String propertyId,
        // 조회할 예약의 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 필드명 reservationId, String 타입을 선택하고 응답 키를 항상 포함하고 null은 허용하지 않는다. 예약 당시 사실과 현재 조회 사실을 명시적으로 구분하기 위해 정했다.
        @Parameter(description = "조회할 예약의 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = "\\S"))
        @NotBlank @Size(min = 1, max = 64) @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*") String reservationId);
}

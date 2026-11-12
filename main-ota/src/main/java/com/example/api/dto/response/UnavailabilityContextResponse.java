package com.example.api.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/** 제공 불가 취소 전 확인과 취소 후 결과 조회. 예약 약속, 최초 취소 결과, 고객 비용과 현재 또는 예상 금전 영향을 구분한다. 조회만으로 취소·환불·추가 수금을 실행하지 않는다. 모든 응답 키는 누락하지 않는다. reservationStatus=CONFIRMED이면 originalCancellationReason, originalCancellationReasonDetail, originalCancellationDecidedAt, originalCancellationCustomerFeeAmount, originalCancellationCustomerFeeCurrency가 모두 null이다. CANCELLED이면 originalCancellationReason을 최초 취소 존재의 기준으로 사용하고 판정 시각·비용·통화도 필수 값으로 제공한다. 이때 상세 사유만 최초 취소 때 기록되지 않았으면 null일 수 있다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "UnavailabilityContextResponse", description = "제공 불가 취소 전 확인과 취소 후 결과 조회. 예약 약속, 최초 취소 결과, 고객 비용과 현재 또는 예상 금전 영향을 구분한다. 조회만으로 취소·환불·추가 수금을 실행하지 않는다. 모든 응답 키는 누락하지 않는다. reservationStatus=CONFIRMED이면 originalCancellationReason, originalCancellationReasonDetail, originalCancellationDecidedAt, originalCancellationCustomerFeeAmount, originalCancellationCustomerFeeCurrency가 모두 null이다. CANCELLED이면 originalCancellationReason을 최초 취소 존재의 기준으로 사용하고 판정 시각·비용·통화도 필수 값으로 제공한다. 이때 상세 사유만 최초 취소 때 기록되지 않았으면 null일 수 있다.")
public record UnavailabilityContextResponse(
    /** 대상 숙소 식별자. 가정: 필드명 propertyId, 필수 1~64자 opaque 문자열로 정해 저장소 형식을 노출하지 않고 대상을 구분한다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 숙소 식별자. 가정: 필드명 propertyId, 필수 1~64자 opaque 문자열로 정해 저장소 형식을 노출하지 않고 대상을 구분한다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "prop_1")
    @NotBlank 
    @Size(min = 1, max = 64)
    String propertyId,
    /** 대상 예약 식별자. 가정: 필드명 reservationId, 필수 1~64자 opaque 문자열로 정해 저장소 형식을 노출하지 않고 대상을 구분한다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 식별자. 가정: 필드명 reservationId, 필수 1~64자 opaque 문자열로 정해 저장소 형식을 노출하지 않고 대상을 구분한다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "rsv_1")
    @NotBlank 
    @Size(min = 1, max = 64)
    String reservationId,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 선택한 단일 판매 상품 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedProductId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 선택한 단일 판매 상품 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedProductId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "prd_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedProductId,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 고객이 동의한 객실·요금제·서비스 조합의 상품 설명을 보존한다. 현재 상품 설명으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 설명을 손실 없이 조회하기 위함이다. 가정: 응답 필드명을 bookedProductDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 고객이 동의한 객실·요금제·서비스 조합의 상품 설명을 보존한다. 현재 상품 설명으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 설명을 손실 없이 조회하기 위함이다. 가정: 응답 필드명을 bookedProductDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 5000, example = "숙박 상품")
    @NotNull
    @Size(min = 1, max = 5000)
    @Pattern(regexp = "[\\s\\S]*(?:\\S)[\\s\\S]*")
    String bookedProductDescription,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 숙소 식별자와 이름. 예약 숙소 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedPropertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 숙소 식별자와 이름. 예약 숙소 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedPropertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "prop_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedPropertyId,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 숙소 식별자와 이름. 예약 당시 숙소 이름. 가정: 표시 이름 길이는 1~200자로 정해 빈 이름을 방지하고 화면 표시를 제한한다. 가정: 응답 필드명을 bookedPropertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 숙소 식별자와 이름. 예약 당시 숙소 이름. 가정: 표시 이름 길이는 1~200자로 정해 빈 이름을 방지하고 화면 표시를 제한한다. 가정: 응답 필드명을 bookedPropertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "서울 스테이")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedPropertyName,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 객실 유형 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 객실 유형 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "room_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedRoomTypeId,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 객실 유형 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRoomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 객실 유형 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRoomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "스탠더드")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedRoomTypeName,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 제공하기로 한 객실의 설명을 보존한다. 현재 객실 설명 변경으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 객실 설명과 확정한 약속을 일치시키기 위함이다. 가정: 응답 필드명을 bookedRoomTypeDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 제공하기로 한 객실의 설명을 보존한다. 현재 객실 설명 변경으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 객실 설명과 확정한 약속을 일치시키기 위함이다. 가정: 응답 필드명을 bookedRoomTypeDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 5000, example = "객실 유형 설명")
    @NotNull
    @Size(min = 1, max = 5000)
    @Pattern(regexp = "[\\s\\S]*(?:\\S)[\\s\\S]*")
    String bookedRoomTypeDescription,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 객실 한 개의 성인·아동 합계 허용 인원. 무료 투숙객도 포함하며 1 이상이다. 가정: 응답 필드명을 bookedRoomTypeTotalCapacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 객실 한 개의 성인·아동 합계 허용 인원. 무료 투숙객도 포함하며 1 이상이다. 가정: 응답 필드명을 bookedRoomTypeTotalCapacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "3")
    @NotNull
    @Min(1)
    Integer bookedRoomTypeTotalCapacity,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 요금제 식별자와 이름. 예약 요금제 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRatePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 요금제 식별자와 이름. 예약 요금제 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRatePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "rate_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedRatePlanId,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 요금제 식별자와 이름. 예약 당시 요금제 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRatePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 요금제 식별자와 이름. 예약 당시 요금제 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRatePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "유연 요금")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedRatePlanName,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 포함되는 체크인 날짜. Asia/Seoul 기준이다. 가정: 응답 필드명을 bookedCheckIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 포함되는 체크인 날짜. Asia/Seoul 기준이다. 가정: 응답 필드명을 bookedCheckIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
    @NotNull
    LocalDate bookedCheckIn,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 포함되지 않는 체크아웃 날짜. bookedCheckIn보다 뒤여야 한다. 가정: 응답 필드명을 bookedCheckOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 포함되지 않는 체크아웃 날짜. bookedCheckIn보다 뒤여야 한다. 가정: 응답 필드명을 bookedCheckOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-22")
    @NotNull
    LocalDate bookedCheckOut,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약한 동일 객실 유형의 수량. 가정: 정수로 표현하며 1 이상이다. 가정: 응답 필드명을 bookedRoomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약한 동일 객실 유형의 수량. 가정: 정수로 표현하며 1 이상이다. 가정: 응답 필드명을 bookedRoomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
    @NotNull
    @Min(1)
    Integer bookedRoomCount,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 이름 부분. 성과 분리하여 숙소 예약 확인에 사용한다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestFirstName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 이름 부분. 성과 분리하여 숙소 예약 확인에 사용한다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestFirstName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "Minjun")
    @NotNull
    @Size(min = 1, max = 100)
    String bookedRepresentativeGuestFirstName,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 성 부분. 이름과 합치지 않는다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestLastName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 성 부분. 이름과 합치지 않는다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestLastName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "Kim")
    @NotNull
    @Size(min = 1, max = 100)
    String bookedRepresentativeGuestLastName,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 객실별 성인·아동 인원. 배열 길이는 bookedRoomCount이고 각 객실의 합은 bookedRoomTypeTotalCapacity 이하다. 가정: 응답 필드명을 bookedRoomOccupancies, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 각 항목의 adults와 children은 각각 0 이상이고 합은 1 이상이며 무료 투숙객도 포함한다. */
    @ArraySchema(arraySchema = @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 객실별 성인·아동 인원. 배열 길이는 bookedRoomCount이고 각 객실의 합은 bookedRoomTypeTotalCapacity 이하다. 가정: 응답 필드명을 bookedRoomOccupancies, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 각 항목의 adults와 children은 각각 0 이상이고 합은 1 이상이며 무료 투숙객도 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedRoomOccupanciesItem.class), minItems = 1)
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull BookedRoomOccupanciesItem> bookedRoomOccupancies,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates 각 항목의 subtotalAmount 합이다. 금액은 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPriceTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates 각 항목의 subtotalAmount 합이다. 금액은 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPriceTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedPriceTotalAmount,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates 각 항목의 subtotalAmount 합이다. 금액은 1원 이상이다. 가정: 응답 필드명을 bookedPriceTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates 각 항목의 subtotalAmount 합이다. 금액은 1원 이상이다. 가정: 응답 필드명을 bookedPriceTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedPriceTotalCurrency,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 체크인 포함·체크아웃 제외 각 숙박일의 가격. 날짜 오름차순이며 누락·중복이 없다. 가정: 응답 필드명을 bookedPriceDailyRates, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 체크인 포함·체크아웃 제외 각 숙박일의 가격. 날짜 오름차순이며 누락·중복이 없다. 가정: 응답 필드명을 bookedPriceDailyRates, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedPriceDailyRatesItem.class), minItems = 1)
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull BookedPriceDailyRatesItem> bookedPriceDailyRates,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 포함 혜택 각각의 이름·설명·수량·단위. 혜택이 없으면 빈 배열이다. 가정: 응답 필드명을 bookedBenefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 포함 혜택 각각의 이름·설명·수량·단위. 혜택이 없으면 빈 배열이다. 가정: 응답 필드명을 bookedBenefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedBenefitsItem.class))
    @NotNull
    @Valid
    List<@NotNull BookedBenefitsItem> bookedBenefits,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 가정: 취소 정책을 FREE_CANCELLATION과 NON_REFUNDABLE로 구분한다. 무료 기한이 있는 정책과 환불 불가 정책의 비용 계산을 분명히 하기 위함이다. 가정: 응답 필드명을 bookedCancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 정책은 고객의 전체 취소 비용 계산용이며 처음부터 예정 결제 실패나 숙소 제공 불가로 취소할 때는 고객 비용 0원을 적용한다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 가정: 취소 정책을 FREE_CANCELLATION과 NON_REFUNDABLE로 구분한다. 무료 기한이 있는 정책과 환불 불가 정책의 비용 계산을 분명히 하기 위함이다. 가정: 응답 필드명을 bookedCancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 정책은 고객의 전체 취소 비용 계산용이며 처음부터 예정 결제 실패나 숙소 제공 불가로 취소할 때는 고객 비용 0원을 적용한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"FREE_CANCELLATION", "NON_REFUNDABLE"}, example = "FREE_CANCELLATION")
    @NotNull
    CustomerReservationCancellationKind bookedCancellationPolicyKind,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한을 정하는 체크인 전 일수 N. 가정: 0 이상이며 bookedCancellationPolicyKind가 NON_REFUNDABLE이면 null이다. 예정 결제 일수와 별도 설정이다. 가정: null은 무료 취소 기한이 없는 환불 불가 정책으로 정했다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한을 정하는 체크인 전 일수 N. 가정: 0 이상이며 bookedCancellationPolicyKind가 NON_REFUNDABLE이면 null이다. 예정 결제 일수와 별도 설정이다. 가정: null은 무료 취소 기한이 없는 환불 불가 정책으로 정했다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", example = "3")
    @Min(0)
    Integer bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 체크인 N일 전 Asia/Seoul 00:00. 이 시각 미만은 비용 0이며 같거나 이후에는 총액 전액이다. 가정: RFC3339 오프셋을 포함하며 bookedCancellationPolicyKind가 NON_REFUNDABLE이면 null이다. 가정: null은 무료 취소 기한이 없는 환불 불가 정책으로 정했다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDeadline, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 체크인 N일 전 Asia/Seoul 00:00. 이 시각 미만은 비용 0이며 같거나 이후에는 총액 전액이다. 가정: RFC3339 오프셋을 포함하며 bookedCancellationPolicyKind가 NON_REFUNDABLE이면 null이다. 가정: null은 무료 취소 기한이 없는 환불 불가 정책으로 정했다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDeadline, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-17T00:00:00+09:00")
    OffsetDateTime bookedCancellationPolicyFreeCancellationDeadline,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedCancellationPolicyLateCancellationFeeAmount,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedCancellationPolicyLateCancellationFeeCurrency,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 가정: 지금 결제는 IMMEDIATE, 나중 결제는 LATER로 구분한다. 고객이 임의의 청구 시각을 입력하는 값이 아니다. 가정: 응답 필드명을 bookedPaymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 가정: 지금 결제는 IMMEDIATE, 나중 결제는 LATER로 구분한다. 고객이 임의의 청구 시각을 입력하는 값이 아니다. 가정: 응답 필드명을 bookedPaymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"IMMEDIATE", "LATER"}, example = "LATER")
    @NotNull
    CustomerReservationPaymentTiming bookedPaymentTermsTiming,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 예정 결제 일수 N. 무료 취소 일수와 독립된 값이다. 가정: bookedPaymentTermsTiming이 LATER이면 0 이상이며 bookedPaymentTermsTiming이 IMMEDIATE이면 null이다. 가정: null은 즉시 결제이므로 예정 일수 없음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 예정 결제 일수 N. 무료 취소 일수와 독립된 값이다. 가정: bookedPaymentTermsTiming이 LATER이면 0 이상이며 bookedPaymentTermsTiming이 IMMEDIATE이면 null이다. 가정: null은 즉시 결제이므로 예정 일수 없음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", example = "5")
    @Min(0)
    Integer bookedPaymentTermsScheduledDaysBeforeCheckIn,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 체크인 N일 전 Asia/Seoul 00:00의 예정 결제 시각. 가정: bookedPaymentTermsTiming이 LATER이면 RFC3339 시각, bookedPaymentTermsTiming이 IMMEDIATE이면 null이다. 가정: null은 즉시 결제이므로 미래 청구 시각 없음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsScheduledAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 체크인 N일 전 Asia/Seoul 00:00의 예정 결제 시각. 가정: bookedPaymentTermsTiming이 LATER이면 RFC3339 시각, bookedPaymentTermsTiming이 IMMEDIATE이면 null이다. 가정: null은 즉시 결제이므로 미래 청구 시각 없음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsScheduledAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-15T00:00:00+09:00")
    OffsetDateTime bookedPaymentTermsScheduledAt,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPaymentTermsAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPaymentTermsAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedPaymentTermsAmount,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 응답 필드명을 bookedPaymentTermsCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 응답 필드명을 bookedPaymentTermsCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedPaymentTermsCurrency,
    /** 대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 고객이 선택한 수단의 마스킹된 표시. 카드 원문 번호·인증 정보는 반환하지 않는다. 가정: 표시 정보가 있으면 1~100자 문자열이고, 선택한 수단의 제공자가 마스킹 표시 정보를 제공하지 않은 경우에만 null로 정했다. 표시 정보의 부재를 결제수단 미선택이나 결제 실패로 오인하지 않게 하기 위함이다. null이어도 예약 생성에는 결제수단 식별자와 소유권 검증이 필수이며, 즉시 결제의 확정에는 결제 성공이, 나중 결제의 확정에는 미래 청구와 유효한 결제수단의 준비가 필요하다. 가정: null은 선택한 결제수단의 제공자가 마스킹 표시 정보를 제공하지 않음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsMethodLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "대상 예약 당시의 상품·객실 설명, 기간·객실 수·인원·가격·혜택·취소·결제 약속. 조회 시 현재 가격으로 다시 계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 고객이 선택한 수단의 마스킹된 표시. 카드 원문 번호·인증 정보는 반환하지 않는다. 가정: 표시 정보가 있으면 1~100자 문자열이고, 선택한 수단의 제공자가 마스킹 표시 정보를 제공하지 않은 경우에만 null로 정했다. 표시 정보의 부재를 결제수단 미선택이나 결제 실패로 오인하지 않게 하기 위함이다. null이어도 예약 생성에는 결제수단 식별자와 소유권 검증이 필수이며, 즉시 결제의 확정에는 결제 성공이, 나중 결제의 확정에는 미래 청구와 유효한 결제수단의 준비가 필요하다. 가정: null은 선택한 결제수단의 제공자가 마스킹 표시 정보를 제공하지 않음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsMethodLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 100, example = "카드 ****1234")
    @Size(min = 1, max = 100)
    String bookedPaymentTermsMethodLabel,
    /** 현재 예약 상태. 가정: 필드명 reservationStatus, 필수 ReservationStatus이며 CONFIRMED 또는 CANCELLED만 반환한다. 확정은 결제 성공을, 취소는 환불 성공을 뜻하지 않는다. 미확정 대상은 이 작업을 진행할 수 없어 409로 거절한다. 가정: 응답 필드명을 reservationStatus, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "현재 예약 상태. 가정: 필드명 reservationStatus, 필수 ReservationStatus이며 CONFIRMED 또는 CANCELLED만 반환한다. 확정은 결제 성공을, 취소는 환불 성공을 뜻하지 않는다. 미확정 대상은 이 작업을 진행할 수 없어 409로 거절한다. 가정: 응답 필드명을 reservationStatus, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"CONFIRMED", "CANCELLED"}, example = "CONFIRMED")
    @NotNull 
    ReservationStatus reservationStatus,
    /** 새 제공 불가 취소 요청이 가능한지 나타낸다. 가정: 필드명 canReportUnavailability, 필수 Boolean이며 CONFIRMED에서만 true, CANCELLED이면 false이다. false여도 이전 요청 재전송은 최초 취소 결과를 반환하며 이 값 자체가 제공 불가 사실이나 취소 완료를 판정하지 않는다. 가정: 응답 필드명을 canReportUnavailability, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "새 제공 불가 취소 요청이 가능한지 나타낸다. 가정: 필드명 canReportUnavailability, 필수 Boolean이며 CONFIRMED에서만 true, CANCELLED이면 false이다. false여도 이전 요청 재전송은 최초 취소 결과를 반환하며 이 값 자체가 제공 불가 사실이나 취소 완료를 판정하지 않는다. 가정: 응답 필드명을 canReportUnavailability, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
    @NotNull 
    Boolean canReportUnavailability,
    /** 고객 부담 비용. 확정 예약의 새 제공 불가 취소를 미리 확인할 때는 0원이고, 이미 취소됐으면 최초 비용을 그대로 반환한다. 임의 비용 면제나 금액 입력 없이 두 경우를 구분하기 위한 선택이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 customerFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "고객 부담 비용. 확정 예약의 새 제공 불가 취소를 미리 확인할 때는 0원이고, 이미 취소됐으면 최초 비용을 그대로 반환한다. 임의 비용 면제나 금액 입력 없이 두 경우를 구분하기 위한 선택이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 customerFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long customerFeeAmount,
    /** 고객 부담 비용. 확정 예약의 새 제공 불가 취소를 미리 확인할 때는 0원이고, 이미 취소됐으면 최초 비용을 그대로 반환한다. 임의 비용 면제나 금액 입력 없이 두 경우를 구분하기 위한 선택이다. 가정: 응답 필드명을 customerFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "고객 부담 비용. 확정 예약의 새 제공 불가 취소를 미리 확인할 때는 0원이고, 이미 취소됐으면 최초 비용을 그대로 반환한다. 임의 비용 면제나 금액 입력 없이 두 경우를 구분하기 위한 선택이다. 가정: 응답 필드명을 customerFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String customerFeeCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 성공이 확인된 서로 다른 논리 결제만 합산한 P이다. 반복 결과 통지는 다시 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactSuccessfulCollectionAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 성공이 확인된 서로 다른 논리 결제만 합산한 P이다. 반복 결과 통지는 다시 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactSuccessfulCollectionAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactSuccessfulCollectionAmount,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 성공이 확인된 서로 다른 논리 결제만 합산한 P이다. 반복 결과 통지는 다시 합산하지 않는다. 가정: 응답 필드명을 financialImpactSuccessfulCollectionCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 성공이 확인된 서로 다른 논리 결제만 합산한 P이다. 반복 결과 통지는 다시 합산하지 않는다. 가정: 응답 필드명을 financialImpactSuccessfulCollectionCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactSuccessfulCollectionCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 성공이 확인된 서로 다른 환불만 합산한 R이다. 성공 수금액을 초과하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactSuccessfulRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 성공이 확인된 서로 다른 환불만 합산한 R이다. 성공 수금액을 초과하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactSuccessfulRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactSuccessfulRefundAmount,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 성공이 확인된 서로 다른 환불만 합산한 R이다. 성공 수금액을 초과하지 않는다. 가정: 응답 필드명을 financialImpactSuccessfulRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 성공이 확인된 서로 다른 환불만 합산한 R이다. 성공 수금액을 초과하지 않는다. 가정: 응답 필드명을 financialImpactSuccessfulRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactSuccessfulRefundCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 현재 확인된 환불 잔액 max(P-R-F,0)이다. F는 customerFeeAmount이다. 확정 예약의 취소 예상에서는 0원, 취소 예약에서는 보존한 최초 비용을 사용한다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactRefundOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. P는 financialImpactSuccessfulCollectionAmount, R은 financialImpactSuccessfulRefundAmount이다. PROCESSING·UNKNOWN은 성공 합계에 포함하지 않는다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 현재 확인된 환불 잔액 max(P-R-F,0)이다. F는 customerFeeAmount이다. 확정 예약의 취소 예상에서는 0원, 취소 예약에서는 보존한 최초 비용을 사용한다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactRefundOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. P는 financialImpactSuccessfulCollectionAmount, R은 financialImpactSuccessfulRefundAmount이다. PROCESSING·UNKNOWN은 성공 합계에 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactRefundOutstandingAmount,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 현재 확인된 환불 잔액 max(P-R-F,0)이다. F는 customerFeeAmount이다. 확정 예약의 취소 예상에서는 0원, 취소 예약에서는 보존한 최초 비용을 사용한다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니다. 가정: 응답 필드명을 financialImpactRefundOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. P는 financialImpactSuccessfulCollectionAmount, R은 financialImpactSuccessfulRefundAmount이다. PROCESSING·UNKNOWN은 성공 합계에 포함하지 않는다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 현재 확인된 환불 잔액 max(P-R-F,0)이다. F는 customerFeeAmount이다. 확정 예약의 취소 예상에서는 0원, 취소 예약에서는 보존한 최초 비용을 사용한다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니다. 가정: 응답 필드명을 financialImpactRefundOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. P는 financialImpactSuccessfulCollectionAmount, R은 financialImpactSuccessfulRefundAmount이다. PROCESSING·UNKNOWN은 성공 합계에 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactRefundOutstandingCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. F는 customerFeeAmount이며 취소 전 예상은 0원, 취소 후에는 최초 비용이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패와 별개다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactCustomerOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. P는 financialImpactSuccessfulCollectionAmount, R은 financialImpactSuccessfulRefundAmount이다. PROCESSING·UNKNOWN은 성공 합계에 포함하지 않는다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. F는 customerFeeAmount이며 취소 전 예상은 0원, 취소 후에는 최초 비용이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패와 별개다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactCustomerOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. P는 financialImpactSuccessfulCollectionAmount, R은 financialImpactSuccessfulRefundAmount이다. PROCESSING·UNKNOWN은 성공 합계에 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactCustomerOutstandingAmount,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. F는 customerFeeAmount이며 취소 전 예상은 0원, 취소 후에는 최초 비용이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패와 별개다. 가정: 응답 필드명을 financialImpactCustomerOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. P는 financialImpactSuccessfulCollectionAmount, R은 financialImpactSuccessfulRefundAmount이다. PROCESSING·UNKNOWN은 성공 합계에 포함하지 않는다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. F는 customerFeeAmount이며 취소 전 예상은 0원, 취소 후에는 최초 비용이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패와 별개다. 가정: 응답 필드명을 financialImpactCustomerOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. P는 financialImpactSuccessfulCollectionAmount, R은 financialImpactSuccessfulRefundAmount이다. PROCESSING·UNKNOWN은 성공 합계에 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactCustomerOutstandingCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. PROCESSING 상태의 결제 대상액 합계이다. 성공 수금액에 더하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactProcessingPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. PROCESSING 상태의 결제 대상액 합계이다. 성공 수금액에 더하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactProcessingPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactProcessingPaymentAmount,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. PROCESSING 상태의 결제 대상액 합계이다. 성공 수금액에 더하지 않는다. 가정: 응답 필드명을 financialImpactProcessingPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. PROCESSING 상태의 결제 대상액 합계이다. 성공 수금액에 더하지 않는다. 가정: 응답 필드명을 financialImpactProcessingPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactProcessingPaymentCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. UNKNOWN 상태의 결제 대상액 합계이다. 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactUnknownPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. UNKNOWN 상태의 결제 대상액 합계이다. 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactUnknownPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactUnknownPaymentAmount,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. UNKNOWN 상태의 결제 대상액 합계이다. 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 응답 필드명을 financialImpactUnknownPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. UNKNOWN 상태의 결제 대상액 합계이다. 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 응답 필드명을 financialImpactUnknownPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactUnknownPaymentCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactProcessingRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactProcessingRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactProcessingRefundAmount,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 응답 필드명을 financialImpactProcessingRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 응답 필드명을 financialImpactProcessingRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactProcessingRefundCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. UNKNOWN 상태의 환불 대상액 합계이다. 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactUnknownRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. UNKNOWN 상태의 환불 대상액 합계이다. 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 financialImpactUnknownRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactUnknownRefundAmount,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. UNKNOWN 상태의 환불 대상액 합계이다. 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 응답 필드명을 financialImpactUnknownRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. UNKNOWN 상태의 환불 대상액 합계이다. 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 응답 필드명을 financialImpactUnknownRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactUnknownRefundCurrency,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 결제 또는 환불에 PROCESSING/UNKNOWN이 있으면 true이다. false여도 미수 비용·미완료 환불이 있으면 금전 처리는 끝나지 않았다. 가정: 응답 필드명을 financialImpactAmountsProvisional, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. financialImpactPayments와 financialImpactRefunds의 상태를 함께 확인하고 기존 작업의 결과 확인 전에 중복 금전 작업을 시작하지 않는다. */
    @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 결제 또는 환불에 PROCESSING/UNKNOWN이 있으면 true이다. false여도 미수 비용·미완료 환불이 있으면 금전 처리는 끝나지 않았다. 가정: 응답 필드명을 financialImpactAmountsProvisional, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. financialImpactPayments와 financialImpactRefunds의 상태를 함께 확인하고 기존 작업의 결과 확인 전에 중복 금전 작업을 시작하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull 
    Boolean financialImpactAmountsProvisional,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 관련 숙박대금·취소 비용 청구를 생성 순서로 제공한다. 없으면 빈 배열이며 조회가 새 청구를 만들지 않는다. 가정: 응답 필드명을 financialImpactPayments, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 관련 숙박대금·취소 비용 청구를 생성 순서로 제공한다. 없으면 빈 배열이며 조회가 새 청구를 만들지 않는다. 가정: 응답 필드명을 financialImpactPayments, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = FinancialImpactPaymentsItem.class))
    @NotNull 
    @Valid
    List<@NotNull FinancialImpactPaymentsItem> financialImpactPayments,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 원결제별 환불 작업을 생성 순서로 제공한다. 없으면 빈 배열이며 조회가 새 환불을 만들지 않는다. 가정: 응답 필드명을 financialImpactRefunds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 원결제별 환불 작업을 생성 순서로 제공한다. 없으면 빈 배열이며 조회가 새 환불을 만들지 않는다. 가정: 응답 필드명을 financialImpactRefunds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = FinancialImpactRefundsItem.class))
    @NotNull 
    @Valid
    List<@NotNull FinancialImpactRefundsItem> financialImpactRefunds,
    /** 확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 필요한 시스템 후속 작업을 중복 없이 제공한다. 빈 배열이며 미수·환불 잔액도 0인 경우에만 금전 처리가 완료된 상태다. 가정: 응답 필드명을 financialImpactRemainingWork, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. WAIT_PAYMENT_RESULT는 기존 결제 결과 확인, WAIT_REFUND_RESULT는 기존 환불 결과 확인, ISSUE_OR_RECOVER_REFUND는 안전한 환불 실행 또는 실패 복구, COLLECT_CANCELLATION_FEE는 확인된 미수 취소 비용 수금이다. */
    @ArraySchema(arraySchema = @Schema(description = "확인된 수금·환불과 처리 중·결과 불명 작업, 남은 환불 및 미수 비용. 확정 예약에서는 비용 0의 취소를 실행할 경우의 예상이며 조회로 작업을 만들지 않는다. 취소 예약에서는 보존된 최초 비용을 적용한 현재 결과이다. 진행 중·결과 불명이 있으면 최종 금전 확정액이 아니다. 필요한 시스템 후속 작업을 중복 없이 제공한다. 빈 배열이며 미수·환불 잔액도 0인 경우에만 금전 처리가 완료된 상태다. 가정: 응답 필드명을 financialImpactRemainingWork, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. WAIT_PAYMENT_RESULT는 기존 결제 결과 확인, WAIT_REFUND_RESULT는 기존 환불 결과 확인, ISSUE_OR_RECOVER_REFUND는 안전한 환불 실행 또는 실패 복구, COLLECT_CANCELLATION_FEE는 확인된 미수 취소 비용 수금이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = CancellationRemainingWork.class, description = "가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다.", allowableValues = {"WAIT_PAYMENT_RESULT", "WAIT_REFUND_RESULT", "ISSUE_OR_RECOVER_REFUND", "COLLECT_CANCELLATION_FEE"}))
    @NotNull 
    @Valid
    List<@NotNull CancellationRemainingWork> financialImpactRemainingWork,
    /** 최초 전체 취소 사유. reservationStatus=CONFIRMED이면 최초 취소가 없어 originalCancellationReason, originalCancellationReasonDetail, originalCancellationDecidedAt, originalCancellationCustomerFeeAmount, originalCancellationCustomerFeeCurrency가 모두 null이다. CANCELLED이면 originalCancellationReason은 반드시 존재하며 최초 취소를 표시한다. 가정: 응답 필드명 originalCancellationReason, 필수 nullable 문자열 코드로 CUSTOMER_REQUEST, SCHEDULED_PAYMENT_FAILED, PROPERTY_UNAVAILABLE만 허용한다. 최초 취소의 존재와 사유를 함께 식별하기 위한 선택이다. 뒤에 제공 불가를 신고해도 최초 사유와 비용을 바꾸지 않는다. */
    @Schema(description = "최초 전체 취소 사유. reservationStatus=CONFIRMED이면 최초 취소가 없어 originalCancellationReason, originalCancellationReasonDetail, originalCancellationDecidedAt, originalCancellationCustomerFeeAmount, originalCancellationCustomerFeeCurrency가 모두 null이다. CANCELLED이면 originalCancellationReason은 반드시 존재하며 최초 취소를 표시한다. 가정: 응답 필드명 originalCancellationReason, 필수 nullable 문자열 코드로 CUSTOMER_REQUEST, SCHEDULED_PAYMENT_FAILED, PROPERTY_UNAVAILABLE만 허용한다. 최초 취소의 존재와 사유를 함께 식별하기 위한 선택이다. 뒤에 제공 불가를 신고해도 최초 사유와 비용을 바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"CUSTOMER_REQUEST", "SCHEDULED_PAYMENT_FAILED", "PROPERTY_UNAVAILABLE"}, example = "CUSTOMER_REQUEST")
    CancellationReason originalCancellationReason,
    /** 최초 취소 때 남긴 자유문장 사유. 가정: 응답 필드명 originalCancellationReasonDetail, 키가 항상 존재하는 nullable String으로 정하고 값이 있으면 1~500자를 보존한다. originalCancellationReason이 null이면 이 값도 null이다. 최초 취소가 있어도 별도 자유문장을 기록하지 않았다면 null일 수 있어 사유 코드와 구분한다. 최초 제공 불가 취소에서는 요청 reason을 보존하며 이후 신고한 새 사유로 덮어쓰지 않는다. */
    @Schema(description = "최초 취소 때 남긴 자유문장 사유. 가정: 응답 필드명 originalCancellationReasonDetail, 키가 항상 존재하는 nullable String으로 정하고 값이 있으면 1~500자를 보존한다. originalCancellationReason이 null이면 이 값도 null이다. 최초 취소가 있어도 별도 자유문장을 기록하지 않았다면 null일 수 있어 사유 코드와 구분한다. 최초 제공 불가 취소에서는 요청 reason을 보존하며 이후 신고한 새 사유로 덮어쓰지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
    @Size(min = 1, max = 500)
    String originalCancellationReasonDetail,
    /** 최초 취소 비용을 판단한 서버 시각. 가정: 응답 필드명 originalCancellationDecidedAt, 키가 항상 존재하는 nullable OffsetDateTime으로 정하고 값이 있으면 오프셋을 포함한 RFC3339 시각을 반환한다. originalCancellationReason이 null인 확정 예약에서는 null이고, 최초 취소가 있는 CANCELLED 예약에서는 반드시 값이 있다. 최초 판정 시각을 재요청·재조회 기준 시각과 구분하기 위해 정했으며 반복 요청으로 갱신하지 않는다. */
    @Schema(description = "최초 취소 비용을 판단한 서버 시각. 가정: 응답 필드명 originalCancellationDecidedAt, 키가 항상 존재하는 nullable OffsetDateTime으로 정하고 값이 있으면 오프셋을 포함한 RFC3339 시각을 반환한다. originalCancellationReason이 null인 확정 예약에서는 null이고, 최초 취소가 있는 CANCELLED 예약에서는 반드시 값이 있다. 최초 판정 시각을 재요청·재조회 기준 시각과 구분하기 위해 정했으며 반복 요청으로 갱신하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-18T10:00:00+09:00")
    OffsetDateTime originalCancellationDecidedAt,
    /** 최초 취소에서 확정한 고객 부담 비용으로 성공 환불액과 구분한다. 가정: 응답 필드명 originalCancellationCustomerFeeAmount, 키가 항상 존재하는 nullable Long으로 정하며 값이 있으면 0 이상의 KRW 정수 원이다. originalCancellationReason이 null인 확정 예약에서는 null이고, 최초 취소가 있는 CANCELLED 예약에서는 반드시 금액이 있다. 넓은 범위의 금액과 취소 부재를 함께 보존하기 위한 선택이다. 처음부터 숙소 제공 불가로 취소했으면 0원이고, 이전 유료 고객 취소가 있으면 나중 신고로 비용을 0원으로 바꾸지 않는다. */
    @Schema(description = "최초 취소에서 확정한 고객 부담 비용으로 성공 환불액과 구분한다. 가정: 응답 필드명 originalCancellationCustomerFeeAmount, 키가 항상 존재하는 nullable Long으로 정하며 값이 있으면 0 이상의 KRW 정수 원이다. originalCancellationReason이 null인 확정 예약에서는 null이고, 최초 취소가 있는 CANCELLED 예약에서는 반드시 금액이 있다. 넓은 범위의 금액과 취소 부재를 함께 보존하기 위한 선택이다. 처음부터 숙소 제공 불가로 취소했으면 0원이고, 이전 유료 고객 취소가 있으면 나중 신고로 비용을 0원으로 바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long originalCancellationCustomerFeeAmount,
    /** 최초 고객 취소 비용의 통화. 가정: 응답 필드명 originalCancellationCustomerFeeCurrency, 키가 항상 존재하는 nullable String으로 정하며 값이 있으면 KRW만 허용한다. originalCancellationReason이 null이면 이 값도 null이고 최초 취소가 있는 CANCELLED 예약에서는 반드시 KRW이다. originalCancellationCustomerFeeAmount의 단위를 보존하고 취소 부재와 금액 0원을 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
    @Schema(description = "최초 고객 취소 비용의 통화. 가정: 응답 필드명 originalCancellationCustomerFeeCurrency, 키가 항상 존재하는 nullable String으로 정하며 값이 있으면 KRW만 허용한다. originalCancellationReason이 null이면 이 값도 null이고 최초 취소가 있는 CANCELLED 예약에서는 반드시 KRW이다. originalCancellationCustomerFeeAmount의 단위를 보존하고 취소 부재와 금액 0원을 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String originalCancellationCustomerFeeCurrency,
    /** 예약과 금전 상태를 함께 관측한 기준 시각. 가정: 필드명 observedAt, 필수 OffsetDateTime으로 RFC3339 오프셋을 포함하여 반환한다. 최초 취소 판정 시각과 현재 조회 기준을 분리하기 위함이다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
    @Schema(description = "예약과 금전 상태를 함께 관측한 기준 시각. 가정: 필드명 observedAt, 필수 OffsetDateTime으로 RFC3339 오프셋을 포함하여 반환한다. 최초 취소 판정 시각과 현재 조회 기준을 분리하기 위함이다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-12-16T11:59:00+09:00")
    @NotNull 
    OffsetDateTime observedAt
) {
    /** 객실별 인원 배분. 인원별 추가 요금은 발생하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "UnavailabilityContextResponseBookedRoomOccupanciesItem", description = "객실별 인원 배분. 인원별 추가 요금은 발생하지 않는다.")
    public record BookedRoomOccupanciesItem(
        /** 동일 유형으로 예약한 객실의 인원 배분 순서. 가정: 1부터 응답 루트의 bookedRoomCount까지 중복 없이 부여하며 물리적 호실 번호가 아니다. 가정: 응답 필드명을 roomIndex, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "동일 유형으로 예약한 객실의 인원 배분 순서. 가정: 1부터 응답 루트의 bookedRoomCount까지 중복 없이 부여하며 물리적 호실 번호가 아니다. 가정: 응답 필드명을 roomIndex, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
        @NotNull
        @Min(1)
        Integer roomIndex,
        /** 해당 객실의 체크인 시점 18세 이상 성인 수. 0 이상의 정수이며 성인 최소 1명 조건은 없다. 성인과 아동의 합은 1 이상이어야 한다. 가정: 응답 필드명을 adults, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "해당 객실의 체크인 시점 18세 이상 성인 수. 0 이상의 정수이며 성인 최소 1명 조건은 없다. 성인과 아동의 합은 1 이상이어야 한다. 가정: 응답 필드명을 adults, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "0", example = "2")
        @NotNull
        @Min(0)
        Integer adults,
        /** 해당 객실의 체크인 시점 0~17세 아동 수. 0 이상의 정수이며 무료 투숙객도 총정원 검사에 포함한다. 가정: 응답 필드명을 children, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "해당 객실의 체크인 시점 0~17세 아동 수. 0 이상의 정수이며 무료 투숙객도 총정원 검사에 포함한다. 가정: 응답 필드명을 children, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "0", example = "1")
        @NotNull
        @Min(0)
        Integer children
    ) {
    }
    /** 한 숙박일에 약속한 단가·수량·합계. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "UnavailabilityContextResponseBookedPriceDailyRatesItem", description = "한 숙박일에 약속한 단가·수량·합계.")
    public record BookedPriceDailyRatesItem(
        /** 가격이 귀속되는 숙박일. Asia/Seoul 기준이다. 가정: 응답 필드명을 stayDate, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "가격이 귀속되는 숙박일. Asia/Seoul 기준이다. 가정: 응답 필드명을 stayDate, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
        @NotNull
        LocalDate stayDate,
        /** 객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 unitPriceAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 unitPriceAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long unitPriceAmount,
        /** 객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 응답 필드명을 unitPriceCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
        @Schema(description = "객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 응답 필드명을 unitPriceCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String unitPriceCurrency,
        /** 이 날짜에 예약한 객실 수. 응답 루트의 bookedRoomCount와 같다. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "이 날짜에 예약한 객실 수. 응답 루트의 bookedRoomCount와 같다. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
        @NotNull
        @Min(1)
        Integer roomCount,
        /** 해당 숙박일 전체 예약 객실의 금액. 같은 배열 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 subtotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "해당 숙박일 전체 예약 객실의 금액. 같은 배열 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 subtotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long subtotalAmount,
        /** 해당 숙박일 전체 예약 객실의 금액. 같은 배열 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 응답 필드명을 subtotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
        @Schema(description = "해당 숙박일 전체 예약 객실의 금액. 같은 배열 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 응답 필드명을 subtotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String subtotalCurrency
    ) {
    }
    /** 예약에 포함된 개별 혜택. 실제 추가 선택이나 유료 추가 구매를 나타내지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "UnavailabilityContextResponseBookedBenefitsItem", description = "예약에 포함된 개별 혜택. 실제 추가 선택이나 유료 추가 구매를 나타내지 않는다.")
    public record BookedBenefitsItem(
        /** 포함 혜택 이름. 가정: 1~200자로 정해 명확한 표시 이름을 제공한다. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "포함 혜택 이름. 가정: 1~200자로 정해 명확한 표시 이름을 제공한다. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "예약 상품 포함 서비스")
        @NotNull
        @Size(min = 1, max = 200)
        String name,
        /** 제공 조건을 포함한 혜택 설명. 가정: 1~2000자로 정해 구체적인 제공 조건을 보존한다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "제공 조건을 포함한 혜택 설명. 가정: 1~2000자로 정해 구체적인 제공 조건을 보존한다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 2000, example = "숙박 기간 동안 상품에 명시된 서비스를 제공한다.")
        @NotNull
        @Size(min = 1, max = 2000)
        String description,
        /** 혜택의 제공 수량. 가정: 수량을 정할 수 있는 혜택은 1 이상의 정수이며 정량화하지 않는 혜택은 null이다. 가정: null은 정량화하지 않는 혜택으로 정했다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "혜택의 제공 수량. 가정: 수량을 정할 수 있는 혜택은 1 이상의 정수이며 정량화하지 않는 혜택은 null이다. 가정: null은 정량화하지 않는 혜택으로 정했다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "1")
        @Min(1)
        Integer quantity,
        /** 제공 수량의 단위. 가정: 1~40자이며 quantity가 null일 때만 null이다. 가정: null은 정량화하지 않는 혜택으로 정했다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "제공 수량의 단위. 가정: 1~40자이며 quantity가 null일 때만 null이다. 가정: null은 정량화하지 않는 혜택으로 정했다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 40)
        @Size(min = 1, max = 40)
        String unit,
        /** 예약 당시 혜택의 제공 대상·시간·이용 조건을 설명과 별도로 보존한다. 가정: 필수 문자열로 정하고 추가 조건이 없으면 빈 문자열을 사용하며 null은 허용하지 않는다. 상품 선택 때 제시한 조건을 확정 후에도 잃지 않기 위함이다. 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "예약 당시 혜택의 제공 대상·시간·이용 조건을 설명과 별도로 보존한다. 가정: 필수 문자열로 정하고 추가 조건이 없으면 빈 문자열을 사용하며 null은 허용하지 않는다. 상품 선택 때 제시한 조건을 확정 후에도 잃지 않기 위함이다. 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "예약 객실 투숙객에게 숙박 기간 동안 제공")
        @NotNull
        String conditions
    ) {
    }
    /** 예약 취소·선점 만료·해제에 따른 후속 금전 판단에 포함되는 결제 시도. 숙박대금과 취소 비용 청구를 구분하고 기존 시도 결과만 기록한다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "UnavailabilityContextResponseFinancialImpactPaymentsItem", description = "예약 취소·선점 만료·해제에 따른 후속 금전 판단에 포함되는 결제 시도. 숙박대금과 취소 비용 청구를 구분하고 기존 시도 결과만 기록한다.")
    public record FinancialImpactPaymentsItem(
        /** 내부 청구 식별자는 저장소 형식을 노출하지 않는 1~64자로 정했다. 가정: 응답 필드명을 chargeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "내부 청구 식별자는 저장소 형식을 노출하지 않는 1~64자로 정했다. 가정: 응답 필드명을 chargeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "chg_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String chargeId,
        /** 가정: 필드명 attemptId, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 청구가 아직 결제를 시작하지 않았음을 뜻한다. 청구의 기존 논리 결제 시도를 식별한다. 수금 시작 전에는 null이다. 가정: 응답 필드명을 attemptId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "가정: 필드명 attemptId, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 청구가 아직 결제를 시작하지 않았음을 뜻한다. 청구의 기존 논리 결제 시도를 식별한다. 수금 시작 전에는 null이다. 가정: 응답 필드명을 attemptId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64)
        @Size(min = 1, max = 64)
        String attemptId,
        /** 숙박대금과 취소 비용을 같은 의무로 합산하지 않기 위한 수금 목적이다. 가정: 응답 필드명을 purpose, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. STAY는 숙박대금, CANCELLATION_FEE는 취소 후 남은 고객 비용이며 서로 다른 의무로 구분한다. */
        @Schema(description = "숙박대금과 취소 비용을 같은 의무로 합산하지 않기 위한 수금 목적이다. 가정: 응답 필드명을 purpose, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. STAY는 숙박대금, CANCELLATION_FEE는 취소 후 남은 고객 비용이며 서로 다른 의무로 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"STAY", "CANCELLATION_FEE"})
        @NotNull 
        CancellationPaymentPurpose purpose,
        /** SCHEDULED는 아직 시작 전, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 결과 확인 대기, CANCELLED는 실행 전 청구 중단이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. UNKNOWN이면 기존 attemptId의 결과를 확인하고 시간 초과만으로 실패나 새 시도로 바꾸지 않는다. */
        @Schema(description = "SCHEDULED는 아직 시작 전, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 결과 확인 대기, CANCELLED는 실행 전 청구 중단이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. UNKNOWN이면 기존 attemptId의 결과를 확인하고 시간 초과만으로 실패나 새 시도로 바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"})
        @NotNull 
        PaymentStatus status,
        /** 이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
        @Schema(description = "이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 서버에 이 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "서버에 이 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-12-16T12:00:00+09:00")
        @NotNull 
        OffsetDateTime updatedAt,
        /** 가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명을 실패로 채우지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명을 실패로 채우지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
    /** 예약 취소·선점 만료·해제에 따라 원결제에 연결된 환불 작업. 외부 환불 성공은 은행 입금 시각을 보장하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "UnavailabilityContextResponseFinancialImpactRefundsItem", description = "예약 취소·선점 만료·해제에 따라 원결제에 연결된 환불 작업. 외부 환불 성공은 은행 입금 시각을 보장하지 않는다.")
    public record FinancialImpactRefundsItem(
        /** 환불 작업의 1~64자 opaque 식별자로 중복 작업을 구분한다. 가정: 응답 필드명을 refundId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "환불 작업의 1~64자 opaque 식별자로 중복 작업을 구분한다. 가정: 응답 필드명을 refundId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "rfd_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String refundId,
        /** 성공한 원결제 시도의 1~64자 식별자이며 환불의 귀속을 보존한다. 가정: 응답 필드명을 originalPaymentId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "성공한 원결제 시도의 1~64자 식별자이며 환불의 귀속을 보존한다. 가정: 응답 필드명을 originalPaymentId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "pay_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String originalPaymentId,
        /** PENDING은 실행 대기, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 기존 작업 확인 대기이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. UNKNOWN이면 기존 refundId의 결과를 확인하며 실패·불명에서도 예약 취소와 점유 해제를 유지한다. SUCCEEDED는 은행 입금 시각까지 보장하지 않는다. */
        @Schema(description = "PENDING은 실행 대기, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 기존 작업 확인 대기이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. UNKNOWN이면 기존 refundId의 결과를 확인하며 실패·불명에서도 예약 취소와 점유 해제를 유지한다. SUCCEEDED는 은행 입금 시각까지 보장하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"PENDING", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN"})
        @NotNull 
        RefundStatus status,
        /** 환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다. */
        @Schema(description = "환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. 이 필드는 설명한 금액의 통화이며 금액 숫자와 별도로 KRW를 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 서버에 환불 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "서버에 환불 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-12-16T12:00:00+09:00")
        @NotNull 
        OffsetDateTime updatedAt,
        /** 가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 환불 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명에는 실패 사유를 만들지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다. */
        @Schema(description = "가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 환불 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명에는 실패 사유를 만들지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체·단위·상태를 명시하고 누락과 부재를 구분하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
    /** 가정: CancellationPaymentPurpose 문자열 코드를 선택하여 같은 결제라도 수금 목적을 구분한다. STAY: 숙박대금 청구; CANCELLATION_FEE: 취소 후 남은 고객 취소 비용의 수금. */
    @Schema(name = "CancellationPaymentPurpose", description = "가정: CancellationPaymentPurpose 문자열 코드를 선택하여 같은 결제라도 수금 목적을 구분한다. STAY: 숙박대금 청구; CANCELLATION_FEE: 취소 후 남은 고객 취소 비용의 수금.")
    public enum CancellationPaymentPurpose {
/** 숙박대금 청구. */
    STAY,
    /** 취소 후 남은 고객 취소 비용의 수금. */
    CANCELLATION_FEE
    }
    /** 가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다. */
    @Schema(name = "CancellationRemainingWork", description = "가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다.")
    public enum CancellationRemainingWork {
/** 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다. */
    WAIT_PAYMENT_RESULT,
    /** 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다. */
    WAIT_REFUND_RESULT,
    /** 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다. */
    ISSUE_OR_RECOVER_REFUND,
    /** 확인된 미수 취소 비용의 수금이 남아 있다. */
    COLLECT_CANCELLATION_FEE
    }
    /** 무료 기한이 있는 정책과 환불 불가 정책을 구분한다. 가정: API 코드로 FREE_CANCELLATION, NON_REFUNDABLE을 선택했다. */
    @Schema(name = "CustomerReservationCancellationKind", description = "무료 기한이 있는 정책과 환불 불가 정책을 구분한다. 가정: API 코드로 FREE_CANCELLATION, NON_REFUNDABLE을 선택했다.")
    public enum CustomerReservationCancellationKind {
FREE_CANCELLATION, NON_REFUNDABLE
    }
    /** 지금 결제와 나중 결제를 구분한다. 가정: API 코드로 IMMEDIATE, LATER를 선택했다. */
    @Schema(name = "CustomerReservationPaymentTiming", description = "지금 결제와 나중 결제를 구분한다. 가정: API 코드로 IMMEDIATE, LATER를 선택했다.")
    public enum CustomerReservationPaymentTiming {
IMMEDIATE, LATER
    }
}

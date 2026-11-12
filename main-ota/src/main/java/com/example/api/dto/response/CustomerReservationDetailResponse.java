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

/** 자기 예약의 보존된 약속과 현재 상태를 구분하여 반환한다. 조회는 예약·결제·환불 상태를 변경하거나 새 작업을 만들지 않는다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CustomerReservationDetailResponse", description = "자기 예약의 보존된 약속과 현재 상태를 구분하여 반환한다. 조회는 예약·결제·환불 상태를 변경하거나 새 작업을 만들지 않는다.")
public record CustomerReservationDetailResponse(
    /** 고객 자신의 예약 또는 진행 중 선점 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "고객 자신의 예약 또는 진행 중 선점 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "res_1")
    @NotNull
    @Size(min = 1, max = 64)
    String reservationId,
    /** 숙소에 제시할 별도 참조 번호. 예약 식별자와 합치지 않는다. 가정: 별도 코드가 발급되지 않은 예약에서는 null이다. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 referenceCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소에 제시할 별도 참조 번호. 예약 식별자와 합치지 않는다. 가정: 별도 코드가 발급되지 않은 예약에서는 null이다. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 referenceCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64, example = "ref_1001")
    @Size(min = 1, max = 64)
    String referenceCode,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 선택한 단일 판매 상품 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedProductId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 선택한 단일 판매 상품 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedProductId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "prd_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedProductId,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 고객이 동의한 객실·요금제·서비스 조합의 상품 설명을 보존한다. 현재 상품 설명으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 설명을 손실 없이 조회하기 위함이다. 가정: 응답 필드명을 bookedProductDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 고객이 동의한 객실·요금제·서비스 조합의 상품 설명을 보존한다. 현재 상품 설명으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 설명을 손실 없이 조회하기 위함이다. 가정: 응답 필드명을 bookedProductDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 5000, example = "숙박 상품")
    @NotNull
    @Size(min = 1, max = 5000)
    @Pattern(regexp = "[\\s\\S]*(?:\\S)[\\s\\S]*")
    String bookedProductDescription,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 숙소 식별자와 이름. 예약 숙소 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedPropertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 숙소 식별자와 이름. 예약 숙소 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedPropertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "prop_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedPropertyId,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 숙소 식별자와 이름. 예약 당시 숙소 이름. 가정: 표시 이름 길이는 1~200자로 정해 빈 이름을 방지하고 화면 표시를 제한한다. 가정: 응답 필드명을 bookedPropertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 숙소 식별자와 이름. 예약 당시 숙소 이름. 가정: 표시 이름 길이는 1~200자로 정해 빈 이름을 방지하고 화면 표시를 제한한다. 가정: 응답 필드명을 bookedPropertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "서울 스테이")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedPropertyName,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 객실 유형 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 객실 유형 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "room_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedRoomTypeId,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 객실 유형 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRoomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 객실 유형 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRoomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "스탠더드")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedRoomTypeName,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 제공하기로 한 객실의 설명을 보존한다. 현재 객실 설명 변경으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 객실 설명과 확정한 약속을 일치시키기 위함이다. 가정: 응답 필드명을 bookedRoomTypeDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 제공하기로 한 객실의 설명을 보존한다. 현재 객실 설명 변경으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 객실 설명과 확정한 약속을 일치시키기 위함이다. 가정: 응답 필드명을 bookedRoomTypeDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 5000, example = "객실 유형 설명")
    @NotNull
    @Size(min = 1, max = 5000)
    @Pattern(regexp = "[\\s\\S]*(?:\\S)[\\s\\S]*")
    String bookedRoomTypeDescription,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 객실 유형과 객실 한 개의 총정원. 객실 한 개의 성인·아동 합계 허용 인원. 무료 투숙객도 포함하며 1 이상이다. 가정: 응답 필드명을 bookedRoomTypeTotalCapacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 객실 유형과 객실 한 개의 총정원. 객실 한 개의 성인·아동 합계 허용 인원. 무료 투숙객도 포함하며 1 이상이다. 가정: 응답 필드명을 bookedRoomTypeTotalCapacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "3")
    @NotNull
    @Min(1)
    Integer bookedRoomTypeTotalCapacity,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 요금제 식별자와 이름. 예약 요금제 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRatePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 요금제 식별자와 이름. 예약 요금제 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRatePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "rate_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedRatePlanId,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 요금제 식별자와 이름. 예약 당시 요금제 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRatePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 요금제 식별자와 이름. 예약 당시 요금제 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRatePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "유연 요금")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedRatePlanName,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 포함되는 체크인 날짜. Asia/Seoul 기준이다. 가정: 응답 필드명을 bookedCheckIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 포함되는 체크인 날짜. Asia/Seoul 기준이다. 가정: 응답 필드명을 bookedCheckIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
    @NotNull
    LocalDate bookedCheckIn,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 포함되지 않는 체크아웃 날짜. bookedCheckIn보다 뒤여야 한다. 가정: 응답 필드명을 bookedCheckOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 포함되지 않는 체크아웃 날짜. bookedCheckIn보다 뒤여야 한다. 가정: 응답 필드명을 bookedCheckOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-22")
    @NotNull
    LocalDate bookedCheckOut,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약한 동일 객실 유형의 수량. 가정: 정수로 표현하며 1 이상이다. 가정: 응답 필드명을 bookedRoomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약한 동일 객실 유형의 수량. 가정: 정수로 표현하며 1 이상이다. 가정: 응답 필드명을 bookedRoomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
    @NotNull
    @Min(1)
    Integer bookedRoomCount,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 이름 부분. 성과 분리하여 숙소 예약 확인에 사용한다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestFirstName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 이름 부분. 성과 분리하여 숙소 예약 확인에 사용한다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestFirstName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "Minjun")
    @NotNull
    @Size(min = 1, max = 100)
    String bookedRepresentativeGuestFirstName,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 성 부분. 이름과 합치지 않는다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestLastName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 성 부분. 이름과 합치지 않는다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestLastName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "Kim")
    @NotNull
    @Size(min = 1, max = 100)
    String bookedRepresentativeGuestLastName,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 객실별 성인·아동 인원. 배열 길이는 bookedRoomCount이고 각 객실의 합은 예약 당시 총정원 이하다. 가정: 응답 필드명을 bookedRoomOccupancies, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 객실별 성인·아동 인원. 배열 길이는 bookedRoomCount이고 각 객실의 합은 예약 당시 총정원 이하다. 가정: 응답 필드명을 bookedRoomOccupancies, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedRoomOccupanciesItem.class), minItems = 1)
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull BookedRoomOccupanciesItem> bookedRoomOccupancies,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates 항목의 subtotalAmount 합이다. 금액은 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPriceTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates 항목의 subtotalAmount 합이다. 금액은 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPriceTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedPriceTotalAmount,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates 항목의 subtotalAmount 합이다. 금액은 1원 이상이다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedPriceTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates 항목의 subtotalAmount 합이다. 금액은 1원 이상이다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedPriceTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedPriceTotalCurrency,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 체크인 포함·체크아웃 제외 각 숙박일의 가격. 날짜 오름차순이며 누락·중복이 없다. 가정: 응답 필드명을 bookedPriceDailyRates, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 체크인 포함·체크아웃 제외 각 숙박일의 가격. 날짜 오름차순이며 누락·중복이 없다. 가정: 응답 필드명을 bookedPriceDailyRates, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedPriceDailyRatesItem.class), minItems = 1)
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull BookedPriceDailyRatesItem> bookedPriceDailyRates,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 포함 혜택 각각의 이름·설명·수량·단위. 혜택이 없으면 빈 배열이다. 가정: 응답 필드명을 bookedBenefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 포함 혜택 각각의 이름·설명·수량·단위. 혜택이 없으면 빈 배열이다. 가정: 응답 필드명을 bookedBenefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedBenefitsItem.class))
    @NotNull
    @Valid
    List<@NotNull BookedBenefitsItem> bookedBenefits,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 가정: 취소 정책을 FREE_CANCELLATION과 NON_REFUNDABLE로 구분한다. 무료 기한이 있는 정책과 환불 불가 정책의 비용 계산을 분명히 하기 위함이다. 가정: 응답 필드명을 bookedCancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 가정: 취소 정책을 FREE_CANCELLATION과 NON_REFUNDABLE로 구분한다. 무료 기한이 있는 정책과 환불 불가 정책의 비용 계산을 분명히 하기 위함이다. 가정: 응답 필드명을 bookedCancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"FREE_CANCELLATION", "NON_REFUNDABLE"}, example = "FREE_CANCELLATION")
    @NotNull
    CustomerReservationCancellationKind bookedCancellationPolicyKind,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한을 정하는 체크인 전 일수 N. 가정: 0 이상이며 NON_REFUNDABLE이면 null이다. 예정 결제 일수와 별도 설정이다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한을 정하는 체크인 전 일수 N. 가정: 0 이상이며 NON_REFUNDABLE이면 null이다. 예정 결제 일수와 별도 설정이다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", example = "3")
    @Min(0)
    Integer bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 체크인 N일 전 Asia/Seoul 00:00. 이 시각 미만은 비용 0이며 같거나 이후에는 총액 전액이다. 가정: RFC3339 오프셋을 포함하며 NON_REFUNDABLE이면 null이다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDeadline, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 체크인 N일 전 Asia/Seoul 00:00. 이 시각 미만은 비용 0이며 같거나 이후에는 총액 전액이다. 가정: RFC3339 오프셋을 포함하며 NON_REFUNDABLE이면 null이다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDeadline, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-17T00:00:00+09:00")
    OffsetDateTime bookedCancellationPolicyFreeCancellationDeadline,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedCancellationPolicyLateCancellationFeeAmount,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedCancellationPolicyLateCancellationFeeCurrency,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 가정: 지금 결제는 IMMEDIATE, 나중 결제는 LATER로 구분한다. 고객이 임의의 청구 시각을 입력하는 값이 아니다. 가정: 응답 필드명을 bookedPaymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 가정: 지금 결제는 IMMEDIATE, 나중 결제는 LATER로 구분한다. 고객이 임의의 청구 시각을 입력하는 값이 아니다. 가정: 응답 필드명을 bookedPaymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"IMMEDIATE", "LATER"}, example = "LATER")
    @NotNull
    CustomerReservationPaymentTiming bookedPaymentTermsTiming,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 예정 결제 일수 N. 무료 취소 일수와 독립된 값이다. 가정: LATER이면 0 이상이며 IMMEDIATE이면 null이다. 가정: 응답 필드명을 bookedPaymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 예정 결제 일수 N. 무료 취소 일수와 독립된 값이다. 가정: LATER이면 0 이상이며 IMMEDIATE이면 null이다. 가정: 응답 필드명을 bookedPaymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", example = "5")
    @Min(0)
    Integer bookedPaymentTermsScheduledDaysBeforeCheckIn,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 체크인 N일 전 Asia/Seoul 00:00의 예정 결제 시각. 가정: LATER이면 RFC3339 시각, IMMEDIATE이면 null이다. 가정: 응답 필드명을 bookedPaymentTermsScheduledAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 체크인 N일 전 Asia/Seoul 00:00의 예정 결제 시각. 가정: LATER이면 RFC3339 시각, IMMEDIATE이면 null이다. 가정: 응답 필드명을 bookedPaymentTermsScheduledAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-15T00:00:00+09:00")
    OffsetDateTime bookedPaymentTermsScheduledAt,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPaymentTermsAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPaymentTermsAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedPaymentTermsAmount,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedPaymentTermsCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedPaymentTermsCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedPaymentTermsCurrency,
    /** 당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 고객이 선택한 수단의 마스킹된 표시. 카드 원문 번호·인증 정보는 반환하지 않는다. 가정: 표시 정보가 있으면 1~100자 문자열이고, 선택한 수단의 제공자가 마스킹 표시 정보를 제공하지 않은 경우에만 null로 정했다. 표시 정보의 부재를 결제수단 미선택이나 결제 실패로 오인하지 않게 하기 위함이다. null이어도 예약 생성에는 결제수단 식별자와 소유권 검증이 필수이며, 즉시 결제의 확정에는 결제 성공이, 나중 결제의 확정에는 미래 청구와 유효한 결제수단의 준비가 필요하다. 가정: 응답 필드명을 bookedPaymentTermsMethodLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "당시 동의한 상품·기간·수량·인원·가격·혜택·취소·결제 약속. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 고객이 선택한 수단의 마스킹된 표시. 카드 원문 번호·인증 정보는 반환하지 않는다. 가정: 표시 정보가 있으면 1~100자 문자열이고, 선택한 수단의 제공자가 마스킹 표시 정보를 제공하지 않은 경우에만 null로 정했다. 표시 정보의 부재를 결제수단 미선택이나 결제 실패로 오인하지 않게 하기 위함이다. null이어도 예약 생성에는 결제수단 식별자와 소유권 검증이 필수이며, 즉시 결제의 확정에는 결제 성공이, 나중 결제의 확정에는 미래 청구와 유효한 결제수단의 준비가 필요하다. 가정: 응답 필드명을 bookedPaymentTermsMethodLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 100, example = "카드 ****1234")
    @Size(min = 1, max = 100)
    String bookedPaymentTermsMethodLabel,
    /** 현재 조회 기준 예약 상태. 저장 상태가 HELD인 미확정 선점에만 만료 판정을 적용한다. observedAt < holdExpiresAt이면 HELD이고, 같거나 지났으면 만료 정리 작업 여부와 무관하게 EXPIRED로 제공한다. CONFIRMED와 CANCELLED 등 후속 상태는 최초 선점 만료 시각만으로 바꾸지 않는다. CONFIRMED는 예약의 유효 확정을 의미하며 결제 완료를 뜻하지 않는다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "현재 조회 기준 예약 상태. 저장 상태가 HELD인 미확정 선점에만 만료 판정을 적용한다. observedAt < holdExpiresAt이면 HELD이고, 같거나 지났으면 만료 정리 작업 여부와 무관하게 EXPIRED로 제공한다. CONFIRMED와 CANCELLED 등 후속 상태는 최초 선점 만료 시각만으로 바꾸지 않는다. CONFIRMED는 예약의 유효 확정을 의미하며 결제 완료를 뜻하지 않는다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"HELD", "CONFIRMED", "EXPIRED", "RELEASED", "CANCELLED"}, example = "CONFIRMED")
    @NotNull
    ReservationStatus status,
    /** 현재 상태가 된 확인된 사유. 가정: 1~500자 설명이며 별도 사유가 없으면 null이다. 확인되지 않은 PG 실패를 사유로 만들지 않는다. 가정: 응답 필드명을 statusReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "현재 상태가 된 확인된 사유. 가정: 1~500자 설명이며 별도 사유가 없으면 null이다. 확인되지 않은 PG 실패를 사유로 만들지 않는다. 가정: 응답 필드명을 statusReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
    @Size(min = 1, max = 500)
    String statusReason,
    /** 선점 요청을 최초 수락한 시각. 가정: RFC3339 오프셋을 포함하고 목록 정렬 기준으로 사용한다. 가정: 응답 필드명을 createdAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "선점 요청을 최초 수락한 시각. 가정: RFC3339 오프셋을 포함하고 목록 정렬 기준으로 사용한다. 가정: 응답 필드명을 createdAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull
    OffsetDateTime createdAt,
    /** 최초 선점 만료 시각. 기본 10분이며 만료 시각과 같으면 유효하지 않다. 가정: 확정·취소 이후에도 최초 시각을 보존한다. 가정: 응답 필드명을 holdExpiresAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "최초 선점 만료 시각. 기본 10분이며 만료 시각과 같으면 유효하지 않다. 가정: 확정·취소 이후에도 최초 시각을 보존한다. 가정: 응답 필드명을 holdExpiresAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:10:00+09:00")
    @NotNull
    OffsetDateTime holdExpiresAt,
    /** 예약을 확정한 시각. 가정: 한번 확정된 예약은 취소 후에도 보존하며 미확정이면 null이다. 가정: 응답 필드명을 confirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약을 확정한 시각. 가정: 한번 확정된 예약은 취소 후에도 보존하며 미확정이면 null이다. 가정: 응답 필드명을 confirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T15:01:00+09:00")
    OffsetDateTime confirmedAt,
    /** 최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 최초 전체 취소 사유. 반복 요청으로 변경하지 않는다. 가정: 응답 필드명을 cancellationReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 해당 정보가 존재할 때 이 값은 필수이며 null일 수 없다. */
    @Schema(description = "최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 최초 전체 취소 사유. 반복 요청으로 변경하지 않는다. 가정: 응답 필드명을 cancellationReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 해당 정보가 존재할 때 이 값은 필수이며 null일 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"CUSTOMER_REQUEST", "SCHEDULED_PAYMENT_FAILED", "PROPERTY_UNAVAILABLE"}, example = "CUSTOMER_REQUEST")
    CancellationReason cancellationReason,
    /** 최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 최초 취소 때 남긴 구체 사유. 가정: 사유 입력이 없으면 null, 있으면 1~500자로 보존한다. 가정: 응답 필드명을 cancellationReasonDetail, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. */
    @Schema(description = "최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 최초 취소 때 남긴 구체 사유. 가정: 사유 입력이 없으면 null, 있으면 1~500자로 보존한다. 가정: 응답 필드명을 cancellationReasonDetail, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 정보가 존재하면 해당 값의 필수성·허용값·범위 제약을 적용한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
    @Size(min = 1, max = 500)
    String cancellationReasonDetail,
    /** 최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 최초 취소 비용을 판단한 시각. 반복 조회·반복 취소로 갱신하지 않는다. 가정: 응답 필드명을 cancellationDecidedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 해당 정보가 존재할 때 이 값은 필수이며 null일 수 없다. */
    @Schema(description = "최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 최초 취소 비용을 판단한 시각. 반복 조회·반복 취소로 갱신하지 않는다. 가정: 응답 필드명을 cancellationDecidedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 해당 정보가 존재할 때 이 값은 필수이며 null일 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-18T10:00:00+09:00")
    OffsetDateTime cancellationDecidedAt,
    /** 최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationCustomerFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 해당 정보가 존재할 때 이 값은 필수이며 null일 수 없다. */
    @Schema(description = "최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationCustomerFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 해당 정보가 존재할 때 이 값은 필수이며 null일 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationCustomerFeeAmount,
    /** 최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationCustomerFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 해당 정보가 존재할 때 이 값은 필수이며 null일 수 없다. */
    @Schema(description = "최초 전체 취소 결과. 가정: 최초 취소 정보는 status=CANCELLED일 때 존재하며 cancellationReason으로 존재 여부를 구분한다. cancellationReason이 null이면 취소 사유·상세 사유·판정 시각·비용·통화는 모두 null이다. 취소 정보가 존재하면 사유·판정 시각·비용·통화는 null이 아니며 cancellationReasonDetail만 자유문장 미기록 시 null이다. 확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationCustomerFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 최초 취소 정보가 존재하지 않으면 이 필드는 null이다. 해당 정보가 존재할 때 이 값은 필수이며 null일 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationCustomerFeeCurrency,
    /** 확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 최근 논리적 결제 시도 또는 예정 의무의 상태. UNKNOWN은 실패가 아니다. 가정: 결제 의무·시도가 아직 만들어지지 않았다면 null이다. 가정: 응답 필드명을 paymentSummaryLatestStatus, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 최근 논리적 결제 시도 또는 예정 의무의 상태. UNKNOWN은 실패가 아니다. 가정: 결제 의무·시도가 아직 만들어지지 않았다면 null이다. 가정: 응답 필드명을 paymentSummaryLatestStatus, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"}, example = "SCHEDULED")
    PaymentStatus paymentSummaryLatestStatus,
    /** 확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 확인된 성공 수금액 합계. 처리 중·결과 불명 금액을 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentSummarySucceededAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 확인된 성공 수금액 합계. 처리 중·결과 불명 금액을 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentSummarySucceededAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long paymentSummarySucceededAmount,
    /** 확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 확인된 성공 수금액 합계. 처리 중·결과 불명 금액을 합산하지 않는다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 paymentSummarySucceededCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 확인된 성공 수금액 합계. 처리 중·결과 불명 금액을 합산하지 않는다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 paymentSummarySucceededCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String paymentSummarySucceededCurrency,
    /** 확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 아직 이행하지 않은 예정 숙박대금. 취소 후에는 신규 예정 수금 의무가 중단되어 0이며 미수 취소 비용은 포함하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentSummaryOutstandingScheduledAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 아직 이행하지 않은 예정 숙박대금. 취소 후에는 신규 예정 수금 의무가 중단되어 0이며 미수 취소 비용은 포함하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentSummaryOutstandingScheduledAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long paymentSummaryOutstandingScheduledAmount,
    /** 확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 아직 이행하지 않은 예정 숙박대금. 취소 후에는 신규 예정 수금 의무가 중단되어 0이며 미수 취소 비용은 포함하지 않는다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 paymentSummaryOutstandingScheduledCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. 아직 이행하지 않은 예정 숙박대금. 취소 후에는 신규 예정 수금 의무가 중단되어 0이며 미수 취소 비용은 포함하지 않는다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 paymentSummaryOutstandingScheduledCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String paymentSummaryOutstandingScheduledCurrency,
    /** 확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. PROCESSING 또는 UNKNOWN 결제 시도가 남았는지 나타낸다. 가정: 여러 시도가 있을 수 있으므로 paymentSummaryLatestStatus와 별도 필수 boolean으로 제공한다. 가정: 응답 필드명을 paymentSummaryHasUnresolvedAttempt, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 수금과 예정 의무 및 현재 결제 처리 상태의 요약. PROCESSING 또는 UNKNOWN 결제 시도가 남았는지 나타낸다. 가정: 여러 시도가 있을 수 있으므로 paymentSummaryLatestStatus와 별도 필수 boolean으로 제공한다. 가정: 응답 필드명을 paymentSummaryHasUnresolvedAttempt, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull
    Boolean paymentSummaryHasUnresolvedAttempt,
    /** 확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 최근 논리적 환불 작업의 상태. 가정: 환불 작업이 없으면 null이며 성공으로 대체하지 않는다. 가정: 응답 필드명을 refundSummaryLatestStatus, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 최근 논리적 환불 작업의 상태. 가정: 환불 작업이 없으면 null이며 성공으로 대체하지 않는다. 가정: 응답 필드명을 refundSummaryLatestStatus, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"PENDING", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN"})
    RefundStatus refundSummaryLatestStatus,
    /** 확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 확인된 성공 환불액 합계. 금융기관 계좌 반영 완료를 보장하는 값은 아니다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundSummarySucceededAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 확인된 성공 환불액 합계. 금융기관 계좌 반영 완료를 보장하는 값은 아니다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundSummarySucceededAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long refundSummarySucceededAmount,
    /** 확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 확인된 성공 환불액 합계. 금융기관 계좌 반영 완료를 보장하는 값은 아니다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 refundSummarySucceededCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 확인된 성공 환불액 합계. 금융기관 계좌 반영 완료를 보장하는 값은 아니다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 refundSummarySucceededCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String refundSummarySucceededCurrency,
    /** 확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 현재 확인된 사실에 따라 아직 성공 환불되지 않은 대상 금액. UNKNOWN 환불은 완료액에 포함하지 않으며 새 중복 환불을 허용하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundSummaryRemainingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 현재 확인된 사실에 따라 아직 성공 환불되지 않은 대상 금액. UNKNOWN 환불은 완료액에 포함하지 않으며 새 중복 환불을 허용하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundSummaryRemainingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long refundSummaryRemainingAmount,
    /** 확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 현재 확인된 사실에 따라 아직 성공 환불되지 않은 대상 금액. UNKNOWN 환불은 완료액에 포함하지 않으며 새 중복 환불을 허용하지 않는다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 refundSummaryRemainingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. 현재 확인된 사실에 따라 아직 성공 환불되지 않은 대상 금액. UNKNOWN 환불은 완료액에 포함하지 않으며 새 중복 환불을 허용하지 않는다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 refundSummaryRemainingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String refundSummaryRemainingCurrency,
    /** 확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. PENDING, PROCESSING 또는 UNKNOWN 환불 작업이 남았는지 나타낸다. 가정: 최근 상태만으로 다른 미완료 작업을 숨기지 않도록 필수 boolean으로 제공한다. 가정: 응답 필드명을 refundSummaryHasUnresolvedAttempt, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "확인된 환불과 남은 환불 및 현재 환불 처리 상태의 요약. PENDING, PROCESSING 또는 UNKNOWN 환불 작업이 남았는지 나타낸다. 가정: 최근 상태만으로 다른 미완료 작업을 숨기지 않도록 필수 boolean으로 제공한다. 가정: 응답 필드명을 refundSummaryHasUnresolvedAttempt, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull
    Boolean refundSummaryHasUnresolvedAttempt,
    /** 가정: 후속 행동을 필수 코드 배열로 제공한다. 화면 문구를 검증 조건으로 차용하지 않고 현재 가능한 탐색·실행을 전달하기 위해 선택했으며, 각 조건을 독립 평가하여 중복 없이 반환하고 가능한 행동이 없으면 빈 배열이다. VIEW_SCHEDULED_PAYMENT는 나중 결제 예약의 남은 예정 의무 조회, VIEW_FINANCIAL_RESULT는 취소 예약 또는 만료·해제 후 결제 결과 확인·환불 등 금전 작업이 남은 예약의 환불·잔액 조회, CANCEL_RESERVATION은 확정 예약의 전체 취소 확인, CONTACT_PROPERTY는 확정 예약의 기존 숙소 연락 경로 조회를 뜻한다. START_NEW_RESERVATION은 예약이 EXPIRED 또는 RELEASED이고 paymentSummaryHasUnresolvedAttempt=false이며 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이 아닐 때만 새 조건 조회·재동의 후 별도 예약 시작을 안내한다. RETRY_IMMEDIATE_PAYMENT는 예약이 HELD이고 observedAt < holdExpiresAt이며 즉시 결제의 실패가 확정됐고 기존 미확인 결제가 남아 있지 않을 때만 새 결제 시도를 안내한다. paymentSummaryHasUnresolvedAttempt=true이거나 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이면 WAIT_FOR_RESULT로 기존 결과 확인을 안내하고, RETRY_IMMEDIATE_PAYMENT와 START_NEW_RESERVATION은 제외한다. 이때 적용 가능한 VIEW_FINANCIAL_RESULT는 유지한다. 결과가 확인된 뒤 새 예약 안내가 가능해져도 기존 예약의 환불 대상은 별도로 남을 수 있어 START_NEW_RESERVATION과 VIEW_FINANCIAL_RESULT를 함께 제공할 수 있다. 가정: 응답 필드명을 nextActions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 후속 행동을 필수 코드 배열로 제공한다. 화면 문구를 검증 조건으로 차용하지 않고 현재 가능한 탐색·실행을 전달하기 위해 선택했으며, 각 조건을 독립 평가하여 중복 없이 반환하고 가능한 행동이 없으면 빈 배열이다. VIEW_SCHEDULED_PAYMENT는 나중 결제 예약의 남은 예정 의무 조회, VIEW_FINANCIAL_RESULT는 취소 예약 또는 만료·해제 후 결제 결과 확인·환불 등 금전 작업이 남은 예약의 환불·잔액 조회, CANCEL_RESERVATION은 확정 예약의 전체 취소 확인, CONTACT_PROPERTY는 확정 예약의 기존 숙소 연락 경로 조회를 뜻한다. START_NEW_RESERVATION은 예약이 EXPIRED 또는 RELEASED이고 paymentSummaryHasUnresolvedAttempt=false이며 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이 아닐 때만 새 조건 조회·재동의 후 별도 예약 시작을 안내한다. RETRY_IMMEDIATE_PAYMENT는 예약이 HELD이고 observedAt < holdExpiresAt이며 즉시 결제의 실패가 확정됐고 기존 미확인 결제가 남아 있지 않을 때만 새 결제 시도를 안내한다. paymentSummaryHasUnresolvedAttempt=true이거나 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이면 WAIT_FOR_RESULT로 기존 결과 확인을 안내하고, RETRY_IMMEDIATE_PAYMENT와 START_NEW_RESERVATION은 제외한다. 이때 적용 가능한 VIEW_FINANCIAL_RESULT는 유지한다. 결과가 확인된 뒤 새 예약 안내가 가능해져도 기존 예약의 환불 대상은 별도로 남을 수 있어 START_NEW_RESERVATION과 VIEW_FINANCIAL_RESULT를 함께 제공할 수 있다. 가정: 응답 필드명을 nextActions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = CustomerReservationNextAction.class, description = "가정: 후속 행동을 필수 코드 배열로 제공한다. 화면 문구를 검증 조건으로 차용하지 않고 현재 가능한 탐색·실행을 전달하기 위해 선택했으며, 각 조건을 독립 평가하여 중복 없이 반환하고 가능한 행동이 없으면 빈 배열이다. VIEW_SCHEDULED_PAYMENT는 나중 결제 예약의 남은 예정 의무 조회, VIEW_FINANCIAL_RESULT는 취소 예약 또는 만료·해제 후 결제 결과 확인·환불 등 금전 작업이 남은 예약의 환불·잔액 조회, CANCEL_RESERVATION은 확정 예약의 전체 취소 확인, CONTACT_PROPERTY는 확정 예약의 기존 숙소 연락 경로 조회를 뜻한다. START_NEW_RESERVATION은 예약이 EXPIRED 또는 RELEASED이고 paymentSummaryHasUnresolvedAttempt=false이며 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이 아닐 때만 새 조건 조회·재동의 후 별도 예약 시작을 안내한다. RETRY_IMMEDIATE_PAYMENT는 예약이 HELD이고 observedAt < holdExpiresAt이며 즉시 결제의 실패가 확정됐고 기존 미확인 결제가 남아 있지 않을 때만 새 결제 시도를 안내한다. paymentSummaryHasUnresolvedAttempt=true이거나 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이면 WAIT_FOR_RESULT로 기존 결과 확인을 안내하고, RETRY_IMMEDIATE_PAYMENT와 START_NEW_RESERVATION은 제외한다. 이때 적용 가능한 VIEW_FINANCIAL_RESULT는 유지한다. 결과가 확인된 뒤 새 예약 안내가 가능해져도 기존 예약의 환불 대상은 별도로 남을 수 있어 START_NEW_RESERVATION과 VIEW_FINANCIAL_RESULT를 함께 제공할 수 있다.", allowableValues = {"VIEW_SCHEDULED_PAYMENT", "VIEW_FINANCIAL_RESULT", "CANCEL_RESERVATION", "CONTACT_PROPERTY", "START_NEW_RESERVATION", "RETRY_IMMEDIATE_PAYMENT", "WAIT_FOR_RESULT"}))
    @NotNull
    @Valid
    List<@NotNull CustomerReservationNextAction> nextActions,
    /** 예약 상태와 금전 요약을 읽은 동일 기준 시각. 가정: RFC3339 오프셋을 포함한다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 상태와 금전 요약을 읽은 동일 기준 시각. 가정: RFC3339 오프셋을 포함한다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:02:00+09:00")
    @NotNull
    OffsetDateTime observedAt
) {
    /** 객실별 인원 배분. 인원별 추가 요금은 발생하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CustomerReservationDetailResponseBookedRoomOccupanciesItem", description = "객실별 인원 배분. 인원별 추가 요금은 발생하지 않는다.")
    public record BookedRoomOccupanciesItem(
        /** 동일 유형으로 예약한 객실의 인원 배분 순서. 가정: 1부터 bookedRoomCount까지 중복 없이 부여하며 물리적 호실 번호가 아니다. 가정: 응답 필드명을 roomIndex, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "동일 유형으로 예약한 객실의 인원 배분 순서. 가정: 1부터 bookedRoomCount까지 중복 없이 부여하며 물리적 호실 번호가 아니다. 가정: 응답 필드명을 roomIndex, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
        @NotNull
        @Min(1)
        Integer roomIndex,
        /** 해당 객실의 체크인 시점 18세 이상 성인 수. 0 이상의 정수이며 성인 최소 1명 조건은 없다. 성인과 아동의 합은 1 이상이어야 한다. 가정: 응답 필드명을 adults, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 객실의 체크인 시점 18세 이상 성인 수. 0 이상의 정수이며 성인 최소 1명 조건은 없다. 성인과 아동의 합은 1 이상이어야 한다. 가정: 응답 필드명을 adults, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "0", example = "2")
        @NotNull
        @Min(0)
        Integer adults,
        /** 해당 객실의 체크인 시점 0~17세 아동 수. 0 이상의 정수이며 무료 투숙객도 총정원 검사에 포함한다. 가정: 응답 필드명을 children, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 객실의 체크인 시점 0~17세 아동 수. 0 이상의 정수이며 무료 투숙객도 총정원 검사에 포함한다. 가정: 응답 필드명을 children, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "0", example = "1")
        @NotNull
        @Min(0)
        Integer children
    ) {
    }
    /** 한 숙박일에 약속한 단가·수량·합계. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CustomerReservationDetailResponseBookedPriceDailyRatesItem", description = "한 숙박일에 약속한 단가·수량·합계.")
    public record BookedPriceDailyRatesItem(
        /** 가격이 귀속되는 숙박일. Asia/Seoul 기준이다. 가정: 응답 필드명을 stayDate, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가격이 귀속되는 숙박일. Asia/Seoul 기준이다. 가정: 응답 필드명을 stayDate, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
        @NotNull
        LocalDate stayDate,
        /** 객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 unitPriceAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 unitPriceAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long unitPriceAmount,
        /** 객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 unitPriceCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 unitPriceCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String unitPriceCurrency,
        /** 이 날짜에 예약한 객실 수. 예약 전체 bookedRoomCount와 같다. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 날짜에 예약한 객실 수. 예약 전체 bookedRoomCount와 같다. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
        @NotNull
        @Min(1)
        Integer roomCount,
        /** 해당 숙박일 전체 예약 객실의 금액. 같은 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 subtotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 숙박일 전체 예약 객실의 금액. 같은 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 subtotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long subtotalAmount,
        /** 해당 숙박일 전체 예약 객실의 금액. 같은 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 subtotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 숙박일 전체 예약 객실의 금액. 같은 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 subtotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String subtotalCurrency
    ) {
    }
    /** 예약에 포함된 개별 혜택. 실제 추가 선택이나 유료 추가 구매를 나타내지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CustomerReservationDetailResponseBookedBenefitsItem", description = "예약에 포함된 개별 혜택. 실제 추가 선택이나 유료 추가 구매를 나타내지 않는다.")
    public record BookedBenefitsItem(
        /** 포함 혜택 이름. 가정: 1~200자로 정해 명확한 표시 이름을 제공한다. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "포함 혜택 이름. 가정: 1~200자로 정해 명확한 표시 이름을 제공한다. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "예약 상품 포함 서비스")
        @NotNull
        @Size(min = 1, max = 200)
        String name,
        /** 제공 조건을 포함한 혜택 설명. 가정: 1~2000자로 정해 구체적인 제공 조건을 보존한다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "제공 조건을 포함한 혜택 설명. 가정: 1~2000자로 정해 구체적인 제공 조건을 보존한다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 2000, example = "숙박 기간 동안 상품에 명시된 서비스를 제공한다.")
        @NotNull
        @Size(min = 1, max = 2000)
        String description,
        /** 혜택의 제공 수량. 가정: 수량을 정할 수 있는 혜택은 1 이상의 정수이며 정량화하지 않는 혜택은 null이다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택의 제공 수량. 가정: 수량을 정할 수 있는 혜택은 1 이상의 정수이며 정량화하지 않는 혜택은 null이다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "1")
        @Min(1)
        Integer quantity,
        /** 제공 수량의 단위. 가정: 1~40자이며 quantity가 null일 때만 null이다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "제공 수량의 단위. 가정: 1~40자이며 quantity가 null일 때만 null이다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 40)
        @Size(min = 1, max = 40)
        String unit,
        /** 예약 당시 혜택의 제공 대상·시간·이용 조건을 설명과 별도로 보존한다. 가정: 필수 문자열로 정하고 추가 조건이 없으면 빈 문자열을 사용하며 null은 허용하지 않는다. 상품 선택 때 제시한 조건을 확정 후에도 잃지 않기 위함이다. 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 혜택의 제공 대상·시간·이용 조건을 설명과 별도로 보존한다. 가정: 필수 문자열로 정하고 추가 조건이 없으면 빈 문자열을 사용하며 null은 허용하지 않는다. 상품 선택 때 제시한 조건을 확정 후에도 잃지 않기 위함이다. 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "예약 객실 투숙객에게 숙박 기간 동안 제공")
        @NotNull
        String conditions
    ) {
    }
    /** 무료 기한이 있는 정책과 환불 불가 정책을 구분한다. 가정: API 코드로 FREE_CANCELLATION, NON_REFUNDABLE을 선택했다. */
    @Schema(name = "CustomerReservationCancellationKind", description = "무료 기한이 있는 정책과 환불 불가 정책을 구분한다. 가정: API 코드로 FREE_CANCELLATION, NON_REFUNDABLE을 선택했다.")
    public enum CustomerReservationCancellationKind {
FREE_CANCELLATION, NON_REFUNDABLE
    }
    /** 가정: 후속 행동을 필수 코드 배열로 제공한다. 화면 문구를 검증 조건으로 차용하지 않고 현재 가능한 탐색·실행을 전달하기 위해 선택했으며, 각 조건을 독립 평가하여 중복 없이 반환하고 가능한 행동이 없으면 빈 배열이다. VIEW_SCHEDULED_PAYMENT는 나중 결제 예약의 남은 예정 의무 조회, VIEW_FINANCIAL_RESULT는 취소 예약 또는 만료·해제 후 결제 결과 확인·환불 등 금전 작업이 남은 예약의 환불·잔액 조회, CANCEL_RESERVATION은 확정 예약의 전체 취소 확인, CONTACT_PROPERTY는 확정 예약의 기존 숙소 연락 경로 조회를 뜻한다. START_NEW_RESERVATION은 예약이 EXPIRED 또는 RELEASED이고 paymentSummaryHasUnresolvedAttempt=false이며 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이 아닐 때만 새 조건 조회·재동의 후 별도 예약 시작을 안내한다. RETRY_IMMEDIATE_PAYMENT는 예약이 HELD이고 observedAt < holdExpiresAt이며 즉시 결제의 실패가 확정됐고 기존 미확인 결제가 남아 있지 않을 때만 새 결제 시도를 안내한다. paymentSummaryHasUnresolvedAttempt=true이거나 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이면 WAIT_FOR_RESULT로 기존 결과 확인을 안내하고, RETRY_IMMEDIATE_PAYMENT와 START_NEW_RESERVATION은 제외한다. 이때 적용 가능한 VIEW_FINANCIAL_RESULT는 유지한다. 결과가 확인된 뒤 새 예약 안내가 가능해져도 기존 예약의 환불 대상은 별도로 남을 수 있어 START_NEW_RESERVATION과 VIEW_FINANCIAL_RESULT를 함께 제공할 수 있다. */
    @Schema(name = "CustomerReservationNextAction", description = "가정: 후속 행동을 필수 코드 배열로 제공한다. 화면 문구를 검증 조건으로 차용하지 않고 현재 가능한 탐색·실행을 전달하기 위해 선택했으며, 각 조건을 독립 평가하여 중복 없이 반환하고 가능한 행동이 없으면 빈 배열이다. VIEW_SCHEDULED_PAYMENT는 나중 결제 예약의 남은 예정 의무 조회, VIEW_FINANCIAL_RESULT는 취소 예약 또는 만료·해제 후 결제 결과 확인·환불 등 금전 작업이 남은 예약의 환불·잔액 조회, CANCEL_RESERVATION은 확정 예약의 전체 취소 확인, CONTACT_PROPERTY는 확정 예약의 기존 숙소 연락 경로 조회를 뜻한다. START_NEW_RESERVATION은 예약이 EXPIRED 또는 RELEASED이고 paymentSummaryHasUnresolvedAttempt=false이며 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이 아닐 때만 새 조건 조회·재동의 후 별도 예약 시작을 안내한다. RETRY_IMMEDIATE_PAYMENT는 예약이 HELD이고 observedAt < holdExpiresAt이며 즉시 결제의 실패가 확정됐고 기존 미확인 결제가 남아 있지 않을 때만 새 결제 시도를 안내한다. paymentSummaryHasUnresolvedAttempt=true이거나 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이면 WAIT_FOR_RESULT로 기존 결과 확인을 안내하고, RETRY_IMMEDIATE_PAYMENT와 START_NEW_RESERVATION은 제외한다. 이때 적용 가능한 VIEW_FINANCIAL_RESULT는 유지한다. 결과가 확인된 뒤 새 예약 안내가 가능해져도 기존 예약의 환불 대상은 별도로 남을 수 있어 START_NEW_RESERVATION과 VIEW_FINANCIAL_RESULT를 함께 제공할 수 있다.")
    public enum CustomerReservationNextAction {
VIEW_SCHEDULED_PAYMENT, VIEW_FINANCIAL_RESULT, CANCEL_RESERVATION, CONTACT_PROPERTY, START_NEW_RESERVATION, RETRY_IMMEDIATE_PAYMENT, WAIT_FOR_RESULT
    }
    /** 지금 결제와 나중 결제를 구분한다. 가정: API 코드로 IMMEDIATE, LATER를 선택했다. */
    @Schema(name = "CustomerReservationPaymentTiming", description = "지금 결제와 나중 결제를 구분한다. 가정: API 코드로 IMMEDIATE, LATER를 선택했다.")
    public enum CustomerReservationPaymentTiming {
IMMEDIATE, LATER
    }
}

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

/** 소유 숙소의 예약 당시 약속과 현재 예약·취소·금전 상태. booked 접두어는 예약 당시 약속, currentRoomInformation 접두어는 현재 객실 정보이며 두 시점의 값을 섞지 않는다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "PartnerReservationDetailResponse", description = "소유 숙소의 예약 당시 약속과 현재 예약·취소·금전 상태. booked 접두어는 예약 당시 약속, currentRoomInformation 접두어는 현재 객실 정보이며 두 시점의 값을 섞지 않는다.")
public record PartnerReservationDetailResponse(
    /** 예약이 속하고 파트너가 소유한 숙소 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약이 속하고 파트너가 소유한 숙소 식별자. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "prop_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*")
    String propertyId,
    /** 예약 식별자. 존재하지 않는 예약과 다른 숙소 예약은 모두 조회 불가로 처리한다. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 식별자. 존재하지 않는 예약과 다른 숙소 예약은 모두 조회 불가로 처리한다. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 64, example = "res_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*")
    String reservationId,
    /** 예약 번호와 별개인 숙소 참조 코드. 가정: 없으면 null, 있으면 1~128자로 보존한다. 가정: 응답 필드명을 referenceCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 번호와 별개인 숙소 참조 코드. 가정: 없으면 null, 있으면 1~128자로 보존한다. 가정: 응답 필드명을 referenceCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 128, example = "ref_1001")
    @Size(min = 1, max = 128)
    String referenceCode,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 선택한 단일 판매 상품 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedProductId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 선택한 단일 판매 상품 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedProductId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "prd_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedProductId,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 고객이 동의한 객실·요금제·서비스 조합의 상품 설명을 보존한다. 현재 상품 설명으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 설명을 손실 없이 조회하기 위함이다. 가정: 응답 필드명을 bookedProductDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 고객이 동의한 객실·요금제·서비스 조합의 상품 설명을 보존한다. 현재 상품 설명으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 설명을 손실 없이 조회하기 위함이다. 가정: 응답 필드명을 bookedProductDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 5000, example = "숙박 상품")
    @NotNull
    @Size(min = 1, max = 5000)
    @Pattern(regexp = "[\\s\\S]*(?:\\S)[\\s\\S]*")
    String bookedProductDescription,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 숙소 식별자와 이름. 예약 숙소 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedPropertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 숙소 식별자와 이름. 예약 숙소 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedPropertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "prop_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedPropertyId,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 숙소 식별자와 이름. 예약 당시 숙소 이름. 가정: 표시 이름 길이는 1~200자로 정해 빈 이름을 방지하고 화면 표시를 제한한다. 가정: 응답 필드명을 bookedPropertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 숙소 식별자와 이름. 예약 당시 숙소 이름. 가정: 표시 이름 길이는 1~200자로 정해 빈 이름을 방지하고 화면 표시를 제한한다. 가정: 응답 필드명을 bookedPropertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "서울 스테이")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedPropertyName,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 객실 유형 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 객실 유형 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "room_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedRoomTypeId,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 객실 유형 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRoomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 객실 유형 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRoomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "스탠더드")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedRoomTypeName,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 제공하기로 한 객실의 설명을 보존한다. 현재 객실 설명 변경으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 객실 설명과 확정한 약속을 일치시키기 위함이다. 가정: 응답 필드명을 bookedRoomTypeDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 예약 당시 제공하기로 한 객실의 설명을 보존한다. 현재 객실 설명 변경으로 덮어쓰지 않는다. 가정: 필수 문자열이며 null·빈 문자열·공백만 있는 값은 허용하지 않고 1~5000자로 정했다. 상품 선택 때 제시한 객실 설명과 확정한 약속을 일치시키기 위함이다. 가정: 응답 필드명을 bookedRoomTypeDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 5000, example = "객실 유형 설명")
    @NotNull
    @Size(min = 1, max = 5000)
    @Pattern(regexp = "[\\s\\S]*(?:\\S)[\\s\\S]*")
    String bookedRoomTypeDescription,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 객실 한 개의 성인·아동 합계 허용 인원. 무료 투숙객도 포함하며 1 이상이다. 가정: 응답 필드명을 bookedRoomTypeTotalCapacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 객실 유형과 객실 한 개의 총정원. 객실 한 개의 성인·아동 합계 허용 인원. 무료 투숙객도 포함하며 1 이상이다. 가정: 응답 필드명을 bookedRoomTypeTotalCapacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "3")
    @NotNull
    @Min(1)
    Integer bookedRoomTypeTotalCapacity,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 요금제 식별자와 이름. 예약 요금제 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRatePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 요금제 식별자와 이름. 예약 요금제 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다. 가정: 응답 필드명을 bookedRatePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "rate_1")
    @NotNull
    @Size(min = 1, max = 64)
    String bookedRatePlanId,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 요금제 식별자와 이름. 예약 당시 요금제 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRatePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 요금제 식별자와 이름. 예약 당시 요금제 이름. 가정: 표시 이름 길이는 1~200자로 정했다. 가정: 응답 필드명을 bookedRatePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "유연 요금")
    @NotNull
    @Size(min = 1, max = 200)
    String bookedRatePlanName,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 포함되는 체크인 날짜. Asia/Seoul 기준이다. 가정: 응답 필드명을 bookedCheckIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 포함되는 체크인 날짜. Asia/Seoul 기준이다. 가정: 응답 필드명을 bookedCheckIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
    @NotNull
    LocalDate bookedCheckIn,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 포함되지 않는 체크아웃 날짜. bookedCheckIn보다 뒤여야 한다. 가정: 응답 필드명을 bookedCheckOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 포함되지 않는 체크아웃 날짜. bookedCheckIn보다 뒤여야 한다. 가정: 응답 필드명을 bookedCheckOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-22")
    @NotNull
    LocalDate bookedCheckOut,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약한 동일 객실 유형의 수량. 가정: 정수로 표현하며 1 이상이다. 가정: 응답 필드명을 bookedRoomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약한 동일 객실 유형의 수량. 가정: 정수로 표현하며 1 이상이다. 가정: 응답 필드명을 bookedRoomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
    @NotNull
    @Min(1)
    Integer bookedRoomCount,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 이름 부분. 성과 분리하여 숙소 예약 확인에 사용한다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestFirstName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 이름 부분. 성과 분리하여 숙소 예약 확인에 사용한다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestFirstName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "Minjun")
    @NotNull
    @Size(min = 1, max = 100)
    String bookedRepresentativeGuestFirstName,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 성 부분. 이름과 합치지 않는다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestLastName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 숙소에 제시할 대표 투숙객 이름. 예약자와 동일인이라고 추정하지 않는다. 대표 투숙객의 성 부분. 이름과 합치지 않는다. 가정: 문자열 1~100자로 정했으며 영문 표기의 의미는 보존하되 ASCII 문자만 허용하는 검증은 추가하지 않는다. 가정: 응답 필드명을 bookedRepresentativeGuestLastName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "Kim")
    @NotNull
    @Size(min = 1, max = 100)
    String bookedRepresentativeGuestLastName,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 객실별 성인·아동 인원. 배열 길이는 bookedRoomCount이고 각 객실의 합은 예약 당시 총정원 이하다. 가정: 응답 필드명을 bookedRoomOccupancies, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 객실별 성인·아동 인원. 배열 길이는 bookedRoomCount이고 각 객실의 합은 예약 당시 총정원 이하다. 가정: 응답 필드명을 bookedRoomOccupancies, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedRoomOccupanciesItem.class), minItems = 1)
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull BookedRoomOccupanciesItem> bookedRoomOccupancies,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates[].subtotalAmount의 합이다. 금액은 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPriceTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates[].subtotalAmount의 합이다. 금액은 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPriceTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedPriceTotalAmount,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates[].subtotalAmount의 합이다. 금액은 1원 이상이다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedPriceTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 모든 숙박일·예약 객실을 포함한 양수 총액. 필수 요금을 포함하며 bookedPriceDailyRates[].subtotalAmount의 합이다. 금액은 1원 이상이다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedPriceTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedPriceTotalCurrency,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 체크인 포함·체크아웃 제외 각 숙박일의 가격. 날짜 오름차순이며 누락·중복이 없다. 가정: 응답 필드명을 bookedPriceDailyRates, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 필수 요금을 포함한 예약 당시 가격. 이후 상품 가격 변경으로 다시 계산하지 않는다. 체크인 포함·체크아웃 제외 각 숙박일의 가격. 날짜 오름차순이며 누락·중복이 없다. 가정: 응답 필드명을 bookedPriceDailyRates, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedPriceDailyRatesItem.class), minItems = 1)
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull BookedPriceDailyRatesItem> bookedPriceDailyRates,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 포함 혜택 각각의 이름·설명·수량·단위. 혜택이 없으면 빈 배열이다. 가정: 응답 필드명을 bookedBenefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 포함 혜택 각각의 이름·설명·수량·단위. 혜택이 없으면 빈 배열이다. 가정: 응답 필드명을 bookedBenefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = BookedBenefitsItem.class))
    @NotNull
    @Valid
    List<@NotNull BookedBenefitsItem> bookedBenefits,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 가정: 취소 정책을 FREE_CANCELLATION과 NON_REFUNDABLE로 구분한다. 무료 기한이 있는 정책과 환불 불가 정책의 비용 계산을 분명히 하기 위함이다. 가정: 응답 필드명을 bookedCancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 가정: 취소 정책을 FREE_CANCELLATION과 NON_REFUNDABLE로 구분한다. 무료 기한이 있는 정책과 환불 불가 정책의 비용 계산을 분명히 하기 위함이다. 가정: 응답 필드명을 bookedCancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"FREE_CANCELLATION", "NON_REFUNDABLE"}, example = "FREE_CANCELLATION")
    @NotNull
    CustomerReservationCancellationKind bookedCancellationPolicyKind,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한을 정하는 체크인 전 일수 N. 가정: 0 이상이며 NON_REFUNDABLE이면 null이다. 예정 결제 일수와 별도 설정이다. 가정: null은 무료 취소 기한이 없는 환불 불가 정책으로 정했다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한을 정하는 체크인 전 일수 N. 가정: 0 이상이며 NON_REFUNDABLE이면 null이다. 예정 결제 일수와 별도 설정이다. 가정: null은 무료 취소 기한이 없는 환불 불가 정책으로 정했다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", example = "3")
    @Min(0)
    Integer bookedCancellationPolicyFreeCancellationDaysBeforeCheckIn,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 체크인 N일 전 Asia/Seoul 00:00. 이 시각 미만은 비용 0이며 같거나 이후에는 총액 전액이다. 가정: RFC3339 오프셋을 포함하며 NON_REFUNDABLE이면 null이다. 가정: null은 무료 취소 기한이 없는 환불 불가 정책으로 정했다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDeadline, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 체크인 N일 전 Asia/Seoul 00:00. 이 시각 미만은 비용 0이며 같거나 이후에는 총액 전액이다. 가정: RFC3339 오프셋을 포함하며 NON_REFUNDABLE이면 null이다. 가정: null은 무료 취소 기한이 없는 환불 불가 정책으로 정했다. 가정: 응답 필드명을 bookedCancellationPolicyFreeCancellationDeadline, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-17T00:00:00+09:00")
    OffsetDateTime bookedCancellationPolicyFreeCancellationDeadline,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedCancellationPolicyLateCancellationFeeAmount,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 취소 정책과 실제 기한. 무료 취소 기한과 같거나 지난 경우, 또는 환불 불가 정책인 경우 고객 취소 비용. 예약 총액과 같다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedCancellationPolicyLateCancellationFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedCancellationPolicyLateCancellationFeeCurrency,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 가정: 지금 결제는 IMMEDIATE, 나중 결제는 LATER로 구분한다. 고객이 임의의 청구 시각을 입력하는 값이 아니다. 가정: 응답 필드명을 bookedPaymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 가정: 지금 결제는 IMMEDIATE, 나중 결제는 LATER로 구분한다. 고객이 임의의 청구 시각을 입력하는 값이 아니다. 가정: 응답 필드명을 bookedPaymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"IMMEDIATE", "LATER"}, example = "LATER")
    @NotNull
    CustomerReservationPaymentTiming bookedPaymentTermsTiming,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 예정 결제 일수 N. 무료 취소 일수와 독립된 값이다. 가정: LATER이면 0 이상이며 IMMEDIATE이면 null이다. 가정: null은 즉시 결제이므로 예정 일수 없음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 예정 결제 일수 N. 무료 취소 일수와 독립된 값이다. 가정: LATER이면 0 이상이며 IMMEDIATE이면 null이다. 가정: null은 즉시 결제이므로 예정 일수 없음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", example = "5")
    @Min(0)
    Integer bookedPaymentTermsScheduledDaysBeforeCheckIn,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 체크인 N일 전 Asia/Seoul 00:00의 예정 결제 시각. 가정: LATER이면 RFC3339 시각, IMMEDIATE이면 null이다. 가정: null은 즉시 결제이므로 미래 청구 시각 없음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsScheduledAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 체크인 N일 전 Asia/Seoul 00:00의 예정 결제 시각. 가정: LATER이면 RFC3339 시각, IMMEDIATE이면 null이다. 가정: null은 즉시 결제이므로 미래 청구 시각 없음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsScheduledAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-15T00:00:00+09:00")
    OffsetDateTime bookedPaymentTermsScheduledAt,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPaymentTermsAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 bookedPaymentTermsAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long bookedPaymentTermsAmount,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedPaymentTermsCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 예약 당시 숙박대금 결제 의무 총액. 취소 비용의 현재 잔액과 구분한다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 bookedPaymentTermsCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String bookedPaymentTermsCurrency,
    /** 예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 고객이 선택한 수단의 마스킹된 표시. 카드 원문 번호·인증 정보는 반환하지 않는다. 가정: 표시 정보가 있으면 1~100자 문자열이고, 선택한 수단의 제공자가 마스킹 표시 정보를 제공하지 않은 경우에만 null로 정했다. 표시 정보의 부재를 결제수단 미선택이나 결제 실패로 오인하지 않게 하기 위함이다. null이어도 예약 생성에는 결제수단 식별자와 소유권 검증이 필수이며, 즉시 결제의 확정에는 결제 성공이, 나중 결제의 확정에는 미래 청구와 유효한 결제수단의 준비가 필요하다. 가정: null은 선택한 결제수단의 제공자가 마스킹 표시 정보를 제공하지 않음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsMethodLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "예약 확정 당시 고객에게 약속한 값이며 현재 상품 변경으로 재계산하지 않는다. 예약 당시 결제 시점·금액·예정 시각 및 마스킹 표시. 고객이 선택한 수단의 마스킹된 표시. 카드 원문 번호·인증 정보는 반환하지 않는다. 가정: 표시 정보가 있으면 1~100자 문자열이고, 선택한 수단의 제공자가 마스킹 표시 정보를 제공하지 않은 경우에만 null로 정했다. 표시 정보의 부재를 결제수단 미선택이나 결제 실패로 오인하지 않게 하기 위함이다. null이어도 예약 생성에는 결제수단 식별자와 소유권 검증이 필수이며, 즉시 결제의 확정에는 결제 성공이, 나중 결제의 확정에는 미래 청구와 유효한 결제수단의 준비가 필요하다. 가정: null은 선택한 결제수단의 제공자가 마스킹 표시 정보를 제공하지 않음으로 정했다. 가정: 응답 필드명을 bookedPaymentTermsMethodLabel, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 100, example = "카드 ****1234")
    @Size(min = 1, max = 100)
    String bookedPaymentTermsMethodLabel,
    /** 객실 하나의 전체 숙박일 가격 합계. 가정: 서버가 bookedPriceDailyRates[].unitPriceAmount를 합산하여 제공하며 예약 객실 수를 곱한 전체 예약 총액과 구분한다. 필수 요금을 포함한 KRW 양수 정수 원이고 취소 후에도 약속 금액으로 보존한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 perRoomStayTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "객실 하나의 전체 숙박일 가격 합계. 가정: 서버가 bookedPriceDailyRates[].unitPriceAmount를 합산하여 제공하며 예약 객실 수를 곱한 전체 예약 총액과 구분한다. 필수 요금을 포함한 KRW 양수 정수 원이고 취소 후에도 약속 금액으로 보존한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 perRoomStayTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long perRoomStayTotalAmount,
    /** 객실 하나의 전체 숙박일 가격 합계. 가정: 서버가 bookedPriceDailyRates[].unitPriceAmount를 합산하여 제공하며 예약 객실 수를 곱한 전체 예약 총액과 구분한다. 필수 요금을 포함한 KRW 양수 정수 원이고 취소 후에도 약속 금액으로 보존한다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 perRoomStayTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "객실 하나의 전체 숙박일 가격 합계. 가정: 서버가 bookedPriceDailyRates[].unitPriceAmount를 합산하여 제공하며 예약 객실 수를 곱한 전체 예약 총액과 구분한다. 필수 요금을 포함한 KRW 양수 정수 원이고 취소 후에도 약속 금액으로 보존한다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 perRoomStayTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String perRoomStayTotalCurrency,
    /** 현재 예약 상태. 가정: 확정된 적 있는 예약 중 CONFIRMED 또는 CANCELLED만 이 조회에 제공한다. 취소는 환불 완료를 뜻하지 않는다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "현재 예약 상태. 가정: 확정된 적 있는 예약 중 CONFIRMED 또는 CANCELLED만 이 조회에 제공한다. 취소는 환불 완료를 뜻하지 않는다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"CONFIRMED", "CANCELLED"}, example = "CONFIRMED")
    @NotNull 
    ReservationStatus status,
    /** 현재 상태가 된 확인된 사유. 가정: 별도 사유가 없으면 null, 있으면 1~500자이며 외부 결제의 불명 결과를 실패 사유로 만들지 않는다. 가정: 응답 필드명을 statusReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "현재 상태가 된 확인된 사유. 가정: 별도 사유가 없으면 null, 있으면 1~500자이며 외부 결제의 불명 결과를 실패 사유로 만들지 않는다. 가정: 응답 필드명을 statusReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
    @Size(min = 1, max = 500)
    String statusReason,
    /** 최초 선점 요청을 수락한 시각. 예약 확정 시각과 별개이며 RFC3339 오프셋을 포함한다. 가정: 응답 필드명을 createdAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "최초 선점 요청을 수락한 시각. 예약 확정 시각과 별개이며 RFC3339 오프셋을 포함한다. 가정: 응답 필드명을 createdAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull 
    OffsetDateTime createdAt,
    /** 최초 예약 확정 시각. 취소 이후에도 보존하며 최초 선점 생성 시각과 구분한다. 가정: 응답 필드명을 confirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "최초 예약 확정 시각. 취소 이후에도 보존하며 최초 선점 생성 시각과 구분한다. 가정: 응답 필드명을 confirmedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:01:00+09:00")
    @NotNull 
    OffsetDateTime confirmedAt,
    /** 최초 전체 취소 사유. 반복 요청으로 변경하지 않는다. 가정: 응답 필드명을 cancellationReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다. */
    @Schema(description = "최초 전체 취소 사유. 반복 요청으로 변경하지 않는다. 가정: 응답 필드명을 cancellationReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"CUSTOMER_REQUEST", "SCHEDULED_PAYMENT_FAILED", "PROPERTY_UNAVAILABLE"}, example = "CUSTOMER_REQUEST")
    CancellationReason cancellationReason,
    /** 최초 취소 때 남긴 구체 사유. 가정: 사유 입력이 없으면 null, 있으면 1~500자로 보존한다. 가정: null은 별도 자유문장 사유가 기록되지 않음으로 정했다. 가정: 응답 필드명을 cancellationReasonDetail, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다. */
    @Schema(description = "최초 취소 때 남긴 구체 사유. 가정: 사유 입력이 없으면 null, 있으면 1~500자로 보존한다. 가정: null은 별도 자유문장 사유가 기록되지 않음으로 정했다. 가정: 응답 필드명을 cancellationReasonDetail, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
    @Size(min = 1, max = 500)
    String cancellationReasonDetail,
    /** 최초 취소 비용을 판단한 시각. 반복 조회·반복 취소로 갱신하지 않는다. 가정: 응답 필드명을 cancellationDecidedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다. */
    @Schema(description = "최초 취소 비용을 판단한 시각. 반복 조회·반복 취소로 갱신하지 않는다. 가정: 응답 필드명을 cancellationDecidedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-18T10:00:00+09:00")
    OffsetDateTime cancellationDecidedAt,
    /** 확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationCustomerFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다. */
    @Schema(description = "확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationCustomerFeeAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationCustomerFeeAmount,
    /** 확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationCustomerFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다. */
    @Schema(description = "확정된 최초 고객 취소 비용. 환불 성공액과 구분한다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationCustomerFeeCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency는 모두 null이다. CANCELLED이면 cancellationReason으로 취소 결과의 존재를 판단하고, cancellationReasonDetail은 별도 자유문장 사유가 기록되지 않았을 때만 null이며 나머지 네 필드는 null일 수 없다. 이후 다른 사유가 제출되어도 최초 결과를 유지한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationCustomerFeeCurrency,
    /** 성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 최근 논리적 결제 시도 또는 예정 의무의 상태. UNKNOWN은 실패가 아니다. 가정: 결제 의무·시도가 아직 만들어지지 않았다면 null이다. 가정: null은 결제 의무·시도가 아직 없음으로 정했다. 가정: 응답 필드명을 paymentSummaryLatestStatus, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 최근 논리적 결제 시도 또는 예정 의무의 상태. UNKNOWN은 실패가 아니다. 가정: 결제 의무·시도가 아직 만들어지지 않았다면 null이다. 가정: null은 결제 의무·시도가 아직 없음으로 정했다. 가정: 응답 필드명을 paymentSummaryLatestStatus, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"}, example = "SCHEDULED")
    PaymentStatus paymentSummaryLatestStatus,
    /** 성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 확인된 성공 수금액 합계. 처리 중·결과 불명 금액을 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentSummarySucceededAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 확인된 성공 수금액 합계. 처리 중·결과 불명 금액을 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentSummarySucceededAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long paymentSummarySucceededAmount,
    /** 성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 확인된 성공 수금액 합계. 처리 중·결과 불명 금액을 합산하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 paymentSummarySucceededCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 확인된 성공 수금액 합계. 처리 중·결과 불명 금액을 합산하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 paymentSummarySucceededCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String paymentSummarySucceededCurrency,
    /** 성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 아직 이행하지 않은 예정 숙박대금. 취소 후에는 신규 예정 수금 의무가 중단되어 0이며 미수 취소 비용은 포함하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentSummaryOutstandingScheduledAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 아직 이행하지 않은 예정 숙박대금. 취소 후에는 신규 예정 수금 의무가 중단되어 0이며 미수 취소 비용은 포함하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 paymentSummaryOutstandingScheduledAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long paymentSummaryOutstandingScheduledAmount,
    /** 성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 아직 이행하지 않은 예정 숙박대금. 취소 후에는 신규 예정 수금 의무가 중단되어 0이며 미수 취소 비용은 포함하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 paymentSummaryOutstandingScheduledCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. 아직 이행하지 않은 예정 숙박대금. 취소 후에는 신규 예정 수금 의무가 중단되어 0이며 미수 취소 비용은 포함하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 paymentSummaryOutstandingScheduledCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String paymentSummaryOutstandingScheduledCurrency,
    /** 성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. PROCESSING 또는 UNKNOWN 결제 시도가 남았는지 나타낸다. 가정: 여러 시도가 있을 수 있으므로 paymentSummaryLatestStatus와 별도 필수 boolean으로 제공한다. 가정: 응답 필드명을 paymentSummaryHasUnresolvedAttempt, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 수금액·미이행 예정 숙박대금·현재 결제 상태와 결과 확인 필요 여부. 예약 확정 여부와 별개로 제공한다. PROCESSING 또는 UNKNOWN 결제 시도가 남았는지 나타낸다. 가정: 여러 시도가 있을 수 있으므로 paymentSummaryLatestStatus와 별도 필수 boolean으로 제공한다. 가정: 응답 필드명을 paymentSummaryHasUnresolvedAttempt, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull
    Boolean paymentSummaryHasUnresolvedAttempt,
    /** 성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 최근 논리적 환불 작업의 상태. 가정: 환불 작업이 없으면 null이며 성공으로 대체하지 않는다. 가정: null은 환불 작업 없음으로 정했다. 가정: 응답 필드명을 refundSummaryLatestStatus, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 최근 논리적 환불 작업의 상태. 가정: 환불 작업이 없으면 null이며 성공으로 대체하지 않는다. 가정: null은 환불 작업 없음으로 정했다. 가정: 응답 필드명을 refundSummaryLatestStatus, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"PENDING", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN"})
    RefundStatus refundSummaryLatestStatus,
    /** 성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 확인된 성공 환불액 합계. 금융기관 계좌 반영 완료를 보장하는 값은 아니다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundSummarySucceededAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 확인된 성공 환불액 합계. 금융기관 계좌 반영 완료를 보장하는 값은 아니다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundSummarySucceededAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long refundSummarySucceededAmount,
    /** 성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 확인된 성공 환불액 합계. 금융기관 계좌 반영 완료를 보장하는 값은 아니다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 refundSummarySucceededCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 확인된 성공 환불액 합계. 금융기관 계좌 반영 완료를 보장하는 값은 아니다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 refundSummarySucceededCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String refundSummarySucceededCurrency,
    /** 성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 현재 확인된 사실에 따라 아직 성공 환불되지 않은 대상 금액. UNKNOWN 환불은 완료액에 포함하지 않으며 새 중복 환불을 허용하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundSummaryRemainingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 현재 확인된 사실에 따라 아직 성공 환불되지 않은 대상 금액. UNKNOWN 환불은 완료액에 포함하지 않으며 새 중복 환불을 허용하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 refundSummaryRemainingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long refundSummaryRemainingAmount,
    /** 성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 현재 확인된 사실에 따라 아직 성공 환불되지 않은 대상 금액. UNKNOWN 환불은 완료액에 포함하지 않으며 새 중복 환불을 허용하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 refundSummaryRemainingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. 현재 확인된 사실에 따라 아직 성공 환불되지 않은 대상 금액. UNKNOWN 환불은 완료액에 포함하지 않으며 새 중복 환불을 허용하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 refundSummaryRemainingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String refundSummaryRemainingCurrency,
    /** 성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. PENDING, PROCESSING 또는 UNKNOWN 환불 작업이 남았는지 나타낸다. 가정: 최근 상태만으로 다른 미완료 작업을 숨기지 않도록 필수 boolean으로 제공한다. 가정: 응답 필드명을 refundSummaryHasUnresolvedAttempt, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "성공 환불액·남은 환불 대상·현재 환불 상태와 미완료 작업 여부. 취소 여부와 별개로 제공한다. PENDING, PROCESSING 또는 UNKNOWN 환불 작업이 남았는지 나타낸다. 가정: 최근 상태만으로 다른 미완료 작업을 숨기지 않도록 필수 boolean으로 제공한다. 가정: 응답 필드명을 refundSummaryHasUnresolvedAttempt, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull
    Boolean refundSummaryHasUnresolvedAttempt,
    /** 성공이 확인된 서로 다른 논리 결제만 합산한 P이다. 반복 결과 통지는 다시 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactSuccessfulCollectionAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "성공이 확인된 서로 다른 논리 결제만 합산한 P이다. 반복 결과 통지는 다시 합산하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactSuccessfulCollectionAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationFinancialImpactSuccessfulCollectionAmount,
    /** 성공이 확인된 서로 다른 논리 결제만 합산한 P이다. 반복 결과 통지는 다시 합산하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactSuccessfulCollectionCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "성공이 확인된 서로 다른 논리 결제만 합산한 P이다. 반복 결과 통지는 다시 합산하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactSuccessfulCollectionCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationFinancialImpactSuccessfulCollectionCurrency,
    /** 성공이 확인된 서로 다른 환불만 합산한 R이다. 성공 수금액을 초과하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactSuccessfulRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "성공이 확인된 서로 다른 환불만 합산한 R이다. 성공 수금액을 초과하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactSuccessfulRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationFinancialImpactSuccessfulRefundAmount,
    /** 성공이 확인된 서로 다른 환불만 합산한 R이다. 성공 수금액을 초과하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactSuccessfulRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "성공이 확인된 서로 다른 환불만 합산한 R이다. 성공 수금액을 초과하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactSuccessfulRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationFinancialImpactSuccessfulRefundCurrency,
    /** 현재 확인된 환불 잔액 max(P-R-F,0)이다. P는 cancellationFinancialImpactSuccessfulCollectionAmount, R은 cancellationFinancialImpactSuccessfulRefundAmount, F는 cancellationCustomerFeeAmount이다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactRefundOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "현재 확인된 환불 잔액 max(P-R-F,0)이다. P는 cancellationFinancialImpactSuccessfulCollectionAmount, R은 cancellationFinancialImpactSuccessfulRefundAmount, F는 cancellationCustomerFeeAmount이다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactRefundOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationFinancialImpactRefundOutstandingAmount,
    /** 현재 확인된 환불 잔액 max(P-R-F,0)이다. P는 cancellationFinancialImpactSuccessfulCollectionAmount, R은 cancellationFinancialImpactSuccessfulRefundAmount, F는 cancellationCustomerFeeAmount이다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactRefundOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "현재 확인된 환불 잔액 max(P-R-F,0)이다. P는 cancellationFinancialImpactSuccessfulCollectionAmount, R은 cancellationFinancialImpactSuccessfulRefundAmount, F는 cancellationCustomerFeeAmount이다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactRefundOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationFinancialImpactRefundOutstandingCurrency,
    /** 현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. P는 cancellationFinancialImpactSuccessfulCollectionAmount, R은 cancellationFinancialImpactSuccessfulRefundAmount, F는 cancellationCustomerFeeAmount이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패와 별개다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactCustomerOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. P는 cancellationFinancialImpactSuccessfulCollectionAmount, R은 cancellationFinancialImpactSuccessfulRefundAmount, F는 cancellationCustomerFeeAmount이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패와 별개다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactCustomerOutstandingAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationFinancialImpactCustomerOutstandingAmount,
    /** 현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. P는 cancellationFinancialImpactSuccessfulCollectionAmount, R은 cancellationFinancialImpactSuccessfulRefundAmount, F는 cancellationCustomerFeeAmount이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패와 별개다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactCustomerOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "현재 확인된 미수 취소 비용 max(F-(P-R),0)이다. P는 cancellationFinancialImpactSuccessfulCollectionAmount, R은 cancellationFinancialImpactSuccessfulRefundAmount, F는 cancellationCustomerFeeAmount이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패와 별개다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactCustomerOutstandingCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationFinancialImpactCustomerOutstandingCurrency,
    /** PROCESSING 상태의 결제 대상액 합계이다. 성공 수금액에 더하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactProcessingPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "PROCESSING 상태의 결제 대상액 합계이다. 성공 수금액에 더하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactProcessingPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationFinancialImpactProcessingPaymentAmount,
    /** PROCESSING 상태의 결제 대상액 합계이다. 성공 수금액에 더하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactProcessingPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "PROCESSING 상태의 결제 대상액 합계이다. 성공 수금액에 더하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactProcessingPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationFinancialImpactProcessingPaymentCurrency,
    /** UNKNOWN 상태의 결제 대상액 합계이다. 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactUnknownPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "UNKNOWN 상태의 결제 대상액 합계이다. 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactUnknownPaymentAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationFinancialImpactUnknownPaymentAmount,
    /** UNKNOWN 상태의 결제 대상액 합계이다. 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactUnknownPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "UNKNOWN 상태의 결제 대상액 합계이다. 실패로 단정하거나 새 시도로 대체하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactUnknownPaymentCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationFinancialImpactUnknownPaymentCurrency,
    /** PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactProcessingRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactProcessingRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationFinancialImpactProcessingRefundAmount,
    /** PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactProcessingRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액과 분리하며 환불 잔액의 일부일 수 있다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactProcessingRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationFinancialImpactProcessingRefundCurrency,
    /** UNKNOWN 상태의 환불 대상액 합계이다. 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactUnknownRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "UNKNOWN 상태의 환불 대상액 합계이다. 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 cancellationFinancialImpactUnknownRefundAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationFinancialImpactUnknownRefundAmount,
    /** UNKNOWN 상태의 환불 대상액 합계이다. 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactUnknownRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "UNKNOWN 상태의 환불 대상액 합계이다. 완료로 표시하지 않으며 환불 잔액의 일부일 수 있다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 cancellationFinancialImpactUnknownRefundCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationFinancialImpactUnknownRefundCurrency,
    /** 결제 또는 환불에 PROCESSING/UNKNOWN이 있으면 true이다. false여도 미수 비용·미완료 환불이 있으면 금전 처리는 끝나지 않았다. 가정: 응답 필드명을 cancellationFinancialImpactAmountsProvisional, JSON 타입을 boolean로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @Schema(description = "결제 또는 환불에 PROCESSING/UNKNOWN이 있으면 true이다. false여도 미수 비용·미완료 환불이 있으면 금전 처리는 끝나지 않았다. 가정: 응답 필드명을 cancellationFinancialImpactAmountsProvisional, JSON 타입을 boolean로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "false")
    Boolean cancellationFinancialImpactAmountsProvisional,
    /** 관련 숙박대금·취소 비용 청구를 생성 순서로 제공한다. 없으면 빈 배열이며 조회가 새 청구를 만들지 않는다. 가정: 응답 필드명을 cancellationFinancialImpactPayments, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @ArraySchema(arraySchema = @Schema(description = "관련 숙박대금·취소 비용 청구를 생성 순서로 제공한다. 없으면 빈 배열이며 조회가 새 청구를 만들지 않는다. 가정: 응답 필드명을 cancellationFinancialImpactPayments, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true), schema = @Schema(implementation = CancellationFinancialImpactPaymentsItem.class))
    @Valid
    List<@NotNull CancellationFinancialImpactPaymentsItem> cancellationFinancialImpactPayments,
    /** 원결제별 환불 작업을 생성 순서로 제공한다. 없으면 빈 배열이며 조회가 새 환불을 만들지 않는다. 가정: 응답 필드명을 cancellationFinancialImpactRefunds, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @ArraySchema(arraySchema = @Schema(description = "원결제별 환불 작업을 생성 순서로 제공한다. 없으면 빈 배열이며 조회가 새 환불을 만들지 않는다. 가정: 응답 필드명을 cancellationFinancialImpactRefunds, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true), schema = @Schema(implementation = CancellationFinancialImpactRefundsItem.class))
    @Valid
    List<@NotNull CancellationFinancialImpactRefundsItem> cancellationFinancialImpactRefunds,
    /** 필요한 시스템 후속 작업을 중복 없이 제공한다. 빈 배열이며 미수·환불 잔액도 0인 경우에만 금전 처리가 완료된 상태다. 가정: 응답 필드명을 cancellationFinancialImpactRemainingWork, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다. */
    @ArraySchema(arraySchema = @Schema(description = "필요한 시스템 후속 작업을 중복 없이 제공한다. 빈 배열이며 미수·환불 잔액도 0인 경우에만 금전 처리가 완료된 상태다. 가정: 응답 필드명을 cancellationFinancialImpactRemainingWork, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: status가 CONFIRMED이면 cancellationFinancialImpact 접두어의 모든 값과 배열은 null이다. CANCELLED이면 cancellationFinancialImpactSuccessfulCollectionAmount로 취소 금전 결과의 존재를 판단하고 같은 접두어의 모든 값과 배열은 null일 수 없다. 관련 결제·환불·후속 작업이 없으면 각각의 배열은 []이다. 최초 고객 취소 비용을 기준으로 계산하며 결과 불명 금액을 최종 금전 결과로 단정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true), schema = @Schema(implementation = CancellationRemainingWork.class, description = "가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다.", allowableValues = {"WAIT_PAYMENT_RESULT", "WAIT_REFUND_RESULT", "ISSUE_OR_RECOVER_REFUND", "COLLECT_CANCELLATION_FEE"}))
    @Valid
    List<@NotNull CancellationRemainingWork> cancellationFinancialImpactRemainingWork,
    /** 현재 설명이 속한 객실 유형이며 예약 당시 객실 유형과 같은 식별자이다. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 currentRoomInformationRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 구성에서 객실 정보가 제거되어 없으면 currentRoomInformationRoomTypeId, currentRoomInformationName, currentRoomInformationDescription, currentRoomInformationObservedAt은 모두 null이다. currentRoomInformationRoomTypeId가 존재하면 네 필드는 모두 null일 수 없다. 일시적인 저장소 조회 장애는 정보 부재로 숨기지 않고 503으로 응답한다. 예약 당시 bookedRoomTypeDescription과 현재 설명을 구분한다. */
    @Schema(description = "현재 설명이 속한 객실 유형이며 예약 당시 객실 유형과 같은 식별자이다. 가정: 공백만인 값을 제외한 1~64자 opaque 문자열로 저장소 형식을 노출하지 않는다. 가정: 응답 필드명을 currentRoomInformationRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 구성에서 객실 정보가 제거되어 없으면 currentRoomInformationRoomTypeId, currentRoomInformationName, currentRoomInformationDescription, currentRoomInformationObservedAt은 모두 null이다. currentRoomInformationRoomTypeId가 존재하면 네 필드는 모두 null일 수 없다. 일시적인 저장소 조회 장애는 정보 부재로 숨기지 않고 503으로 응답한다. 예약 당시 bookedRoomTypeDescription과 현재 설명을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "\\S", minLength = 1, maxLength = 64, example = "room_1")
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*")
    String currentRoomInformationRoomTypeId,
    /** 현재 고객용 객실 유형 이름. 가정: 1~200자로 표시하며 현재 구성의 이름을 읽는다. 가정: 응답 필드명을 currentRoomInformationName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 구성에서 객실 정보가 제거되어 없으면 currentRoomInformationRoomTypeId, currentRoomInformationName, currentRoomInformationDescription, currentRoomInformationObservedAt은 모두 null이다. currentRoomInformationRoomTypeId가 존재하면 네 필드는 모두 null일 수 없다. 일시적인 저장소 조회 장애는 정보 부재로 숨기지 않고 503으로 응답한다. 예약 당시 bookedRoomTypeDescription과 현재 설명을 구분한다. */
    @Schema(description = "현재 고객용 객실 유형 이름. 가정: 1~200자로 표시하며 현재 구성의 이름을 읽는다. 가정: 응답 필드명을 currentRoomInformationName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 구성에서 객실 정보가 제거되어 없으면 currentRoomInformationRoomTypeId, currentRoomInformationName, currentRoomInformationDescription, currentRoomInformationObservedAt은 모두 null이다. currentRoomInformationRoomTypeId가 존재하면 네 필드는 모두 null일 수 없다. 일시적인 저장소 조회 장애는 정보 부재로 숨기지 않고 503으로 응답한다. 예약 당시 bookedRoomTypeDescription과 현재 설명을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 200, example = "스탠더드")
    @Size(min = 1, max = 200)
    String currentRoomInformationName,
    /** 현재 고객용 객실 설명. 가정: 현재 구성 입력과 맞춰 공백만인 값을 제외한 1~5000자로 표시한다. 가정: 응답 필드명을 currentRoomInformationDescription, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 구성에서 객실 정보가 제거되어 없으면 currentRoomInformationRoomTypeId, currentRoomInformationName, currentRoomInformationDescription, currentRoomInformationObservedAt은 모두 null이다. currentRoomInformationRoomTypeId가 존재하면 네 필드는 모두 null일 수 없다. 일시적인 저장소 조회 장애는 정보 부재로 숨기지 않고 503으로 응답한다. 예약 당시 bookedRoomTypeDescription과 현재 설명을 구분한다. */
    @Schema(description = "현재 고객용 객실 설명. 가정: 현재 구성 입력과 맞춰 공백만인 값을 제외한 1~5000자로 표시한다. 가정: 응답 필드명을 currentRoomInformationDescription, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 구성에서 객실 정보가 제거되어 없으면 currentRoomInformationRoomTypeId, currentRoomInformationName, currentRoomInformationDescription, currentRoomInformationObservedAt은 모두 null이다. currentRoomInformationRoomTypeId가 존재하면 네 필드는 모두 null일 수 없다. 일시적인 저장소 조회 장애는 정보 부재로 숨기지 않고 503으로 응답한다. 예약 당시 bookedRoomTypeDescription과 현재 설명을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "\\S", minLength = 1, maxLength = 5000, example = "현재 객실 시설 안내")
    @Size(min = 1, max = 5000)
    @Pattern(regexp = "[\\s\\S]*\\S[\\s\\S]*")
    String currentRoomInformationDescription,
    /** 현재 설명을 조회한 기준 시각. RFC3339 오프셋을 포함하며 바깥 응답의 observedAt과 같다. 가정: 응답 필드명을 currentRoomInformationObservedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 구성에서 객실 정보가 제거되어 없으면 currentRoomInformationRoomTypeId, currentRoomInformationName, currentRoomInformationDescription, currentRoomInformationObservedAt은 모두 null이다. currentRoomInformationRoomTypeId가 존재하면 네 필드는 모두 null일 수 없다. 일시적인 저장소 조회 장애는 정보 부재로 숨기지 않고 503으로 응답한다. 예약 당시 bookedRoomTypeDescription과 현재 설명을 구분한다. */
    @Schema(description = "현재 설명을 조회한 기준 시각. RFC3339 오프셋을 포함하며 바깥 응답의 observedAt과 같다. 가정: 응답 필드명을 currentRoomInformationObservedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 구성에서 객실 정보가 제거되어 없으면 currentRoomInformationRoomTypeId, currentRoomInformationName, currentRoomInformationDescription, currentRoomInformationObservedAt은 모두 null이다. currentRoomInformationRoomTypeId가 존재하면 네 필드는 모두 null일 수 없다. 일시적인 저장소 조회 장애는 정보 부재로 숨기지 않고 503으로 응답한다. 예약 당시 bookedRoomTypeDescription과 현재 설명을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T15:02:00+09:00")
    OffsetDateTime currentRoomInformationObservedAt,
    /** 가정: status가 CONFIRMED이면 true, CANCELLED이면 false이다. 파트너가 제공 불가 요청을 처음 실행할 수 있는 상태를 뜻하며 실제로 숙박 제공이 불가능하다는 판정이나 취소 완료를 뜻하지 않는다. 가정: 응답 필드명을 canReportUnavailability, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: status가 CONFIRMED이면 true, CANCELLED이면 false이다. 파트너가 제공 불가 요청을 처음 실행할 수 있는 상태를 뜻하며 실제로 숙박 제공이 불가능하다는 판정이나 취소 완료를 뜻하지 않는다. 가정: 응답 필드명을 canReportUnavailability, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
    @NotNull 
    Boolean canReportUnavailability,
    /** 약속·예약·취소·금전 요약·현재 객실 설명을 읽은 동일 기준 시각. RFC3339 오프셋을 포함한다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "약속·예약·취소·금전 요약·현재 객실 설명을 읽은 동일 기준 시각. RFC3339 오프셋을 포함한다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:02:00+09:00")
    @NotNull 
    OffsetDateTime observedAt
) {
    /** 객실별 인원 배분. 인원별 추가 요금은 발생하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "PartnerReservationDetailResponseBookedRoomOccupanciesItem", description = "객실별 인원 배분. 인원별 추가 요금은 발생하지 않는다.")
    public record BookedRoomOccupanciesItem(
        /** 동일 유형으로 예약한 객실의 인원 배분 순서. 가정: 1부터 응답의 bookedRoomCount까지 중복 없이 부여하며 물리적 호실 번호가 아니다. 가정: 응답 필드명을 roomIndex, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "동일 유형으로 예약한 객실의 인원 배분 순서. 가정: 1부터 응답의 bookedRoomCount까지 중복 없이 부여하며 물리적 호실 번호가 아니다. 가정: 응답 필드명을 roomIndex, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
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
    @Schema(name = "PartnerReservationDetailResponseBookedPriceDailyRatesItem", description = "한 숙박일에 약속한 단가·수량·합계.")
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
        /** 객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 unitPriceCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 한 개·한 숙박일의 필수 요금 포함 판매가격. 양수 KRW 정수 원이므로 unitPriceAmount는 1 이상이다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 unitPriceCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String unitPriceCurrency,
        /** 이 날짜에 예약한 객실 수. 응답의 bookedRoomCount와 같다. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 날짜에 예약한 객실 수. 응답의 bookedRoomCount와 같다. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "1", example = "1")
        @NotNull
        @Min(1)
        Integer roomCount,
        /** 해당 숙박일 전체 예약 객실의 금액. 같은 배열 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 subtotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 숙박일 전체 예약 객실의 금액. 같은 배열 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 subtotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long subtotalAmount,
        /** 해당 숙박일 전체 예약 객실의 금액. 같은 배열 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 subtotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 숙박일 전체 예약 객실의 금액. 같은 배열 항목의 unitPriceAmount × roomCount이며 양수 단가와 양수 객실 수를 곱하므로 1원 이상이다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 subtotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String subtotalCurrency
    ) {
    }
    /** 예약에 포함된 개별 혜택. 실제 추가 선택이나 유료 추가 구매를 나타내지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "PartnerReservationDetailResponseBookedBenefitsItem", description = "예약에 포함된 개별 혜택. 실제 추가 선택이나 유료 추가 구매를 나타내지 않는다.")
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
        /** 혜택의 제공 수량. 가정: 수량을 정할 수 있는 혜택은 1 이상의 정수이며 정량화하지 않는 혜택은 null이다. 가정: null은 정량화하지 않는 혜택으로 정했다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택의 제공 수량. 가정: 수량을 정할 수 있는 혜택은 1 이상의 정수이며 정량화하지 않는 혜택은 null이다. 가정: null은 정량화하지 않는 혜택으로 정했다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "1")
        @Min(1)
        Integer quantity,
        /** 제공 수량의 단위. 가정: 1~40자이며 quantity가 null일 때만 null이다. 가정: null은 정량화하지 않는 혜택으로 정했다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "제공 수량의 단위. 가정: 1~40자이며 quantity가 null일 때만 null이다. 가정: null은 정량화하지 않는 혜택으로 정했다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 40)
        @Size(min = 1, max = 40)
        String unit,
        /** 예약 당시 혜택의 제공 대상·시간·이용 조건을 설명과 별도로 보존한다. 가정: 필수 문자열로 정하고 추가 조건이 없으면 빈 문자열을 사용하며 null은 허용하지 않는다. 상품 선택 때 제시한 조건을 확정 후에도 잃지 않기 위함이다. 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 혜택의 제공 대상·시간·이용 조건을 설명과 별도로 보존한다. 가정: 필수 문자열로 정하고 추가 조건이 없으면 빈 문자열을 사용하며 null은 허용하지 않는다. 상품 선택 때 제시한 조건을 확정 후에도 잃지 않기 위함이다. 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "예약 객실 투숙객에게 숙박 기간 동안 제공")
        @NotNull
        String conditions
    ) {
    }
    /** 예약 취소·선점 만료·해제에 따른 후속 금전 판단에 포함되는 결제 시도. 숙박대금과 취소 비용 청구를 구분하고 기존 시도 결과만 기록한다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "PartnerReservationDetailResponseCancellationFinancialImpactPaymentsItem", description = "예약 취소·선점 만료·해제에 따른 후속 금전 판단에 포함되는 결제 시도. 숙박대금과 취소 비용 청구를 구분하고 기존 시도 결과만 기록한다.")
    public record CancellationFinancialImpactPaymentsItem(
        /** 내부 청구 식별자는 저장소 형식을 노출하지 않는 1~64자로 정했다. 가정: 응답 필드명을 chargeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "내부 청구 식별자는 저장소 형식을 노출하지 않는 1~64자로 정했다. 가정: 응답 필드명을 chargeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "chg_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String chargeId,
        /** 가정: 필드명 attemptId, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 청구가 아직 결제를 시작하지 않았음을 뜻한다. 청구의 기존 논리 결제 시도를 식별한다. 수금 시작 전에는 null이다. 가정: 응답 필드명을 attemptId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 필드명 attemptId, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 청구가 아직 결제를 시작하지 않았음을 뜻한다. 청구의 기존 논리 결제 시도를 식별한다. 수금 시작 전에는 null이다. 가정: 응답 필드명을 attemptId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64)
        @Size(min = 1, max = 64)
        String attemptId,
        /** 숙박대금과 취소 비용을 같은 의무로 합산하지 않기 위한 수금 목적이다. 가정: 응답 필드명을 purpose, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "숙박대금과 취소 비용을 같은 의무로 합산하지 않기 위한 수금 목적이다. 가정: 응답 필드명을 purpose, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"STAY", "CANCELLATION_FEE"})
        @NotNull 
        CancellationPaymentPurpose purpose,
        /** SCHEDULED는 아직 시작 전, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 결과 확인 대기, CANCELLED는 실행 전 청구 중단이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "SCHEDULED는 아직 시작 전, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 결과 확인 대기, CANCELLED는 실행 전 청구 중단이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"})
        @NotNull 
        PaymentStatus status,
        /** 이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 서버에 이 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "서버에 이 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-12-16T12:00:00+09:00")
        @NotNull 
        OffsetDateTime updatedAt,
        /** 가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명을 실패로 채우지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명을 실패로 채우지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
    /** 예약 취소·선점 만료·해제에 따라 원결제에 연결된 환불 작업. 외부 환불 성공은 은행 입금 시각을 보장하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "PartnerReservationDetailResponseCancellationFinancialImpactRefundsItem", description = "예약 취소·선점 만료·해제에 따라 원결제에 연결된 환불 작업. 외부 환불 성공은 은행 입금 시각을 보장하지 않는다.")
    public record CancellationFinancialImpactRefundsItem(
        /** 환불 작업의 1~64자 opaque 식별자로 중복 작업을 구분한다. 가정: 응답 필드명을 refundId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "환불 작업의 1~64자 opaque 식별자로 중복 작업을 구분한다. 가정: 응답 필드명을 refundId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "rfd_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String refundId,
        /** 성공한 원결제 시도의 1~64자 식별자이며 환불의 귀속을 보존한다. 가정: 응답 필드명을 originalPaymentId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "성공한 원결제 시도의 1~64자 식별자이며 환불의 귀속을 보존한다. 가정: 응답 필드명을 originalPaymentId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "pay_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String originalPaymentId,
        /** PENDING은 실행 대기, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 기존 작업 확인 대기이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "PENDING은 실행 대기, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 기존 작업 확인 대기이다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"PENDING", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN"})
        @NotNull 
        RefundStatus status,
        /** 환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 amount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 currency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 서버에 환불 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "서버에 환불 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-12-16T12:00:00+09:00")
        @NotNull 
        OffsetDateTime updatedAt,
        /** 가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 환불 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명에는 실패 사유를 만들지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 환불 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명에는 실패 사유를 만들지 않는다. 가정: 응답 필드명을 failureReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
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

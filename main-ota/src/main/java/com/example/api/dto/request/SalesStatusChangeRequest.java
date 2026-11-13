package com.example.api.dto.request;
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
@Schema(name = "SalesStatusChangeRequest", description = "한 대상 범위의 판매 상태를 설정하는 요청. 가격·판매 한도·점유·예약 상태는 입력받지 않는다.")
public record SalesStatusChangeRequest(
    /** 가정: 필드명 scope, SalesStatusScope 표현과 필수이며 null 불가로 정했다. 대상과 처리 조건을 명시적으로 교환하기 위한 선택이다. */
    @Schema(description = "판매 상태를 변경할 범위. PROPERTY, ROOM_TYPE, RATE_PLAN, PRODUCT, ROOM_TYPE_DATE 중 하나이다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"PROPERTY", "ROOM_TYPE", "RATE_PLAN", "PRODUCT", "ROOM_TYPE_DATE"})
    @NotNull 
    SalesStatusScope scope,
    /** 가정: 문자열 길이는 1~64자로 선택했다. 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 필드명 targetId, String 표현과 필수이며 null 불가로 정했다. 대상과 처리 조건을 명시적으로 교환하기 위한 선택이다. */
    @Schema(description = "대상 식별자. PROPERTY이면 경로 propertyId와 같아야 하며 다른 범위에서는 같은 숙소의 해당 구성 요소 식별자이다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "room_1")
    @NotBlank 
    @Size(min = 1, max = 64)
    String targetId,
    /** 모든 날짜는 Asia/Seoul 기준이다. 가정: 필드명 from, LocalDate 표현과 필수이며 null 불가로 정했다. 대상과 처리 조건을 명시적으로 교환하기 위한 선택이다. */
    @Schema(description = "시작 날짜를 포함한다. ROOM_TYPE_DATE에서는 실제 변경 시작일이고 다른 범위에서는 응답 평가기간 시작일이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
    @NotNull 
    LocalDate from,
    /** 가정: 기간은 1~366일로 제한하여 날짜별 응답 크기를 관리한다. ROOM_TYPE_DATE 외의 범위는 이 기간 밖의 날짜에도 상태가 적용된다. 가정: 필드명 to, LocalDate 표현과 필수이며 null 불가로 정했다. 대상과 처리 조건을 명시적으로 교환하기 위한 선택이다. */
    @Schema(description = "종료 날짜를 제외한다. from보다 뒤여야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-22")
    @NotNull 
    LocalDate to,
    /** 가정: 필드명 state, SalesStatusValue 표현과 필수이며 null 불가로 정했다. 대상과 처리 조건을 명시적으로 교환하기 위한 선택이다. */
    @Schema(description = "요청 상태. OPEN은 대상의 차단만 해제하며 상위·다른 대상·파트너 정지 차단을 무시하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"OPEN", "SUSPENDED"})
    @NotNull 
    SalesStatusValue state,
    /** OPEN에서는 생략/null이어야 하고 저장된 중단 사유를 지운다. 1~500자이며 공백만인 값은 거절한다. 구조화된 외부 사유 목록이 자체 정책으로 확인되지 않아 자유문장으로 정했다. 가정: 문자열 길이는 1~500자로 선택했다. 표시 값의 길이와 전송 부담을 명확히 하기 위한 제한이다. 가정: 필드명 reason, String 표현과 생략 또는 null 허용로 정했다. 대상과 처리 조건을 명시적으로 교환하기 위한 선택이다. */
    @Schema(description = "가정: 중단의 자유문장 사유. SUSPENDED에서 선택 입력이며 누락/null은 사유 미기록이다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, pattern = "\\S", example = "객실 정비")
    @Size(min = 1, max = 500)
    @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$")
    String reason,
    /** 가정: 날짜·대상 간 동시 변경 손실을 막기 위해 숙소 단위 낙관적 버전을 사용한다. 가정: 필드명 expectedVersion, Long 표현과 필수이며 null 불가로 정했다. 대상과 처리 조건을 명시적으로 교환하기 위한 선택이다. */
    @Schema(description = "조회한 숙소 판매 상태 전체의 버전. 서버 현재 버전과 다르면 전체 요청을 409로 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "7")
    @NotNull 
    @Min(0)
    Long expectedVersion
) {
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

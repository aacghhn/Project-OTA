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

/** 상품 비교와 견적 계산의 동일 숙박 조건. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "SearchStayRequest", description = "상품 비교와 견적 계산의 동일 숙박 조건.")
public record SearchStayRequest(
    /** 체크인 포함, Asia/Seoul 날짜. 체크아웃보다 앞서야 한다. 가정: 서버 필드명을 checkIn로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "체크인 포함, Asia/Seoul 날짜. 체크아웃보다 앞서야 한다. 가정: 서버 필드명을 checkIn로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
    @NotNull
    LocalDate checkIn,
    /** 체크아웃 제외, Asia/Seoul 날짜. 가정: 서버 필드명을 checkOut로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "체크아웃 제외, Asia/Seoul 날짜. 가정: 서버 필드명을 checkOut로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-22")
    @NotNull
    LocalDate checkOut,
    /** 양수 객실 수, rooms 개수와 일치해야 한다. 가정: 서버 필드명을 roomCount로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "양수 객실 수, rooms 개수와 일치해야 한다. 가정: 서버 필드명을 roomCount로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "1", example = "1")
    @NotNull
    @Min(1)
    Integer roomCount,
    /** 객실별 성인·아동 인원. 인원별 추가 요금은 부과하지 않는다. 가정: 서버 필드명을 rooms로 정하고 array 구조 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @ArraySchema(arraySchema = @Schema(description = "객실별 성인·아동 인원. 인원별 추가 요금은 부과하지 않는다. 가정: 서버 필드명을 rooms로 정하고 array 구조 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = RoomsItem.class), minItems = 1)
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull  RoomsItem> rooms
) {
    /** 한 객실의 인원 배분. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "SearchStayRequestRoomsItem", description = "한 객실의 인원 배분.")
    public record RoomsItem(
        /** 객실별 인원 배분의 순서. 가정: 1부터 시작하며 동일 요청 안에서 중복이 없어야 한다. 물리적 호실 번호와 구별하기 위한 순번이다. 가정: 서버 필드명을 roomIndex로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "객실별 인원 배분의 순서. 가정: 1부터 시작하며 동일 요청 안에서 중복이 없어야 한다. 물리적 호실 번호와 구별하기 위한 순번이다. 가정: 서버 필드명을 roomIndex로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "1", example = "1")
        @NotNull
        @Min(1)
        Integer roomIndex,
        /** 해당 객실의 만 18세 이상 성인 수. 별도의 최소 성인 인원은 두지 않으며 성인과 아동 합계가 1 이상이고 객실 정원 이하여야 한다. 가정: 서버 필드명을 adults로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "해당 객실의 만 18세 이상 성인 수. 별도의 최소 성인 인원은 두지 않으며 성인과 아동 합계가 1 이상이고 객실 정원 이하여야 한다. 가정: 서버 필드명을 adults로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "0", example = "2")
        @NotNull
        @Min(0)
        Integer adults,
        /** 체크인 시점 만 0~17세 아동 수. 무료 투숙 아동도 총정원에 포함한다. 가정: 서버 필드명을 children로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "체크인 시점 만 0~17세 아동 수. 무료 투숙 아동도 총정원에 포함한다. 가정: 서버 필드명을 children로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "0", example = "0")
        @NotNull
        @Min(0)
        Integer children
    ) {
    }
}

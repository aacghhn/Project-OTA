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
@Schema(name = "BookingQuoteRequest", description = "선택 상품의 최종 견적 읽기 요청.")
public record BookingQuoteRequest(
    /** 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 서버 필드명을 productId로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "대상을 구분하는 불투명 식별자. 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "product-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String productId,
    /** 가정: 서버 필드명을 stay로 정하고 기간·객실 수·객실별 인원을 구분하는 객체 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "동일 기간·인원·수량으로 최종 확인할 조건.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Valid
    Stay stay
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingQuoteRequestStayRoomsItem", description = "한 객실의 인원 배분.")
    public record StayRoomsItem(
        /** 물리적 호실 번호와 구별하기 위한 순번이다. 가정: 서버 필드명을 roomIndex로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "객실별 인원 배분의 순서. 가정: 1부터 시작하며 동일 요청 안에서 중복이 없어야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull
        @Min(1)
        Integer roomIndex,
        /** 가정: 서버 필드명을 adults로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "해당 객실의 만 18세 이상 성인 수. 별도의 최소 성인 인원은 두지 않으며 성인과 아동 합계가 1 이상이고 객실 정원 이하여야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
        @NotNull
        @Min(0)
        Integer adults,
        /** 가정: 서버 필드명을 children로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "체크인 시점 만 0~17세 아동 수. 무료 투숙 아동도 총정원에 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
        @NotNull
        @Min(0)
        Integer children
    ) {
    }
    /** 가정: 서버 필드명을 stay로 정하고 기간·객실 수·객실별 인원을 구분하는 객체 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingQuoteRequestStay", description = "동일 기간·인원·수량으로 최종 확인할 조건.")
    public record Stay(
        /** 가정: 서버 필드명을 checkIn로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "체크인 포함, Asia/Seoul 날짜. 체크아웃보다 앞서야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull
        LocalDate checkIn,
        /** 가정: 서버 필드명을 checkOut로 정하고 LocalDate 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "체크아웃 제외, Asia/Seoul 날짜.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-22")
        @NotNull
        LocalDate checkOut,
        /** 가정: 서버 필드명을 roomCount로 정하고 정수 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @Schema(description = "양수 객실 수, rooms 개수와 일치해야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull
        @Min(1)
        Integer roomCount,
        /** 가정: 서버 필드명을 rooms로 정하고 array 구조 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
        @ArraySchema(arraySchema = @Schema(description = "객실별 성인·아동 인원. 인원별 추가 요금은 부과하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = StayRoomsItem.class))
        @NotNull
        @Size(min = 1)
        @Valid
        List<@NotNull  StayRoomsItem> rooms
    ) {
    }
}

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

/** 가정: 커서는 첫 조회 시각과 정렬 경계를 보존하여 페이지 중복을 줄이며 후속 상태 변경은 각 페이지의 observedAt 기준이다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CustomerReservationListResponse", description = "자기 예약 목록. 생성 시각 내림차순, 같은 시각이면 예약 식별자 내림차순으로 정렬한다.")
public record CustomerReservationListResponse(
    /** 가정: 응답 필드명을 items, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "고객 소유 예약과 진행 중 선점의 목록. 없으면 빈 배열이며 타인 예약을 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ItemsItem.class))
    @NotNull
    @Valid
    List<@NotNull ItemsItem> items,
    /** 가정: 응답 필드명을 nextCursor, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "다음 페이지를 읽을 불투명 커서. 가정: 1~256자이고 마지막 페이지이면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    @Size(min = 1, max = 256)
    String nextCursor,
    /** 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "이번 페이지 상태를 계산한 기준 시각. 가정: RFC3339 오프셋을 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:02:00+09:00")
    @NotNull
    OffsetDateTime observedAt
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CustomerReservationListResponseItemsItem", description = "목록에서 예약을 선택하는 데 필요한 요약. 상세 약속·사유·결제·환불은 예약 상세에서 확인한다.")
    public record ItemsItem(
        /** 가정: 응답 필드명을 reservationId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "상세 조회에서 사용할 예약 식별자. 가정: 식별자는 내부 저장 방식과 독립된 1~64자 문자열로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "res_1")
        @NotNull
        @Size(min = 1, max = 64)
        String reservationId,
        /** 가정: 응답 필드명을 propertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 숙소 이름. 가정: 1~200자로 표시한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울 스테이")
        @NotNull
        @Size(min = 1, max = 200)
        String propertyName,
        /** 가정: 응답 필드명을 checkIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "포함되는 체크인 날짜. Asia/Seoul 기준이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull
        LocalDate checkIn,
        /** 가정: 응답 필드명을 checkOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "포함되지 않는 체크아웃 날짜. Asia/Seoul 기준이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-22")
        @NotNull
        LocalDate checkOut,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 객실 수. 가정: 응답 필드명을 roomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "1")
        @NotNull
        @Min(1)
        Integer roomCount,
        /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 totalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 전체 숙박대금. 현재 상품 가격으로 다시 계산하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
        @NotNull 
        @Min(0)
        Long totalAmount,
        /** 가정: 이 통화값은 KRW 문자열로 표현해 원 단위를 구분한다. 가정: 응답 필드명을 totalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "예약 당시 전체 숙박대금. 현재 상품 가격으로 다시 계산하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String totalCurrency,
        /** CONFIRMED와 CANCELLED 등 후속 상태는 최초 선점 만료 시각만으로 바꾸지 않는다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "조회 기준 예약 상태. 저장 상태가 HELD인 미확정 선점만 조회 시각이 만료 시각과 같거나 지났으면 정리 작업 여부와 무관하게 EXPIRED로 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"HELD", "CONFIRMED", "EXPIRED", "RELEASED", "CANCELLED"}, example = "CONFIRMED")
        @NotNull
        ReservationStatus status,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "최초 선점 수락 시각이며 정렬에 사용한다. 가정: 응답 필드명을 createdAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:00:00+09:00")
        @NotNull
        OffsetDateTime createdAt
    ) {
    }
}

package com.example.api.dto.request;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/** 한 객실 유형의 여러 숙박일 한도를 모두 반영하거나 모두 거절하는 요청. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "DailySalesLimitUpdateRequest", description = "한 객실 유형의 여러 숙박일 한도를 모두 반영하거나 모두 거절하는 요청.")
public record DailySalesLimitUpdateRequest(
    /** 한도를 설정할 객실 유형 식별자. 요금제별로 별도 한도를 만들지 않는다. 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다. 가정: 필드명 roomTypeId와 문자열 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
    @Schema(description = "한도를 설정할 객실 유형 식별자. 요금제별로 별도 한도를 만들지 않는다. 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다. 가정: 필드명 roomTypeId와 문자열 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = ".*\\S.*", minLength = 1, maxLength = 64, example = "room_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = ".*\\S.*")
    String roomTypeId,
    /** 변경할 숙박일과 한도 목록. 가정: 한 요청은 1~366개 항목으로 제한해 원자적 변경 크기를 제한하며 같은 날짜 중복은 거절한다. 날짜가 연속일 필요는 없고 요청에 없는 날짜는 변경하지 않는다. 가정: 필드명 days와 숙박일·한도를 갖는 배열 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "변경할 숙박일과 한도 목록. 가정: 한 요청은 1~366개 항목으로 제한해 원자적 변경 크기를 제한하며 같은 날짜 중복은 거절한다. 날짜가 연속일 필요는 없고 요청에 없는 날짜는 변경하지 않는다. 가정: 필드명 days와 숙박일·한도를 갖는 배열 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = DaysItem.class), minItems = 1, maxItems = 366)
    @NotNull 
    @Size(min = 1, max = 366)
    @Valid
    List<@NotNull  DaysItem> days
) {
    /** 한 객실 유형의 하루 OTA 판매 한도 설정. 점유와 여유를 입력하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "DailySalesLimitUpdateRequestDaysItem", description = "한 객실 유형의 하루 OTA 판매 한도 설정. 점유와 여유를 입력하지 않는다.")
    public record DaysItem(
        /** 설정할 숙박일. 가정: 변경 시점의 Asia/Seoul 오늘 이상 날짜만 받아 과거 재고 설정을 덮어쓰지 않는다. 가정: 필드명 stayDate와 현지 날짜 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
        @Schema(description = "설정할 숙박일. 가정: 변경 시점의 Asia/Seoul 오늘 이상 날짜만 받아 과거 재고 설정을 덮어쓰지 않는다. 가정: 필드명 stayDate와 현지 날짜 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date", example = "2026-12-20")
        @NotNull 
        LocalDate stayDate,
        /** 해당 객실 유형의 모든 요금제가 공유하는 OTA 판매 한도. 단위는 객실 개수이고 0 이상이며 처리 시점의 점유량 이상이어야 한다. 254와 255도 일반 수량이며 무제한 코드는 없다. 가정: 필드명 otaLimit와 32비트 정수 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
        @Schema(description = "해당 객실 유형의 모든 요금제가 공유하는 OTA 판매 한도. 단위는 객실 개수이고 0 이상이며 처리 시점의 점유량 이상이어야 한다. 254와 255도 일반 수량이며 무제한 코드는 없다. 가정: 필드명 otaLimit와 32비트 정수 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "0", example = "5")
        @NotNull 
        @Min(0)
        Integer otaLimit
    ) {
    }
}

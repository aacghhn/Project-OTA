package com.example.api.dto.request;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "DailySalesPriceUpdateRequest", description = "한 판매 상품의 여러 숙박일 가격을 모두 반영하거나 모두 거절하는 요청.")
public record DailySalesPriceUpdateRequest(
    /** 가정: 저장 형식을 노출하지 않는 1~64자 식별자를 사용하며 공백만인 값은 거절한다. 가정: 필드명 productId와 문자열 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
    @Schema(description = "가격을 설정할 판매 상품 식별자. 같은 숙소의 객실 유형과 요금제 조합이다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ".*\\S.*", example = "product_1")
    @NotNull 
    @Size(min = 1, max = 64)
    @Pattern(regexp = ".*\\S.*")
    String productId,
    /** 날짜가 연속일 필요는 없고 요청에 없는 날짜는 변경하지 않는다. 가정: 필드명 days와 숙박일·금액·통화를 갖는 배열 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "변경할 숙박일과 가격 목록. 가정: 한 요청은 1~366개 항목으로 제한해 원자적 변경 크기를 제한하며 같은 날짜 중복은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = DaysItem.class))
    @NotNull 
    @Size(min = 1, max = 366)
    @Valid
    List<@NotNull  DaysItem> days
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "DailySalesPriceUpdateRequestDaysItem", description = "한 판매 상품의 하루 가격 설정. 날짜별 가격은 필수 요금 포함 원화 정수 원이며 0원 판매는 허용하지 않는다.")
    public record DaysItem(
        /** 가정: 필드명 stayDate와 현지 날짜 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
        @Schema(description = "설정할 숙박일. 가정: 변경 시점의 Asia/Seoul 오늘 이상 날짜만 받아 과거 판매 설정을 덮어쓰지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull 
        LocalDate stayDate,
        /** 가정: 필드명 amount와 64비트 정수 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
        @Schema(description = "객실 하나·숙박일 하나의 필수 요금 포함 판매가격. 양수 원화 정수만 허용한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "150000")
        @NotNull 
        @Min(1)
        Long amount,
        /** 가정: 필드명 currency와 문자열 표현을 선택하고 필수값으로 제공하고 null을 허용하지 않는다. 입력 대상과 계산 결과를 명시적으로 구분하기 위한 결정이다. */
        @Schema(description = "통화는 KRW만 허용한다. 가정: 단위를 명시적으로 확인하도록 생략할 수 없는 문자열로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency
    ) {
    }
}

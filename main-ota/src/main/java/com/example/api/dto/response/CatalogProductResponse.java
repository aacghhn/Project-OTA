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

@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CatalogProductResponse", description = "동일 숙소 객실 유형과 요금제의 판매 상품 연결.")
public record CatalogProductResponse(
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "생성된 판매 상품 식별자. 가정: 응답 필드명을 productId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "product_1")
    @NotBlank

    @Size(min = 1, max = 64)
    String productId,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 저장 성공마다 증가하는 판매 상품 버전. 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "1")
    @NotNull

    @Min(0)
    Long version,
    /** 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "같은 숙소의 객실 유형 식별자. 다른 숙소 구성과 연결할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "room_1")
    @NotBlank

    @Size(min = 1, max = 64)
    String roomTypeId,
    /** 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "같은 숙소의 요금제 식별자. 요금제 추가가 객실 수량 증가를 뜻하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "rate_1")
    @NotBlank

    @Size(min = 1, max = 64)
    String ratePlanId,
    /** 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "고객용 판매 상품명. 가정: 1~200자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "스탠다드 더블 · 조식 포함")
    @NotBlank

    @Size(min = 1, max = 200)
    String name,
    /** 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "상품의 객실·서비스 조합 설명. 가정: 1~5000자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "동일 객실 유형과 조식 포함 요금제를 연결한 판매 상품입니다.")
    @NotBlank

    @Size(min = 1, max = 5000)
    String description
) {
}

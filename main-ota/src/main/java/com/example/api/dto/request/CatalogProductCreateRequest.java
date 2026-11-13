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
@Schema(name = "CatalogProductCreateRequest", description = "같은 숙소의 객실 유형 하나와 요금제 하나를 연결한다.")
public record CatalogProductCreateRequest(
    /** 가정: 필드명 roomTypeId, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "같은 숙소의 객실 유형 식별자. 다른 숙소 구성과 연결할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "room_1")
    @NotBlank

    @Size(min = 1, max = 64)
    String roomTypeId,
    /** 가정: 필드명 ratePlanId, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "같은 숙소의 요금제 식별자. 요금제 추가가 객실 수량 증가를 뜻하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "rate_1")
    @NotBlank

    @Size(min = 1, max = 64)
    String ratePlanId,
    /** 가정: 필드명 name, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "고객용 판매 상품명. 가정: 1~200자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "스탠다드 더블 · 조식 포함")
    @NotBlank

    @Size(min = 1, max = 200)
    String name,
    /** 가정: 필드명 description, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "상품의 객실·서비스 조합 설명. 가정: 1~5000자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "동일 객실 유형과 조식 포함 요금제를 연결한 판매 상품입니다.")
    @NotBlank

    @Size(min = 1, max = 5000)
    String description
) {
}

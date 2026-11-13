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
@Schema(name = "CatalogProductUpdateRequest", description = "판매 상품명과 설명만 변경한다. 연결된 객실 유형·요금제는 변경할 수 없다.")
public record CatalogProductUpdateRequest(
    /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "가정: 현재 판매 상품 버전을 필수로 받아 동시 편집을 검사한다. 가정: 필드명 expectedVersion, integer 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "1")
    @NotNull

    @Min(0)
    Long expectedVersion,
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

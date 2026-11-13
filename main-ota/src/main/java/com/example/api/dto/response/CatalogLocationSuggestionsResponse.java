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
@Schema(name = "CatalogLocationSuggestionsResponse", description = "주소 후보 검색 결과. 실제 입력 주소를 자동 저장하지 않는다.")
public record CatalogLocationSuggestionsResponse(
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 검색한 주소 문자열을 1~200자로 되돌려 대상을 식별한다. 가정: 응답 필드명을 query, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울 중구 예시로")
    @NotBlank

    @Size(min = 1, max = 200)
    String query,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 최대 20개 후보를 반환하며 빈 배열은 일치하는 검증 후보 없음이다. 가정: 응답 필드명을 candidates, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = CandidatesItem.class))
    @NotNull

    @Size(max = 20)
    @Valid
    List<@NotNull  CandidatesItem> candidates
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogLocationSuggestionsResponseCandidatesItem", description = "주소 검색 후보. 선택한 후보의 좌표를 입력 주소와 대조할 수 있도록 제공한다.")
    public record CandidatesItem(
        /** 가정: 응답 필드명을 candidateId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 서버가 검증한 주소 후보 식별자. 선택 후 주소 입력과 동일한 후보인지 다시 검증한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "loc_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String candidateId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 후보 선택을 위한 전체 주소 표시문구. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울특별시 중구 예시로 10")
        @NotBlank

        @Size(min = 1, max = 500)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "국내 후보의 국가 코드 KR. 가정: 응답 필드명을 countryCode, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KR$", allowableValues = {"KR"}, example = "KR")
        @NotBlank

        @Size(min = 2, max = 2)
        @Pattern(regexp = "^KR$")
        String countryCode,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후보의 주·도 행정구역. 가정: 응답 필드명을 province, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울특별시")
        @NotBlank

        @Size(min = 1, max = 100)
        String province,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후보의 도시. 가정: 응답 필드명을 city, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울")
        @NotBlank

        @Size(min = 1, max = 100)
        String city,
        /** 가정: 응답 필드명을 district, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후보의 지역. 없으면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "중구")
        @Size(min = 1, max = 100)
        String district,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후보의 국내 5자리 우편번호. 가정: 응답 필드명을 postalCode, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^[0-9]{5}$", example = "04524")
        @NotBlank

        @Size(min = 5, max = 5)
        @Pattern(regexp = "^[0-9]{5}$")
        String postalCode,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "후보의 영문 상세 주소. 가정: 응답 필드명을 englishAddress, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "10 Example-ro, Jung-gu, Seoul")
        @NotBlank

        @Size(min = 1, max = 500)
        String englishAddress,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증한 후보 위도를 숫자로 제공한다. 가정: 응답 필드명을 latitude, JSON 타입을 숫자로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "37.5665")
        @NotNull

        @DecimalMin(value = "-90", inclusive = true)
        @DecimalMax("90")
        BigDecimal latitude,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증한 후보 경도를 숫자로 제공한다. 가정: 응답 필드명을 longitude, JSON 타입을 숫자로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "126.978")
        @NotNull

        @DecimalMin(value = "-180", inclusive = true)
        @DecimalMax("180")
        BigDecimal longitude
    ) {
    }
}

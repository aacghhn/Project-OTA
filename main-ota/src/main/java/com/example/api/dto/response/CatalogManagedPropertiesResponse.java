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
@Schema(name = "CatalogManagedPropertiesResponse", description = "파트너가 소유한 숙소 목록의 한 페이지. 다른 소유자의 숙소는 포함하지 않는다.")
public record CatalogManagedPropertiesResponse(
    /** 가정: 응답 필드명을 items, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 요청한 페이지 크기 이하의 숙소 목록. propertyId 오름차순으로 반환하며 소유 숙소가 없으면 빈 배열이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ItemsItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  ItemsItem> items,
    /** 가정: 응답 필드명을 nextCursor, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 다음 페이지를 지정하는 불투명 문자열을 1~1024자로 반환한다. 더 없으면 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    @Size(min = 1, max = 1024)
    String nextCursor
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogManagedPropertiesResponseItemsItem", description = "인증된 파트너가 관리하는 숙소의 선택 항목.")
    public record ItemsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "관리할 숙소의 식별자. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "property_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String propertyId,
        /** 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 현재 등록된 숙소 이름을 표시한다. 상세 구성을 시작하기 전에도 숙소 등록 때의 이름으로 대상을 식별할 수 있다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울 예시 숙소")
        @NotBlank

        @Size(min = 1, max = 200)
        String name
    ) {
    }
}

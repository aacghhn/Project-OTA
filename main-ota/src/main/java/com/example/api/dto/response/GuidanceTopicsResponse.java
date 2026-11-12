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

/** 로그인 없이 확인하는 공개 안내 분류. 안내가 준비되지 않았으면 빈 목록을 상태와 함께 반환한다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "GuidanceTopicsResponse", description = "로그인 없이 확인하는 공개 안내 분류. 안내가 준비되지 않았으면 빈 목록을 상태와 함께 반환한다.")
public record GuidanceTopicsResponse(
    /** AVAILABLE이면 공개할 분류가 하나 이상 있으며 NOT_PROVIDED이면 아직 없다. 가정: 필드명 status, 필수 문자열, 허용값 AVAILABLE, NOT_PROVIDED로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "AVAILABLE이면 공개할 분류가 하나 이상 있으며 NOT_PROVIDED이면 아직 없다. 가정: 필드명 status, 필수 문자열, 허용값 AVAILABLE, NOT_PROVIDED로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"AVAILABLE", "NOT_PROVIDED"}, example = "AVAILABLE")
    @NotNull
    @Pattern(regexp = "^(?:AVAILABLE|NOT_PROVIDED)$")
    String status,
    /** 제공하는 안내 언어. 가정: 필드명 locale, 필수 문자열, 허용값 ko-KR로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 locale, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "제공하는 안내 언어. 가정: 필드명 locale, 필수 문자열, 허용값 ko-KR로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 locale, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"ko-KR"}, example = "ko-KR")
    @NotNull
    @Pattern(regexp = "^(?:ko\\-KR)$")
    String locale,
    /** 공개한 분류를 displayOrder, topicId 순으로 제공한다. 가정: 필드명 topics, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 topics, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "공개한 분류를 displayOrder, topicId 순으로 제공한다. 가정: 필드명 topics, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 topics, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = TopicsItem.class), minItems = 0)
    @NotNull
    @Size(min = 0)
    @Valid
    List<@NotNull  TopicsItem> topics,
    /** 목록을 읽은 서버 기준 시각. 가정: 필드명 observedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다. RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 허용하지 않는다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "목록을 읽은 서버 기준 시각. 가정: 필드명 observedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다. RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 허용하지 않는다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull
    OffsetDateTime observedAt
) {
    /** 공개 안내의 분류 한 개. 선택 상태는 클라이언트의 탐색 상태이며 이 객체가 예약을 변경하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "GuidanceTopicsResponseTopicsItem", description = "공개 안내의 분류 한 개. 선택 상태는 클라이언트의 탐색 상태이며 이 객체가 예약을 변경하지 않는다.")
    public record TopicsItem(
        /** 주제 식별자. 가정: 필드명 topicId, String, 1~64자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 topicId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "주제 식별자. 가정: 필드명 topicId, String, 1~64자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 topicId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "cancellation")
        @NotNull
        @Size(min = 1, max = 64)
        String topicId,
        /** 고객에게 표시할 자체 주제 이름. 가정: 필드명 label, String, 1~100자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "고객에게 표시할 자체 주제 이름. 가정: 필드명 label, String, 1~100자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "예약 취소")
        @NotNull
        @Size(min = 1, max = 100)
        String label,
        /** 가정: 필드명 displayOrder, 필수 0 이상의 Integer로 정해 운영팀이 정한 표시 순서를 전달한다. 같은 값이면 topicId 오름차순이다. 가정: 응답 필드명을 displayOrder, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "가정: 필드명 displayOrder, 필수 0 이상의 Integer로 정해 운영팀이 정한 표시 순서를 전달한다. 같은 값이면 topicId 오름차순이다. 가정: 응답 필드명을 displayOrder, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minimum = "0", example = "1")
        @NotNull
        @Min(0)
        Integer displayOrder
    ) {
    }
}

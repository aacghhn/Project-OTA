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
@Schema(name = "GuidanceArticleListResponse", description = "검증되어 공개 중인 안내만 보여주는 목록. 검색 결과가 없어도 정상 조회이다.")
public record GuidanceArticleListResponse(
    /** 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "AVAILABLE은 일치하는 안내가 한 개 이상임, NO_MATCH는 공개 안내는 있으나 조건에 맞는 항목이 없음, NOT_PROVIDED는 공개 안내 자체가 없음을 뜻한다. 가정: 필드명 status, 필수 문자열, 허용값 AVAILABLE, NO_MATCH, NOT_PROVIDED로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"AVAILABLE", "NO_MATCH", "NOT_PROVIDED"}, example = "AVAILABLE")
    @NotNull
    @Pattern(regexp = "^(?:AVAILABLE|NO_MATCH|NOT_PROVIDED)$")
    String status,
    /** 가정: 응답 필드명을 locale, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "안내 언어. 가정: 필드명 locale, 필수 문자열, 허용값 ko-KR로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"ko-KR"}, example = "ko-KR")
    @NotNull
    @Pattern(regexp = "^(?:ko\\-KR)$")
    String locale,
    /** 가정: 필드명 items, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 items, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "질문·요약 목록. 가정: articleId 오름차순으로 고정해 이어보기 순서를 명확히 한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ItemsItem.class))
    @NotNull
    @Size
    @Valid
    List<@NotNull  ItemsItem> items,
    /** 가정: 필드명 nextCursor, String, 1~256자, 키는 항상 포함하고 해당 필드에 명시한 조건에서만 null을 허용으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 nextCursor, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "다음 페이지가 없으면 null이다. 가정: 조건과 마지막 articleId를 결합한 불투명 토큰을 사용하고 다른 조건에 재사용하면 400으로 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "cursor-next")
    @Size(min = 1, max = 256)
    String nextCursor,
    /** 가정: 필드명 observedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다. RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 허용하지 않는다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "이 목록의 조회 기준 시각. 이후 콘텐츠 갱신으로 다음 페이지의 내용이 달라질 수 있다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull
    OffsetDateTime observedAt
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "GuidanceArticleListResponseItemsItem", description = "검증해 공개한 안내의 목록 항목. 질문의 문구는 자체 콘텐츠이며 외부의 미확인 답변을 대신 게시하지 않는다.")
    public record ItemsItem(
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 articleId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "안내 식별자. 가정: 필드명 articleId, String, 1~64자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "cancel-cost")
        @NotNull
        @Size(min = 1, max = 64)
        String articleId,
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 topicId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "안내가 속한 분류 식별자. 가정: 필드명 topicId, String, 1~64자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "cancellation")
        @NotNull
        @Size(min = 1, max = 64)
        String topicId,
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 question, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "고객이 선택할 자체 질문 또는 안내 제목. 가정: 필드명 question, String, 1~500자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예약을 취소하면 얼마를 부담하나요?")
        @NotNull
        @Size(min = 1, max = 500)
        String question,
        /** 가정: 필드명 summary, String, 0~1000자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 summary, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "본문의 핵심 조건을 줄인 자체 안내. 요약이 필요 없으면 빈 문자열이다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예약 당시 취소 조건과 취소 요청 시각으로 비용을 판단합니다.")
        @NotNull
        @Size(max = 1000)
        String summary,
        /** RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 허용하지 않는다. 가정: 응답 필드명을 updatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "공개된 제목·요약·본문이 마지막으로 갱신된 시각. 가정: 필드명 updatedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:00:00+09:00")
        @NotNull
        OffsetDateTime updatedAt
    ) {
    }
}

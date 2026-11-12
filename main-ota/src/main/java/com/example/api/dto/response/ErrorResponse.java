package com.example.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

/** 업무 실패와 HTTP 입력 오류를 같은 구조로 제공하며 원시 요청 내용은 되돌려주지 않는다. */
@Schema(description = "요청 거절 이유와 필드 오류. 금전 처리의 실패 상태는 정상 조회 응답의 결제 또는 환불 상태로 별도 표현한다.")
public record ErrorResponse(
    // 가정: code는 필수 문자열이다. 화면 문구와 독립적인 오류 분기를 위해 선택했다.
    @Schema(description = "가정: 필수 문자열 code는 오류 분기용 기계 코드이다. 예: INVALID_REQUEST, RESOURCE_NOT_FOUND, IDEMPOTENCY_CONFLICT.", requiredMode = Schema.RequiredMode.REQUIRED, example = "INVALID_REQUEST") @NotNull String code,
    // 가정: message는 필수 문자열이며 고객에게 설명할 안전한 이유만 담는다.
    @Schema(description = "가정: 필수 문자열 message에 대상·조건·거절 이유를 담아 표시한다. 원시 결제 자료와 비밀 값은 포함하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "체크아웃은 체크인 이후여야 합니다.") @NotNull String message,
    // 가정: requestId는 서버가 매 응답에 부여하는 필수 UUID 문자열이다. 오류 문의의 대상을 식별한다.
    @Schema(description = "가정: 필수 UUID 문자열 requestId는 서버가 발급하는 오류 응답 식별자이다. 외부 입력을 그대로 반사하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "uuid", example = "3d96c26b-1f39-49b1-b4a0-62c7cbb0da03") @NotNull String requestId,
    // 가정: timestamp는 필수 OffsetDateTime이며 서버의 오류 응답 생성 시각을 UTC 오프셋으로 기록한다.
    @Schema(description = "가정: 필수 timestamp는 오류 응답 생성 시각이다. RFC3339 UTC 오프셋으로 기록하여 시각을 식별한다.", requiredMode = Schema.RequiredMode.REQUIRED, type = "string", format = "date-time", example = "2026-11-11T03:00:00Z") @NotNull OffsetDateTime timestamp,
    // 가정: violations는 필수 배열이며 개별 필드 오류가 없으면 빈 배열이다. 오류 하나와 여러 필드 오류를 구분한다.
    @Schema(description = "가정: 필수 violations 배열은 필드별 오류이며 해당 오류가 없으면 []이다.", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull List<@Valid FieldViolation> violations
) {
    @Schema(name = "FieldViolation", description = "거절한 입력 필드와 안전한 설명. 입력값 자체는 포함하지 않는다.")
    public record FieldViolation(
        // 가정: field는 필수 문자열 경로이며 본문 전체 문제는 body로 표현하여 어느 입력이 거절됐는지 식별한다.
        @Schema(description = "가정: 필수 문자열 field는 body.checkIn, query.limit, header.Idempotency-Key 같은 입력 경로이다. 전체 본문 문제는 body이다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "body.checkOut") @NotNull String field,
        // 가정: message는 필수 문자열이며 제약을 만족하지 못한 이유를 구체적으로 표시한다.
        @Schema(description = "가정: 필수 문자열 message는 필드 제약 위반 이유를 설명한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "체크인 이후 날짜여야 합니다.") @NotNull String message
    ) {}
}

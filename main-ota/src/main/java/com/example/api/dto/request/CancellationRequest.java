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
@Schema(name = "CancellationRequest", description = "고객이 자신의 예약 전체 취소를 명시적으로 요청한다. 금액·판정 시각·취소 사유 코드는 서버가 정하며 객실 일부를 선택할 입력은 없다.")
public record CancellationRequest(
    /** 첫 취소에는 미리 조회한 서버 서명 토큰이 필요하다. 토큰이 고객·예약·비용과 일치하고 5분 유효기간 안이어야 한다. 이미 취소된 예약의 최초 결과 반환에는 토큰 검증을 생략한다. */
    @Schema(description = "가정: 필드명 confirmationToken, String 표현을 선택했다. 입력은 생략하거나 null로 보낼 수 있다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    @Size(min = 1, max = 2048)
    String confirmationToken,
    /** true만 허용하여 전체 숙박일·전체 객실 취소에 대한 명시적 실행 의사를 받는다. */
    @Schema(description = "가정: 필드명 confirmEntireReservation, Boolean 표현을 선택했다. 필수값으로 제공하여 누락과 처리 상태를 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"true"}, example = "true")
    @NotNull 
    @AssertTrue 
    Boolean confirmEntireReservation,
    /** 확인된 사유 선택지가 없어 선택 자유문장으로 정했다. 제공하면 공백만인 값은 허용하지 않으며 1~500자로 제한해 상황을 기록한다. 사유 코드 CUSTOMER_REQUEST는 서버가 정한다. */
    @Schema(description = "가정: 필드명 reasonDetail, String 표현을 선택했다. 입력은 생략하거나 null로 보낼 수 있다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, pattern = "^[\\s\\S]*\\S[\\s\\S]*$")
    @Size(min = 1, max = 500)
    @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$")
    String reasonDetail
) {
}

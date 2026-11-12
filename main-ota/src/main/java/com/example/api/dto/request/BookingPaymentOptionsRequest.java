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

/** 견적에 맞는 결제 선택지 읽기. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "BookingPaymentOptionsRequest", description = "견적에 맞는 결제 선택지 읽기.")
public record BookingPaymentOptionsRequest(
    /** 가정: 견적에서 받은 서명 토큰 1~4096자를 필수로 보낸다. 고객과 상품에 맞는 결제 선택지를 계산하기 위해 사용한다. 가정: 서버 필드명을 quoteToken로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "가정: 견적에서 받은 서명 토큰 1~4096자를 필수로 보낸다. 고객과 상품에 맞는 결제 선택지를 계산하기 위해 사용한다. 가정: 서버 필드명을 quoteToken로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 4096, example = "quote-token-1")
    @NotBlank
    @Size(min = 1, max = 4096)
    String quoteToken
) {
}

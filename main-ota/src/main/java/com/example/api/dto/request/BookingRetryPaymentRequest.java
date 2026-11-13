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
@Schema(name = "BookingRetryPaymentRequest", description = "실패가 확정된 즉시 결제의 새 시도 요청.")
public record BookingRetryPaymentRequest(
    /** 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 서버 필드명을 paymentMethodId로 정하고 문자열 필수값으로 표현한다. 화면 요소의 속성이 확정되지 않아 클라이언트와 서버가 명시적으로 같은 값을 교환하도록 정했다. */
    @Schema(description = "새 즉시 결제 시도에 사용할 자신의 결제수단. 이전 수단과 달라도 소유권과 유효성을 다시 검사한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "paymentMethod-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String paymentMethodId
) {
}

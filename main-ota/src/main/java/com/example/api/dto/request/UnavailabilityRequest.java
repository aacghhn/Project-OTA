package com.example.api.dto.request;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/** 파트너가 자기 숙소의 예약 전체를 제공할 수 없음을 알리고 취소를 요청하는 입력. 임의 환불액·비용 면제·예약 일부 선택은 입력으로 받지 않는다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "UnavailabilityRequest", description = "파트너가 자기 숙소의 예약 전체를 제공할 수 없음을 알리고 취소를 요청하는 입력. 임의 환불액·비용 면제·예약 일부 선택은 입력으로 받지 않는다.")
public record UnavailabilityRequest(
    /** 제공할 수 없는 구체적인 사유. 가정: 필드명 reason, 필수 자유문장 1~500자로 받고 null·빈문자·공백만인 값은 거절한다. 구조화된 사유 선택지가 확인되지 않아 상황을 기록할 수 있게 정했다. 이미 취소된 예약에서는 이 새 사유로 최초 사유를 덮어쓰지 않는다. */
    @Schema(description = "제공할 수 없는 구체적인 사유. 가정: 필드명 reason, 필수 자유문장 1~500자로 받고 null·빈문자·공백만인 값은 거절한다. 구조화된 사유 선택지가 확인되지 않아 상황을 기록할 수 있게 정했다. 이미 취소된 예약에서는 이 새 사유로 최초 사유를 덮어쓰지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "\\S", minLength = 1, maxLength = 500, example = "수도 배관 파손으로 예약 객실을 제공할 수 없습니다.")
    @NotBlank 
    @Size(min = 1, max = 500)
    String reason,
    /** 예약 전체 취소에 대한 명시적 확인. 가정: 필드명 confirmEntireReservation, 필수 Boolean true만 허용하여 화면 탐색과 실제 취소 요청을 구분한다. false·null·누락은 거절한다. */
    @Schema(description = "예약 전체 취소에 대한 명시적 확인. 가정: 필드명 confirmEntireReservation, 필수 Boolean true만 허용하여 화면 탐색과 실제 취소 요청을 구분한다. false·null·누락은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"true"}, example = "true")
    @NotNull 
    @AssertTrue 
    Boolean confirmEntireReservation
) {
}

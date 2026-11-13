package com.example.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가정: PaymentStatus이라는 enum 이름과 문자열 코드를 선택하여 업무 상태를 API에서 구분한다. SCHEDULED: 청구 예정이며 아직 결제를 시작하지 않았다; PROCESSING: 기존 결제 시도의 결과를 기다리고 있다; SUCCEEDED: 결제 성공이 확인되었다; FAILED: 결제 실패가 확정되었다; UNKNOWN: 결과를 확인하지 못해 기존 결제 시도의 확인이 필요하다; CANCELLED: 아직 시작하지 않은 청구가 중단되었다.")
public enum PaymentStatus {
    /** 청구 예정이며 아직 결제를 시작하지 않았다. */
    SCHEDULED,
    /** 기존 결제 시도의 결과를 기다리고 있다. */
    PROCESSING,
    /** 결제 성공이 확인되었다. */
    SUCCEEDED,
    /** 결제 실패가 확정되었다. */
    FAILED,
    /** 결과를 확인하지 못해 기존 결제 시도의 확인이 필요하다. */
    UNKNOWN,
    /** 아직 시작하지 않은 청구가 중단되었다. */
    CANCELLED
}

package com.example.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가정: ReservationStatus이라는 enum 이름과 문자열 코드를 선택하여 업무 상태를 API에서 구분한다. HELD: 선점 중이며 만료 시각 전까지만 유효하다; CONFIRMED: 예약 약속이 확정되었으며 결제 완료를 뜻하지 않는다; EXPIRED: 선점 만료 시각 이상으로 정리 작업과 무관하게 무효이다; RELEASED: 확정 전에 선점이 해제되었다; CANCELLED: 예약 전체가 취소되었으며 환불 완료를 뜻하지 않는다.")
public enum ReservationStatus {
    /** 선점 중이며 만료 시각 전까지만 유효하다. */
    HELD,
    /** 예약 약속이 확정되었으며 결제 완료를 뜻하지 않는다. */
    CONFIRMED,
    /** 선점 만료 시각 이상으로 정리 작업과 무관하게 무효이다. */
    EXPIRED,
    /** 확정 전에 선점이 해제되었다. */
    RELEASED,
    /** 예약 전체가 취소되었으며 환불 완료를 뜻하지 않는다. */
    CANCELLED
}

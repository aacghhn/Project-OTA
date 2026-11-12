package com.example.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/** 가정: CancellationReason이라는 enum 이름과 문자열 코드를 선택하여 업무 상태를 API에서 구분한다. CUSTOMER_REQUEST: 고객의 명시적 전체 취소 요청; SCHEDULED_PAYMENT_FAILED: 예정 결제 실패 확정에 따른 시스템 전체 취소; PROPERTY_UNAVAILABLE: 파트너의 숙박 제공 불가에 따른 전체 취소. */
@Schema(description = "가정: CancellationReason이라는 enum 이름과 문자열 코드를 선택하여 업무 상태를 API에서 구분한다. CUSTOMER_REQUEST: 고객의 명시적 전체 취소 요청; SCHEDULED_PAYMENT_FAILED: 예정 결제 실패 확정에 따른 시스템 전체 취소; PROPERTY_UNAVAILABLE: 파트너의 숙박 제공 불가에 따른 전체 취소.")
public enum CancellationReason {
    /** 고객의 명시적 전체 취소 요청. */
    CUSTOMER_REQUEST,
    /** 예정 결제 실패 확정에 따른 시스템 전체 취소. */
    SCHEDULED_PAYMENT_FAILED,
    /** 파트너의 숙박 제공 불가에 따른 전체 취소. */
    PROPERTY_UNAVAILABLE
}

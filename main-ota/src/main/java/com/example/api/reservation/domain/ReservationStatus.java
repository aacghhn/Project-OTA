package com.example.api.reservation.domain;

/** 예약의 상태. 만료는 저장 상태가 아니라 날짜별 점유의 Hold와 기준 시각으로 판정한다. */
public enum ReservationStatus {
    HOLD,
    CONFIRMED,
    RELEASED
}

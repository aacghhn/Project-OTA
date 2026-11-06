package com.example.api.reservation.domain;

/**
 * 예약 당시 대표 투숙객의 정보. 로그인한 예약자의 식별자와 별도로 보관한다.
 */
public record GuestInfo(String name, String contact) {
}

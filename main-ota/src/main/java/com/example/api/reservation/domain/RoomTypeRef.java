package com.example.api.reservation.domain;

/**
 * 예약 맥락에서 숙소와 객실 타입을 가리키는 값.
 * 실제 소속 관계는 상품·숙소 쪽에서 확인한다.
 */
public record RoomTypeRef(String propertyId, String roomTypeId) {
}

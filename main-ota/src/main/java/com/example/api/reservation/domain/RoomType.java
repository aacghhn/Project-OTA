package com.example.api.reservation.domain;

import java.util.Objects;

/**
 * 예약 맥락에서 객실 타입을 가리키는 값. 객실 타입이 실제로 그 숙소에 속하는지는 이 패키지에서 검증하지 않는다.
 * */
public record RoomType(String propertyId, String roomTypeId) {

    public RoomType {
        Objects.requireNonNull(propertyId);
        Objects.requireNonNull(roomTypeId);
    }
}

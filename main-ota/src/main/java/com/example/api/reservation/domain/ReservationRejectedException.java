package com.example.api.reservation.domain;

import lombok.Getter;

/**
 * 규칙상 허용되지 않아 거절된 요청. 거절되면 상태는 바뀌지 않는다.
 * */
@Getter
public class ReservationRejectedException extends RuntimeException {

    public enum Reason {
        STOCK_UNDEFINED,
        SALE_STOPPED,
        INSUFFICIENT_STOCK,
        HOLD_RELEASED,
        HOLD_EXPIRED,
        ALREADY_CONFIRMED
    }

    private final Reason reason;

    public ReservationRejectedException(Reason reason, String detail) {
        super(reason + ": " + detail);
        this.reason = reason;
    }
}

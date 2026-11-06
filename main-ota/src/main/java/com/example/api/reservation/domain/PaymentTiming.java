package com.example.api.reservation.domain;

/**
 * 고객이 선택한 지불 시점. 카드·계좌 등 결제 수단과 구분한다.
 */
public enum PaymentTiming {
    PAY_NOW,
    PAY_LATER
}

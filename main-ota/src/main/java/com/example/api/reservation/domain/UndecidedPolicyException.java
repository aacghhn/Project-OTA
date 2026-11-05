package com.example.api.reservation.domain;

/**
 * 아직 정하지 않은 정책의 경로에 도달했다는 뜻이다. 정책을 임의로 고르지 않고 상태도 바꾸지 않는다.
 * */
public class UndecidedPolicyException extends RuntimeException {

    public UndecidedPolicyException(String message) {
        super(message);
    }
}

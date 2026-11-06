package com.example.api.reservation.domain;

/**
 * 예약 생성의 저장 경계.
 * 재고 조회와 이 저장은 하나의 트랜잭션·동시성 제어 범위에 포함되어야 한다.
 */
public interface ReservationRepository {

    /**
     * 새 예약을 저장하며, 같은 예약을 중복 점유하게 만드는 생성을 원자적으로 거절한다.
     * 만료된 HOLD도 중복 검사에서 제외하지 않는다.
     * 중복 요청의 응답과 해제 후 식별자 재사용 정책은 저장 구현 시 별도로 정한다.
     */
    void add(Reservation reservation);
}

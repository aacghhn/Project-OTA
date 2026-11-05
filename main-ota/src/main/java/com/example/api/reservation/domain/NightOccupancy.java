package com.example.api.reservation.domain;

import java.time.Instant;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * NightStock의 내부 엔티티인 날짜별 점유. NightStock 안에서 예약 식별자로 구별한다.
 * HOLD/확정은 이 점유의 상태이며, 만료 판정은 여기서만 한다.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
final class NightOccupancy {

    @EqualsAndHashCode.Include
    private final String reservationId;
    @Getter
    private final int quantity;
    private final Instant expiresAt;
    @Getter
    private boolean confirmed;

    NightOccupancy(String reservationId, int quantity, Instant expiresAt, Instant startedAt) {
        this.reservationId = Objects.requireNonNull(reservationId);
        if (quantity <= 0) {
            throw new IllegalArgumentException("점유 수량은 양수여야 한다: " + quantity);
        }
        this.quantity = quantity;
        this.expiresAt = Objects.requireNonNull(expiresAt);
        Objects.requireNonNull(startedAt);
        if (!expiresAt.isAfter(startedAt)) {
            throw new IllegalArgumentException("만료 시각은 선점 시작 시각보다 뒤여야 한다");
        }
    }

    /** 확정 점유는 expiresAt 에 의존하지 않는다. */
    boolean occupiesAt(Instant at) {
        Objects.requireNonNull(at);
        return confirmed || isHoldValidAt(at);
    }

    boolean isHoldValidAt(Instant at) {
        Objects.requireNonNull(at);
        if (at.equals(expiresAt)) {
            throw new UndecidedPolicyException("now == expiresAt 인 선점의 유효 여부는 미정이다");
        }
        return at.isBefore(expiresAt);
    }

    void confirm() {
        confirmed = true;
    }
}

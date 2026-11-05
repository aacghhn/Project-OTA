package com.example.api.reservation.domain;

import com.example.api.reservation.domain.ReservationRejectedException.Reason;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * (RoomType, Night)로 식별하는 날짜별 재고 애그리거트 루트.
 * 그 날짜의 판매 수량을 책임지며 판매 한도, 판매 중단, 날짜별 점유를 보호한다.
 * 판매 중단과 재개는 한도와 점유를 건드리지 않는다.
 * 예약은 식별자로만 참조한다.
 */
@Getter
public final class NightStock {

    private final RoomType roomType;
    private final Night night;
    private final int salesLimit;
    private boolean saleStopped;
    @Getter(AccessLevel.NONE)
    private final Map<String, NightOccupancy> occupancies = new HashMap<>();

    public NightStock(RoomType roomType, Night night, int salesLimit) {
        this.roomType = Objects.requireNonNull(roomType);
        this.night = Objects.requireNonNull(night);
        if (salesLimit < 0) {
            throw new IllegalArgumentException("판매 한도는 음수일 수 없다: " + salesLimit);
        }
        this.salesLimit = salesLimit;
    }

    public void stopSale() {
        saleStopped = true;
    }

    public void resumeSale() {
        saleStopped = false;
    }

    /** 판매 중단 여부와 무관하게, 같은 기준 시각의 유효한 점유로 계산한다. */
    public long remaining(Instant at) {
        Objects.requireNonNull(at);
        long usage = occupancies.values().stream()
                .filter(occupancy -> occupancy.occupiesAt(at))
                .mapToLong(NightOccupancy::getQuantity)
                .sum();
        return salesLimit - usage;
    }

    /** 새 점유는 이 날짜의 사용량이 판매 한도를 넘게 만들 수 없다. */
    void hold(String reservationId, int quantity, Instant expiresAt, Instant at) {
        NightOccupancy occupancy = createHold(reservationId, quantity, expiresAt, at);
        occupancies.put(reservationId, occupancy);
    }

    /** 새 점유를 허용할 수 있는지 검사한다. 상태는 바꾸지 않는다. */
    void checkHoldable(String reservationId, int quantity, Instant expiresAt, Instant at) {
        createHold(reservationId, quantity, expiresAt, at);
    }

    private NightOccupancy createHold(String reservationId, int quantity, Instant expiresAt, Instant at) {
        NightOccupancy occupancy = new NightOccupancy(reservationId, quantity, expiresAt, at);
        if (occupancies.containsKey(reservationId)) {
            throw new IllegalStateException("이미 점유한 예약이다: " + reservationId);
        }
        if (saleStopped) {
            throw new ReservationRejectedException(Reason.SALE_STOPPED, night.toString());
        }
        if (remaining(at) < quantity) {
            throw new ReservationRejectedException(Reason.INSUFFICIENT_STOCK, night.toString());
        }
        return occupancy;
    }

    /** 만료는 이미 정해진 거절이므로 미정인 판매 중단보다 먼저 판정한다. */
    void confirm(String reservationId, Instant at) {
        checkConfirmable(reservationId, at);
        occupancies.get(reservationId).confirm();
    }

    /** 날짜별 점유를 확정할 수 있는지 검사한다. 상태는 바꾸지 않는다. */
    void checkConfirmable(String reservationId, Instant at) {
        Objects.requireNonNull(reservationId);
        Objects.requireNonNull(at);
        NightOccupancy occupancy = occupancies.get(reservationId);
        if (occupancy == null) {
            throw new ReservationRejectedException(Reason.HOLD_RELEASED, reservationId);
        }
        if (!occupancy.isHoldValidAt(at)) {
            throw new ReservationRejectedException(Reason.HOLD_EXPIRED, reservationId);
        }
        if (saleStopped) {
            throw new UndecidedPolicyException("선점 중 판매 중단된 재고의 확정 가능 여부는 미정이다");
        }
    }

    /** 반복해도 결과가 같다. 확정 점유는 해제할 수 없다. */
    void release(String reservationId) {
        checkReleasable(reservationId);
        occupancies.remove(reservationId);
    }

    /** 날짜별 점유를 해제할 수 있는지 검사한다. 상태는 바꾸지 않는다. */
    void checkReleasable(String reservationId) {
        Objects.requireNonNull(reservationId);
        NightOccupancy occupancy = occupancies.get(reservationId);
        if (occupancy != null && occupancy.isConfirmed()) {
            throw new ReservationRejectedException(Reason.ALREADY_CONFIRMED, "확정 점유는 해제할 수 없다: " + reservationId);
        }
    }
}

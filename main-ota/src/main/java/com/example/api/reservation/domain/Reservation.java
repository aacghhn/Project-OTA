package com.example.api.reservation.domain;

import com.example.api.reservation.domain.ReservationRejectedException.Reason;
import java.time.Instant;
import java.util.Objects;
import lombok.Getter;

/**
 * 선점부터 확정까지 동일한 정체성을 유지하는 예약 애그리거트 루트. 고객과의 약속을 책임진다.
 * 재고 점유와 만료 판정은 NightStock이 맡으며, 둘을 잇는 절차는 ReservationProcess가 맡는다.
 */
@Getter
public final class Reservation {

    private final String id;
    private final RoomType roomType;
    private final StayPeriod period;
    private final int quantity;
    private ReservationStatus status;
    private ReservationTerms terms;
    private Instant confirmedAt;

    private Reservation(String id, RoomType roomType, StayPeriod period, int quantity) {
        this.id = Objects.requireNonNull(id);
        this.roomType = Objects.requireNonNull(roomType);
        this.period = Objects.requireNonNull(period);
        this.quantity = quantity;
        this.status = ReservationStatus.HOLD;
    }

    static Reservation hold(String id, RoomType roomType, StayPeriod period, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("점유 수량은 양수여야 한다: " + quantity);
        }
        return new Reservation(id, roomType, period, quantity);
    }

    /** 확정 가능한 상태인지 검사한다. 상태는 바꾸지 않는다. */
    void checkConfirmable() {
        switch (status) {
            case CONFIRMED -> throw new UndecidedPolicyException("같은 예약의 두번째 확정 응답은 미정이다");
            case RELEASED -> throw new ReservationRejectedException(Reason.HOLD_RELEASED, id);
            case HOLD -> {
            }
        }
    }

    /** 모든 NightStock의 날짜별 확정을 마친 뒤에 호출한다. 만료는 다시 판정하지 않는다. */
    void confirm(ReservationTerms terms, Instant at) {
        Objects.requireNonNull(terms);
        Objects.requireNonNull(at);
        checkConfirmable();
        this.terms = terms;
        this.confirmedAt = at;
        this.status = ReservationStatus.CONFIRMED;
    }

    /** 반복해도 결과가 같다. 확정 예약은 해제할 수 없다. */
    void release() {
        checkReleasable();
        status = ReservationStatus.RELEASED;
    }

    /** 해제 가능한 상태인지 검사한다. 상태는 바꾸지 않는다. */
    void checkReleasable() {
        if (status == ReservationStatus.CONFIRMED) {
            throw new ReservationRejectedException(Reason.ALREADY_CONFIRMED, "확정 점유는 해제할 수 없다: " + id);
        }
    }
}

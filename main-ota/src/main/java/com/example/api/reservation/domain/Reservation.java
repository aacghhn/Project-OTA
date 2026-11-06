package com.example.api.reservation.domain;

import com.example.api.reservation.domain.ReservationRejectedException.Reason;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.Instant;
import java.util.*;

/**
 * 선점부터 확정까지 같은 정체성을 유지하는 예약 애그리거트 루트.
 * 기간 전체의 점유는 하나의 수량, 만료 시각, 상태로 표현한다.
 */
@Getter
public final class Reservation {

    private final String id;
    private final String bookerId;
    private final RoomTypeRef roomType;
    private final StayPeriod period;
    private final int quantity;
    private final Instant heldAt;
    private final Instant expiresAt;
    private final ReservationTerms terms;
    private final GuestInfo guest;
    private final String specialRequest;
    private final List<ConsentRecord> consents;
    private ReservationStatus status;
    @Getter(AccessLevel.NONE)
    private Instant confirmedAt;

    private Reservation(
            String id,
            String bookerId,
            RoomTypeRef roomType,
            StayPeriod period,
            int quantity,
            Instant heldAt,
            Instant expiresAt,
            ReservationTerms terms,
            GuestInfo guest,
            String specialRequest,
            List<ConsentRecord> consents
    ) {
        this.id = id;
        this.bookerId = bookerId;
        this.roomType = roomType;
        this.period = period;
        this.quantity = quantity;
        this.heldAt = heldAt;
        this.expiresAt = expiresAt;
        this.terms = terms;
        this.guest = guest;
        this.specialRequest = specialRequest;
        this.consents = List.copyOf(consents);
        this.status = ReservationStatus.HOLD;
    }

    /**
     * 모든 숙박일의 판매 조건과 잔여 수량을 검사한 뒤 예약을 생성한다.
     * 예약 조건은 결제 요청 전인 이 시점에 보관한다.
     * 호출자는 roomType과 at을 기준으로 조회한 날짜별 재고를 전달한다.
     * 같은 예약의 중복 생성과 동시 선점 방지는 조회·저장을 조율하는 경계에서 보장한다.
     */
    public static Reservation hold(
            String id,
            String bookerId,
            RoomTypeRef roomType,
            StayPeriod period,
            int quantity,
            Instant expiresAt,
            ReservationTerms terms,
            GuestInfo guest,
            String specialRequest,
            List<ConsentRecord> consents,
            Collection<NightAvailability> availability,
            Instant at
    ) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("점유 수량은 양수여야 한다: " + quantity);
        }
        if (!expiresAt.isAfter(at)) {
            throw new IllegalArgumentException("만료 시각은 선점 시작 시각보다 뒤여야 한다");
        }
        Map<Night, NightAvailability> nightsByDate = indexAvailability(availability);
        for (Night night : period.nights()) {
            if (!nightsByDate.containsKey(night)) {
                throw new ReservationRejectedException(Reason.STOCK_UNDEFINED, night.toString());
            }
            NightAvailability stock = nightsByDate.get(night);
            if (stock.saleStopped()) {
                throw new ReservationRejectedException(Reason.SALE_STOPPED, night.toString());
            }
            if (stock.remaining() < quantity) {
                throw new ReservationRejectedException(Reason.INSUFFICIENT_STOCK, night.toString());
            }
        }
        return new Reservation(
                id, bookerId, roomType, period, quantity, at, expiresAt,
                terms, guest, specialRequest, consents
        );
    }

    /**
     * 호출자는 해당 예약의 결제 성공 또는 후불 확정 조건 충족을 먼저 확인한다.
     * 호출자는 이 예약의 객실 타입과 at을 기준으로 조회한 현재 판매 조건을 전달한다.
     * 이미 선점한 수량을 다시 확보하지 않는다.
     */
    public void confirm(Collection<NightAvailability> availability, Instant at) {
        switch (status) {
            case CONFIRMED -> throw new UndecidedPolicyException("같은 예약의 두번째 확정 응답은 미정이다");
            case RELEASED -> throw new ReservationRejectedException(Reason.HOLD_RELEASED, id);
            case HOLD -> {
            }
        }
        // 만료는 이미 정해진 거절이므로 미정인 판매 중단보다 먼저 판정한다.
        if (!isHoldValidAt(at)) {
            throw new ReservationRejectedException(Reason.HOLD_EXPIRED, id);
        }
        Map<Night, NightAvailability> nightsByDate = indexAvailability(availability);
        List<Night> nights = period.nights();
        for (Night night : nights) {
            if (!nightsByDate.containsKey(night)) {
                throw new IllegalArgumentException("대상 기간의 재고가 전달되지 않았다: " + night);
            }
        }
        for (Night night : nights) {
            if (nightsByDate.get(night).saleStopped()) {
                throw new UndecidedPolicyException("선점 중 판매 중단된 재고의 확정 가능 여부는 미정이다");
            }
        }
        confirmedAt = at;
        status = ReservationStatus.CONFIRMED;
    }

    /**
     * 반복해도 결과가 같다. 확정 예약의 취소는 선점 해제와 별도의 업무다.
     */
    public void release() {
        if (status == ReservationStatus.CONFIRMED) {
            throw new ReservationRejectedException(Reason.ALREADY_CONFIRMED, "확정 점유는 해제할 수 없다: " + id);
        }
        status = ReservationStatus.RELEASED;
    }

    /**
     * 해당 객실 타입과 숙박 기간의 점유량 조회에 사용하는 판정.
     * 확정 예약은 선점 만료와 무관하게 점유하며, 만료만으로 저장 상태를 변경하지 않는다.
     */
    public boolean occupiesAt(Instant at) {
        return switch (status) {
            case HOLD -> isHoldValidAt(at);
            case CONFIRMED -> true;
            case RELEASED -> false;
        };
    }

    /**
     * 아직 확정되지 않은 예약에는 확정 시각이 없다.
     */
    public Optional<Instant> getConfirmedAt() {
        return Optional.ofNullable(confirmedAt);
    }

    private static Map<Night, NightAvailability> indexAvailability(Collection<NightAvailability> availability) {
        Map<Night, NightAvailability> nightsByDate = new HashMap<>();
        for (NightAvailability stock : availability) {
            if (nightsByDate.containsKey(stock.night())) {
                throw new IllegalArgumentException("같은 객실 타입과 날짜의 재고가 중복되었다: " + stock.night());
            }
            nightsByDate.put(stock.night(), stock);
        }
        return nightsByDate;
    }

    private boolean isHoldValidAt(Instant at) {
        if (at.equals(expiresAt)) {
            throw new UndecidedPolicyException("now == expiresAt 인 선점의 유효 여부는 미정이다");
        }
        return at.isBefore(expiresAt);
    }
}

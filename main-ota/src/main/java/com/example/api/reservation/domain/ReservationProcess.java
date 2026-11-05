package com.example.api.reservation.domain;

import com.example.api.reservation.domain.ReservationRejectedException.Reason;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 예약과 날짜별 점유의 선점, 확정, 해제 규칙을 조율하는 무상태 도메인 서비스.
 * 대상 전체의 조건을 검사한 뒤 상태를 변경한다.
 */
public final class ReservationProcess {

    /** 모든 Night의 재고를 검사한 뒤 같은 수량으로 선점한다. */
    public Reservation placeHold(
            String id,
            RoomType roomType,
            StayPeriod period,
            int quantity,
            Instant expiresAt,
            Collection<NightStock> stocks,
            Instant at
    ) {
        Reservation reservation = Reservation.hold(id, roomType, period, quantity);
        Map<Night, NightStock> stocksByNight = indexStocks(roomType, stocks);
        List<NightStock> targets = new ArrayList<>();
        for (Night night : period.nights()) {
            NightStock stock = stocksByNight.get(night);
            if (stock == null) {
                throw new ReservationRejectedException(Reason.STOCK_UNDEFINED, night.toString());
            }
            stock.checkHoldable(id, quantity, expiresAt, at);
            targets.add(stock);
        }
        targets.forEach(stock -> stock.hold(id, quantity, expiresAt, at));
        return reservation;
    }

    /**
     * 예약과 모든 날짜별 점유의 확정 조건을 검사한다.
     * 모든 날짜별 점유를 확정한 뒤 예약을 확정한다.
     */
    public void confirm(Reservation reservation, ReservationTerms terms, Collection<NightStock> stocks, Instant at) {
        Objects.requireNonNull(reservation);
        Objects.requireNonNull(terms);
        Objects.requireNonNull(at);
        reservation.checkConfirmable();
        String id = reservation.getId();
        List<NightStock> targets = occupiedStocks(reservation, stocks);
        targets.forEach(stock -> stock.checkConfirmable(id, at));
        targets.forEach(stock -> stock.confirm(id, at));
        reservation.confirm(terms, at);
    }

    /** 예약과 모든 날짜별 점유의 해제 조건을 검사한 뒤 해제한다. */
    public void release(Reservation reservation, Collection<NightStock> stocks) {
        Objects.requireNonNull(reservation);
        reservation.checkReleasable();
        List<NightStock> targets = occupiedStocks(reservation, stocks);
        String id = reservation.getId();
        targets.forEach(stock -> stock.checkReleasable(id));
        targets.forEach(stock -> stock.release(id));
        reservation.release();
    }

    /** 기존 점유가 있는 재고는 정의되어 있어야 하므로, 없으면 거절이 아니라 호출 오류다. */
    private List<NightStock> occupiedStocks(Reservation reservation, Collection<NightStock> stocks) {
        Map<Night, NightStock> stocksByNight = indexStocks(reservation.getRoomType(), stocks);
        List<NightStock> targets = new ArrayList<>();
        for (Night night : reservation.getPeriod().nights()) {
            NightStock stock = stocksByNight.get(night);
            if (stock == null) {
                throw new IllegalArgumentException("대상 기간의 재고가 전달되지 않았다: " + night);
            }
            targets.add(stock);
        }
        return targets;
    }

    private Map<Night, NightStock> indexStocks(RoomType roomType, Collection<NightStock> stocks) {
        Objects.requireNonNull(stocks);
        Map<Night, NightStock> stocksByNight = new HashMap<>();
        for (NightStock stock : stocks) {
            if (stock.getRoomType().equals(roomType)
                    && stocksByNight.putIfAbsent(stock.getNight(), stock) != null) {
                throw new IllegalArgumentException("같은 객실 타입과 날짜의 재고가 중복되었다: " + stock.getNight());
            }
        }
        return stocksByNight;
    }
}

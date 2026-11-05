package com.example.api.reservation.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/** 체크인 Night부터 체크아웃 날짜 직전 Night까지의 숙박 기간. */
public record StayPeriod(LocalDate checkin, LocalDate checkout) {

    public StayPeriod {
        if (!new Night(checkin).isBefore(new Night(checkout))) {
            throw new IllegalArgumentException("체크인은 체크아웃보다 앞서야 한다");
        }
    }

    public List<Night> nights() {
        Night endExclusive = new Night(checkout);
        return Stream.iterate(new Night(checkin), night -> night.isBefore(endExclusive), Night::next)
                .toList();
    }

    public boolean contains(Night night) {
        return !night.isBefore(new Night(checkin)) && night.isBefore(new Night(checkout));
    }
}

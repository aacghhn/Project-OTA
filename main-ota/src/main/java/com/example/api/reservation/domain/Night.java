package com.example.api.reservation.domain;

import java.time.LocalDate;
import java.util.Objects;

/** 숙소 현지 날짜로 식별하는 재고 점유 단위인 1박. */
public record Night(LocalDate date) implements Comparable<Night> {

    public Night {
        Objects.requireNonNull(date);
    }

    public Night next() {
        return new Night(date.plusDays(1));
    }

    public boolean isBefore(Night other) {
        return compareTo(other) < 0;
    }

    @Override
    public int compareTo(Night other) {
        return date.compareTo(other.date);
    }
}

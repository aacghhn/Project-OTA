package com.example.api.reservation.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * 통화가 다른 금액은 합산, 차감, 비교하지 않는다. 환산은 여기서 하지 않는다.
 * */
public record Money(BigDecimal amount, Currency currency) implements Comparable<Money> {

    public Money {
        amount = Objects.requireNonNull(amount).stripTrailingZeros();
        Objects.requireNonNull(currency);
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return amount.compareTo(other.amount);
    }

    private void requireSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("통화가 다르다: " + currency + ", " + other.currency);
        }
    }
}

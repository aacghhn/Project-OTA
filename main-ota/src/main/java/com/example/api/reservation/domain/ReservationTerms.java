package com.example.api.reservation.domain;

import java.util.Objects;

/**
 * 확정 시점에 복사해 둔 조건. 상품 설정을 참조하지 않아 이후 변경의 영향을 받지 않는다.
 * 총액의 항목과 산식은 해당 기능에서 정한다.
 * */
public record ReservationTerms(Money total, String cancellationPolicy, PaymentMethod paymentMethod) {

    public ReservationTerms {
        Objects.requireNonNull(total);
        Objects.requireNonNull(cancellationPolicy);
        Objects.requireNonNull(paymentMethod);
    }
}

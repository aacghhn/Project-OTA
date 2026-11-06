package com.example.api.reservation.domain;

import java.util.List;

/**
 * 선점 시 고객이 선택한 조건의 불변 스냅샷. 이후 상품 설정 변경에 영향을 받지 않는다.
 * 인원별 가격, 혜택, 할인과 총액은 상품·요금 쪽에서 결정한 결과를 전달받는다.
 */
public record ReservationTerms(
        Money total,
        String cancellationPolicy,
        PaymentTiming paymentTiming,
        int adults,
        List<String> includedBenefits,
        List<String> appliedDiscounts
) {

    public ReservationTerms {
        if (adults < 0) {
            throw new IllegalArgumentException("성인 수는 음수일 수 없다: " + adults);
        }
        includedBenefits = List.copyOf(includedBenefits);
        appliedDiscounts = List.copyOf(appliedDiscounts);
    }
}

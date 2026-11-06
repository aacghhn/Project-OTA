package com.example.api.reservation.domain;

/**
 * 예약 판단에 전달하는 하루치 재고 조회 결과. 자체 상태 전이나 저장 책임은 없다.
 * occupiedQuantity는 같은 기준 시각에 점유하는 예약들의 객실 수량 합계다.
 * 판매 중단·재개는 판매 설정만 바꾸며 판매 한도와 예약의 점유를 건드리지 않는다.
 */
public record NightAvailability(Night night, int salesLimit, long occupiedQuantity, boolean saleStopped) {

    public NightAvailability {
        if (salesLimit < 0) {
            throw new IllegalArgumentException("판매 한도는 음수일 수 없다: " + salesLimit);
        }
        if (occupiedQuantity < 0) {
            throw new IllegalArgumentException("점유 수량 합계는 음수일 수 없다: " + occupiedQuantity);
        }
    }

    /**
     * 판매 중단 여부와 무관하게 계산한다. 부족한 수량을 0으로 보정하지 않는다.
     */
    public long remaining() {
        return salesLimit - occupiedQuantity;
    }
}

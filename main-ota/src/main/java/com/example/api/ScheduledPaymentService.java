package com.example.api;

import com.example.api.dto.response.ScheduledPaymentResponse;

/**
 * 나중 결제 예약의 예정 청구를 조회하고 시스템 시각 도래 및 외부 결과 확인을 처리하는 계약이다.
 * 고객용 HTTP API는 조회만 제공하며 자동 청구·결과 확인 메서드는 신뢰된 시스템 작업만 호출한다.
 * 구현체, 스케줄러, 외부 결제 연동은 이 인터페이스에 포함하지 않는다.
 */
public interface ScheduledPaymentService {
    /**
     * 인증된 고객 자신의 예약인지 검증한 후 예약 당시 예정 금액·시각·마스킹 수단과 현재 결과를 반환한다.
     * 예약 확정 이후 가격이나 상품 설정이 바뀌어도 예정 청구 약속을 재계산하지 않는다.
     * 예약이 취소되었어도 이미 준비된 예정 청구와 최초 취소 결과는 조회할 수 있다.
     * 성공 수금액은 성공이 확인된 시도만 합산하며 처리 중·불명 결과를 성공 또는 실패로 단정하지 않는다.
     * 조회는 새 결제, 결제수단 변경, 환불, 예약 취소를 실행하지 않는다.
     *
     * @param customerId 인증 계층이 확인한 고객 계정 식별자. 요청 본문에서 받지 않는다.
     * @param reservationId 가정: 예약 식별자는 1~64자 opaque 문자열로 받아 저장소 식별 형식을 노출하지 않는다.
     * @return 동일 관측 시점의 청구·예약·남은 의무. 예약이 CANCELLED이면 cancellationReason,
     * cancellationCancelledAt, cancellationCustomerCostAmount, cancellationCustomerCostCurrency를 모두 제공한다.
     * 취소되지 않은 예약은 이 네 값을 모두 null로 반환한다. 취소 비용 의무와 환불 상세는 별도 금전 조회에 남긴다.
     * @throws InvalidRequestException 예약 식별자가 비었거나 64자를 초과한 경우
     * @throws AccessDeniedException 인증된 계정이 고객 역할이 아닌 경우
     * @throws ResourceNotFoundException 예약이 없거나 본인 소유가 아니거나 해당 예약에 예정 청구가 존재하지 않는 경우. 타인 예약의 존재 여부를 구분해 노출하지 않는다
     * @throws TemporarilyUnavailableException 조회용 저장소가 일시적으로 응답하지 않아 현재 상태를 제공할 수 없는 경우
     */
    ScheduledPaymentResponse getScheduledPayment(String customerId, String reservationId);

    /**
     * 신뢰된 시스템 작업이 예정 청구 하나의 기한 도래 여부를 확인하고 최초 논리 결제 시도를 시작한다.
     * 서버의 현재 시각이 예약 당시 예정 시각 이상이고 예약이 CONFIRMED이며 아직 시작하지 않은 청구만 실행한다.
     * 예정 시각은 체크인 날짜에서 결제용 설정 일수를 뺀 날의 Asia/Seoul 00:00이며 취소 기한 설정과 독립된다.
     * 취소된 예약은 신규 숙박대금 수금을 시작하지 않는다. 예약 취소와 실행이 경쟁하면 동일 예약 단위에서
     * 청구 시작 가능 여부를 직렬화하여 취소 이후 새 시도를 만들지 않는다.
     * 가정: chargeId를 내부 멱등성 단위로 사용하여 중복 스케줄 호출이 동일한 논리 시도와 외부 요청 키를 재사용하게 한다.
     * 이미 시작한 청구는 새 시도를 만들지 않는다. 외부 호출 시간 초과는 UNKNOWN으로 남겨 기존 시도를 확인한다.
     *
     * @param chargeId 가정: 예정 청구의 1~64자 opaque 식별자로, 예약 준비 때 생성한 청구를 지정한다.
     * @throws InvalidRequestException 청구 식별자가 비었거나 64자를 초과한 경우
     * @throws ResourceNotFoundException 예정 청구가 없는 경우
     * @throws TemporarilyUnavailableException 외부 요청 시작 전 저장소 장애로 원자적인 시도 생성을 보장할 수 없는 경우
     */
    void processDueScheduledPayment(String chargeId);

    /**
     * 신뢰된 시스템 작업이 이미 생성된 논리 시도의 외부 결과를 재확인하고 확인된 결과만 반영한다.
     * 가정: chargeId에 연결된 기존 시도와 외부 요청 키로만 조회하며 재확인을 새 결제 시도로 취급하지 않는다.
     * 결과 불명은 예약을 유지하며 확인 작업을 남긴다. 성공이 확인되면 같은 성공 결과의 반복 통지를 중복 합산하지 않는다.
     * 실패가 확정되고 예약이 아직 확정 상태이면 유예나 자동 신규 결제 재시도 없이 전체 취소하고 점유를 해제하며
     * 고객 취소 비용을 0원, 취소 사유를 SCHEDULED_PAYMENT_FAILED로 보존한다.
     * 이미 취소된 예약에는 최초 사유·판정 시각·비용을 유지한다. 취소 전 시작된 결제가 나중에 성공하면 예약을
     * 부활시키지 않는다. 성공 수금액에서 성공 환불액을 뺀 순수금액이 최초 취소 비용보다 크면 차액을 환불 대상으로,
     * 작으면 부족액을 미수 취소 비용 의무로 남긴다. 이미 진행 중이거나 결과 불명인 금전 작업은 먼저 확인해 중복 처리하지 않는다.
     * 환불 실패나 결과 불명 때문에 예약 취소와 점유 해제를 되돌리지 않는다.
     * 외부 결과 자체가 불명인 것은 조회 장애 예외가 아니라 PaymentStatus.UNKNOWN으로 보존할 업무 상태다.
     *
     * @param chargeId 가정: 기존 예정 청구의 1~64자 opaque 식별자로, 확인 대상 시도를 찾는다.
     * @throws InvalidRequestException 청구 식별자가 비었거나 64자를 초과한 경우
     * @throws ResourceNotFoundException 예정 청구가 없는 경우
     * @throws TemporarilyUnavailableException 확인 결과를 안전하게 기록할 수 없는 저장소 장애가 발생한 경우
     */
    void reconcileScheduledPayment(String chargeId);
}

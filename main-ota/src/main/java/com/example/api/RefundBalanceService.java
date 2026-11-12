package com.example.api;

import com.example.api.dto.response.RefundBalanceResponse;

/**
 * 예약 취소·선점 만료·해제 뒤 금전 결과를 읽고 시스템의 잔액 대조·복구 조건을 정의한다.
 * 고객용 HTTP 조회는 상태와 금액을 읽기만 하며 시스템 후속 작업을 시작하지 않는다.
 * 구현체, 저장소·외부 결제·환불 연동과 작업 스케줄러는 포함하지 않는다.
 */
public interface RefundBalanceService {
    /**
     * 고객 역할과 예약 소유권을 확인하고 예약 종료 결과·최초 고객 비용·청구·결제·환불을 같은 관측 시점으로 읽는다.
     * 가정: 취소·선점 만료·해제 또는 환불·취소 비용 처리 대상이 아직 없는 예약은 409로 거절하여 빈 결과를 완료로 보이지 않는다.
     * 존재하지 않는 예약과 타인 소유 예약은 모두 404이며 고객 역할이 아니면 403이다.
     * 전체 취소에는 최초 사유·판정 시각·고객 비용을 보존한다. 선점 만료·해제에는 cancellationReason, cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency를 모두 null로 제공하고 targetCustomerCostAmount를 0원으로 둔다.
     * 성공이 확인된 서로 다른 논리 결제 합계 P는 financialImpactSuccessfulCollectionAmount, 성공 환불 합계 R은 financialImpactSuccessfulRefundAmount, 확정 고객 부담 목표액 F는 targetCustomerCostAmount로 구분한다.
     * confirmedNetCollectionAmount=P-R, financialImpactRefundOutstandingAmount=max(P-R-F,0), financialImpactCustomerOutstandingAmount=max(F-(P-R),0)이다. 각 통화 필드는 KRW이다.
     * PROCESSING/UNKNOWN인 결제·환불 대상액을 P나 R에 더하지 않으며 financialImpactAmountsProvisional=true와 VERIFYING을 제공한다.
     * 환불 잔액 전액이 PROCESSING/UNKNOWN인 기존 환불에 이미 배정되어 있고 다른 미완료 작업이 없으면 financialImpactRemainingWork에는 WAIT_REFUND_RESULT만 제공한다.
     * 이 경우 잔액이 남아 있다는 이유만으로 ISSUE_OR_RECOVER_REFUND를 추가하지 않으며 기존 결과가 확정되기 전 새 환불·재시도를 만들지 않는다.
     * 진행·불명 작업이 없지만 환불 잔액·미수 비용 또는 유효한 미완료 작업이 있으면 ACTION_REQUIRED다.
     * P-R=F이고 유효한 미완료 작업이 없을 때만 COMPLETED다. 성공한 후속 재시도로 해결된 선행 실패 기록은 그대로 남기되 미완료 작업으로 세지 않는다.
     * 결제 실패는 비용 의무 소멸을 뜻하지 않는다. 환불 실패·결과 불명은 예약 취소나 점유 해제를 되돌리지 않는다.
     * financialImpactPayments와 financialImpactRefunds는 각각 charges[].attempts[]와 refunds[]의 요약 투영이며 동일 observedAt의 식별자·상태·금액·통화가 일치해야 한다. 아직 시도가 없는 청구의 요약은 attemptId=null이며 해당 charges[].attempts는 빈 배열이다. 상세 시도·재시도·사건 시각은 charges/refunds에 유지한다.
     * 청구와 그 시도, 원결제와 환불, 실패와 후속 재시도 관계를 보존한다. refunds[].originalPaymentId는 charges[].attempts[].attemptId 중 실제 성공 시도를 가리킨다.
     * 결제·환불의 확인된 성공과 실패는 사건 시각으로 표시하며 시간 초과만으로 확정하지 않는다.
     * 외부 환불 성공 시각은 금융기관 입금 확인 시각이 아니고 조회 응답은 입금 예정일·환불 소요 일수를 보장하지 않는다.
     * 조회는 새 환불·청구·재시도·예약 취소를 생성하지 않는다. 반복 조회해도 성공 합계나 처리 횟수가 증가하지 않는다.
     *
     * @param customerId 인증 계층이 제공한 고객 계정 식별자이며 요청 본문에서 받지 않는다.
     * @param reservationId 가정: 저장소 표현을 숨기기 위해 1~64자 opaque 문자열로 선택한 예약 식별자
     * @return 동일 관측 시점의 확인된 금전 사실, 최초 부담 목표액, 미완료 작업과 잔여 의무
     * @throws InvalidRequestException 예약 식별자가 비었거나 64자를 초과한 경우
     * @throws AccessDeniedException 인증된 호출자가 고객 역할이 아닌 경우
     * @throws ResourceNotFoundException 예약이 없거나 본인 소유 범위에 없는 경우
     * @throws ConflictException 아직 예약 종료 또는 후속 환불·취소 비용 처리 대상이 아닌 경우
     * @throws TemporarilyUnavailableException 금전 사실과 예약 결과를 동일 기준으로 조회할 수 없는 저장소 장애가 발생한 경우
     */
    RefundBalanceResponse getRefundBalance(String customerId, String reservationId);

    /**
     * 신뢰된 시스템 작업이 기존 결제·환불 결과와 최초 고객 부담 목표액을 대조하여 남은 작업을 결정한다.
     * 고객 HTTP 요청에서 호출하지 않는다. 예약이 취소되거나 선점이 끝나면 새 숙박대금 수금을 중단하되 이미 시작한 외부 작업은 계속 확인한다.
     * PENDING인 환불 또는 실행 대기 결제와 PROCESSING/UNKNOWN인 기존 작업이 있으면 해당 작업을 진행·조회하며 추가 금전 작업을 만들지 않는다.
     * 확인이 끝난 성공 결제 합계 P, 성공 환불 합계 R과 최초 고객 부담 목표액 F를 사용한다.
     * P-R>F이면 차액을 성공한 원결제에 귀속시켜 환불하고, P-R<F이면 부족한 취소 비용 의무를 유지하며 아직 청구가 없을 때 목적별 청구를 만든다.
     * P-R=F이면 추가 청구·환불이 없다. 이미 만들어진 취소 비용 청구를 중복 생성하거나 재시도 때 청구 금액을 변경하지 않는다.
     * 가정: 예약 식별자를 대조 직렬화 단위로 선택하고 청구 목적·원결제·논리 작업 식별자를 중복 방지 기준으로 사용한다.
     * 같은 결과 통지나 중복 대조 호출은 P·R·점유 해제 횟수를 늘리지 않는다. 한 원결제에 성공·진행·불명 환불이 중복 배정되지 않게 환불 한도를 검사한다.
     * 환불 실패 확정 후 재시도는 선행 실패를 보존한 새 환불 작업으로 연결하고 같은 선행 작업의 후속 재시도는 하나만 허용한다.
     * 후속 환불은 선행 작업의 원결제·금액·사유를 유지한다. 결과 불명인 작업에는 새 재시도를 만들지 않는다.
     * 취소 비용 수금 실패는 미수 의무를 유지하며 실패 확정 이후에만 같은 청구에 새 시도를 허용한다.
     * 예정 숙박대금의 실패 확정에는 유예나 자동 신규 결제 재시도 없이 예약 전체 취소와 고객 비용 0을 적용하고, 이미 다른 사유로 취소됐다면 최초 비용을 유지한다.
     * 선점 종료 후 뒤늦은 결제 성공은 예약을 부활시키지 않고 고객 부담 목표액 0에 맞춰 반환한다.
     * 환불 실패나 결과 불명은 취소·점유 해제를 되돌리지 않으며 다음 확인 시각과 안전한 오류 사유를 남긴다.
     * 외부 호출 시간 초과는 UNKNOWN으로 유지하고 같은 기존 작업을 확인한다. 외부 호출을 저장소 롤백으로 되돌릴 수 있다고 가정하지 않는다.
     *
     * @param reservationId 가정: 기존 작업을 찾는 1~64자 opaque 예약 식별자
     * @throws InvalidRequestException 예약 식별자가 비었거나 64자를 초과한 경우
     * @throws ResourceNotFoundException 대조할 예약이 없는 경우
     * @throws ConflictException 예약이 아직 금전 후속 처리 대상이 아닌 경우
     * @throws TemporarilyUnavailableException 중복 방지와 환불 한도를 안전하게 기록할 수 없는 저장소 장애가 발생한 경우
     */
    void reconcileFinancialOutcome(String reservationId);
}

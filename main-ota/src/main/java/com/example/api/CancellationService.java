package com.example.api;

import com.example.api.dto.request.CancellationRequest;
import com.example.api.dto.response.CancellationPreviewResponse;
import com.example.api.dto.response.CancellationResultResponse;

/**
 * 고객의 예약 전체 취소와 비용 동의, 최초 결과 보존에 관한 서비스 계약이다.
 * 예약 취소와 모든 숙박일의 점유 해제는 함께 처리하며 외부 환불 성공을 기다리지 않는다.
 * 서비스 구현체, DB 트랜잭션과 결제·환불 연동 구현은 제공하지 않는다.
 */
public interface CancellationService {
    /**
     * 고객 역할과 예약 소유권을 확인하고 예약 당시 약속을 기준으로 전체 취소 비용을 계산한다.
     * 무료 취소 기한은 체크인 날짜에서 설정 일수 N을 뺀 날의 Asia/Seoul 00:00이다.
     * 서버의 계산 시각이 기한보다 작으면 비용은 0원이며, 같거나 이후에는 예약 총액 전액이다.
     * 환불 불가 정책의 고객 취소 비용도 총액 전액이며 미결제라고 비용을 0원으로 만들지 않는다.
     * 금액은 필수 요금을 포함한 KRW 정수 원이다. 가격·혜택·취소 정책의 현재 설정으로 약속을 바꾸지 않는다.
     * 가정: 확정 예약에는 고객·예약·예상 비용·발급 및 만료 시각을 결합한 서명 토큰을 발급한다.
     * 유효기간은 5분이며 토큰 만료는 무료 취소 기한을 연장하지 않는다. 읽기 요청은 예약·금전 상태를 변경하지 않는다.
     * 이미 취소된 예약은 최초 비용·사유·판정 시각과 현재 금전 상태를 제공하며 canCancel=false, 토큰과 만료 시각은 null이다.
     * originalCancellationReason이 null이면 최초 취소가 아직 없으므로 originalCancellationReasonDetail, originalCancellationDecidedAt, originalCancellationCustomerFeeAmount, originalCancellationCustomerFeeCurrency도 모두 null이다. originalCancellationReason이 있으면 최초 취소 결과가 존재하며 사유·판정 시각·비용 금액·KRW 통화는 null일 수 없고, 자유문장 사유만 기록되지 않은 경우 null일 수 있다.
     * P=financialImpactSuccessfulCollectionAmount, R=financialImpactSuccessfulRefundAmount, F=customerFeeAmount로 두고
     * financialImpactRefundOutstandingAmount=max(P-R-F,0), financialImpactCustomerOutstandingAmount=max(F-(P-R),0)을 계산한다.
     * 진행 중 또는 불명인 결제·환불이 있으면 financialImpactAmountsProvisional=true로 표시하고 최종 지급액처럼 안내하지 않는다.
     * 취소 전의 환불 잔액과 financialImpactRemainingWork는 취소 실행 시 예상이며 새 금전 작업을 생성하지 않는다.
     *
     * @param customerId 인증 계층이 확인한 고객 계정 식별자이며 요청 본문에서 받지 않는다.
     * @param reservationId 가정: 예약을 식별하는 1~64자 opaque 문자열이다.
     * @return 예약 당시 약속, 계산 기준 시각, 예상 또는 최초 비용, 현재 확인 가능한 금전 영향
     * @throws InvalidRequestException 예약 식별자의 형식·길이가 유효하지 않은 경우
     * @throws AccessDeniedException 호출 주체가 고객 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 예약이 없거나 고객 소유 범위에 없는 경우
     * @throws ConflictException 예약이 CONFIRMED 또는 CANCELLED 상태가 아닌 경우
     * @throws TemporarilyUnavailableException 현재 예약·금전 상태를 일관되게 조회할 수 없는 경우
     */
    CancellationPreviewResponse getCancellationPreview(String customerId, String reservationId);

    /**
     * 고객 역할·소유권과 confirmEntireReservation=true를 확인한 후 전체 취소 요청을 처리한다.
     * 가정: Idempotency-Key는 필수 1~128자이고 고객 계정·작업·키가 같은 요청은 기록된 최초 응답을 재생한다.
     * 요청 지문에는 경로의 예약 식별자와 본문을 포함한다. 선택 필드의 누락과 null은 같은 값으로 정규화하며
     * 그 밖의 토큰·사유·동의 값은 원문 그대로 비교한다. 같은 키의 다른 요청은 이미 취소되었어도 409이다.
     * 멱등 요청 재생은 토큰 만료 전에 성공한 요청의 네트워크 재전송을 실패로 바꾸지 않는다.
     * 새 키로 이미 취소된 예약을 요청하면 최초 사유·자유문장·판정 시각·비용을 그대로 반환하며
     * 새 사유를 적용하거나 비용을 다시 계산하지 않는다. 이 경우 토큰 누락·만료·비용 재검증은 적용하지 않는다.
     * 첫 취소는 CONFIRMED 상태에만 허용한다. 서명 토큰이 고객·예약과 일치하고 현재 시각이 만료 시각 미만인지 확인한다.
     * 예약 상태 잠금과 같은 판단 범위에서 서버 시각으로 비용을 다시 계산한다. 토큰에 담긴 동의 비용과 다르면
     * 409 CANCELLATION_COST_CHANGED로 거절하며 예약을 유지한다. 고객은 최신 예상 비용을 조회해 다시 동의해야 한다.
     * 최초 취소 사유는 CUSTOMER_REQUEST, 선택 자유문장 사유는 1~500자이며 공백만인 값을 허용하지 않는다.
     * 요청의 취소 비용·임의 환불액·객실 일부 선택·판정 시각 입력은 받지 않는다.
     * 첫 취소는 최초 결과 보존, CANCELLED 전이, 전체 숙박일 점유 해제와 후속 금전 작업의 중복 방지를 함께 보장한다.
     * 예정 결제 시작과 경합할 때 동일 예약의 처리 순서를 정해 취소 이후 신규 숙박대금 수금을 시작하지 않는다.
     * 취소 전에 이미 시작한 결제는 기존 결과를 확인한다. 뒤늦게 성공해도 예약을 부활시키지 않고 최초 비용에 맞게 정산한다.
     * P=financialImpactSuccessfulCollectionAmount, R=financialImpactSuccessfulRefundAmount, F=cancellationCustomerFeeAmount로 두고
     * financialImpactRefundOutstandingAmount=max(P-R-F,0), financialImpactCustomerOutstandingAmount=max(F-(P-R),0)을 표시한다.
     * 진행 또는 결과 불명인 기존 작업을 확인하기 전에 새 금전 작업을 만들어 중복 수금·환불하지 않는다.
     * 미결제 예약의 유료 취소는 미수 비용을 남기며 수금 실패와 의무 소멸을 혼동하지 않는다.
     * 환불 실패·결과 불명에도 취소와 점유 해제를 유지한다. HTTP 성공은 로컬 전체 취소 완료이며 환불 성공이 아니다.
     * 가정: 같은 멱등 요청은 기록된 응답 스냅샷을 반환하므로 최신 금전 상태는 결과 조회로 확인한다.
     *
     * @param customerId 인증된 고객 계정 식별자
     * @param reservationId 가정: 1~64자 opaque 예약 식별자
     * @param idempotencyKey 가정: 네트워크 재전송을 식별하는 필수 1~128자 문자열
     * @param request 명시적 전체 취소 동의, 첫 취소용 토큰과 선택 사유
     * @return 보존된 최초 취소 결과와 처리 당시 확인된 금전 후속 상태
     * @throws InvalidRequestException 입력 누락·형식·길이·동의가 유효하지 않거나 첫 취소의 토큰이 없거나 서명·대상이 일치하지 않는 경우
     * @throws AccessDeniedException 호출 주체가 고객 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 예약이 없거나 고객 소유 범위에 없는 경우
     * @throws ConflictException 취소할 상태가 아니거나 토큰이 만료되었거나 비용이 바뀌었거나 멱등 키의 이전 요청과 다른 경우
     * @throws TemporarilyUnavailableException 안전한 로컬 처리를 확인할 수 없는 저장소 장애가 발생한 경우. 동일 멱등 키를 유지해 재조회·재요청한다.
     */
    CancellationResultResponse cancelEntireReservation(String customerId, String reservationId, String idempotencyKey, CancellationRequest request);

    /**
     * 고객 역할·소유권을 확인한 후 최초 취소 사유·판정 시각·비용과 최신 금전 상태를 조회한다.
     * 고객 요청, 예정 결제 실패 또는 숙소 제공 불가로 취소되었어도 최초 결과를 같은 구조로 제공한다.
     * 이미 취소된 예약의 조회는 최초 비용을 새로 계산하거나 점유를 다시 해제하지 않는다.
     * 진행 중·결과 불명·실패·성공을 각 결제·환불 작업에 표시하며 새 환불이나 청구는 만들지 않는다.
     * 가정: 취소 결과가 아직 없는 예약은 404 CANCELLATION_NOT_FOUND로 반환하여 미확정 결과를 생성하지 않는다.
     *
     * @param customerId 인증된 고객 계정 식별자
     * @param reservationId 가정: 조회 대상을 식별하는 1~64자 opaque 문자열
     * @return 최초 전체 취소 사실과 현재 확인된 금전 후속 상태
     * @throws InvalidRequestException 예약 식별자 형식·길이가 유효하지 않은 경우
     * @throws AccessDeniedException 호출 주체가 고객 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 예약이 없거나 고객 소유 범위가 아니거나 아직 취소 결과가 없는 경우
     * @throws TemporarilyUnavailableException 현재 취소·금전 상태를 일관되게 조회할 수 없는 경우
     */
    CancellationResultResponse getCancellationResult(String customerId, String reservationId);
}

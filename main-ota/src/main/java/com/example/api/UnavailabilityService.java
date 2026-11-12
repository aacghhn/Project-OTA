package com.example.api;

import com.example.api.dto.request.UnavailabilityRequest;
import com.example.api.dto.response.CancellationResultResponse;
import com.example.api.dto.response.UnavailabilityContextResponse;

/**
 * 파트너 소유 숙소의 제공 불가 취소를 처리하는 계약이다.
 * 로컬 예약 취소·점유 해제와 외부 결제·환불의 확인은 서로 다른 결과로 보존한다.
 * 서비스 구현체, DB 트랜잭션, PG 통신과 복구 작업의 실행 구현은 포함하지 않는다.
 */
public interface UnavailabilityService {
    /**
     * 파트너 역할, 숙소 소유권과 예약의 해당 숙소 귀속을 확인한 후 예약 당시 약속과 현재 상태를 읽는다.
     * 가정: CONFIRMED 또는 CANCELLED만 조회한다. 그 밖의 상태는 아직 성립한 숙박 약속이 없어 409로 거절한다.
     * 조회 대상의 상품·객실 설명, 숙박 기간·수량·인원·가격·혜택·취소·결제 조건은 예약 당시 값으로 보존한다.
     * CONFIRMED이면 canReportUnavailability=true, customerFeeAmount=0, customerFeeCurrency=KRW이다.
     * 최초 취소가 없어 originalCancellationReason, originalCancellationReasonDetail, originalCancellationDecidedAt,
     * originalCancellationCustomerFeeAmount, originalCancellationCustomerFeeCurrency는 모두 null이다.
     * 이때 financialImpact 접두어의 잔액과 후속 작업 필드는 제공 불가로 전체 취소를 실행할 경우의 예상이다. 조회는 예약을 취소하거나 환불을 생성하지 않는다.
     * CANCELLED이면 canReportUnavailability=false이고 originalCancellationReason, originalCancellationDecidedAt,
     * originalCancellationCustomerFeeAmount, originalCancellationCustomerFeeCurrency가 반드시 존재한다.
     * originalCancellationReasonDetail만 최초 취소 때 별도 사유가 기록되지 않았으면 null일 수 있다.
     * 최초 고객 취소 비용이 0원보다 크더라도 제공 불가 경로에서 조회했다는 이유로 0원으로 다시 계산하지 않는다.
     * financialImpactSuccessfulCollectionAmount인 P, financialImpactSuccessfulRefundAmount인 R, customerFeeAmount인 F로 환불 잔액 max(P-R-F,0)과 미수 비용 max(F-(P-R),0)을 표시한다.
     * 결제 또는 환불에 PROCESSING/UNKNOWN 작업이 있으면 financialImpactAmountsProvisional=true이며 최종 금전 확정액이 아니다.
     * 이미 취소된 예약은 환불 실패·결과 불명이어도 취소와 점유 해제를 유지하며 남은 확인·복구 작업을 표시한다.
     * 금액은 각각의 Amount 필드에 KRW 정수 원으로, 통화는 짝이 되는 Currency 필드에 보존한다.
     * financialImpactPayments의 chargeId·attemptId·purpose로 청구와 기존 결제 시도를, financialImpactRefunds의
     * refundId·originalPaymentId로 원결제별 환불을 구분한다. 배열 항목의 amount와 currency도 함께 반환한다.
     * 예약과 금전 상태는 하나의 observedAt 기준으로 읽는다. 읽기만으로 상태 변경·신규 수금·환불·점유 해제를 반복하지 않는다.
     * 가정: 이 조회는 실행 전 확인과 실행 후 최신 결과 확인에 함께 사용한다. 사전 조회를 명령의 필수 선행조건으로 두지 않으며
     * 명령 처리 시 소유권과 현재 예약 상태를 다시 검사한다.
     *
     * @param partnerId 인증 계층이 확인한 파트너 계정 식별자. 요청 본문으로 대체하지 않는다.
     * @param propertyId 가정: 대상 숙소의 필수 1~64자 opaque 문자열이며 공백만인 값은 허용하지 않는다.
     * @param reservationId 가정: 해당 숙소에 속한 예약의 필수 1~64자 opaque 문자열이며 공백만인 값은 허용하지 않는다.
     * @return 예약 약속, 최초 취소 여부, 표시 비용과 현재 또는 예상 금전 영향
     * @throws InvalidRequestException 숙소 또는 예약 식별자의 형식·길이가 유효하지 않은 경우
     * @throws AccessDeniedException 인증된 계정이 파트너 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 숙소·예약이 없거나 소유권·예약 귀속이 맞지 않는 경우. 타인 자원 존재를 구분해 노출하지 않는다.
     * @throws ConflictException 예약 상태가 CONFIRMED 또는 CANCELLED가 아닌 경우
     * @throws TemporarilyUnavailableException 예약과 금전 상태를 일관되게 조회할 수 없는 저장소 장애가 발생한 경우
     */
    UnavailabilityContextResponse getUnavailabilityContext(String partnerId, String propertyId, String reservationId);

    /**
     * 파트너 역할, 숙소 소유권과 예약 귀속을 확인한 뒤 제공 불가 사유와 명시적인 전체 취소 확인을 처리한다.
     * 가정: reason은 필수 자유문장 1~500자이며 null·빈문자·공백만인 값을 거절한다. 구조화된 사유 선택지가 없어 구체 상황을 보존한다.
     * 가정: confirmEntireReservation은 필수 true만 허용하여 화면 탐색과 실제 전체 취소 명령을 구분한다.
     * 가정: Idempotency-Key는 필수 1~128자의 nonblank 문자열이다. 파트너 계정·작업·키를 범위로 최초 경로와 본문을 보존한다.
     * 요청 지문에는 propertyId, reservationId, reason 원문과 confirmEntireReservation을 포함한다. 사유의 공백이나 줄바꿈을
     * 임의 정규화하지 않는다. 같은 키와 같은 입력은 최초 응답 스냅샷을 재생하며 다른 경로·사유·확인 값은 409로 거절한다.
     * 입력과 소유권 검증 뒤 동일 요청 재생을 현재 예약 상태보다 먼저 판단하여 이미 성공한 네트워크 재전송이 실패하지 않게 한다.
     * 새 키의 요청에서 예약이 이미 CANCELLED이면 최초 사유·자유문장·판정 시각·비용을 반환한다. 새 제공 불가 사유를 덮어쓰거나
     * 최초 고객 취소 비용을 0원으로 다시 계산하지 않는다. 다른 사유의 중복 요청도 점유 해제나 금전 작업을 반복하지 않는다.
     * 최초 취소는 CONFIRMED 상태에서만 허용한다. 같은 예약의 고객 취소·예정 결제·다른 제공 불가 요청과 처리 순서를 정한다.
     * 새 제공 불가 취소의 최초 사유는 PROPERTY_UNAVAILABLE, 사유 상세는 reason, 고객 부담 비용은 0원이며 판정 시각은 서버 시각이다.
     * 최초 결과 저장, CANCELLED 전이와 전체 숙박일 점유 해제를 함께 보장하고 외부 환불 성공을 기다리지 않는다.
     * 새 취소에서 확인된 성공 수금액은 전액 환불 대상으로 연결한다. 이미 성공한 환불 R은 차감하여 남은 환불은 max(P-R,0)이다.
     * 취소 이후 새 숙박대금 수금을 시작하지 않는다. 취소 전에 시작한 결제는 기존 결과를 확인하고 뒤늦은 성공에도 예약을
     * 부활시키지 않으며 최초 비용에 맞는 금전 후속 처리로 이어간다. 다른 사유의 선행 취소가 있으면 그 최초 비용 F를 유지한다.
     * 진행 중 또는 결과 불명인 기존 결제·환불을 확인하기 전에 새 금전 작업을 만들어 중복 수금·환불하지 않는다.
     * 원결제 성공 금액을 초과해 환불하지 않으며 동일 외부 결과를 다시 반영해 성공 수금·성공 환불 합계를 늘리지 않는다.
     * 환불 실패·결과 불명에도 예약 취소와 점유 해제를 되돌리지 않는다. 작업별 상태·사유·남은 확인 또는 복구 작업을 반환한다.
     * 이 취소는 숙소·객실·상품의 판매 중단을 자동 해제하거나 새 중단 상태를 설정하지 않는다. 보존된 판매 차단·가격·한도에 따라
     * 이후 신규 예약 수락 가능 여부가 따로 결정된다. 점유 해제를 외부 판매 복구 정책과 동일시하지 않는다.
     * 임의 환불액·비용 면제 승인, 노쇼 취소 대체, 대체 숙소·추가 보상·정산 또는 지원 상담 접수는 처리하지 않는다.
     * 가정: 반환 HTTP 200은 로컬 전체 취소 완료 또는 최초 결과 재생이다. 외부 결제·환불 결과 불명은 본문의 업무 상태이며
     * 이를 취소 실패로 바꾸지 않는다. 동일 키 응답은 저장된 스냅샷이므로 이후 금전 진행은 결과 GET을 통해 확인한다.
     *
     * @param partnerId 인증 계층이 확인한 파트너 계정 식별자
     * @param propertyId 가정: 대상 숙소의 필수 1~64자 opaque 문자열
     * @param reservationId 가정: 해당 숙소 예약의 필수 1~64자 opaque 문자열
     * @param idempotencyKey 가정: 중복 전체 취소를 방지하는 필수 1~128자 nonblank 요청 키
     * @param request 필수 제공 불가 사유와 전체 취소 확인. 서버가 결정할 비용·환불액은 포함하지 않는다.
     * @return 보존한 최초 취소와 응답 기준 시점의 결제·환불·남은 금전 작업
     * @throws InvalidRequestException 식별자·사유·멱등 키의 형식 또는 길이가 유효하지 않거나 전체 취소 확인이 true가 아닌 경우
     * @throws AccessDeniedException 인증된 계정이 파트너 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 숙소·예약이 없거나 소유권·예약 귀속이 맞지 않는 경우
     * @throws ConflictException 예약이 CONFIRMED/CANCELLED가 아니거나 동일 멱등 키에 다른 요청이 연결된 경우
     * @throws TemporarilyUnavailableException 로컬 처리 결과를 안전하게 확인할 수 없는 저장소 장애가 발생한 경우. 동일 키로 결과를 다시 확인한다.
     */
    CancellationResultResponse reportUnavailability(String partnerId, String propertyId, String reservationId,
        String idempotencyKey, UnavailabilityRequest request);
}

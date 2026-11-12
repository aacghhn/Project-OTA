package com.example.api;

import com.example.api.dto.response.CustomerReservationDetailResponse;
import com.example.api.dto.response.CustomerReservationListResponse;
import com.example.api.dto.response.ReservationStatus;

/**
 * 인증된 고객의 예약 당시 약속과 조회 시점 상태를 반환하는 계약이다.
 * actorId는 인증 토큰에서 검증한 고객 식별자이며 요청자가 임의로 지정하는 고객 번호가 아니다.
 * 읽기는 예약 상태 전이·점유 해제·수금·환불 작업을 실행하지 않는다.
 * 저장 상태가 HELD인 미확정 선점에만 observedAt >= holdExpiresAt 판정을 적용하며, 이 조건을 만족하면 정리 작업 여부와 무관하게 EXPIRED로 표시한다.
 * CONFIRMED와 CANCELLED 등 후속 상태는 최초 선점 만료 시각만으로 바꾸지 않는다.
 * 예약 확정은 결제 성공과 독립되며 예약 취소는 환불 완료와 독립된다.
 */
public interface CustomerReservationService {
    /**
     * 고객 소유의 예약과 진행 중 선점만 반환한다. 가격·혜택·판매 상태의 현재 설정으로 과거 약속을 재계산하지 않는다.
     * 가정: 생성 시각 내림차순, 동일 시각에는 예약 식별자 내림차순으로 정렬한다.
     * 가정: cursor는 첫 페이지 기준 시각·정렬 경계·필터를 보존하는 1~256자 불투명 문자열이며
     * 다음 페이지에서 필터가 다르거나 커서가 잘못되면 400으로 거절한다. 생략 시 첫 페이지를 읽는다.
     * 가정: limit은 1~100, 기본 20으로 제한하고 결과가 없으면 items=[], nextCursor=null을 반환한다.
     * status가 null이면 모든 상태를 조회한다. 저장 상태가 HELD인 미확정 선점은 observedAt >= holdExpiresAt이면 정리 작업 여부와 무관하게 EXPIRED 필터에 포함한다.
     * CONFIRMED와 CANCELLED 등 후속 상태는 최초 선점 만료 시각만으로 바꾸지 않는다.
     * 각 페이지의 observedAt을 기준으로 상태를 산정하므로 이전 페이지 이후 발생한 상태 변경은 후속 조회에 반영될 수 있다.
     * @throws InvalidRequestException 필터·커서·페이지 크기가 허용 조건을 위반한 경우
     * @throws UnauthenticatedException 유효한 고객 인증이 없는 경우
     * @throws AccessDeniedException 인증 주체가 고객 역할이 아닌 경우
     */
    CustomerReservationListResponse listCustomerReservations(String actorId, ReservationStatus status, String cursor, int limit);

    /**
     * 식별한 고객 자신의 예약 또는 선점 하나를 반환하며 다른 예약으로 대체하지 않는다.
     * 가정: 예약 식별자는 1~64자 문자열이고 타인 예약과 존재하지 않는 예약을 모두 404로 처리하여 소유 여부를 노출하지 않는다.
     * 예약 당시 상품·기간·수량·객실별 인원·가격·혜택·취소·결제 조건을 보존하여 반환한다.
     * 상품 설명과 제공 객실의 설명도 예약 당시 값으로 보존하며 이후 상품·객실 설명 변경으로 덮어쓰지 않는다.
     * 별도 참조 번호와 대표 투숙객 이름·성은 독립 값으로 제공한다. 발급되지 않은 참조 번호는 null이다.
     * CONFIRMED만 유효한 확정 예약이며 HELD는 만료 전의 미확정 점유이다.
     * 저장 상태가 HELD인 미확정 선점에만 observedAt >= holdExpiresAt 판정을 적용하여 정리 작업 여부와 무관하게 EXPIRED로 표시한다.
     * CONFIRMED와 CANCELLED 등 후속 상태는 최초 선점 만료 시각만으로 바꾸지 않는다.
     * HELD에서 즉시 결제 실패가 확정되고 현재 시각이 만료 전이며 기존 미확인 결제가 없을 때만 RETRY_IMMEDIATE_PAYMENT를 제공한다.
     * paymentSummaryHasUnresolvedAttempt=true이거나 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이면 WAIT_FOR_RESULT를 제공한다.
     * 이 경우 기존 처리 중·불명 결과를 확인하기 전에는 RETRY_IMMEDIATE_PAYMENT와 START_NEW_RESERVATION을 모두 제외하며 적용 가능한 금전 조회는 유지한다.
     * CONFIRMED이면 전체 취소 확인과 숙소 연락 경로를 제공하며 bookedPaymentTermsTiming=LATER인 예약의 남은 의무에는 VIEW_SCHEDULED_PAYMENT를 제공한다.
     * EXPIRED 또는 RELEASED이고 paymentSummaryHasUnresolvedAttempt=false이며 paymentSummaryLatestStatus가 PROCESSING 또는 UNKNOWN이 아닐 때만 START_NEW_RESERVATION을 제공한다.
     * START_NEW_RESERVATION은 새 조건을 조회하고 다시 동의하여 별도 예약을 시작하도록 안내하며 기존 예약을 부활시키지 않는다.
     * CANCELLED 또는 만료·해제 후 결제 결과 확인·환불 등 금전 작업이 남아 있으면 VIEW_FINANCIAL_RESULT를 제공한다.
     * 확인된 기존 결제 성공으로 환불 대상이 남더라도 미확인 결제가 없다면 새 예약 안내와 금전 조회를 함께 제공할 수 있다.
     * 가정: 각 후속 행동 조건을 독립 평가하여 중복 없는 필수 배열로 반환하고, 가능한 행동이 없으면 빈 배열로 정해 실행 가능 상태를 명확히 전달한다.
     * 환불 실패·결과 불명은 예약 취소를 되돌리지 않고 성공 환불액에 포함하지 않는다.
     * @throws InvalidRequestException 식별자가 비어 있거나 64자를 초과한 경우
     * @throws UnauthenticatedException 유효한 고객 인증이 없는 경우
     * @throws AccessDeniedException 인증 주체가 고객 역할이 아닌 경우
     * @throws ResourceNotFoundException 예약이 없거나 다른 고객 소유인 경우
     */
    CustomerReservationDetailResponse getCustomerReservation(String actorId, String reservationId);
}

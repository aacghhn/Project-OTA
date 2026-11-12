package com.example.api;

import com.example.api.dto.request.*;
import com.example.api.dto.response.*;

/** 고객 소유권, 전 기간 재고·조건 검증과 예약·결제의 독립적 생명주기를 보장해야 하는 서비스 계약. 구현체는 포함하지 않는다. */
public interface BookingService {
    /**
     * 로그인 없이 정렬과 숙소 유형의 선택지·초기 상태를 읽는다. 선택이나 조회는 재고를 확보하지 않는다.
     * @throws TemporarilyUnavailableException 정렬·숙소 유형 선택지의 현재 설정을 읽을 수 없어 선택지 조회를 일시적으로 제공하지 못한다.
     */
    SearchOptionsResponse getSearchOptions();

    /**
     * 가정: 객실별 인원 배열을 전달하기 위해 POST 읽기로 제공한다. 숙소명과 도시명은 각각 조건이며 함께 있으면 AND이다. 같은 기간·인원·객실 수로 필수 요금 포함 총액을 비교한다. 유효한 유료 우선 노출 메타데이터가 있는 숙소를 먼저 배치하고 각 그룹 안에서 선택한 기준으로 정렬하며 후기 점수가 없으면 해당 그룹 마지막에 둔다. 광고 구매·과금은 이 API의 기능이 아니다. 조회는 예약·선점·결제를 만들지 않는다.
     * @throws InvalidRequestException 검색 본문의 날짜 형식·순서, 객실 수와 인원 배열의 일치, 객실별 인원 합계, 숙소명·도시명 길이, 정렬·유형 선택값 또는 가격·페이지 범위가 유효하지 않아 검색하지 않는다.
     * @throws TemporarilyUnavailableException 숙소 검색 또는 동일 숙박 조건의 가격·판매 여유 조회를 일시적으로 수행할 수 없어 검색 결과를 제공하지 못한다.
     */
    SearchResultResponse searchProperties(SearchCriteriaRequest request);

    /**
     * 공개 숙소 상세와 객실·요금제 조합을 같은 숙박 조건으로 계산한다. 가정: 인원 배열을 담기 위한 POST 읽기이다. 같은 객실 유형의 여러 요금제는 재고를 공유하며 조회 결과는 예약 수락을 보장하지 않는다.
     * @throws InvalidRequestException 숙소 식별자가 1~64자 비공백 문자열이 아니거나 숙박일 순서·객실 수·객실별 인원 배분이 유효하지 않아 상품을 조회하지 않는다.
     * @throws ResourceNotFoundException 지정한 식별자의 공개 숙소를 찾을 수 없어 숙소 상세와 판매 상품을 제공하지 않는다. 공개 조회이므로 고객의 숙소 소유권을 요구하지 않는다.
     * @throws TemporarilyUnavailableException 지정 숙소의 상세 정보 또는 요청 기간의 상품·가격·판매 여유를 일시적으로 읽을 수 없어 조회 결과를 제공하지 못한다.
     */
    BookingPropertyOffersResponse getPropertyOffers(String propertyId, SearchStayRequest request);

    /**
     * 고객이 선택한 상품의 기간·인원·객실 수·가격·혜택·취소·결제 조건과 이용약관·개인정보 처리방침을 확인한다. 가정: 저장 부작용 없는 서명 견적 토큰을 10분간 제공한다. 조회는 선점하지 않으며 생성 때 현재 조건을 다시 검증한다. 인증 주체는 고객이어야 하며 견적 토큰에 해당 고객을 귀속시킨다. 조회 대상은 판매 상품이고 고객의 상품 소유권을 요구하지 않는다.
     * @throws InvalidRequestException 상품 식별자 또는 숙박 조건 본문이 유효하지 않거나 체크인·체크아웃 순서, 객실 수와 인원 배열, 객실별 인원 합계가 맞지 않아 견적을 계산하지 않는다.
     * @throws UnauthenticatedException 유효한 고객 인증이 없어 고객에게 귀속되는 견적 토큰을 발행하지 않는다.
     * @throws AccessDeniedException 인증 주체가 고객 역할이 아니므로 예약용 견적을 제공하지 않는다.
     * @throws ResourceNotFoundException 지정한 판매 상품 또는 그 상품의 공개 숙소·객실·요금제를 찾을 수 없어 견적을 제공하지 않는다. 판매 상품을 고객이 소유해야 하는 것은 아니다.
     * @throws ConflictException 요청 기간 중 가격 누락·판매 중단·판매 여유 부족 또는 객실 정원 초과로 현재 상품의 최종 예약 조건을 구성할 수 없어 견적을 거절한다.
     * @throws TemporarilyUnavailableException 상품 조건·가격·동의 대상 버전을 읽거나 견적 토큰을 생성하는 작업을 일시적으로 수행할 수 없어 견적을 제공하지 못한다.
     */
    BookingQuoteResponse getBookingQuote(String customerId, BookingQuoteRequest request);

    /**
     * 고객 자신의 수단과 지금 결제·나중에 결제의 가능 여부를 읽는다. 가정: 서명 견적을 전달하는 POST 읽기이다. 나중 결제 시각은 상품의 독립된 설정에서 계산하며 임의 시각 입력은 없다. 수단이나 시점의 선택만으로 예약을 확정하지 않는다. 인증 주체는 고객이어야 하며 견적 토큰의 고객 귀속을 확인한다. 결제수단 목록에는 해당 고객의 수단만 포함한다.
     * @throws InvalidRequestException 견적 토큰이 누락되었거나 1~4096자 문자열 형식 또는 서명 검증이 유효하지 않아 결제 선택지를 조회하지 않는다.
     * @throws UnauthenticatedException 유효한 고객 인증이 없어 고객에게 귀속된 견적과 결제수단의 선택지를 제공하지 않는다.
     * @throws AccessDeniedException 인증 주체가 고객 역할이 아니므로 예약 결제 선택지를 제공하지 않는다.
     * @throws ResourceNotFoundException 견적이 이 고객에게 귀속되지 않거나 견적에 지정된 상품을 찾을 수 없어 결제 선택지를 제공하지 않는다. 다른 고객의 결제수단은 목록에 포함하지 않는다.
     * @throws ConflictException 견적 토큰이 만료되었거나 견적의 상품 조건이 현재 조건과 달라 선택지를 계산하지 않는다. 새 견적을 확인해야 한다. 상품이 특정 결제 시점만 허용하거나 수단이 사용 불가인 경우에는 200 응답의 비활성 상태와 사유로 구분한다.
     * @throws TemporarilyUnavailableException 고객 결제수단의 사용 가능 상태 또는 견적에 해당하는 결제 조건을 일시적으로 읽을 수 없어 선택지를 제공하지 못한다.
     */
    BookingPaymentOptionsResponse getBookingPaymentOptions(String customerId, BookingPaymentOptionsRequest request);

    /**
     * 전체 숙박일의 1원 이상 가격·공유 판매 여유·판매 상태·객실별 성인+아동 합계 1 이상 및 총정원 이하와 동의를 재검증하여 모두 수락할 때만 10분 선점한다. 즉시 결제는 성공 확인 후, 나중 결제는 미래 청구·유효 수단 준비 후 선점 유효성과 현재 조건을 다시 확인해 확정한다. 조건 변경은 선점을 해제하고 새 견적에 재동의한 새 예약으로 진행한다. 가정: 확인 중을 포함해 수락된 요청은 항상 202와 별도 예약·결제 상태를 반환한다. 가정: Idempotency-Key는 필수 1~128자 문자열이며 고객·작업·키를 범위로 최초 요청 내용을 보존한다. 같은 내용은 최초 수락 결과를 재생하고 다른 내용은 409이다. 중복 예약·점유·결제를 막기 위해 정했다. 인증 주체는 고객이어야 하며 타인의 대상·결제수단은 존재 여부를 숨겨 404로 거절한다. 날짜별 판매가격이 1원 이상인지, 객실별 성인+아동 합계가 1 이상이고 총정원 이하인지 검증한다. 별도 성인 최소 인원은 요구하지 않는다. 전 기간 가격·재고·총정원 검증과 선점은 원자적으로 처리하고 같은 객실의 여러 요금제도 하나의 판매 한도를 공유한다. 선점 만료·해제 뒤 결제 성공이 확인되면 예약을 확정하거나 되살리지 않고 성공 수금액을 환불 대상으로 기록한다. 결제 결과 불명은 기존 시도를 조회·복구하며 신규 시도로 중복 수금하지 않는다. 예정 결제 실패가 확정되면 유예나 자동 신규 재시도 없이 전체 취소하고 고객 취소 비용을 0으로 처리하며 결과 불명이면 예약을 유지한다. 나중 결제 예정 시각이 현재 시각보다 늦지 않으면 선점을 거절하며 즉시 결제로 바꾸지 않는다. 무료 취소 기한 N과 청구 예정 N은 독립 설정이다. 조건 변경 시 기존 선점을 해제하고 새 조건에 다시 동의한 새 예약만 허용한다. 생성에 사용한 서버 계산 금액·혜택·취소·결제 조건과 대표 투숙객·객실별 인원을 예약 당시 약속으로 보존한다.
     * @throws InvalidRequestException 견적 토큰의 형식·서명, 대표 투숙객 이름·성, 필수 동의 두 종류의 버전·동의값, 결제 시점·수단 식별자 또는 멱등 키의 형식이 유효하지 않아 예약을 시작하지 않는다.
     * @throws UnauthenticatedException 유효한 고객 인증이 없어 예약·선점·결제 요청을 수락하지 않는다.
     * @throws AccessDeniedException 인증 주체가 고객 역할이 아니므로 예약 요청을 수락하지 않는다.
     * @throws ResourceNotFoundException 견적의 상품이나 선택한 결제수단을 찾을 수 없거나 견적·결제수단이 이 고객에게 귀속되지 않아 예약을 시작하지 않는다.
     * @throws ConflictException 견적 만료·조건 변경, 기간 중 가격 누락·판매 중단·재고 부족·정원 초과, 유효하지 않은 결제수단, 이미 도래한 나중 결제 시각 또는 동일 멱등 키에 다른 요청 내용이 있어 예약을 거절한다. 조건 변경이 선점 뒤 확인되면 그 선점을 해제하고 새 견적에 동의한 새 예약을 요구한다.
     * @throws TemporarilyUnavailableException 예약 요청의 수락 여부를 기록하기 전에 저장 또는 조건 확인을 일시적으로 수행할 수 없어 요청을 수락하지 못한다. 이미 수락한 요청의 결제 결과 불명은 이 오류로 실패 처리하지 않고 202 응답의 paymentStatus=UNKNOWN과 기존 요청 결과 조회로 구분한다.
     */
    BookingAttemptResponse createBooking(String customerId, String idempotencyKey, BookingCreateRequest request);

    /**
     * 자신의 선점·확정·만료·해제 상태와 결제 상태를 따로 확인한다. 미확정 선점의 상태가 HELD이고 현재 시각이 만료와 같거나 늦으면 정리 작업 전이어도 EXPIRED로 평가한다. 이미 CONFIRMED인 예약은 최초 선점 만료 시각이 지나도 확정 상태를 유지한다. 결과 불명은 실패나 성공으로 단정하지 않는다. 읽기는 새 결제나 환불을 시작하지 않는다. 인증 주체는 고객이어야 하며 해당 예약의 소유권을 확인한다. 타인의 예약 요청은 존재 여부를 숨겨 404로 거절한다.
     * 가정: 현재 결과에서 가능한 후속 행동을 필수 문자열 배열로 제공하며 허용 코드는 VIEW_RESULT, RETRY_PAYMENT, START_NEW_RESERVATION, VIEW_RESERVATION, VIEW_REFUNDS이다. VIEW_RESULT는 reservationStatus=HELD이거나 EXPIRED/RELEASED 상태에서 paymentStatus가 PROCESSING/UNKNOWN인 경우 반환하며, GET /api/customer/reservation-attempts/{reservationId}로 기존 예약 요청과 결제 결과를 다시 읽는다. RETRY_PAYMENT는 reservationStatus=HELD, asOf가 heldUntil보다 이른 시각, paymentTiming=IMMEDIATE, paymentStatus=FAILED를 모두 만족할 때만 반환하며, POST /api/customer/reservation-attempts/{reservationId}/payment-attempts로 자신의 유효한 수단을 다시 검증받아 새 결제 시도를 요청한다. START_NEW_RESERVATION은 reservationStatus가 EXPIRED/RELEASED이고 기존 paymentStatus가 PROCESSING/UNKNOWN이 아닐 때 반환하며, 숙소 검색과 새 상품 견적 확인 단계로 이동해 새 조건에 동의한 별도 예약을 시작한다. 기존 예약을 부활시키는 동작이 아니다. VIEW_RESERVATION은 reservationStatus=CONFIRMED 또는 confirmedAt이 있는 CANCELLED에서 반환하며, GET /api/customer/reservations/{reservationId}로 확정 당시 약속과 현재 예약 상태를 확인한다. VIEW_REFUNDS는 reservationStatus가 EXPIRED/RELEASED/CANCELLED이면 반환하며, GET /api/customer/reservations/{reservationId}/financial-outcome으로 확인된 환불 대상·처리 상태·잔여 금전 의무를 읽는다. 환불이 없거나 금액이 0인 결과도 확인할 수 있고 조회 자체는 환불을 생성하지 않는다. 각 조건을 독립적으로 평가하여 필요한 코드를 중복 없이 함께 반환한다. 예를 들어 만료 후 결제 성공이 확인되어 환불 대상이 생겼으면 START_NEW_RESERVATION과 VIEW_REFUNDS를 함께 반환한다. 만료 후 결제 결과가 UNKNOWN이면 VIEW_RESULT와 VIEW_REFUNDS만 반환한다. paymentStatus가 PROCESSING/UNKNOWN이면 RETRY_PAYMENT와 START_NEW_RESERVATION은 반환하지 않아 기존 결과를 확인하기 전 새 결제를 안내하지 않는다. 상태 코드만 보고 고객이 잘못된 실행을 하지 않도록 행동의 조건과 대상을 명시하는 방식을 선택했다.
     * @throws InvalidRequestException 예약 식별자가 1~64자 비공백 문자열이 아니어서 예약 요청 결과를 조회하지 않는다.
     * @throws UnauthenticatedException 유효한 고객 인증이 없어 예약 요청 결과와 결제 상태를 제공하지 않는다.
     * @throws AccessDeniedException 인증 주체가 고객 역할이 아니므로 고객용 예약 요청 결과를 제공하지 않는다.
     * @throws ResourceNotFoundException 지정 예약 요청이 없거나 이 고객의 예약이 아니므로 결과를 제공하지 않는다. 다른 고객 예약의 존재 여부는 드러내지 않는다.
     * @throws TemporarilyUnavailableException 예약 요청과 기록된 결제·환불 연결 상태를 일시적으로 읽을 수 없어 결과를 제공하지 못한다. 저장된 결제 상태가 UNKNOWN인 경우에는 503이 아닌 정상 조회 응답의 상태값으로 제공한다.
     */
    BookingAttemptResponse getBookingAttempt(String customerId, String reservationId);

    /**
     * 즉시 결제 실패가 확정되고 현재 시각이 선점 만료보다 앞선 경우에만 새 논리적 결제 시도를 만든다. 이전 수단 또는 새 수단 모두 고객 소유권·유효성을 다시 검사한다. PROCESSING/UNKNOWN, 만료·해제·확정 또는 나중 결제에서는 409로 거절한다. 가정: Idempotency-Key는 필수 1~128자 문자열이며 고객·작업·키를 범위로 최초 요청 내용을 보존한다. 같은 내용은 최초 수락 결과를 재생하고 다른 내용은 409이다. 중복 예약·점유·결제를 막기 위해 정했다. 인증 주체는 고객이어야 하며 타인의 대상·결제수단은 존재 여부를 숨겨 404로 거절한다. 날짜별 판매가격이 1원 이상인지, 객실별 성인+아동 합계가 1 이상이고 총정원 이하인지 검증한다. 별도 성인 최소 인원은 요구하지 않는다. 전 기간 가격·재고·총정원 검증과 선점은 원자적으로 처리하고 같은 객실의 여러 요금제도 하나의 판매 한도를 공유한다. 선점 만료·해제 뒤 결제 성공이 확인되면 예약을 확정하거나 되살리지 않고 성공 수금액을 환불 대상으로 기록한다. 결제 결과 불명은 기존 시도를 조회·복구하며 신규 시도로 중복 수금하지 않는다. 예정 결제 실패가 확정되면 유예나 자동 신규 재시도 없이 전체 취소하고 고객 취소 비용을 0으로 처리하며 결과 불명이면 예약을 유지한다.
     * @throws InvalidRequestException 예약 식별자, 새 시도에 사용할 결제수단 식별자 또는 멱등 키가 필수 문자열 형식·길이를 만족하지 않아 새 결제 시도를 시작하지 않는다.
     * @throws UnauthenticatedException 유효한 고객 인증이 없어 즉시 결제의 새 시도를 수락하지 않는다.
     * @throws AccessDeniedException 인증 주체가 고객 역할이 아니므로 즉시 결제를 다시 시도할 수 없다.
     * @throws ResourceNotFoundException 예약 또는 결제수단을 찾을 수 없거나 이 고객 소유가 아니므로 새 결제 시도를 시작하지 않는다. 기존 수단을 재사용할 때도 같은 소유권 검사를 한다.
     * @throws ConflictException 즉시 결제의 실패가 확정되지 않았거나 선점이 만료·해제되었거나 예약이 확정된 경우, 나중 결제인 경우, 현재 상품 조건·수단 유효성이 바뀐 경우 또는 같은 멱등 키에 다른 요청을 보낸 경우 새 시도를 거절한다. PROCESSING·UNKNOWN 상태에서는 기존 결제 결과만 확인해야 한다.
     * @throws TemporarilyUnavailableException 새 논리적 결제 시도의 수락 기록을 남기기 전에 상태 확인 또는 저장을 일시적으로 수행할 수 없어 재시도를 수락하지 못한다. 이미 수락한 시도의 결제 결과 불명은 202 응답의 UNKNOWN으로 제공하고 기존 시도의 결과를 확인한다.
     */
    BookingAttemptResponse retryBookingPayment(String customerId, String reservationId, String idempotencyKey, BookingRetryPaymentRequest request);

}

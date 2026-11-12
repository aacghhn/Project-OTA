package com.example.api;

import com.example.api.dto.response.PartnerReservationDetailResponse;
import com.example.api.dto.response.PartnerReservationListResponse;
import java.time.LocalDate;

/**
 * 인증된 파트너의 소유 숙소에 속한 예약 약속과 현재 취소·금전 결과를 읽는 서비스 계약.
 * 목록·상세 조회는 예약 수락, 이행 완료, 체크인·체크아웃 처리, 취소, 수금 또는 환불을 실행하지 않는다.
 * 서비스 구현체·DB 조회 구현·외부 예약 수신 연동은 포함하지 않는다.
 */
public interface PartnerReservationService {
    /**
     * 파트너 역할과 숙소 소유권을 먼저 검사한 뒤 해당 숙소에 속한 확정·취소 예약만 조회한다.
     * 가정: CONFIRMED와 CANCELLED는 각각 현재 확정 상태와 전체 취소 상태이며 ALL은 이 두 상태를 함께 뜻한다.
     * 선점 중·만료·해제 예약은 아직 숙박 제공 약속이 확정되지 않은 대상이므로 이 목록에 포함하지 않는다.
     * 가정: status 생략은 CONFIRMED, limit 생략은 20으로 Controller에서 정하며 그 외 빈값·잘못된 코드는 거절한다.
     * 예약 번호와 참조 코드는 별개이며 각 지정값의 정확한 일치를 검사한다. 대표 투숙객 이름과 성도 별도로 비교한다.
     * 가정: 문자열 검색은 대소문자와 공백을 임의 정규화하지 않는 정확한 일치이고 여러 조건은 AND로 적용한다.
     * 날짜 필터는 Asia/Seoul 기준 체크인 날짜에 대해 checkInFrom 포함·checkInTo 제외이며, 생성일이나 변경 시각 필터가 아니다.
     * 양쪽 날짜가 모두 있으면 시작일이 종료일보다 빨라야 한다. 한쪽이 없으면 그 방향의 경계를 적용하지 않는다.
     * 가정: 정렬은 체크인 날짜 오름차순과 예약 식별자 오름차순이며 cursor는 숙소·상태·검색 조건과 마지막 정렬 키에 결합한다.
     * 다른 숙소나 다른 검색 조건의 cursor를 거절한다. 페이지 크기는 1~100이며 결과가 없으면 items=[]와 nextCursor=null이다.
     * 현재 페이지의 모든 상태를 하나의 observedAt 기준으로 읽는다. 가정: 서로 다른 페이지에 걸친 고정 스냅샷은 보장하지 않는다.
     * 객실·요금제 이름과 기간·수량·대표 투숙객·총액은 예약 당시 보존 값이며 현재 상품 설정으로 다시 계산하지 않는다.
     * 예약 확정은 결제 성공과 같지 않고 취소는 환불 완료와 같지 않다. 반복 조회는 상태나 작업 수를 늘리지 않는다.
     *
     * @param partnerId 인증 계층이 확인한 파트너 계정 식별자. 고객 입력으로 대체하지 않는다.
     * @param propertyId 가정: 소유 숙소를 지정하는 필수 1~64자 opaque 문자열
     * @param status 가정: ALL, CONFIRMED 또는 CANCELLED이며 Controller가 생략값을 CONFIRMED로 정한다.
     * @param reservationId 가정: 선택 1~64자 예약 번호의 정확한 일치 조건이며 null이면 필터를 적용하지 않는다.
     * @param referenceCode 가정: 선택 1~128자 숙소 참조 코드의 정확한 일치 조건이며 null이면 필터를 적용하지 않는다.
     * @param guestFirstName 가정: 선택 1~100자 대표 투숙객 이름의 정확한 일치 조건이며 null이면 필터를 적용하지 않는다.
     * @param guestLastName 가정: 선택 1~100자 대표 투숙객 성의 정확한 일치 조건이며 null이면 필터를 적용하지 않는다.
     * @param checkInFrom 가정: 체크인 날짜의 포함 시작일이며 null이면 하한이 없다.
     * @param checkInTo 가정: 체크인 날짜의 제외 종료일이며 null이면 상한이 없다.
     * @param limit 가정: 1~100의 페이지 크기이며 Controller가 생략값을 20으로 정한다.
     * @param cursor 가정: 선택 1~1024자 다음 페이지 토큰이며 null이면 첫 페이지이다.
     * @return 선택한 숙소와 일치하는 예약 요약, 다음 페이지 토큰과 동일 관측 시각
     * @throws InvalidRequestException 문자열 형식·길이·상태·페이지 크기·날짜 순서 또는 cursor의 검색 조건 결합이 유효하지 않은 경우
     * @throws AccessDeniedException 인증된 계정이 파트너 역할이 아닌 경우
     * @throws ResourceNotFoundException 숙소가 없거나 해당 파트너의 소유 범위에 없는 경우
     * @throws TemporarilyUnavailableException 현재 목록 상태를 일관되게 읽을 수 없는 저장소 장애가 발생한 경우
     */
    PartnerReservationListResponse listPartnerReservations(String partnerId, String propertyId, String status,
        String reservationId, String referenceCode, String guestFirstName, String guestLastName,
        LocalDate checkInFrom, LocalDate checkInTo, Integer limit, String cursor);

    /**
     * 파트너 역할과 숙소 소유권, 예약의 해당 숙소 귀속을 검사하여 확정된 적 있는 예약의 약속을 반환한다.
     * 가정: 현재 CONFIRMED 또는 CANCELLED 상태인 예약만 제공하며 아직 확정된 적 없는 대상은 404로 처리한다.
     * 숙소·예약이 없거나 소유 범위와 맞지 않으면 같은 404를 사용하여 다른 숙소 예약의 존재를 노출하지 않는다.
     * 예약 당시 상품·객실 설명과 유형·요금제·기간·수량·대표 투숙객·객실별 인원·가격 구성·혜택·취소·결제 조건을 보존한다.
     * 고객에게 제공하는 같은 예약의 약속·현재 상태·최초 취소 결과·결제 및 환불 사실과 모순되는 값을 만들지 않는다.
     * 객실 하나의 전체 숙박일 합계 perRoomStayTotalAmount는 bookedPriceDailyRates[].unitPriceAmount의 합이며 전체 객실의 bookedPriceTotalAmount와 구분한다. 각 금액의 Currency 필드는 KRW이다.
     * 현재 객실 이름·설명은 currentRoomInformation 접두어 필드로 제공한다. 가정: 현재 구성 정보가 제거되면 네 필드는 모두 null이며
     * currentRoomInformationRoomTypeId가 있으면 이름·설명·관측 시각도 null일 수 없다. 현재 정보 조회 장애는 503이다. 예약 당시 설명을 현재 설명으로 바꾸지 않는다.
     * CONFIRMED이면 cancellation 및 cancellationFinancialImpact 접두어의 모든 필드와 배열은 null이다. CANCELLED이면 cancellationReason과 cancellationFinancialImpactSuccessfulCollectionAmount가 반드시 존재하고, 최초 사유·판정 시각·비용과
     * 그 비용을 기준으로 확인한 수금·환불·진행 중/결과 불명 작업·남은 환불·미수 취소 비용은 null 없이 각각 제공한다.
     * 취소 상세 사유만 입력이 없으면 null이고 결제·환불·후속 작업 배열은 해당 작업이 없으면 []이다. 작업 항목의 attemptId는 수금 시작 전 null, failureReason은 확정된 실패 사유가 없으면 null이다.
     * 취소 결과는 나중에 다른 사유가 전달되거나 상품 정책이 변경되어도 다시 계산하지 않는다.
     * 성공한 결제와 환불 사실만 합산하며 PROCESSING/UNKNOWN 금액을 성공 또는 실패로 단정하지 않는다.
     * 미완료 환불·추가 청구의 실패는 조회 실패가 아니라 정상 응답의 업무 상태이다. 시간 초과로 완료를 만들지 않는다.
     * 환불 실패나 결과 불명 때문에 예약 취소와 점유 해제를 되돌리지 않는다. 금전 상태 확인을 위해 새 작업을 만들지 않는다.
     * 가정: canReportUnavailability는 CONFIRMED에서만 true이다. 제공 불가 사실을 판정하거나 취소를 실행한 결과가 아니다.
     * 응답의 모든 현재 상태와 금전 합계는 같은 observedAt 기준이며 조회는 최초 확정·취소 시각을 갱신하지 않는다.
     *
     * @param partnerId 인증 계층이 확인한 파트너 계정 식별자
     * @param propertyId 가정: 소유 숙소의 필수 1~64자 opaque 식별자
     * @param reservationId 가정: 해당 숙소에 속한 예약의 필수 1~64자 opaque 식별자
     * @return 보존한 예약 약속, 현재 예약·취소·금전 상태와 별도로 구분한 현재 객실 설명
     * @throws InvalidRequestException 숙소 또는 예약 식별자의 형식·길이가 유효하지 않은 경우
     * @throws AccessDeniedException 인증된 계정이 파트너 역할이 아닌 경우
     * @throws ResourceNotFoundException 숙소·예약이 없거나 소유·귀속이 맞지 않거나 아직 확정된 적 없는 예약인 경우
     * @throws TemporarilyUnavailableException 예약·금전·현재 객실 정보를 같은 기준으로 읽을 수 없는 저장소 장애가 발생한 경우
     */
    PartnerReservationDetailResponse getPartnerReservation(String partnerId, String propertyId, String reservationId);
}

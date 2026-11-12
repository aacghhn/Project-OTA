package com.example.api;

import com.example.api.dto.request.SalesStatusChangeRequest;
import com.example.api.dto.response.SalesStatusResponse.SalesStatusScope;
import com.example.api.dto.response.SalesStatusResponse;
import com.example.api.dto.response.SalesStatusMutationResponse;
import java.time.LocalDate;

/** 판매 차단의 소유권·범위·동시성 검증을 보장하는 서비스 계약. 구현체나 저장소 연동은 포함하지 않는다. */
public interface SalesStatusService {
    /**
     * 파트너 역할과 숙소 소유권, 대상의 숙소 소속을 확인하고 대상 자체 상태를 반환한다.
     * PROPERTY의 targetId는 propertyId와 같아야 한다. ROOM_TYPE_DATE는 객실 유형 식별자로 날짜별 상태를 조회한다.
     * 날짜별 특정 상품과 파트너 계정 정지는 수정 대상으로 만들지 않으며 파트너 정지는 남은 차단에서만 제공한다.
     * 가정: from 포함·to 제외의 Asia/Seoul 날짜를 사용하며 1~366일 기간이고 과거 조회도 허용한다.
     * 날짜 없는 범위의 상태는 전체 날짜에 적용되며 이 기간은 영향 평가를 위한 범위이다.
     * 가정: 별도 중단 설정이 없는 대상은 OPEN, 중단 사유·변경 시각은 null, 숙소 상태 버전은 최초 0이다.
     * 연결된 상품별 날짜 가격·한도·점유·여유와 모든 적용 차단을 같은 observedAt 기준에서 읽는다.
     * 점유는 확정 수량과 현재 시각이 만료 시각보다 작은 선점 수량의 합이며 한도·여유는 실제 보유 수량이 아니다.
     * 가격 미설정이면 priceAmount와 priceCurrency가 함께 null이고 설정됐으면 양쪽 모두 null이 아니며 통화는 KRW이다.
     * salesLimit 미설정에서는 salesLimit과 available이 null이고, 한도에서 점유를 뺀 여유 0은 수량 소진으로 구분한다.
     * readyForOneRoom은 가격·한도가 있고 판매 차단이 없으며 여유가 1 이상인 날짜에만 true이다.
     * 모든 날짜가 준비돼도 인원·전체 숙박 조건과 최종 시점 재검증 전에는 예약 수락이나 확보를 보장하지 않는다.
     * 조회는 상태·가격·한도·예약·점유를 변경하지 않는다.
     * @param partnerId 인증 계층이 확인한 파트너 계정 식별자. 요청 본문에서 받지 않는다.
     * @param propertyId 가정: 파트너 소유 숙소를 식별하는 1~64자 opaque 문자열
     * @param scope 가정: 숙소·객실 유형·요금제·판매 상품·객실 유형 숙박일 중 하나를 나타내는 enum
     * @param targetId 가정: 선택 범위의 대상을 식별하는 1~64자 opaque 문자열
     * @param from 평가 시작 날짜. 이 날짜를 포함한다.
     * @param to 평가 종료 날짜. 이 날짜를 제외한다.
     * @throws InvalidRequestException 식별자·범위·날짜 순서 또는 1~366일 기간 제약을 만족하지 못하는 경우
     * @throws AccessDeniedException 호출 주체가 파트너 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 숙소·대상이 없거나 파트너·숙소의 소유 범위에 없는 경우
     * @throws TemporarilyUnavailableException 일관된 기준 시점의 판매 조건을 조회할 수 없는 경우
     */
    SalesStatusResponse getSalesStatus(String partnerId, String propertyId, SalesStatusScope scope, String targetId, LocalDate from, LocalDate to);

    /**
     * 파트너 역할과 숙소 소유권을 확인하고 선택한 한 범위의 상태를 원자적으로 설정한다.
     * 가정: Idempotency-Key는 필수 1~128자이고 파트너 계정·작업·키가 같은 요청의 경로와 본문을 비교한다.
     * 같은 키·같은 요청은 최초 응답 스냅샷을 재생하며 판매 상태 변경을 다시 수행하지 않는다.
     * reason의 누락과 null은 같은 값으로 정규화한다. 다른 입력은 그대로 비교하며 같은 키의 다른 내용은 409이다.
     * 멱등 결과 재생은 expectedVersion 비교보다 먼저 수행하되 현재 호출자의 역할·소유권을 먼저 확인한다.
     * 가정: 숙소 판매 상태 전체에 expectedVersion을 사용하며 현재 버전과 다르면 전체 요청을 거절한다.
     * 상태 또는 사유가 실제로 바뀐 경우에만 버전을 한 번 증가시키고 변경된 대상의 updatedAt을 서버 시각으로 기록한다.
     * 같은 상태·같은 사유를 새 키로 반복 설정하면 changed=false이고 버전·변경 시각을 유지한다.
     * PROPERTY의 targetId는 경로 숙소 식별자이며 다른 대상은 같은 숙소에 속해야 한다.
     * ROOM_TYPE_DATE는 from 포함·to 제외 전체 날짜를 함께 변경한다. 처리 시점 Asia/Seoul 오늘 이전 날짜가 있으면 모두 거절한다.
     * 날짜 없는 범위는 전체 날짜의 신규 수락에 적용되며 from/to는 저장 결과의 영향 평가만 제한한다.
     * 날짜별 특정 상품·요금제 조합, 파트너 정지 변경, 가격·한도·점유 직접 수정은 제공하지 않는다.
     * SUSPENDED의 reason은 선택 1~500자 자유문장이고 공백만인 값을 거절한다. 누락/null은 사유 미기록으로 저장한다.
     * OPEN에서 reason을 보내면 거절하고, 유효한 재개는 저장된 중단 사유를 지운다.
     * 판매 상태 변경과 신규 선점·최종 확정의 수락 검증은 같은 동시성 경계에서 순서를 정한다.
     * 중단이 먼저 반영되면 이후 신규 선점과 미확정 예약 확정을 거절한다. 조건이 바뀐 미확정 예약은 기존 약속을
     * 덮어쓰지 않고 선점을 해제하며, 해제 후 결제 성공은 예약을 부활시키지 않고 환불 대상으로 연결한다.
     * 이미 확정한 예약의 약속·상태·점유를 유지하고 날짜별 가격·OTA 판매 한도를 삭제하거나 바꾸지 않는다.
     * OPEN은 요청한 범위만 해제한다. 상위·다른 대상의 차단이나 파트너 정지가 남으면 신규 수락을 허용하지 않는다.
     * 한도 증가와 이 상태 변경은 별개이며 수량 소진은 중단 사유 코드로 대신하지 않는다.
     * 응답은 저장 직후 같은 기준 시점의 영향받는 상품·날짜·가격·한도·점유·여유·남은 차단을 반환한다.
     * HTTP 200은 상태 설정 결과이며 환불·취소·예약 확정이나 무조건적인 판매 가능을 뜻하지 않는다.
     * @param partnerId 인증된 파트너 계정 식별자
     * @param propertyId 가정: 1~64자 opaque 숙소 식별자
     * @param idempotencyKey 가정: 재전송을 식별하는 필수 1~128자 문자열
     * @param request 변경 범위·대상·평가기간·요청 상태·선택 사유·조회 버전
     * @throws InvalidRequestException 필수 입력, 범위, 기간, 과거 날짜 변경, 사유의 상태별 조건을 만족하지 못한 경우
     * @throws AccessDeniedException 호출 주체가 파트너 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 숙소·대상이 없거나 파트너·숙소의 소유 범위에 없는 경우
     * @throws ConflictException 현재 숙소 판매 상태 버전이 다르거나 같은 멱등 키의 경로·본문이 다른 경우
     * @throws TemporarilyUnavailableException 안전한 변경 결과를 확인할 수 없는 경우. 재요청에는 같은 멱등 키를 유지한다.
     */
    SalesStatusMutationResponse setSalesStatus(String partnerId, String propertyId, String idempotencyKey, SalesStatusChangeRequest request);
}

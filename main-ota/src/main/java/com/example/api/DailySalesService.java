package com.example.api;

import com.example.api.dto.request.DailySalesPriceUpdateRequest;
import com.example.api.dto.request.DailySalesLimitUpdateRequest;
import com.example.api.dto.response.DailySalesPricesResponse;
import com.example.api.dto.response.DailySalesInventoryResponse;
import java.time.LocalDate;

/**
 * 소유 파트너의 날짜별 가격과 OTA 한도 관리 계약이다.
 * 점유량은 확정 예약 수량과 기준 시각에 아직 유효한 선점 수량의 합이며 여유는 한도에서 점유를 뺀 값이다.
 * 실제 보유 객실 수, 객실 호실, 인원별 추가 요금, 외부 재고 동기화는 이 계약에서 제공하지 않는다.
 * 서비스 구현체와 DB 처리 구현은 포함하지 않는다.
 */
public interface DailySalesService {
    /**
     * 파트너 역할과 숙소 소유권, 해당 숙소의 판매 상품·객실 유형·요금제 관계를 검증하고 날짜별 값을 읽는다.
     * 조회 범위는 Asia/Seoul 날짜의 from 포함·to 제외이며 모든 날짜를 빠짐없이 오름차순으로 반환한다.
     * 가정: from/to는 필수이고 from < to, 기간은 1~366일로 제한한다. 응답 크기를 제한하기 위한 선택이며
     * 예약의 최대 숙박일수 제한은 아니다. 과거 숙박일도 조회할 수 있으나 그 날짜의 현재 저장 설정을 보여준다.
     * 가격 미설정은 각 days 항목의 priceAmount와 priceCurrency를 모두 null로 제공한다. 한도 미설정은
     * inventoryOtaLimit과 inventoryRemaining을 null로 제공하며 어느 미설정 값도 0으로 대체하지 않는다.
     * 한도 미설정 날짜에는 점유를 허용하지 않아 inventoryConfirmedOccupancy, inventoryHeldOccupancy,
     * inventoryOccupied가 모두 0이다. inventoryStayDate는 stayDate와 같으며 inventorySalesBlocks는 null이 아닌 배열이다.
     * 설정된 가격은 1원 이상 KRW 정수이며 필수 요금을 포함한다. 요금제를 추가해도 객실의 공유 한도는 늘지 않는다.
     * 단일 observedAt으로 가격·한도·예약·선점·차단을 관측하고 inventoryHeldOccupancy에는 observedAt < 만료 시각인 선점만 합산한다.
     * 같은 days 항목의 inventoryOccupied = inventoryConfirmedOccupancy + inventoryHeldOccupancy이며,
     * 한도가 설정되면 inventoryRemaining = inventoryOtaLimit - inventoryOccupied이다.
     * 같은 days 항목의 inventorySalesBlocks는 파트너·숙소·객실 유형·객실 유형 숙박일 차단을 제공하고,
     * productSalesBlocks는 해당 요금제·상품 차단을 제공한다. 수량 소진과 판매 중단을 합치지 않는다.
     * salePrerequisitesMet는 같은 days 항목의 priceAmount와 priceCurrency 및 inventoryOtaLimit이 설정되고,
     * inventoryRemaining이 1 이상이며 inventorySalesBlocks와 productSalesBlocks가 모두 빈 배열일 때만 true이다.
     * 이 값은 전체 숙박 기간·인원·결제 시점·최신 조건의 재검증을 대신하지 않으며 조회는 선점이나 변경을 만들지 않는다.
     *
     * @param partnerId 인증 계층이 확인한 파트너 계정 식별자
     * @param propertyId 가정: 필수 1~64자 opaque 숙소 식별자이며 공백만인 값은 거절한다.
     * @param productId 가정: 필수 1~64자 opaque 판매 상품 식별자이며 공백만인 값은 거절한다.
     * @param from 포함되는 조회 시작 숙박일
     * @param to 제외되는 조회 종료 숙박일
     * @return 같은 기준 시점의 날짜별 가격, 공유 재고, 판매 차단
     * @throws InvalidRequestException 식별자 또는 날짜가 누락되거나 날짜 순서·366일 범위를 만족하지 않는 경우
     * @throws AccessDeniedException 인증 주체가 파트너 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 숙소나 상품이 없거나 파트너의 소유 범위·해당 숙소 소속이 아닌 경우
     * @throws TemporarilyUnavailableException 현재 가격·수량·차단을 일관된 시점으로 읽을 수 없는 경우
     */
    DailySalesPricesResponse getDailySalesPrices(String partnerId, String propertyId, String productId, LocalDate from, LocalDate to);

    /**
     * 파트너 역할·숙소 소유권과 판매 상품의 소속을 검증한 후 요청한 날짜의 가격을 모두 변경하거나 모두 거절한다.
     * 금액은 필수 요금을 포함한 KRW 정수 원이며 1 이상이어야 한다. 소수 가격·0원·음수·다른 통화는 400이다.
     * 가정: 한 요청은 한 상품의 1~366개 날짜이며 중복 날짜와 null 항목은 거절한다. 날짜는 연속일 필요가 없으며
     * 변경 당시 Asia/Seoul 오늘 이상만 허용해 과거 설정을 보호한다. 미래 날짜 상한이나 예약 숙박일수 제한은 만들지 않는다.
     * 가정: 멱등 키는 파트너·작업·키 범위에서 최초 결과를 보존한다. 요청 지문에 숙소·상품과 날짜별 금액·통화를 포함하고
     * 날짜 순서만 오름차순으로 정규화한다. 같은 내용은 최초 응답을 반환하며 다른 내용은 409로 거절한다.
     * 인증·소유권 검증 후 멱등 재생을 날짜의 현재 유효성 재검증보다 먼저 수행하여 자정 뒤 재전송도 중복 변경하지 않는다.
     * 가격 변경과 신규 선점·예약 최종 조건 검증의 처리 순서를 원자적으로 정한다. 최종 확인에서 가격이 달라진 선점은
     * 기존 조건으로 확정하지 않고 해제한 뒤 새 조건에 동의한 새 예약으로 진행한다. 기존 확정 예약의 가격 약속은 보존한다.
     * 가격 변경은 OTA 한도를 수정하지 않고 어떤 수준의 판매 중단도 해제하지 않는다.
     * 응답은 변경한 날짜만 오름차순으로 포함하며 변경 후 동일 observedAt의 가격·한도·점유·여유·차단을 반환한다.
     * 동일 키 재생 응답은 최초 처리 시점의 스냅샷이며 최신 점유·여유는 조회로 확인한다.
     * 처리 결과를 확인하지 못하면 성공으로 추정하지 않는다. 저장 장애 뒤에도 같은 키로 재요청해 중복 처리를 방지한다.
     *
     * @param partnerId 인증된 파트너 계정 식별자
     * @param propertyId 가정: 필수 1~64자 opaque 숙소 식별자
     * @param idempotencyKey 가정: 재전송을 식별하는 필수 1~128자 공백만이 아닌 문자열
     * @param request 변경할 판매 상품과 숙박일별 양수 가격·KRW 통화
     * @return 모든 요청 날짜의 가격 변경 후 계산 상태 또는 동일 요청의 최초 응답
     * @throws InvalidRequestException 날짜·가격·통화·목록·식별자가 유효하지 않거나 변경 날짜가 과거인 경우
     * @throws AccessDeniedException 인증 주체가 파트너 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 숙소나 상품이 없거나 파트너의 소유 범위·해당 숙소 소속이 아닌 경우
     * @throws ConflictException 같은 멱등 키로 숙소·상품·날짜별 가격이 다른 요청을 보낸 경우
     * @throws TemporarilyUnavailableException 요청 전체의 안전한 반영 결과를 확인할 수 없는 경우
     */
    DailySalesPricesResponse updateDailySalesPrices(String partnerId, String propertyId, String idempotencyKey, DailySalesPriceUpdateRequest request);

    /**
     * 파트너 역할과 숙소 소유권, 객실 유형의 숙소 소속을 검증하고 날짜별 공유 OTA 한도·점유·여유를 조회한다.
     * 가정: from 포함·to 제외의 필수 날짜 범위는 1~366일이며 과거 날짜도 현재 저장 설정을 읽을 수 있다.
     * 모든 요청 날짜를 오름차순으로 반환하며, 한도가 없으면 otaLimit과 remaining은 null이고 각 점유량은 0이다.
     * 확정 예약과 observedAt < 선점 만료 시각인 유효 선점만 같은 기준 시점에서 합산하여 occupied를 계산한다.
     * remaining은 같은 시점의 otaLimit - occupied이다. 동일 객실 유형에 연결된 모든 요금제는 이 수량을 공유한다.
     * salesBlocks는 PARTNER, PROPERTY, ROOM_TYPE, ROOM_TYPE_DATE의 적용 차단만 제공한다.
     * 여유가 있거나 차단 목록이 비어도 특정 상품의 가격·요금제·상품 차단을 확인하기 전에는 예약 가능을 보장하지 않는다.
     * 조회는 점유를 직접 수정하거나 만료 정리, 가격 변경, 한도 변경, 판매 재개를 실행하지 않는다.
     *
     * @param partnerId 인증된 파트너 계정 식별자
     * @param propertyId 가정: 필수 1~64자 opaque 숙소 식별자
     * @param roomTypeId 가정: 필수 1~64자 opaque 객실 유형 식별자
     * @param from 포함되는 조회 시작 숙박일
     * @param to 제외되는 조회 종료 숙박일
     * @return 한도·확정점유·유효선점·총점유·여유·객실 수준 차단을 분리한 조회 결과
     * @throws InvalidRequestException 식별자 또는 날짜가 누락되거나 날짜 순서·366일 범위를 만족하지 않는 경우
     * @throws AccessDeniedException 인증 주체가 파트너 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 숙소나 객실 유형이 없거나 파트너의 소유 범위·해당 숙소 소속이 아닌 경우
     * @throws TemporarilyUnavailableException 현재 수량·차단을 일관된 시점으로 읽을 수 없는 경우
     */
    DailySalesInventoryResponse getDailySalesInventory(String partnerId, String propertyId, String roomTypeId, LocalDate from, LocalDate to);

    /**
     * 파트너 역할·숙소 소유권과 객실 유형 소속을 검증한 후 날짜별 OTA 판매 한도를 모두 변경하거나 모두 거절한다.
     * 한도는 0 이상이며 실제 보유 객실 수를 뜻하지 않는다. 점유량과 판매 여유는 클라이언트의 편집 입력으로 받지 않는다.
     * 가정: 한 요청은 한 객실 유형의 1~366개 날짜이며 중복 날짜·null 항목·변경 당시 Asia/Seoul 오늘 이전 날짜는 400이다.
     * 한도 변경과 예약·선점·해제·만료 평가가 경쟁하면 날짜별 재고에 대해 동일한 처리 순서를 정한다.
     * 변경을 적용하는 같은 판단 시점에서 확정 예약과 유효 선점을 합산한다. 한 날짜라도 요청 한도가 현재 occupied보다
     * 작으면 409 INVENTORY_LIMIT_BELOW_OCCUPANCY로 전체 요청을 거절하며 앞의 날짜도 일부 적용하지 않는다.
     * 유효 선점은 판단 시각이 만료 시각보다 작은 경우만 포함하며 정리 작업 여부로 만료 선점을 점유에 남기지 않는다.
     * 같은 객실의 여러 요금제에 한도를 복제하지 않으며 변경 후에도 초과 점유나 음수 여유를 허용하지 않는다.
     * 한도 증가·0으로 변경·다시 증가한 사실만으로 판매 중단을 해제하지 않고 가격과 기존 확정 약속도 보존한다.
     * 가정: 파트너·작업·멱등 키가 같으면 경로의 숙소, 객실 유형, 날짜별 한도를 비교한다. 날짜 순서만 정규화하며
     * 같은 내용은 최초 응답을 재생하고 다른 내용은 이미 반영됐어도 409이다. 인증·소유권 확인 후 멱등 재생을 먼저 수행한다.
     * 응답은 변경한 날짜만 오름차순으로 제공하며 새 한도와 같은 observedAt의 점유·여유·차단을 제공한다.
     * 원자적 반영 결과를 확인할 수 없는 장애에는 성공을 가정하지 않으며 동일 키 재요청으로 결과를 확인한다.
     *
     * @param partnerId 인증된 파트너 계정 식별자
     * @param propertyId 가정: 필수 1~64자 opaque 숙소 식별자
     * @param idempotencyKey 가정: 필수 1~128자 공백만이 아닌 문자열로 변경 재전송을 식별한다.
     * @param request 변경할 객실 유형과 숙박일별 OTA 판매 한도
     * @return 모든 요청 날짜의 변경 후 계산 상태 또는 동일 요청의 최초 응답
     * @throws InvalidRequestException 식별자·목록·날짜·한도가 유효하지 않거나 변경 날짜가 과거인 경우
     * @throws AccessDeniedException 인증 주체가 파트너 역할을 갖지 않는 경우
     * @throws ResourceNotFoundException 숙소나 객실 유형이 없거나 파트너의 소유 범위·해당 숙소 소속이 아닌 경우
     * @throws ConflictException 요청 한도가 현재 점유량보다 작거나 같은 멱등 키에 다른 요청 내용이 사용된 경우
     * @throws TemporarilyUnavailableException 요청 전체의 안전한 반영 결과를 확인할 수 없는 경우
     */
    DailySalesInventoryResponse updateDailySalesLimits(String partnerId, String propertyId, String idempotencyKey, DailySalesLimitUpdateRequest request);
}

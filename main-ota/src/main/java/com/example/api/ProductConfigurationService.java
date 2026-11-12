package com.example.api;

import com.example.api.dto.request.*;
import com.example.api.dto.response.*;

/**
 * 인증된 파트너의 자기 숙소 상품 구성 계약. 저장은 예약 수락의 조건 재검증과 일관된 순서로 반영한다.
 * 기존 예약의 상품·정원·혜택·취소·결제 스냅샷은 보존하고 변경 요청으로 덮어쓰지 않는다.
 * 실제 보유수량·날짜별 가격·판매한도·판매 중단과 PG 연동 구현은 이 인터페이스의 책임이 아니다.
 */
public interface ProductConfigurationService {

    /**
     * 인증된 파트너가 소유한 숙소만 조회하여 이후 구성·가격·판매상태·예약 조회의 대상을 선택한다. 가정: propertyId 오름차순 커서 페이지이며 페이지 크기는 기본 20, 허용 1~100이다. 조회 중 소유권이 바뀐 숙소는 다시 소유권을 검증해 제외한다. 소유 숙소가 없거나 요청 커서 이후에 대상이 없으면 200으로 items 빈 배열과 nextCursor=null을 반환한다. 파트너 역할을 확인하고 현재 소유한 숙소만 페이지에 포함한다. 목록 조회에는 특정 숙소 식별자나 주소 검색어 입력이 없다.
     * @throws InvalidRequestException limit가 정수 1~100이 아니거나 cursor가 1~1024자 문자열이 아니다. 다른 파트너 또는 다른 조회 조건에 발급한 커서도 거절한다. 누락된 limit는 20, 누락된 cursor는 첫 페이지이다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogManagedPropertiesResponse listManagedProperties(String partnerId, Integer limit, String cursor);

    /**
     * 자기 숙소의 기본정보·객실 유형·요금제·연결 상품을 동일 조회 기준으로 읽는다. 조회는 가격·재고·판매상태·예약을 변경하지 않는다. 가정: configuredPropertyId=null이면 기본정보 미구성이며 조회 대상 propertyId를 제외한 모든 property 접두어 필드도 null이다. configuredPropertyId가 있으면 propertyId와 같고 propertyVersion을 포함한 필수 기본정보는 null일 수 없다. propertyPhotos는 미구성이면 null, 구성되었으면 1~50장이며 빈 배열은 불가하다. propertyAmenityIds·propertyHighlightAmenityIds·propertyAddressLocalizedAddresses의 빈 배열은 구성된 상태에서 해당 항목이 없다는 뜻이다. propertyPhotos 및 roomTypes[].photos 각 항목의 assetId와 assetMetadataId는 같은 자산이며 asset 접두어 파일 정보는 그 자산에 속한다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다.
     * @throws InvalidRequestException propertyId가 필수 1~64자 숙소 식별자 형식을 만족하지 않는다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소가 없거나 인증된 파트너의 소유 범위에 없다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogConfigurationResponse getProductConfiguration(String partnerId, String propertyId);

    /**
     * 자기 숙소 구성에 사용할 시설·침대·전망·사진 태그·혜택·취소·결제 선택지와 업로드 제한을 읽는다. 기존 선택은 구성 조회값으로 판단한다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다.
     * @throws InvalidRequestException propertyId가 필수 1~64자 숙소 식별자 형식을 만족하지 않는다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소가 없거나 인증된 파트너의 소유 범위에 없다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogConfigurationOptionsResponse getProductConfigurationOptions(String partnerId, String propertyId);

    /**
     * 입력 주소에 맞는 국내 후보와 좌표를 조회한다. 후보 선택은 주소 저장이 아니며 저장할 때 후보와 입력 주소의 일치를 다시 확인한다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다.
     * @throws InvalidRequestException propertyId가 필수 1~64자 문자열이 아니거나 query가 필수 1~200자 주소 검색어가 아니다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소가 없거나 인증된 파트너의 소유 범위에 없다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogLocationSuggestionsResponse getCatalogLocationSuggestions(String partnerId, String propertyId, String query);

    /**
     * 자기 숙소의 설명·주소·시설·사진을 전체 교체한다. 누락된 선택값은 제거하며 소유 계정은 변경하지 않는다. 기존 예약의 숙소 설명과 약속은 보존한다. 가정: 변경 요청은 Idempotency-Key 필수 1~128자이며 파트너·operation·키 단위로 최초 경로와 본문을 보존한다. 같은 입력은 최초 결과를 재생하고 다른 경로나 본문은 409로 거절한다. 가정: expectedVersion이 현재 대상 버전과 다르면 409이며 변경하지 않는다. 모든 필드 검증을 통과해야 해당 구성 전체를 원자적으로 교체한다. 응답의 각 photos 항목에서 assetId와 assetMetadataId는 동일한 업로드 자산 식별자이며 assetUrl·assetMediaType·assetWidthPixels·assetHeightPixels·assetSizeBytes는 그 자산의 검증된 파일 정보다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다. 숙소 시설·사진 태그 선택지의 존재와 적용 대상을 재검증한다. 시설 식별자와 언어별 주소의 언어 태그가 각 배열에서 중복되면 400이다. 사진 자산은 동일 숙소 소유여야 하며 현재 다른 귀속 대상에 연결된 자산은 409로 거절한다. 사진 배열은 1~50장, 자산·표시순서는 중복 없이, 대표사진은 정확히 한 장이어야 한다. 태그는 최대 한 개이며 적용 가능한 선택지를 검증한다. 교체에서 빠진 사진은 해당 구성에서만 연결 해제하고 예약 당시 참조 자산은 보존한다. 주요시설은 숙소 전체시설의 부분집합이어야 한다. 주소 후보를 지정했으면 동일 주소인지 재확인하고 불일치하면 400이다. 소유자 표시 이름은 인증 계정에서 읽으며 본문으로 소유권을 바꿀 수 없다.
     * @throws InvalidRequestException 경로 숙소 식별자·멱등 키·expectedVersion의 형식 또는 숙소 이름·소개·주소의 필수값과 길이가 유효하지 않다. 국내 국가 KR·5자리 우편번호·주소 후보 일치, 언어 태그 중복 금지, 숙소 시설 선택 및 중복 금지, 주요시설의 전체시설 부분집합, 사진 1~50장·자산과 표시순서 중복 금지·대표 한 장·태그 선택 조건을 만족해야 한다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소가 없거나 파트너 소유 범위에 없다. 선택한 사진 자산이 없거나 해당 숙소 소유가 아닌 경우도 정보를 노출하지 않고 거절한다.
     * @throws ConflictException expectedVersion이 현재 숙소 구성 버전과 다르거나 사진 자산이 현재 다른 귀속 대상에 연결되어 있다. 같은 파트너·작업·멱등 키에 이전과 다른 경로나 본문을 보내도 변경 없이 거절한다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogPropertyResponse updateCatalogProperty(String partnerId, String propertyId, String idempotencyKey, CatalogPropertyUpdateRequest request);

    /**
     * 자기 숙소의 이미지 파일 하나를 실제 내용으로 검증하고 불변 자산을 만든다. 업로드 후 구성의 사진 배열에 자산을 지정해야 숙소 또는 객실에 표시된다. 가정: 변경 요청은 Idempotency-Key 필수 1~128자이며 파트너·operation·키 단위로 최초 경로와 본문을 보존한다. 같은 입력은 최초 결과를 재생하고 다른 경로나 본문은 409로 거절한다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다. Base64를 표준 형식으로 디코딩하고 실제 이미지 형식·픽셀·바이트를 검증한다. JPEG/PNG만, 너비>=800·높이>=600, 원본<=4,000,000바이트이며 최대 Base64 길이는 5,333,336자다. 실패한 업로드는 구성에 연결하지 않는다. 파일명은 저장 경로로 쓰지 않는다.
     * @throws InvalidRequestException 숙소 식별자·멱등 키의 형식, fileName 1~255자, mediaType image/jpeg 또는 image/png, 표준 Base64 길이 4~5,333,336자 조건을 만족하지 않는다. 디코딩한 실제 형식은 mediaType과 같아야 하며 너비 800픽셀 이상·높이 600픽셀 이상·원본 4,000,000바이트 이하여야 한다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소가 없거나 인증된 파트너의 소유 범위에 없다.
     * @throws ConflictException 같은 파트너·업로드 작업·멱등 키에 이전과 다른 숙소 경로나 업로드 본문을 보내면 새 자산을 만들지 않고 거절한다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogUploadedPhotoResponse uploadCatalogPhoto(String partnerId, String propertyId, String idempotencyKey, CatalogPhotoUploadRequest request);

    /**
     * 자기 숙소에 설명·총정원·침대·시설·사진으로 객실 유형을 생성한다. 실제 객실 보유수량이나 날짜별 OTA 판매한도를 만들거나 늘리지 않는다. 가정: 변경 요청은 Idempotency-Key 필수 1~128자이며 파트너·operation·키 단위로 최초 경로와 본문을 보존한다. 같은 입력은 최초 결과를 재생하고 다른 경로나 본문은 409로 거절한다. 응답의 각 photos 항목에서 assetId와 assetMetadataId는 동일한 업로드 자산 식별자이며 assetUrl·assetMediaType·assetWidthPixels·assetHeightPixels·assetSizeBytes는 그 자산의 검증된 파일 정보다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다. 양수 총정원과 선택 면적·욕실 수를 검증하고 객실 시설·침대 종류·전망·사진 태그 선택지의 존재와 적용 대상을 재검증한다. 시설 식별자와 침대 종류가 각 배열에서 중복되면 400이다. 침대 종류별 수량은 양수이며 전체 종류는 1~20개다. 사진 자산은 동일 숙소 소유여야 하며 현재 다른 귀속 대상에 연결된 자산은 409로 거절한다. 사진 배열은 1~50장, 자산·표시순서는 중복 없이, 대표사진은 정확히 한 장이어야 한다. 태그는 최대 한 개이며 적용 가능한 선택지를 검증한다.
     * @throws InvalidRequestException 숙소 식별자·멱등 키 형식 또는 객실 이름·설명의 필수값과 길이가 유효하지 않다. 총정원은 양수, 등록 면적은 0 초과, 욕실 수는 0 이상이어야 한다. 침대 1~20종류·양수 수량·종류 중복 금지, 객실 시설·전망·사진 태그의 유효한 선택, 시설 중복 금지, 사진 1~50장·자산과 표시순서 중복 금지·대표 한 장 조건을 만족해야 한다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소가 없거나 파트너 소유 범위에 없다. 선택한 사진 자산이 없거나 해당 숙소 소유가 아닌 경우도 정보를 노출하지 않고 거절한다.
     * @throws ConflictException 선택한 사진 자산이 현재 다른 귀속 대상에 연결되어 있거나 같은 파트너·생성 작업·멱등 키에 이전과 다른 경로나 본문을 보내면 객실 유형을 생성하지 않는다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogRoomResponse createCatalogRoomType(String partnerId, String propertyId, String idempotencyKey, CatalogRoomCreateRequest request);

    /**
     * 자기 숙소의 기존 객실 구성을 전체 교체한다. 새 정원은 앞으로 수락할 예약에 사용하고 기존 확정 예약의 인원·정원 약속을 바꾸지 않는다. 가정: 변경 요청은 Idempotency-Key 필수 1~128자이며 파트너·operation·키 단위로 최초 경로와 본문을 보존한다. 같은 입력은 최초 결과를 재생하고 다른 경로나 본문은 409로 거절한다. 가정: expectedVersion이 현재 대상 버전과 다르면 409이며 변경하지 않는다. 모든 필드 검증을 통과해야 해당 구성 전체를 원자적으로 교체한다. 응답의 각 photos 항목에서 assetId와 assetMetadataId는 동일한 업로드 자산 식별자이며 assetUrl·assetMediaType·assetWidthPixels·assetHeightPixels·assetSizeBytes는 그 자산의 검증된 파일 정보다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다. 양수 총정원과 선택 면적·욕실 수를 검증하고 객실 시설·침대 종류·전망·사진 태그 선택지의 존재와 적용 대상을 재검증한다. 시설 식별자와 침대 종류가 각 배열에서 중복되면 400이다. 침대 종류별 수량은 양수이며 전체 종류는 1~20개다. 사진 자산은 동일 숙소 소유여야 하며 현재 다른 귀속 대상에 연결된 자산은 409로 거절한다. 사진 배열은 1~50장, 자산·표시순서는 중복 없이, 대표사진은 정확히 한 장이어야 한다. 태그는 최대 한 개이며 적용 가능한 선택지를 검증한다. 교체에서 빠진 사진은 해당 구성에서만 연결 해제하고 예약 당시 참조 자산은 보존한다.
     * @throws InvalidRequestException 숙소·객실 식별자·멱등 키·expectedVersion 형식 또는 객실 이름·설명의 필수값과 길이가 유효하지 않다. 총정원은 양수, 등록 면적은 0 초과, 욕실 수는 0 이상이어야 한다. 침대 1~20종류·양수 수량·종류 중복 금지, 객실 시설·전망·사진 태그의 유효한 선택, 시설 중복 금지, 사진 1~50장·자산과 표시순서 중복 금지·대표 한 장 조건을 만족해야 한다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소 또는 수정할 객실 유형이 없거나 파트너 소유 범위에 없다. 선택한 사진 자산이 없거나 해당 숙소 소유가 아닌 경우도 정보를 노출하지 않고 거절한다.
     * @throws ConflictException expectedVersion이 현재 객실 유형 버전과 다르거나 사진 자산이 현재 다른 귀속 대상에 연결되어 있다. 같은 파트너·수정 작업·멱등 키에 이전과 다른 경로나 본문을 보내도 변경하지 않는다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogRoomResponse updateCatalogRoomType(String partnerId, String propertyId, String roomTypeId, String idempotencyKey, CatalogRoomUpdateRequest request);

    /**
     * 자기 숙소에 혜택·취소·결제 조건을 가진 요금제를 생성한다. 무료 취소 설정 일수와 나중 결제 설정 일수는 독립적이며 요금제 추가로 객실 수량이 증가하지 않는다. 가정: 변경 요청은 Idempotency-Key 필수 1~128자이며 파트너·operation·키 단위로 최초 경로와 본문을 보존한다. 같은 입력은 최초 결과를 재생하고 다른 경로나 본문은 409로 거절한다. 응답에서는 cancellationPolicyKind와 cancellationPolicyFreeCancellationDaysBeforeCheckIn으로 취소 조건을, paymentTermsTiming과 paymentTermsScheduledDaysBeforeCheckIn으로 결제 조건을 각각 제공한다. 요청의 cancellationPolicy와 paymentTerms는 별도 중첩 객체를 유지하며 두 설정 일수는 독립적이다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다. 고객용 이름·내부 이름은 각각 1~200자이며 혜택 배열은 최대 50개다. 혜택 optionId가 있으면 존재하며 사용 가능한 포함 서비스 선택지인지 검증한다. 직접 작성한 혜택은 optionId=null로 받는다. FREE_CANCELLATION이면 무료 취소 일수 N>=0가 필요하고 NON_REFUNDABLE이면 그 값은 null이어야 한다. LATER이면 결제 일수 N>=0가 필요하고 IMMEDIATE이면 null이어야 한다. 두 N은 독립이다. 혜택 quantity와 unit은 둘 다 null이거나 양수 수량과 단위 문자열이 함께 있어야 한다. 제공 조건 문자열은 비어 있을 수 있다.
     * @throws InvalidRequestException 숙소 식별자·멱등 키 형식 또는 고객용·내부용 이름과 혜택의 필수값·길이가 유효하지 않다. 혜택은 최대 50개이고 optionId가 있으면 사용 가능한 혜택 선택지여야 하며 수량·단위는 함께 null이거나 양수 수량과 단위를 함께 제공해야 한다. FREE_CANCELLATION과 LATER는 각각 독립된 음이 아닌 N을 요구하고 NON_REFUNDABLE과 IMMEDIATE는 해당 N이 null이어야 한다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소가 없거나 인증된 파트너의 소유 범위에 없다.
     * @throws ConflictException 같은 파트너·요금제 생성 작업·멱등 키에 이전과 다른 숙소 경로나 본문을 보내면 요금제를 생성하지 않는다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogRatePlanResponse createCatalogRatePlan(String partnerId, String propertyId, String idempotencyKey, CatalogRatePlanCreateRequest request);

    /**
     * 자기 숙소 요금제의 현재 혜택·취소·결제 조건을 전체 교체한다. 이미 확정된 예약의 당시 조건을 다시 계산하지 않는다. 가정: 변경 요청은 Idempotency-Key 필수 1~128자이며 파트너·operation·키 단위로 최초 경로와 본문을 보존한다. 같은 입력은 최초 결과를 재생하고 다른 경로나 본문은 409로 거절한다. 가정: expectedVersion이 현재 대상 버전과 다르면 409이며 변경하지 않는다. 모든 필드 검증을 통과해야 해당 구성 전체를 원자적으로 교체한다. 응답에서는 cancellationPolicyKind와 cancellationPolicyFreeCancellationDaysBeforeCheckIn으로 취소 조건을, paymentTermsTiming과 paymentTermsScheduledDaysBeforeCheckIn으로 결제 조건을 각각 제공한다. 요청의 cancellationPolicy와 paymentTerms는 별도 중첩 객체를 유지하며 두 설정 일수는 독립적이다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다. 고객용 이름·내부 이름은 각각 1~200자이며 혜택 배열은 최대 50개다. 혜택 optionId가 있으면 존재하며 사용 가능한 포함 서비스 선택지인지 검증한다. 직접 작성한 혜택은 optionId=null로 받는다. FREE_CANCELLATION이면 무료 취소 일수 N>=0가 필요하고 NON_REFUNDABLE이면 그 값은 null이어야 한다. LATER이면 결제 일수 N>=0가 필요하고 IMMEDIATE이면 null이어야 한다. 두 N은 독립이다. 혜택 quantity와 unit은 둘 다 null이거나 양수 수량과 단위 문자열이 함께 있어야 한다. 제공 조건 문자열은 비어 있을 수 있다.
     * @throws InvalidRequestException 숙소·요금제 식별자·멱등 키·expectedVersion 형식 또는 고객용·내부용 이름과 혜택의 필수값·길이가 유효하지 않다. 혜택은 최대 50개이고 optionId가 있으면 사용 가능한 혜택 선택지여야 하며 수량·단위는 함께 null이거나 양수 수량과 단위를 함께 제공해야 한다. FREE_CANCELLATION과 LATER는 각각 독립된 음이 아닌 N을 요구하고 NON_REFUNDABLE과 IMMEDIATE는 해당 N이 null이어야 한다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소 또는 요금제가 없거나 인증된 파트너가 소유한 같은 숙소의 대상이 아니다.
     * @throws ConflictException expectedVersion이 현재 요금제 버전과 다르거나 같은 파트너·수정 작업·멱등 키에 이전과 다른 경로나 본문을 보내면 요금제를 변경하지 않는다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogRatePlanResponse updateCatalogRatePlan(String partnerId, String propertyId, String ratePlanId, String idempotencyKey, CatalogRatePlanUpdateRequest request);

    /**
     * 같은 숙소의 객실 유형 하나와 요금제 하나를 연결한다. 같은 조합은 한 번만 만들며 연결 성공은 날짜별 가격이나 판매 가능·예약 확보를 보장하지 않는다. 가정: 변경 요청은 Idempotency-Key 필수 1~128자이며 파트너·operation·키 단위로 최초 경로와 본문을 보존한다. 같은 입력은 최초 결과를 재생하고 다른 경로나 본문은 409로 거절한다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다. 객실 유형·요금제의 숙소 일치와 동일 조합 유일성을 같은 변경 경계에서 검사한다. 요금제를 추가해도 객실 유형·숙박일의 공유 한도는 증가하지 않는다. 연결된 두 식별자는 생성 후 불변이다.
     * @throws InvalidRequestException 숙소·객실 유형·요금제 식별자가 필수 1~64자 문자열이 아니거나 멱등 키가 필수 1~128자 문자열이 아니다. 판매 상품 name은 필수 1~200자, description은 필수 1~5000자여야 한다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소나 연결할 객실 유형·요금제가 없거나 모두 인증된 파트너의 동일 숙소에 속하지 않는다.
     * @throws ConflictException 같은 숙소의 객실 유형·요금제 조합이 이미 연결되어 있거나 같은 파트너·연결 작업·멱등 키에 이전과 다른 경로나 본문을 보내면 판매 상품을 생성하지 않는다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogProductResponse createCatalogProduct(String partnerId, String propertyId, String idempotencyKey, CatalogProductCreateRequest request);

    /**
     * 자기 숙소 판매 상품의 고객용 이름과 설명만 변경한다. 객실 유형·요금제 연결을 바꾸지 않으며 기존 예약의 약속도 변경하지 않는다. 가정: 변경 요청은 Idempotency-Key 필수 1~128자이며 파트너·operation·키 단위로 최초 경로와 본문을 보존한다. 같은 입력은 최초 결과를 재생하고 다른 경로나 본문은 409로 거절한다. 가정: expectedVersion이 현재 대상 버전과 다르면 409이며 변경하지 않는다. 모든 필드 검증을 통과해야 해당 구성 전체를 원자적으로 교체한다. 파트너 역할과 숙소 소유권을 검사하며 타인 소유 대상은 존재를 노출하지 않고 404이다. 이름 1~200자와 설명 1~5000자만 변경 입력으로 받으며, expectedVersion은 필수 0 이상 정수로 현재 버전과 비교한다. 연결된 객실 유형·요금제와 그 구성은 변경 대상이 아니다.
     * @throws InvalidRequestException 숙소·판매 상품 식별자가 필수 1~64자 문자열이 아니거나 멱등 키가 필수 1~128자 문자열이 아니다. expectedVersion은 필수 0 이상 정수이며 name은 필수 1~200자, description은 필수 1~5000자여야 한다.
     * @throws UnauthenticatedException 파트너 인증이 없거나 유효하지 않다.
     * @throws AccessDeniedException 인증 주체가 파트너 역할이 아니다.
     * @throws ResourceNotFoundException 경로의 숙소 또는 판매 상품이 없거나 인증된 파트너가 소유한 같은 숙소의 대상이 아니다.
     * @throws ConflictException expectedVersion이 현재 판매 상품 버전과 다르거나 같은 파트너·설명 수정 작업·멱등 키에 이전과 다른 경로나 본문을 보내면 이름과 설명을 변경하지 않는다.
     * @throws TemporarilyUnavailableException 조회 또는 변경을 안전하게 처리할 내부 경로가 일시적으로 불가하다.
     */
    CatalogProductResponse updateCatalogProduct(String partnerId, String propertyId, String productId, String idempotencyKey, CatalogProductUpdateRequest request);
}

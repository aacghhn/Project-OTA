package com.example.api;

import com.example.api.dto.response.*;

/** 공개 검증된 자체 안내와 숙소가 제공한 기존 연락 경로의 읽기 계약. 구현체와 콘텐츠 관리·메시지 전송 기능을 포함하지 않는다. */
public interface GuidanceService {
    /**
     * 로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 가정: 한국어(ko-KR) 분류를 displayOrder와 topicId 순서로 제공한다. 준비된 안내 분류가 없으면 200 NOT_PROVIDED와 빈 목록을 제공한다.
     * 가정: 공개 정보의 게시·검증·철회는 서비스 운영팀이 책임지며 이 조회는 콘텐츠를 갱신하지 않는다.
     * 공개 분류가 없으면 topics=[]와 NOT_PROVIDED를 반환한다.
     * @throws TemporarilyUnavailableException 공개 분류 저장소가 일시적으로 응답하지 않는다. 안내 미제공은 이 오류가 아니라 200 NOT_PROVIDED이다.
     */
    GuidanceTopicsResponse listGuidanceTopics();

    /**
     * 로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 가정: 확인되어 공개 중인 자체 안내만 articleId 오름차순으로 조회하며 분류·검색어·페이지 크기·커서를 선택 입력받는다. 검색 결과가 없으면 200 NO_MATCH 또는 공개 안내 자체가 없으면 200 NOT_PROVIDED다. 목록은 조회 시점 기준이며 콘텐츠 수정 중 여러 페이지의 고정 스냅샷을 보장하지 않는다.
     * 가정: 공개 정보의 게시·검증·철회는 서비스 운영팀이 책임지며 이 조회는 콘텐츠를 갱신하지 않는다.
     * 공개 검증된 안내만 반환한다. 앞뒤 공백을 제거한 검색어로 제목·본문을 부분문자 검색하며 대소문자를 구분하지 않는다.
     * 커서는 topicId·정규화한 keyword·limit에 묶으며 다른 조건 재사용이나 손상은 400으로 거절한다.
     * 페이지마다 현재 공개 상태를 다시 확인해 철회된 답변을 노출하지 않는다. 목록 순서는 articleId 오름차순이다.
     * @param topicId 가정: 선택 1~64자 주제 식별자 topicId로 분류를 좁힌다. 생략은 전체 분류이며 빈 문자열·공백만 있는 값은 허용하지 않는다. 없는 분류는 404이다.
     * @param keyword 가정: 선택 1~100자 keyword로 공개 제목·본문의 부분문자 검색을 한다. 앞뒤 공백을 제거하고 대소문자를 구분하지 않는다. 생략은 검색어 제한 없음이며 공백만 있는 값은 400이다.
     * @param limit 가정: limit는 1~100 정수, 생략 시 20으로 정해 목록 크기를 제한한다.
     * @param cursor 가정: 선택 1~256자 opaque cursor로 이어본다. 생략은 첫 페이지이다. 같은 topicId·정규화한 keyword·limit에서만 재사용하며 조건 불일치나 손상은 400이다.
     * @throws InvalidRequestException 주제·검색어·커서 길이, 공백만 있는 값, 페이지 크기 또는 커서와 검색 조건의 일치 검증에 실패했다.
     * @throws ResourceNotFoundException 지정한 공개 분류가 존재하지 않는다.
     * @throws TemporarilyUnavailableException 공개 안내 저장소가 일시적으로 응답하지 않는다.
     */
    GuidanceArticleListResponse listGuidanceArticles(String topicId, String keyword, Integer limit, String cursor);

    /**
     * 로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 검증된 자체 정책의 대상·조건·결과와 필요한 정보·다음 행동을 반환한다. 가정: 알려진 안내에 답변이 준비되지 않았거나 유효성을 확인하지 못하면 200 NOT_PROVIDED/UNVERIFIED와 빈 답변·행동 목록을 반환한다. 외부의 환불 소요 일수·할인 조건·금융사 입금 시점을 우리 서비스의 보장으로 게시하지 않는다.
     * 가정: 공개 정보의 게시·검증·철회는 서비스 운영팀이 책임지며 이 조회는 콘텐츠를 갱신하지 않는다.
     * AVAILABLE일 때 sections는 한 개 이상이고 maintenanceVerifiedAt은 null이 아니다. maintenanceMaintainer와 maintenanceBasis는 확인 시각이 null인 상태에서도 항상 제공한다. 문단 순서는 중복되지 않는 양수이다.
     * NOT_PROVIDED 또는 UNVERIFIED이면 sections·requiredInformation·nextActions는 빈 배열이며 maintenanceVerifiedAt은 null이다.
     * 외부 도움말의 환불 일수·외부 수단·할인 조건을 자체 보장으로 게시하지 않는다. 현재 예약의 판정은 조회된 개인 예약 결과와 구분한다.
     * @param articleId 안내 식별자. 가정: articleId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.
     * @throws InvalidRequestException 안내 식별자가 비었거나 공백뿐이거나 64자를 초과한다.
     * @throws ResourceNotFoundException 공개 대상으로 식별할 수 있는 안내가 없다.
     * @throws TemporarilyUnavailableException 안내 저장소가 일시적으로 응답하지 않는다. 확인되지 않은 답변은 200 UNVERIFIED로 구분한다.
     */
    GuidanceArticleResponse getGuidanceArticle(String articleId);

    /**
     * 로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. FAQ의 선택·열람·피드백 또는 개인 예약 선택을 선행조건으로 요구하지 않는다. 가정: 숙소가 제공하고 서비스 운영팀이 정확성·공개 허용을 확인한 기존 전화·이메일·HTTPS 경로만 공개한다. 미제공·미확인에는 200 NOT_PROVIDED/UNVERIFIED와 빈 연락 목록을 반환한다. 연락하거나 늦게 도착한다는 사실만으로 무료 취소·환불 완료·노쇼가 되지 않으며 실제 전달·응답·문제 해결 결과는 기록하지 않는다.
     * 가정: 공개 정보의 게시·검증·철회는 서비스 운영팀이 책임지며 이 조회는 콘텐츠를 갱신하지 않는다.
     * 공개 연락 정보의 소유 대상이 propertyId와 일치하는지 검증한다. 개인 예약 소유권이나 로그인은 요구하지 않는다.
     * AVAILABLE일 때 methods는 한 개 이상이고 모든 경로는 확인된 공개 값이다. PHONE/EMAIL/WEB 유형에 맞는 값 하나만 존재하고 나머지 값은 null이다.
     * NOT_PROVIDED/UNVERIFIED이면 methods=[]이고 maintenanceVerifiedAt은 null이다. maintenanceMaintainer와 maintenanceBasis는 날짜 값이 null이어도 제공한다. 미확인 전화·메일·주소를 추측하거나 다른 숙소 경로로 대체하지 않는다.
     * 서비스 운영팀은 숙소가 제공한 연락 값과 공개 허용을 확인한 뒤 게시하고, 정확성을 확인할 수 없으면 제공을 중단한다.
     * 예약 확인 정보는 종류와 준비 조건만 반환한다. 개인의 실제 예약 번호·날짜·이름을 공개 응답에 포함하지 않는다.
     * 연락 안내가 취소 비용 면제, 환불 완료, 노쇼 판정이나 외부 메시지의 전달·접수·해결을 의미하지 않는다.
     * @param propertyId 숙소 식별자. 가정: propertyId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.
     * @throws InvalidRequestException 숙소 식별자가 비었거나 공백뿐이거나 64자를 초과한다.
     * @throws ResourceNotFoundException 공개 대상으로 식별할 수 있는 숙소가 없다.
     * @throws TemporarilyUnavailableException 숙소 연락 정보 저장소가 일시적으로 응답하지 않는다. 연락 정보의 미제공·미확인은 200으로 구분한다.
     */
    GuidancePropertyContactsResponse getPropertyContactMethods(String propertyId);

}

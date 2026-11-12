package com.example.api;

import com.example.api.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** 공개 안내와 숙소 연락 경로의 읽기만 제공한다. 인증 및 FAQ 열람 이력을 요구하지 않는다. */
@RestController
@Validated
@Tag(name = "Public guidance and property contact", description = "공개 안내와 독립된 숙소 연락 경로 조회")
public class GuidanceController {
    private final GuidanceService service;

    public GuidanceController(GuidanceService service) { this.service = service; }

    /** 로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 가정: 한국어(ko-KR) 분류를 displayOrder와 topicId 순서로 제공한다. 준비된 안내 분류가 없으면 200 NOT_PROVIDED와 빈 목록을 제공한다. */
    @GetMapping(value = "/api/public/guidance/topics", produces = "application/json")
    @SecurityRequirements
    @Operation(operationId = "listGuidanceTopics", summary = "공개 안내 분류 조회", description = "로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 가정: 한국어(ko-KR) 분류를 displayOrder와 topicId 순서로 제공한다. 준비된 안내 분류가 없으면 200 NOT_PROVIDED와 빈 목록을 제공한다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "공개 조회 결과이며 안내 미제공·미확인도 정상 본문의 상태로 구분한다.", content = @Content(schema = @Schema(implementation = GuidanceTopicsResponse.class), examples = @ExampleObject(value = "{\"status\":\"AVAILABLE\",\"locale\":\"ko-KR\",\"topics\":[{\"topicId\":\"cancellation\",\"label\":\"예약 취소\",\"displayOrder\":1},{\"topicId\":\"payment-refund\",\"label\":\"결제/환불 정보\",\"displayOrder\":2},{\"topicId\":\"check-in-contact\",\"label\":\"체크인 및 숙소 연락\",\"displayOrder\":3}],\"observedAt\":\"2026-11-11T15:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "503", description = "공개 분류 저장소가 일시적으로 응답하지 않는다. 안내 미제공은 이 오류가 아니라 200 NOT_PROVIDED이다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"공개 분류 저장소가 일시적으로 응답하지 않는다. 안내 미제공은 이 오류가 아니라 200 NOT_PROVIDED이다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}")))
    })
    public ResponseEntity<GuidanceTopicsResponse> listGuidanceTopics() {
        return ResponseEntity.ok(service.listGuidanceTopics());
    }

    /** 로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 가정: 확인되어 공개 중인 자체 안내만 articleId 오름차순으로 조회하며 분류·검색어·페이지 크기·커서를 선택 입력받는다. 검색 결과가 없으면 200 NO_MATCH 또는 공개 안내 자체가 없으면 200 NOT_PROVIDED다. 목록은 조회 시점 기준이며 콘텐츠 수정 중 여러 페이지의 고정 스냅샷을 보장하지 않는다. */
    @GetMapping(value = "/api/public/guidance/articles", produces = "application/json")
    @SecurityRequirements
    @Operation(operationId = "listGuidanceArticles", summary = "공개 안내 목록과 검색", description = "로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 가정: 확인되어 공개 중인 자체 안내만 articleId 오름차순으로 조회하며 분류·검색어·페이지 크기·커서를 선택 입력받는다. 검색 결과가 없으면 200 NO_MATCH 또는 공개 안내 자체가 없으면 200 NOT_PROVIDED다. 목록은 조회 시점 기준이며 콘텐츠 수정 중 여러 페이지의 고정 스냅샷을 보장하지 않는다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "공개 조회 결과이며 안내 미제공·미확인도 정상 본문의 상태로 구분한다.", content = @Content(schema = @Schema(implementation = GuidanceArticleListResponse.class), examples = @ExampleObject(value = "{\"status\":\"AVAILABLE\",\"locale\":\"ko-KR\",\"items\":[{\"articleId\":\"cancel-cost\",\"topicId\":\"cancellation\",\"question\":\"예약을 취소하면 얼마를 부담하나요?\",\"summary\":\"예약 당시 취소 조건과 취소 요청 시각으로 비용을 판단합니다.\",\"updatedAt\":\"2026-11-11T15:00:00+09:00\"}],\"nextCursor\":null,\"observedAt\":\"2026-11-11T15:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "주제·검색어·커서 길이, 공백만 있는 값, 페이지 크기 또는 커서와 검색 조건의 일치 검증에 실패했다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"주제·검색어·커서 길이, 공백만 있는 값, 페이지 크기 또는 커서와 검색 조건의 일치 검증에 실패했다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "지정한 공개 분류가 존재하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\",\"message\":\"지정한 공개 분류가 존재하지 않는다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "공개 안내 저장소가 일시적으로 응답하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"공개 안내 저장소가 일시적으로 응답하지 않는다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}")))
    })
    public ResponseEntity<GuidanceArticleListResponse> listGuidanceArticles(
        // 가정: 선택 1~64자 주제 식별자 topicId로 분류를 좁힌다. 생략은 전체 분류이며 빈 문자열·공백만 있는 값은 허용하지 않는다. 없는 분류는 404이다.
        @Parameter(description = "가정: 선택 1~64자 주제 식별자 topicId로 분류를 좁힌다. 생략은 전체 분류이며 빈 문자열·공백만 있는 값은 허용하지 않는다. 없는 분류는 404이다.", required = false, schema = @Schema(type = "string", minLength = 1, maxLength = 64, pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "cancellation")
        @RequestParam(name = "topicId", required = false) @Size(min = 1, max = 64) @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$") String topicId,
        // 가정: 선택 1~100자 keyword로 공개 제목·본문의 부분문자 검색을 한다. 앞뒤 공백을 제거하고 대소문자를 구분하지 않는다. 생략은 검색어 제한 없음이며 공백만 있는 값은 400이다.
        @Parameter(description = "가정: 선택 1~100자 keyword로 공개 제목·본문의 부분문자 검색을 한다. 앞뒤 공백을 제거하고 대소문자를 구분하지 않는다. 생략은 검색어 제한 없음이며 공백만 있는 값은 400이다.", required = false, schema = @Schema(type = "string", minLength = 1, maxLength = 100, pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "\ucde8\uc18c")
        @RequestParam(name = "keyword", required = false) @Size(min = 1, max = 100) @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$") String keyword,
        // 가정: limit는 1~100 정수, 생략 시 20으로 정해 목록 크기를 제한한다.
        @Parameter(description = "가정: limit는 1~100 정수, 생략 시 20으로 정해 목록 크기를 제한한다.", required = false, schema = @Schema(type = "integer", minimum = "1", maximum = "100", defaultValue = "20"), example = "20")
        @RequestParam(name = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit,
        // 가정: 선택 1~256자 opaque cursor로 이어본다. 생략은 첫 페이지이다. 같은 topicId·정규화한 keyword·limit에서만 재사용하며 조건 불일치나 손상은 400이다.
        @Parameter(description = "가정: 선택 1~256자 opaque cursor로 이어본다. 생략은 첫 페이지이다. 같은 topicId·정규화한 keyword·limit에서만 재사용하며 조건 불일치나 손상은 400이다.", required = false, schema = @Schema(type = "string", minLength = 1, maxLength = 256, pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "cursor-next")
        @RequestParam(name = "cursor", required = false) @Size(min = 1, max = 256) @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$") String cursor
    ) {
        return ResponseEntity.ok(service.listGuidanceArticles(topicId, keyword, limit, cursor));
    }

    /** 로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 검증된 자체 정책의 대상·조건·결과와 필요한 정보·다음 행동을 반환한다. 가정: 알려진 안내에 답변이 준비되지 않았거나 유효성을 확인하지 못하면 200 NOT_PROVIDED/UNVERIFIED와 빈 답변·행동 목록을 반환한다. 외부의 환불 소요 일수·할인 조건·금융사 입금 시점을 우리 서비스의 보장으로 게시하지 않는다. */
    @GetMapping(value = "/api/public/guidance/articles/{articleId}", produces = "application/json")
    @SecurityRequirements
    @Operation(operationId = "getGuidanceArticle", summary = "공개 안내 내용과 적용 조건 조회", description = "로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. 검증된 자체 정책의 대상·조건·결과와 필요한 정보·다음 행동을 반환한다. 가정: 알려진 안내에 답변이 준비되지 않았거나 유효성을 확인하지 못하면 200 NOT_PROVIDED/UNVERIFIED와 빈 답변·행동 목록을 반환한다. 외부의 환불 소요 일수·할인 조건·금융사 입금 시점을 우리 서비스의 보장으로 게시하지 않는다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "공개 조회 결과이며 안내 미제공·미확인도 정상 본문의 상태로 구분한다.", content = @Content(schema = @Schema(implementation = GuidanceArticleResponse.class), examples = @ExampleObject(value = "{\"articleId\":\"cancel-cost\",\"topicId\":\"cancellation\",\"question\":\"예약을 취소하면 얼마를 부담하나요?\",\"status\":\"AVAILABLE\",\"statusMessage\":\"예약 당시 취소 조건과 요청 시각을 확인해 주세요.\",\"applicabilityServiceScope\":\"DOMESTIC_ACCOMMODATION\",\"applicabilityConditions\":[\"확정된 국내 숙소 예약의 고객 전체 취소에 적용합니다.\"],\"applicabilityTimeZone\":\"Asia/Seoul\",\"sections\":[{\"order\":1,\"heading\":\"취소 비용 판단\",\"body\":\"예약 당시 무료 취소 기한보다 앞서 취소하면 비용은 0원입니다. 기한과 같거나 이후이면 예약 총액을 부담합니다. 환불 불가 예약도 총액을 부담합니다.\"},{\"order\":2,\"heading\":\"환불 진행 확인\",\"body\":\"예약 취소와 환불 완료는 별개입니다. 진행 중이거나 결과가 불명인 결제·환불은 기존 결과를 확인하며 완료로 표시하지 않습니다.\"}],\"requiredInformation\":[],\"nextActions\":[{\"kind\":\"PREVIEW_CANCELLATION\",\"label\":\"취소 비용 확인\",\"instruction\":\"본인의 예약을 선택해 취소 비용을 확인한 뒤 전체 취소를 명시적으로 요청해 주세요.\",\"requiredContext\":[\"RESERVATION_ID\"]}],\"maintenanceMaintainer\":\"SERVICE_OPERATIONS\",\"maintenanceBasis\":\"PROJECT_POLICY\",\"maintenanceUpdatedAt\":\"2026-11-11T15:00:00+09:00\",\"maintenanceVerifiedAt\":\"2026-11-11T15:00:00+09:00\",\"observedAt\":\"2026-11-11T15:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "안내 식별자가 비었거나 공백뿐이거나 64자를 초과한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"안내 식별자가 비었거나 공백뿐이거나 64자를 초과한다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "공개 대상으로 식별할 수 있는 안내가 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\",\"message\":\"공개 대상으로 식별할 수 있는 안내가 없다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "안내 저장소가 일시적으로 응답하지 않는다. 확인되지 않은 답변은 200 UNVERIFIED로 구분한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"안내 저장소가 일시적으로 응답하지 않는다. 확인되지 않은 답변은 200 UNVERIFIED로 구분한다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}")))
    })
    public ResponseEntity<GuidanceArticleResponse> getGuidanceArticle(
        // 안내 식별자. 가정: articleId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "안내 식별자. 가정: articleId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", minLength = 1, maxLength = 64, pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "cancel-cost")
        @PathVariable("articleId") @NotBlank @Size(min = 1, max = 64) String articleId
    ) {
        return ResponseEntity.ok(service.getGuidanceArticle(articleId));
    }

    /** 로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. FAQ의 선택·열람·피드백 또는 개인 예약 선택을 선행조건으로 요구하지 않는다. 가정: 숙소가 제공하고 서비스 운영팀이 정확성·공개 허용을 확인한 기존 전화·이메일·HTTPS 경로만 공개한다. 미제공·미확인에는 200 NOT_PROVIDED/UNVERIFIED와 빈 연락 목록을 반환한다. 연락하거나 늦게 도착한다는 사실만으로 무료 취소·환불 완료·노쇼가 되지 않으며 실제 전달·응답·문제 해결 결과는 기록하지 않는다. */
    @GetMapping(value = "/api/public/properties/{propertyId}/contact-methods", produces = "application/json")
    @SecurityRequirements
    @Operation(operationId = "getPropertyContactMethods", summary = "FAQ와 무관한 숙소 연락 경로 조회", description = "로그인 없이 누구나 조회한다. 조회·반복 조회는 예약·결제·취소·환불 상태를 바꾸지 않고 상담 접수·메시지 저장·전달·피드백 수집을 실행하지 않는다. FAQ의 선택·열람·피드백 또는 개인 예약 선택을 선행조건으로 요구하지 않는다. 가정: 숙소가 제공하고 서비스 운영팀이 정확성·공개 허용을 확인한 기존 전화·이메일·HTTPS 경로만 공개한다. 미제공·미확인에는 200 NOT_PROVIDED/UNVERIFIED와 빈 연락 목록을 반환한다. 연락하거나 늦게 도착한다는 사실만으로 무료 취소·환불 완료·노쇼가 되지 않으며 실제 전달·응답·문제 해결 결과는 기록하지 않는다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "공개 조회 결과이며 안내 미제공·미확인도 정상 본문의 상태로 구분한다.", content = @Content(schema = @Schema(implementation = GuidancePropertyContactsResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property-1\",\"propertyName\":\"예시 숙소\",\"status\":\"NOT_PROVIDED\",\"statusMessage\":\"숙소 연락 정보가 아직 제공되지 않았습니다.\",\"methods\":[],\"reservationInformation\":[{\"kind\":\"RESERVATION_ID\",\"label\":\"예약 번호\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 예약 번호 항목을 확인해 주세요.\"},{\"kind\":\"REFERENCE_CODE\",\"label\":\"별도 참조 번호\",\"availability\":\"IF_PRESENT\",\"instruction\":\"본인의 예약 상세에서 별도 참조 번호가 있는 경우에만 확인해 주세요.\"},{\"kind\":\"CHECK_IN\",\"label\":\"체크인 날짜\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 체크인 날짜 항목을 확인해 주세요.\"},{\"kind\":\"CHECK_OUT\",\"label\":\"체크아웃 날짜\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 체크아웃 날짜 항목을 확인해 주세요.\"},{\"kind\":\"REPRESENTATIVE_GIVEN_NAME\",\"label\":\"대표 투숙객 영문 이름\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 대표 투숙객 영문 이름 항목을 확인해 주세요.\"},{\"kind\":\"REPRESENTATIVE_FAMILY_NAME\",\"label\":\"대표 투숙객 영문 성\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 대표 투숙객 영문 성 항목을 확인해 주세요.\"}],\"maintenanceMaintainer\":\"SERVICE_OPERATIONS\",\"maintenanceBasis\":\"PROPERTY_PROVIDED\",\"maintenanceUpdatedAt\":null,\"maintenanceVerifiedAt\":null,\"observedAt\":\"2026-11-11T15:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "숙소 식별자가 비었거나 공백뿐이거나 64자를 초과한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"숙소 식별자가 비었거나 공백뿐이거나 64자를 초과한다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "공개 대상으로 식별할 수 있는 숙소가 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\",\"message\":\"공개 대상으로 식별할 수 있는 숙소가 없다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "503", description = "숙소 연락 정보 저장소가 일시적으로 응답하지 않는다. 연락 정보의 미제공·미확인은 200으로 구분한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"TEMPORARILY_UNAVAILABLE\",\"message\":\"숙소 연락 정보 저장소가 일시적으로 응답하지 않는다. 연락 정보의 미제공·미확인은 200으로 구분한다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T15:00:00+09:00\",\"violations\":[]}")))
    })
    public ResponseEntity<GuidancePropertyContactsResponse> getPropertyContactMethods(
        // 숙소 식별자. 가정: propertyId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "숙소 식별자. 가정: propertyId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", minLength = 1, maxLength = 64, pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "property-1")
        @PathVariable("propertyId") @NotBlank @Size(min = 1, max = 64) String propertyId
    ) {
        return ResponseEntity.ok(service.getPropertyContactMethods(propertyId));
    }

}

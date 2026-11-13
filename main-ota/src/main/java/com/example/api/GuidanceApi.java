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

/** 공개 안내와 숙소 연락 경로의 읽기만 제공한다. 인증 및 FAQ 열람 이력을 요구하지 않는다. */
@Tag(name = "Public guidance and property contact", description = "공개 안내와 독립된 숙소 연락 경로 조회")
public interface GuidanceApi {

    @SecurityRequirements
    @Operation(operationId = "listGuidanceTopics", summary = "공개 안내 분류 조회", description = "인증 없이 이용할 수 있는 안내 분류를 조회한다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "공개 조회 결과이며 안내 미제공·미확인도 정상 본문의 상태로 구분한다.", content = @Content(schema = @Schema(implementation = GuidanceTopicsResponse.class), examples = @ExampleObject(value = "{\"status\":\"AVAILABLE\",\"locale\":\"ko-KR\",\"topics\":[{\"topicId\":\"cancellation\",\"label\":\"예약 취소\",\"displayOrder\":1},{\"topicId\":\"payment-refund\",\"label\":\"결제/환불 정보\",\"displayOrder\":2},{\"topicId\":\"check-in-contact\",\"label\":\"체크인 및 숙소 연락\",\"displayOrder\":3}],\"observedAt\":\"2026-11-11T15:00:00+09:00\"}")))
    })
    ResponseEntity<GuidanceTopicsResponse> listGuidanceTopics();

    @SecurityRequirements
    @Operation(operationId = "listGuidanceArticles", summary = "공개 안내 목록과 검색", description = "분류와 검색어로 공개 안내 목록을 조회한다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "공개 조회 결과이며 안내 미제공·미확인도 정상 본문의 상태로 구분한다.", content = @Content(schema = @Schema(implementation = GuidanceArticleListResponse.class), examples = @ExampleObject(value = "{\"status\":\"AVAILABLE\",\"locale\":\"ko-KR\",\"items\":[{\"articleId\":\"cancel-cost\",\"topicId\":\"cancellation\",\"question\":\"예약을 취소하면 얼마를 부담하나요?\",\"summary\":\"예약 당시 취소 조건과 취소 요청 시각으로 비용을 판단합니다.\",\"updatedAt\":\"2026-11-11T15:00:00+09:00\"}],\"nextCursor\":null,\"observedAt\":\"2026-11-11T15:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "주제·검색어·커서 길이, 공백만 있는 값, 페이지 크기 또는 커서와 검색 조건의 일치 검증에 실패했다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "지정한 공개 분류가 존재하지 않는다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<GuidanceArticleListResponse> listGuidanceArticles(
        // 가정: 선택 1~64자 주제 식별자 topicId로 분류를 좁힌다. 생략은 전체 분류이며 빈 문자열·공백만 있는 값은 허용하지 않는다. 없는 분류는 404이다.
        @Parameter(description = "가정: 선택 1~64자 주제 식별자 topicId로 분류를 좁힌다. 생략은 전체 분류이며 빈 문자열·공백만 있는 값은 허용하지 않는다.", required = false, schema = @Schema(type = "string", pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "cancellation")
        @Size(min = 1, max = 64) @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$") String topicId,
        // 가정: 선택 1~100자 keyword로 공개 제목·본문의 부분문자 검색을 한다. 앞뒤 공백을 제거하고 대소문자를 구분하지 않는다. 생략은 검색어 제한 없음이며 공백만 있는 값은 400이다.
        @Parameter(description = "가정: 선택 1~100자 keyword로 공개 제목·본문의 부분문자 검색을 한다. 앞뒤 공백을 제거하고 대소문자를 구분하지 않는다.", required = false, schema = @Schema(type = "string", pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "\ucde8\uc18c")
        @Size(min = 1, max = 100) @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$") String keyword,
        // 가정: limit는 1~100 정수, 생략 시 20으로 정해 목록 크기를 제한한다.
        @Parameter(description = "가정: limit는 1~100 정수, 생략 시 20으로 정해 목록 크기를 제한한다.", required = false, schema = @Schema(type = "integer", defaultValue = "20"), example = "20")
        @Min(1) @Max(100) Integer limit,
        // 가정: 선택 1~256자 opaque cursor로 이어본다. 생략은 첫 페이지이다. 같은 topicId·정규화한 keyword·limit에서만 재사용하며 조건 불일치나 손상은 400이다.
        @Parameter(description = "가정: 선택 1~256자 opaque cursor로 이어본다. 생략은 첫 페이지이다.", required = false, schema = @Schema(type = "string", pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "cursor-next")
        @Size(min = 1, max = 256) @Pattern(regexp = "^[\\s\\S]*\\S[\\s\\S]*$") String cursor
    );

    @SecurityRequirements
    @Operation(operationId = "getGuidanceArticle", summary = "공개 안내 내용과 적용 조건 조회", description = "공개 안내의 답변과 적용 조건을 조회한다. 열람만으로 업무 요청이 접수되지는 않는다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "공개 조회 결과이며 안내 미제공·미확인도 정상 본문의 상태로 구분한다.", content = @Content(schema = @Schema(implementation = GuidanceArticleResponse.class), examples = @ExampleObject(value = "{\"articleId\":\"cancel-cost\",\"topicId\":\"cancellation\",\"question\":\"예약을 취소하면 얼마를 부담하나요?\",\"status\":\"AVAILABLE\",\"statusMessage\":\"예약 당시 취소 조건과 요청 시각을 확인해 주세요.\",\"applicabilityServiceScope\":\"DOMESTIC_ACCOMMODATION\",\"applicabilityConditions\":[\"확정된 국내 숙소 예약의 고객 전체 취소에 적용합니다.\"],\"applicabilityTimeZone\":\"Asia/Seoul\",\"sections\":[{\"order\":1,\"heading\":\"취소 비용 판단\",\"body\":\"예약 당시 무료 취소 기한보다 앞서 취소하면 비용은 0원입니다. 기한과 같거나 이후이면 예약 총액을 부담합니다. 환불 불가 예약도 총액을 부담합니다.\"},{\"order\":2,\"heading\":\"환불 진행 확인\",\"body\":\"예약 취소와 환불 완료는 별개입니다. 진행 중이거나 결과가 불명인 결제·환불은 기존 결과를 확인하며 완료로 표시하지 않습니다.\"}],\"requiredInformation\":[],\"nextActions\":[{\"kind\":\"PREVIEW_CANCELLATION\",\"label\":\"취소 비용 확인\",\"instruction\":\"본인의 예약을 선택해 취소 비용을 확인한 뒤 전체 취소를 명시적으로 요청해 주세요.\",\"requiredContext\":[\"RESERVATION_ID\"]}],\"maintenanceMaintainer\":\"SERVICE_OPERATIONS\",\"maintenanceBasis\":\"PROJECT_POLICY\",\"maintenanceUpdatedAt\":\"2026-11-11T15:00:00+09:00\",\"maintenanceVerifiedAt\":\"2026-11-11T15:00:00+09:00\",\"observedAt\":\"2026-11-11T15:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "안내 식별자가 비었거나 공백뿐이거나 64자를 초과한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "공개 대상으로 식별할 수 있는 안내가 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<GuidanceArticleResponse> getGuidanceArticle(
        // 안내 식별자. 가정: articleId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "안내 식별자. 가정: articleId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "cancel-cost")
        @NotBlank @Size(min = 1, max = 64) String articleId
    );

    @SecurityRequirements
    @Operation(operationId = "getPropertyContactMethods", summary = "FAQ와 무관한 숙소 연락 경로 조회", description = "숙소의 공개 연락 방법과 연락할 때 준비할 정보를 조회한다. 안내 열람 없이도 조회할 수 있다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "공개 조회 결과이며 안내 미제공·미확인도 정상 본문의 상태로 구분한다.", content = @Content(schema = @Schema(implementation = GuidancePropertyContactsResponse.class), examples = @ExampleObject(value = "{\"propertyId\":\"property-1\",\"propertyName\":\"예시 숙소\",\"status\":\"NOT_PROVIDED\",\"statusMessage\":\"숙소 연락 정보가 아직 제공되지 않았습니다.\",\"methods\":[],\"reservationInformation\":[{\"kind\":\"RESERVATION_ID\",\"label\":\"예약 번호\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 예약 번호 항목을 확인해 주세요.\"},{\"kind\":\"REFERENCE_CODE\",\"label\":\"별도 참조 번호\",\"availability\":\"IF_PRESENT\",\"instruction\":\"본인의 예약 상세에서 별도 참조 번호가 있는 경우에만 확인해 주세요.\"},{\"kind\":\"CHECK_IN\",\"label\":\"체크인 날짜\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 체크인 날짜 항목을 확인해 주세요.\"},{\"kind\":\"CHECK_OUT\",\"label\":\"체크아웃 날짜\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 체크아웃 날짜 항목을 확인해 주세요.\"},{\"kind\":\"REPRESENTATIVE_GIVEN_NAME\",\"label\":\"대표 투숙객 영문 이름\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 대표 투숙객 영문 이름 항목을 확인해 주세요.\"},{\"kind\":\"REPRESENTATIVE_FAMILY_NAME\",\"label\":\"대표 투숙객 영문 성\",\"availability\":\"ALWAYS\",\"instruction\":\"본인의 예약 상세에서 대표 투숙객 영문 성 항목을 확인해 주세요.\"}],\"maintenanceMaintainer\":\"SERVICE_OPERATIONS\",\"maintenanceBasis\":\"PROPERTY_PROVIDED\",\"maintenanceUpdatedAt\":null,\"maintenanceVerifiedAt\":null,\"observedAt\":\"2026-11-11T15:00:00+09:00\"}"))),
        @ApiResponse(responseCode = "400", description = "숙소 식별자가 비었거나 공백뿐이거나 64자를 초과한다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"INVALID_REQUEST\",\"message\":\"입력값을 확인해 주세요.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}"))),
        @ApiResponse(responseCode = "404", description = "공개 대상으로 식별할 수 있는 숙소가 없다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\",\"message\":\"요청한 대상을 찾을 수 없습니다.\",\"requestId\":\"3d96c26b-1f39-49b1-b4a0-62c7cbb0da03\",\"timestamp\":\"2026-11-11T03:00:00Z\",\"violations\":[]}")))
    })
    ResponseEntity<GuidancePropertyContactsResponse> getPropertyContactMethods(
        // 숙소 식별자. 가정: propertyId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.
        @Parameter(description = "숙소 식별자. 가정: propertyId라는 필수 1~64자 opaque 문자열 경로로 정해 저장소 형식을 노출하지 않는다.", required = true, schema = @Schema(type = "string", pattern = "^[\\s\\S]*\\S[\\s\\S]*$"), example = "property-1")
        @NotBlank @Size(min = 1, max = 64) String propertyId
    );
}

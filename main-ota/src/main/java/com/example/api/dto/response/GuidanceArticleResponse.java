package com.example.api.dto.response;
import com.example.api.dto.response.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/** maintenanceMaintainer와 maintenanceBasis는 관리 날짜가 null이어도 항상 제공하며, 날짜의 null은 담당 주체나 정보 근거의 부재를 뜻하지 않는다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "GuidanceArticleResponse", description = "공개 안내 상세. 아직 준비되지 않았거나 확인이 철회된 안내는 상태만 알리고 답변·행동을 빈 배열로 제공한다.")
public record GuidanceArticleResponse(
    /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 articleId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "알려진 안내 식별자. 가정: 필드명 articleId, String, 1~64자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "cancel-cost")
    @NotNull
    @Size(min = 1, max = 64)
    String articleId,
    /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 topicId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "안내 분류 식별자. 가정: 필드명 topicId, String, 1~64자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "cancellation")
    @NotNull
    @Size(min = 1, max = 64)
    String topicId,
    /** 가정: 필드명 question, String, 1~500자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 question, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "자체 질문 또는 안내 제목. 본문 미제공이어도 안내의 대상을 식별한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예약을 취소하면 얼마를 부담하나요?")
    @NotNull
    @Size(min = 1, max = 500)
    String question,
    /** 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "AVAILABLE은 검증해 공개한 답변, NOT_PROVIDED는 아직 준비되지 않은 답변, UNVERIFIED는 기존 답변의 유효성을 확인하지 못한 상태이다. 가정: 필드명 status, 필수 문자열, 허용값 AVAILABLE, NOT_PROVIDED, UNVERIFIED로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"AVAILABLE", "NOT_PROVIDED", "UNVERIFIED"}, example = "AVAILABLE")
    @NotNull
    @Pattern(regexp = "^(?:AVAILABLE|NOT_PROVIDED|UNVERIFIED)$")
    String status,
    /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 statusMessage, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "현재 제공 상태를 설명하는 자체 문구. 가정: 필드명 statusMessage, String, 1~500자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예약 당시 취소 조건과 요청 시각을 확인해 주세요.")
    @NotNull
    @Size(min = 1, max = 500)
    String statusMessage,
    /** 국내 숙소 예약에 제공하는 자체 안내의 적용 서비스. 가정: 필드명 applicabilityServiceScope, 필수 문자열, 허용값 DOMESTIC_ACCOMMODATION로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 applicabilityServiceScope, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "안내 대상과 적용 조건. 안내 적용 대상·조건·시간대 값은 응답에 항상 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"DOMESTIC_ACCOMMODATION"}, example = "DOMESTIC_ACCOMMODATION")
    @NotNull
    @Pattern(regexp = "^(?:DOMESTIC_ACCOMMODATION)$")
    String applicabilityServiceScope,
    /** 안내가 적용되는 업무 조건을 각각 설명한다. 가정: 각 문장은 1~500자이며 예외가 없는 일반 안내는 빈 배열을 허용한다. 가정: 필드명 applicabilityConditions, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 applicabilityConditions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "안내 대상과 적용 조건. 안내 적용 대상·조건·시간대 값은 응답에 항상 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 500))
    @NotNull
    @Size
    @Valid
    List<@NotNull  @Size(min = 1, max = 500) String> applicabilityConditions,
    /** 날짜·기한을 설명할 때 사용하는 숙소 시간대. 가정: 필드명 applicabilityTimeZone, 필수 문자열, 허용값 Asia/Seoul로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 applicabilityTimeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "안내 대상과 적용 조건. 안내 적용 대상·조건·시간대 값은 응답에 항상 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotNull
    @Pattern(regexp = "^(?:Asia/Seoul)$")
    String applicabilityTimeZone,
    /** 가정: 필드명 sections, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 sections, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "AVAILABLE일 때 하나 이상의 검증된 답변 문단. 다른 상태이면 []이며 확인되지 않은 문장을 노출하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = SectionsItem.class))
    @NotNull
    @Size
    @Valid
    List<@NotNull  SectionsItem> sections,
    /** 가정: 필드명 requiredInformation, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 requiredInformation, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "답변에서 숙소 확인을 위해 준비하도록 하는 정보의 종류. 답변 미제공이면 []이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = RequiredInformationItem.class))
    @NotNull
    @Size
    @Valid
    List<@NotNull  RequiredInformationItem> requiredInformation,
    /** 가정: 필드명 nextActions, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 nextActions, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "검증된 답변의 다음 탐색 행동. 답변 미제공이면 []이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = NextActionsItem.class))
    @NotNull
    @Size
    @Valid
    List<@NotNull  NextActionsItem> nextActions,
    /** 관리 담당 주체와 정보 근거는 갱신·확인 시각이 null이어도 필수로 제공한다. 자체 안내 검증과 숙소 제공 연락 정보의 확인·게시·철회 책임을 담당하는 운영 주체. 별도의 상담 접수나 파트너 관리 화면을 뜻하지 않는다. 가정: 필드명 maintenanceMaintainer, 필수 문자열, 허용값 SERVICE_OPERATIONS로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 maintenanceMaintainer, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "안내의 확인 근거·운영 책임·갱신 시각. maintenanceBasis는 PROJECT_POLICY이다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"SERVICE_OPERATIONS"}, example = "SERVICE_OPERATIONS")
    @NotNull
    @Pattern(regexp = "^(?:SERVICE_OPERATIONS)$")
    String maintenanceMaintainer,
    /** 관리 담당 주체와 정보 근거는 갱신·확인 시각이 null이어도 필수로 제공한다. PROJECT_POLICY는 확정된 자체 업무 정책, PROPERTY_PROVIDED는 숙소가 제공하는 연락 정보의 출처를 뜻한다. 미제공일 때는 기대 제공 주체이며 값의 존재나 검증 완료를 뜻하지 않는다. 검증 여부는 maintenanceVerifiedAt과 제공 상태로 구분한다. 가정: 필드명 maintenanceBasis, 필수 문자열, 허용값 PROJECT_POLICY, PROPERTY_PROVIDED로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 maintenanceBasis, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "안내의 확인 근거·운영 책임·갱신 시각. maintenanceBasis는 PROJECT_POLICY이다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"PROJECT_POLICY", "PROPERTY_PROVIDED"}, example = "PROJECT_POLICY")
    @NotNull
    @Pattern(regexp = "^(?:PROJECT_POLICY|PROPERTY_PROVIDED)$")
    String maintenanceBasis,
    /** 관리 담당 주체와 정보 근거는 갱신·확인 시각이 null이어도 필수로 제공한다. 정보의 마지막 갱신 시각. 한 번도 제공되지 않았다면 null이다. 가정: 필드명 maintenanceUpdatedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다. RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 설명한 사건이 아직 없음을 뜻한다. 가정: 응답 필드명을 maintenanceUpdatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "안내의 확인 근거·운영 책임·갱신 시각. maintenanceBasis는 PROJECT_POLICY이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    OffsetDateTime maintenanceUpdatedAt,
    /** 관리 담당 주체와 정보 근거는 갱신·확인 시각이 null이어도 필수로 제공한다. 현재 공개할 수 있는 정보로 확인한 시각. 미제공 또는 확인이 철회된 경우 null이다. 가정: 필드명 maintenanceVerifiedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다. RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 설명한 사건이 아직 없음을 뜻한다. 가정: 응답 필드명을 maintenanceVerifiedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "안내의 확인 근거·운영 책임·갱신 시각. maintenanceBasis는 PROJECT_POLICY이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    OffsetDateTime maintenanceVerifiedAt,
    /** RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 허용하지 않는다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "답변 제공 상태를 판정한 조회 시각. 가정: 필드명 observedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull
    OffsetDateTime observedAt
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "GuidanceArticleResponseSectionsItem", description = "답변의 제목·본문과 순서. 단순 텍스트이며 HTML이나 실행 코드를 전송하지 않는다.")
    public record SectionsItem(
        /** 가정: 응답 필드명을 order, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "가정: 필드명 order, 필수 1 이상의 Integer로 정해 문단을 순서대로 읽게 한다. 한 답변 안에서 중복을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull
        @Min(1)
        Integer order,
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 heading, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "문단 제목. 가정: 필드명 heading, String, 1~200자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "취소 비용 판단")
        @NotNull
        @Size(min = 1, max = 200)
        String heading,
        /** 가정: 필드명 body, String, 1~5000자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 body, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "구체적인 업무 대상·조건·결과를 포함하는 답변 문장. 외부 사이트를 읽어야 이해되는 별칭만 보내지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예약 당시 무료 취소 기한보다 앞서 취소하면 비용은 0원입니다. 기한과 같거나 이후이면 예약 총액을 부담합니다. 환불 불가 예약도 총액을 부담합니다.")
        @NotNull
        @Size(min = 1, max = 5000)
        String body
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "GuidanceArticleResponseRequiredInformationItem", description = "숙소에 확인을 요청할 때 준비할 정보의 종류. 공개 응답에는 개인 예약의 실제 값을 포함하지 않는다.")
    public record RequiredInformationItem(
        /** 가정: 응답 필드명을 kind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "예약 번호·추가 참조 번호·날짜·이름을 서로 다른 정보로 구분한다. 가정: 필드명 kind, 필수 문자열, 허용값 RESERVATION_ID, REFERENCE_CODE, CHECK_IN, CHECK_OUT, REPRESENTATIVE_GIVEN_NAME, REPRESENTATIVE_FAMILY_NAME로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"RESERVATION_ID", "REFERENCE_CODE", "CHECK_IN", "CHECK_OUT", "REPRESENTATIVE_GIVEN_NAME", "REPRESENTATIVE_FAMILY_NAME"}, example = "RESERVATION_ID")
        @NotNull
        @Pattern(regexp = "^(?:RESERVATION_ID|REFERENCE_CODE|CHECK_IN|CHECK_OUT|REPRESENTATIVE_GIVEN_NAME|REPRESENTATIVE_FAMILY_NAME)$")
        String kind,
        /** 가정: 필드명 label, String, 1~100자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "고객에게 표시할 정보 이름. 대표 투숙객 이름과 성은 별도 항목으로 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예약 번호")
        @NotNull
        @Size(min = 1, max = 100)
        String label,
        /** 가정: 응답 필드명을 availability, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "ALWAYS는 예약 정보에 존재하는 항목, IF_PRESENT는 해당 예약에 별도 값이 있을 때만 준비할 항목이다. 가정: 필드명 availability, 필수 문자열, 허용값 ALWAYS, IF_PRESENT로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"ALWAYS", "IF_PRESENT"}, example = "ALWAYS")
        @NotNull
        @Pattern(regexp = "^(?:ALWAYS|IF_PRESENT)$")
        String availability,
        /** 가정: 필드명 instruction, String, 1~500자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 instruction, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "해당 정보의 확인·전달 방법. 영문 이름과 성은 예약에 기록된 값을 그대로 확인하며 임의로 뒤바꾸지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "본인의 예약 상세에서 예약 번호를 확인해 숙소에 알려 주세요.")
        @NotNull
        @Size(min = 1, max = 500)
        String instruction
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "GuidanceArticleResponseNextActionsItem", description = "안내를 읽은 뒤 수행할 탐색 행동. 이 객체를 읽거나 선택했다는 사실로 예약·결제·환불을 실행하지 않는다.")
    public record NextActionsItem(
        /** 가정: 응답 필드명을 kind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "조회 화면 또는 취소 비용 확인 화면으로 이동할 목적을 구분한다. 가정: 필드명 kind, 필수 문자열, 허용값 VIEW_RESERVATION, VIEW_FINANCIAL_RESULT, PREVIEW_CANCELLATION, VIEW_PROPERTY_CONTACT로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"VIEW_RESERVATION", "VIEW_FINANCIAL_RESULT", "PREVIEW_CANCELLATION", "VIEW_PROPERTY_CONTACT"}, example = "VIEW_RESERVATION")
        @NotNull
        @Pattern(regexp = "^(?:VIEW_RESERVATION|VIEW_FINANCIAL_RESULT|PREVIEW_CANCELLATION|VIEW_PROPERTY_CONTACT)$")
        String kind,
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "탐색 행동의 자체 표시 문구. 가정: 필드명 label, String, 1~100자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "취소 비용 확인")
        @NotNull
        @Size(min = 1, max = 100)
        String label,
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 instruction, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "이동한 화면에서 확인하거나 명시적으로 요청해야 할 행동을 설명한다. 가정: 필드명 instruction, String, 1~500자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "본인의 예약을 선택해 취소 비용을 확인한 뒤 전체 취소를 명시적으로 요청해 주세요.")
        @NotNull
        @Size(min = 1, max = 500)
        String instruction,
        /** 가정: 응답 필드명을 requiredContext, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @ArraySchema(arraySchema = @Schema(description = "가정: 필드명 requiredContext, 필수 문자열 배열이며 RESERVATION_ID 또는 PROPERTY_ID를 허용한다. 탐색에 필요한 대상을 클라이언트가 선택하게 하며 공개 응답에 개인 식별값을 담지 않기 위해 정했다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = String.class, allowableValues = {"RESERVATION_ID", "PROPERTY_ID"}), uniqueItems = true)
        @NotNull
        @Valid
        List<@NotNull  String> requiredContext
    ) {
    }
}

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
@Schema(name = "GuidancePropertyContactsResponse", description = "FAQ의 선택·열람 여부와 무관하게 조회하는 숙소 연락 경로. 개인 예약 내용과 상담 기록을 포함하지 않는다.")
public record GuidancePropertyContactsResponse(
    /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "대상 숙소 식별자. 가정: 필드명 propertyId, String, 1~64자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "property-1")
    @NotNull
    @Size(min = 1, max = 64)
    String propertyId,
    /** 가정: 필드명 propertyName, String, 1~200자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 propertyName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "연락할 숙소의 현재 공개 이름. 다른 숙소의 정보로 대체하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예시 숙소")
    @NotNull
    @Size(min = 1, max = 200)
    String propertyName,
    /** 가정: 응답 필드명을 status, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "AVAILABLE은 확인된 공개 경로가 하나 이상, NOT_PROVIDED는 경로가 아직 제공되지 않음, UNVERIFIED는 제공된 정보의 정확성 또는 공개 허용을 확인하지 못함이다. 가정: 필드명 status, 필수 문자열, 허용값 AVAILABLE, NOT_PROVIDED, UNVERIFIED로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"AVAILABLE", "NOT_PROVIDED", "UNVERIFIED"}, example = "AVAILABLE")
    @NotNull
    @Pattern(regexp = "^(?:AVAILABLE|NOT_PROVIDED|UNVERIFIED)$")
    String status,
    /** 가정: 필드명 statusMessage, String, 1~500자, 필수이며 null을 허용하지 않음으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 statusMessage, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "연락 경로를 제공하는 상태의 설명. 접수 완료·전달 완료·문제 해결 완료로 표현하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "숙소 연락 정보가 아직 제공되지 않았습니다.")
    @NotNull
    @Size(min = 1, max = 500)
    String statusMessage,
    /** 가정: 필드명 methods, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 methods, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "AVAILABLE일 때만 하나 이상의 확인된 경로를 반환한다. 미제공·미확인이면 []이며 의심되는 값을 대신 반환하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = MethodsItem.class))
    @NotNull
    @Size
    @Valid
    List<@NotNull  MethodsItem> methods,
    /** 가정: 필드명 reservationInformation, 필수 배열, null을 허용하지 않으며 항목이 없으면 []로 정했다. 독립된 항목을 손실 없이 표시하기 위함이다. 가정: 응답 필드명을 reservationInformation, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소 연락 시 준비할 예약 정보 종류. 실제 개인 값은 포함하지 않으며 FAQ 열람 없이도 제공한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ReservationInformationItem.class))
    @NotNull
    @Size
    @Valid
    List<@NotNull  ReservationInformationItem> reservationInformation,
    /** 관리 담당 주체와 정보 근거는 갱신·확인 시각이 null이어도 필수로 제공한다. 자체 안내 검증과 숙소 제공 연락 정보의 확인·게시·철회 책임을 담당하는 운영 주체. 별도의 상담 접수나 파트너 관리 화면을 뜻하지 않는다. 가정: 필드명 maintenanceMaintainer, 필수 문자열, 허용값 SERVICE_OPERATIONS로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 maintenanceMaintainer, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "숙소 제공 정보의 운영팀 확인·갱신 책임. maintenanceBasis는 PROPERTY_PROVIDED이며 미제공/미확인이면 maintenanceVerifiedAt은 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"SERVICE_OPERATIONS"}, example = "SERVICE_OPERATIONS")
    @NotNull
    @Pattern(regexp = "^(?:SERVICE_OPERATIONS)$")
    String maintenanceMaintainer,
    /** 관리 담당 주체와 정보 근거는 갱신·확인 시각이 null이어도 필수로 제공한다. PROJECT_POLICY는 확정된 자체 업무 정책, PROPERTY_PROVIDED는 숙소가 제공하는 연락 정보의 출처를 뜻한다. 미제공일 때는 기대 제공 주체이며 값의 존재나 검증 완료를 뜻하지 않는다. 검증 여부는 maintenanceVerifiedAt과 제공 상태로 구분한다. 가정: 필드명 maintenanceBasis, 필수 문자열, 허용값 PROJECT_POLICY, PROPERTY_PROVIDED로 정해 서로 다른 표시 상태나 행동을 구분한다. 가정: 응답 필드명을 maintenanceBasis, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "숙소 제공 정보의 운영팀 확인·갱신 책임. maintenanceBasis는 PROPERTY_PROVIDED이며 미제공/미확인이면 maintenanceVerifiedAt은 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"PROJECT_POLICY", "PROPERTY_PROVIDED"}, example = "PROJECT_POLICY")
    @NotNull
    @Pattern(regexp = "^(?:PROJECT_POLICY|PROPERTY_PROVIDED)$")
    String maintenanceBasis,
    /** 관리 담당 주체와 정보 근거는 갱신·확인 시각이 null이어도 필수로 제공한다. 정보의 마지막 갱신 시각. 한 번도 제공되지 않았다면 null이다. 가정: 필드명 maintenanceUpdatedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다. RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 설명한 사건이 아직 없음을 뜻한다. 가정: 응답 필드명을 maintenanceUpdatedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "숙소 제공 정보의 운영팀 확인·갱신 책임. maintenanceBasis는 PROPERTY_PROVIDED이며 미제공/미확인이면 maintenanceVerifiedAt은 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    OffsetDateTime maintenanceUpdatedAt,
    /** 관리 담당 주체와 정보 근거는 갱신·확인 시각이 null이어도 필수로 제공한다. 현재 공개할 수 있는 정보로 확인한 시각. 미제공 또는 확인이 철회된 경우 null이다. 가정: 필드명 maintenanceVerifiedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다. RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 설명한 사건이 아직 없음을 뜻한다. 가정: 응답 필드명을 maintenanceVerifiedAt, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "숙소 제공 정보의 운영팀 확인·갱신 책임. maintenanceBasis는 PROPERTY_PROVIDED이며 미제공/미확인이면 maintenanceVerifiedAt은 null이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    OffsetDateTime maintenanceVerifiedAt,
    /** RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 허용하지 않는다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
    @Schema(description = "연락 정보 제공 상태를 판정한 조회 시각. 가정: 필드명 observedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull
    OffsetDateTime observedAt
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "GuidancePropertyContactsResponseMethodsItem", description = "숙소가 제공하고 운영팀이 확인해 공개를 허용한 기존 연락 경로 한 개. 이 정보 제공은 메시지의 전달이나 응답을 보장하지 않는다.")
    public record MethodsItem(
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 methodId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "연락 경로 식별자. 가정: 필드명 methodId, String, 1~64자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "front-desk-email")
        @NotNull
        @Size(min = 1, max = 64)
        String methodId,
        /** 가정: 응답 필드명을 type, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "PHONE은 전화, EMAIL은 이메일, WEB은 숙소가 이미 제공하는 외부 연락 페이지이다. 가정: 필드명 type, 필수 문자열, 허용값 PHONE, EMAIL, WEB로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"PHONE", "EMAIL", "WEB"}, example = "EMAIL")
        @NotNull
        @Pattern(regexp = "^(?:PHONE|EMAIL|WEB)$")
        String type,
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "숙소가 확인한 연락 경로의 표시 이름. 가정: 필드명 label, String, 1~100자, 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "숙소 연락 이메일")
        @NotNull
        @Size(min = 1, max = 100)
        String label,
        /** 가정: 필드명 phoneNumber, String, 8~16자, 키는 항상 포함하고 해당 필드에 명시한 조건에서만 null을 허용으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 phoneNumber, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "PHONE일 때 E.164 전화번호이며 다른 유형이면 null이다. 가정: 국제번호 형식으로 통일해 전화 발신 대상을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^\\+[1-9][0-9]{6,14}$", example = "+82200000000")
        @Size(min = 8, max = 16)
        @Pattern(regexp = "^\\+[1-9][0-9]{6,14}$")
        String phoneNumber,
        /** 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 emailAddress, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "EMAIL일 때 수신 이메일 주소이며 다른 유형이면 null이다. 가정: 필드명 emailAddress, String, 1~254자, 키는 항상 포함하고 해당 필드에 명시한 조건에서만 null을 허용으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "email", example = "frontdesk@property.example.invalid")
        @Size(min = 1, max = 254)
        @Email
        String emailAddress,
        /** 가정: 필드명 webUrl, String, 1~2048자, 키는 항상 포함하고 해당 필드에 명시한 조건에서만 null을 허용으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 webUrl, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "WEB일 때 공개된 HTTPS 연락 페이지이며 다른 유형이면 null이다. 가정: 새 메시지를 대신 보내지 않고 기존 외부 경로를 안내한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "uri", pattern = "^https://.+", example = "https://property.example.invalid/contact")
        @Size(min = 1, max = 2048)
        @Pattern(regexp = "^https://.+")
        String webUrl,
        /** 가정: 필드명 availabilityText, String, 1~500자, 키는 항상 포함하고 해당 필드에 명시한 조건에서만 null을 허용으로 정했다. 표시 의미와 입력 한도를 명확히 하기 위한 선택이다. 가정: 응답 필드명을 availabilityText, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "연락 가능 시간 또는 조건의 숙소 확인 문구. 확인된 안내가 없으면 null이며 24시간 가능하다고 추측하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "가상 예시: 매일 09:00~18:00(Asia/Seoul)에 이메일 문의를 확인합니다.")
        @Size(min = 1, max = 500)
        String availabilityText,
        /** 가정: 응답 필드명을 timeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "연락 가능 시간에 사용하는 숙소 현지 시간대. 가정: 필드명 timeZone, 필수 문자열, 허용값 Asia/Seoul로 정해 서로 다른 표시 상태나 행동을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
        @NotNull
        @Pattern(regexp = "^(?:Asia/Seoul)$")
        String timeZone,
        /** RFC3339 오프셋 시각을 사용하여 기준 시점을 식별한다. null은 허용하지 않는다. 가정: 응답 필드명을 verifiedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 필드명으로 구분하고 단위·상태를 독립적으로 전달하기 위한 결정이다. */
        @Schema(description = "이 경로의 값과 공개 허용을 운영팀이 확인한 시각. 가정: 필드명 verifiedAt, 키는 항상 포함하는 OffsetDateTime으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:00:00+09:00")
        @NotNull
        OffsetDateTime verifiedAt
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "GuidancePropertyContactsResponseReservationInformationItem", description = "숙소에 확인을 요청할 때 준비할 정보의 종류. 공개 응답에는 개인 예약의 실제 값을 포함하지 않는다.")
    public record ReservationInformationItem(
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
}

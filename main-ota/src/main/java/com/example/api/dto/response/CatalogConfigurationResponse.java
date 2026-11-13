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

/** null이면 조회 대상 propertyId를 제외한 모든 property 접두어 필드도 null이며 최초 저장은 expectedVersion=0으로 요청한다. 값이 있으면 propertyId와 같고 필수 기본정보와 propertyPhotos 1~50장이 함께 존재한다. roomTypes·ratePlans·products는 각각 독립된 배열이며 항목이 없으면 빈 배열이다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CatalogConfigurationResponse", description = "파트너 소유 숙소의 현재 기본정보·객실 유형·요금제·판매 상품을 조회한다. 가정: 숙소 기본정보의 저장 여부는 configuredPropertyId로 구분한다.")
public record CatalogConfigurationResponse(
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "조회한 숙소 식별자. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "property_1")
    @NotBlank

    @Size(min = 1, max = 64)
    String propertyId,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 숙소 기본정보가 아직 저장되지 않은 상태이며 propertyId를 제외한 모든 property 접두어 필드와 configuredPropertyId는 null이다. configuredPropertyId가 있으면 조회 대상 propertyId와 같아야 하고 기본정보가 구성된 상태이다. 이 기준으로 미구성과 선택값 미설정을 구별한다. 최초 저장 요청의 expectedVersion은 0이다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "구성 소유 숙소의 식별자. 가정: 응답 필드명을 configuredPropertyId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*", example = "property_1")
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String configuredPropertyId,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. */
    @Schema(description = "가정: 저장 성공마다 증가하는 버전으로 동시 편집의 기준을 제공한다. 가정: 응답 필드명을 propertyVersion, JSON 타입을 64비트 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", example = "1")
    @Min(0)
    Long propertyVersion,
    /** 가정: 응답 필드명을 propertyOwnerDisplayName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "가정: 인증 계정의 표시 이름을 읽기 전용으로 제공한다. 소유권 이전 입력이 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*", example = "파트너 소유자")
    @Size(min = 1, max = 200)
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyOwnerDisplayName,
    /** 가정: 응답 필드명을 propertyName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "고객에게 보이는 숙소명. 가정: 1~200자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*", example = "서울 예시 숙소")
    @Size(min = 1, max = 200)
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyName,
    /** 가정: 응답 필드명을 propertyPreviousName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있더라도 이 선택값을 등록하지 않았으면 null이다. */
    @Schema(description = "이전 숙소명. 가정: 선택 1~200자로 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    @Size(min = 1, max = 200)
    String propertyPreviousName,
    /** 가정: 응답 필드명을 propertyChainName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있더라도 이 선택값을 등록하지 않았으면 null이다. */
    @Schema(description = "체인 소속. 가정: 선택 1~100자로 받으며 브랜드와 합치지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    @Size(min = 1, max = 100)
    String propertyChainName,
    /** 가정: 응답 필드명을 propertyBrandName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있더라도 이 선택값을 등록하지 않았으면 null이다. */
    @Schema(description = "브랜드 소속. 가정: 선택 1~100자로 받으며 체인과 합치지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    @Size(min = 1, max = 100)
    String propertyBrandName,
    /** 가정: 응답 필드명을 propertyStarRating, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있더라도 이 선택값을 등록하지 않았으면 null이다. */
    @Schema(description = "숙소 성급이며 이용 후기 평점과 다르다. 가정: 선택 정수 0~5이며 null은 성급 미등록이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "3")
    @Min(0)
    @Max(5)
    Integer propertyStarRating,
    /** 가정: 응답 필드명을 propertyShortDescription, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "고객 요약 영역의 짧은 소개. 가정: 자동 생성 대신 파트너가 1~1000자로 직접 작성한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*", example = "도심에 위치한 숙소입니다.")
    @Size(min = 1, max = 1000)
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyShortDescription,
    /** 가정: 응답 필드명을 propertyDescription, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "고객 상세 영역의 설명. 가정: 1~5000자로 직접 작성한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*", example = "숙소의 시설과 위치를 직접 설명합니다.")
    @Size(min = 1, max = 5000)
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyDescription,
    /** 가정: 주소 후보 식별자를 선택값으로 받는다. null이면 직접 입력한 주소이며 좌표가 검증되지 않은 상태다. 가정: 응답 필드명을 propertyAddressLocationCandidateId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있더라도 이 선택값을 등록하지 않았으면 null이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "loc_1")
    @Size(min = 1, max = 64)
    String propertyAddressLocationCandidateId,
    /** 국내 숙소 국가. 가정: ISO 국가 코드 KR만 허용한다. 가정: 응답 필드명을 propertyAddressCountryCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KR$", allowableValues = {"KR"}, example = "KR")
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^KR$")
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyAddressCountryCode,
    /** 주·도에 해당하는 광역 행정구역. 가정: 1~100자로 받는다. 가정: 응답 필드명을 propertyAddressProvince, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*", example = "서울특별시")
    @Size(min = 1, max = 100)
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyAddressProvince,
    /** 도시. 가정: 1~100자로 받으며 광역 행정구역과 별도 보존한다. 가정: 응답 필드명을 propertyAddressCity, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*", example = "서울")
    @Size(min = 1, max = 100)
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyAddressCity,
    /** 세부 지역. 가정: 지역이 없으면 null이며 1~100자로 받는다. 가정: 응답 필드명을 propertyAddressDistrict, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있더라도 이 선택값을 등록하지 않았으면 null이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "중구")
    @Size(min = 1, max = 100)
    String propertyAddressDistrict,
    /** 우편번호. 가정: 국내 5자리 숫자로 받는다. 가정: 응답 필드명을 propertyAddressPostalCode, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^[0-9]{5}$", example = "04524")
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^[0-9]{5}$")
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyAddressPostalCode,
    /** 영문 상세 주소. 가정: 1~500자로 별도 보존하고 한글 주소로 대체하지 않는다. 가정: 응답 필드명을 propertyAddressEnglishAddress, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 가정: null 허용과 공백 검증을 함께 표현하기 위해 Java 17 Character.isWhitespace의 문자 범위를 정규식에 명시한다. 값이 있으면 이 기준의 공백이 아닌 문자를 적어도 하나 포함해야 하며, 공백만 있는 문자열은 거절한다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*", example = "10 Example-ro, Jung-gu, Seoul")
    @Size(min = 1, max = 500)
    @Pattern(regexp = "[\\s\\S]*[^\\u0009-\\u000D\\u001C-\\u0020\\u1680\\u2000-\\u2006\\u2008-\\u200A\\u2028-\\u2029\\u205F\\u3000][\\s\\S]*")
    String propertyAddressEnglishAddress,
    /** 언어별 주소 목록. 가정: 최대 20개이며 빈 배열은 추가 언어 주소 없음이다. 가정: 응답 필드명을 propertyAddressLocalizedAddresses, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 구성된 숙소에서 빈 배열은 등록된 항목이 없다는 뜻이며 미구성의 null과 구분한다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true), schema = @Schema(implementation = PropertyAddressLocalizedAddressesItem.class))
    @Size(max = 20)
    @Valid
    List<@NotNull  PropertyAddressLocalizedAddressesItem> propertyAddressLocalizedAddresses,
    /** 가정: 선택한 후보로 검증한 위도를 필수 nullable 숫자로 제공한다. 직접 주소 입력으로 좌표를 확인하지 못했으면 null이다. 가정: 응답 필드명을 propertyAddressLatitude, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있더라도 이 선택값을 등록하지 않았으면 null이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "37.5665")
    @DecimalMin(value = "-90", inclusive = true)
    @DecimalMax("90")
    BigDecimal propertyAddressLatitude,
    /** 가정: 선택한 후보로 검증한 경도를 필수 nullable 숫자로 제공한다. 직접 주소 입력으로 좌표를 확인하지 못했으면 null이다. 가정: 응답 필드명을 propertyAddressLongitude, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있더라도 이 선택값을 등록하지 않았으면 null이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "126.978")
    @DecimalMin(value = "-180", inclusive = true)
    @DecimalMax("180")
    BigDecimal propertyAddressLongitude,
    /** 가정: 응답 필드명을 propertyAmenityIds, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 구성된 숙소에서 빈 배열은 등록된 항목이 없다는 뜻이며 미구성의 null과 구분한다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소 공통 시설. 가정: 제공된 PROPERTY 시설 식별자만 최대 100개 선택하며 직접 새 시설명은 입력하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "[\"WIFI\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), uniqueItems = true)
    @Size(max = 100)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> propertyAmenityIds,
    /** 가정: 응답 필드명을 propertyHighlightAmenityIds, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 구성된 숙소에서 빈 배열은 등록된 항목이 없다는 뜻이며 미구성의 null과 구분한다. */
    @ArraySchema(arraySchema = @Schema(description = "고객 주요시설 영역에 표시할 숙소 시설. 가정: 전체 propertyAmenityIds의 부분집합을 최대 10개 선택한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "[\"WIFI\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), uniqueItems = true)
    @Size(max = 10)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> propertyHighlightAmenityIds,
    /** 가정: 응답 필드명을 propertyPhotos, JSON 타입을 배열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: configuredPropertyId가 null이면 이 필드는 null이다. configuredPropertyId가 있으면 이 필드도 null일 수 없다. 구성된 숙소 사진은 1~50장이고 빈 배열은 허용하지 않는다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소에 귀속되는 사진. 가정: 최소 1장·최대 50장이며 객실 유형 사진과 합치지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true), schema = @Schema(implementation = PropertyPhotosItem.class))
    @Size(min = 1, max = 50)
    @Valid
    List<@NotNull  PropertyPhotosItem> propertyPhotos,
    /** 가정: 응답 필드명을 roomTypes, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "현재 객실 유형 목록. 없으면 빈 배열이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = RoomTypesItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  RoomTypesItem> roomTypes,
    /** 가정: 응답 필드명을 ratePlans, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "현재 요금제 목록. 없으면 빈 배열이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = RatePlansItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  RatePlansItem> ratePlans,
    /** 가정: 응답 필드명을 products, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "현재 객실 유형·요금제 연결 목록. 없으면 빈 배열이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ProductsItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  ProductsItem> products,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 동일 조회의 기준 시각을 RFC3339 오프셋 포함 시각으로 제공한다. 가정: 응답 필드명을 observedAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T15:00:00+09:00")
    @NotNull

    OffsetDateTime observedAt
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationResponsePropertyAddressLocalizedAddressesItem", description = "언어별 상세 주소.")
    public record PropertyAddressLocalizedAddressesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: BCP47 언어 태그를 2~35자로 받아 고객 언어별 주소를 식별한다. 가정: 응답 필드명을 languageTag, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^[A-Za-z]{2,8}(?:-[A-Za-z0-9]{1,8})*$", example = "ko-KR")
        @NotBlank

        @Size(min = 2, max = 35)
        @Pattern(regexp = "^[A-Za-z]{2,8}(?:-[A-Za-z0-9]{1,8})*$")
        String languageTag,
        /** 가정: 응답 필드명을 address, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 언어의 상세 주소. 가정: 최대 500자로 직접 입력한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울특별시 중구 예시로 10")
        @NotBlank

        @Size(min = 1, max = 500)
        String address
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationResponsePropertyPhotosItem", description = "해당 숙소 또는 객실에 귀속된 사진 표시 설정.")
    public record PropertyPhotosItem(
        /** 가정: 응답 필드명을 assetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 사진 표시 설정이 가리키는 업로드 자산이며 같은 배열 항목의 assetMetadataId와 값이 항상 같다. */
        @Schema(description = "가정: 같은 숙소 소유로 업로드가 완료된 자산 식별자만 받는다. 타 숙소 자산을 연결할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "asset_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String assetId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 고객에게 표시할 사진 설명을 필수 1~300자로 받으며 대체 텍스트로도 사용한다. 가정: 응답 필드명을 caption, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "객실 전경")
        @NotBlank

        @Size(min = 1, max = 300)
        String caption,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 사진 태그는 제공된 선택지 중 하나이며 null이면 태그가 없다. 가정: 응답 필드명을 tagId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "ROOM")
        @Size(min = 1, max = 64)
        String tagId,
        /** 가정: 응답 필드명을 main, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 해당 귀속 대상의 대표사진 여부이다. 사진 배열마다 정확히 한 장만 true여야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean main,
        /** 가정: 응답 필드명을 sortOrder, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 해당 대상 안의 표시 순서이며 0부터 시작한다. 같은 배열 안에 중복할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "0")
        @NotNull

        @Min(0)
        Integer sortOrder,
        /** 가정: 응답 필드명을 assetMetadataId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: assetUrl·assetMediaType·assetWidthPixels·assetHeightPixels·assetSizeBytes의 소유 자산이며 같은 배열 항목의 assetId와 값이 항상 같다. 표시 설정의 참조와 파일 메타데이터의 귀속을 구분하기 위해 별도 이름을 유지한다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 같은 숙소 구성에 연결할 수 있는 사진 자산 식별자.", requiredMode = Schema.RequiredMode.REQUIRED, example = "asset_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String assetMetadataId,
        /** 예약 당시 사진 참조를 유지하도록 자산 내용은 변경하지 않는다. 가정: 응답 필드명을 assetUrl, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 가정: 고객이 볼 수 있는 HTTPS 자산 주소다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "uri", pattern = "^https://", example = "https://assets.example.com/property_1/asset_1.png")
        @NotBlank

        @Size(min = 1, max = 2048)
        @Pattern(regexp = "^https://")
        String assetUrl,
        /** JPEG 또는 PNG다. 가정: 응답 필드명을 assetMediaType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 실제 이미지 형식.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^image/(jpeg|png)$", allowableValues = {"image/jpeg", "image/png"}, example = "image/png")
        @NotBlank

        @Size(min = 1, max = 16)
        @Pattern(regexp = "^image/(jpeg|png)$")
        String assetMediaType,
        /** 채택한 최소값은 800픽셀이다. 가정: 응답 필드명을 assetWidthPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 이미지 너비.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "800")
        @NotNull

        @Min(800)
        Integer assetWidthPixels,
        /** 채택한 최소값은 600픽셀이다. 가정: 응답 필드명을 assetHeightPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 이미지 높이.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "600")
        @NotNull

        @Min(600)
        Integer assetHeightPixels,
        /** 채택한 상한은 십진 4MB인 4,000,000바이트다. 가정: 응답 필드명을 assetSizeBytes, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 디코딩된 원본 이미지 크기.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "2785")
        @NotNull

        @Min(1)
        @Max(4000000)
        Long assetSizeBytes
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationResponseRoomTypesItemBedsItem", description = "현재 객실의 침대 종류와 수량.")
    public record RoomTypesItemBedsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 조회가 제공하는 침대 종류 식별자만 허용한다. 가정: 응답 필드명을 typeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "DOUBLE")
        @NotBlank

        @Size(min = 1, max = 64)
        String typeId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 해당 종류 침대 수량을 양수 정수로 받는다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "1")
        @NotNull

        @Min(1)
        Integer quantity
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationResponseRoomTypesItemPhotosItem", description = "해당 숙소 또는 객실에 귀속된 사진 표시 설정.")
    public record RoomTypesItemPhotosItem(
        /** 가정: 응답 필드명을 assetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 사진 표시 설정이 가리키는 업로드 자산이며 같은 배열 항목의 assetMetadataId와 값이 항상 같다. */
        @Schema(description = "가정: 같은 숙소 소유로 업로드가 완료된 자산 식별자만 받는다. 타 숙소 자산을 연결할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "asset_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String assetId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 고객에게 표시할 사진 설명을 필수 1~300자로 받으며 대체 텍스트로도 사용한다. 가정: 응답 필드명을 caption, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "객실 전경")
        @NotBlank

        @Size(min = 1, max = 300)
        String caption,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 사진 태그는 제공된 선택지 중 하나이며 null이면 태그가 없다. 가정: 응답 필드명을 tagId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "ROOM")
        @Size(min = 1, max = 64)
        String tagId,
        /** 가정: 응답 필드명을 main, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 해당 귀속 대상의 대표사진 여부이다. 사진 배열마다 정확히 한 장만 true여야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean main,
        /** 가정: 응답 필드명을 sortOrder, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 해당 대상 안의 표시 순서이며 0부터 시작한다. 같은 배열 안에 중복할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "0")
        @NotNull

        @Min(0)
        Integer sortOrder,
        /** 가정: 응답 필드명을 assetMetadataId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: assetUrl·assetMediaType·assetWidthPixels·assetHeightPixels·assetSizeBytes의 소유 자산이며 같은 배열 항목의 assetId와 값이 항상 같다. 표시 설정의 참조와 파일 메타데이터의 귀속을 구분하기 위해 별도 이름을 유지한다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 같은 숙소 구성에 연결할 수 있는 사진 자산 식별자.", requiredMode = Schema.RequiredMode.REQUIRED, example = "asset_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String assetMetadataId,
        /** 예약 당시 사진 참조를 유지하도록 자산 내용은 변경하지 않는다. 가정: 응답 필드명을 assetUrl, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 가정: 고객이 볼 수 있는 HTTPS 자산 주소다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "uri", pattern = "^https://", example = "https://assets.example.com/property_1/asset_1.png")
        @NotBlank

        @Size(min = 1, max = 2048)
        @Pattern(regexp = "^https://")
        String assetUrl,
        /** JPEG 또는 PNG다. 가정: 응답 필드명을 assetMediaType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 실제 이미지 형식.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^image/(jpeg|png)$", allowableValues = {"image/jpeg", "image/png"}, example = "image/png")
        @NotBlank

        @Size(min = 1, max = 16)
        @Pattern(regexp = "^image/(jpeg|png)$")
        String assetMediaType,
        /** 채택한 최소값은 800픽셀이다. 가정: 응답 필드명을 assetWidthPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 이미지 너비.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "800")
        @NotNull

        @Min(800)
        Integer assetWidthPixels,
        /** 채택한 최소값은 600픽셀이다. 가정: 응답 필드명을 assetHeightPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 이미지 높이.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "600")
        @NotNull

        @Min(600)
        Integer assetHeightPixels,
        /** 채택한 상한은 십진 4MB인 4,000,000바이트다. 가정: 응답 필드명을 assetSizeBytes, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 디코딩된 원본 이미지 크기.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "2785")
        @NotNull

        @Min(1)
        @Max(4000000)
        Long assetSizeBytes
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationResponseRoomTypesItem", description = "객실 유형의 현재 구성. 실제 보유수량은 포함하지 않는다.")
    public record RoomTypesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "생성된 객실 유형 식별자. 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "room_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String roomTypeId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 저장 성공마다 증가하는 객실 유형 버전. 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "1")
        @NotNull

        @Min(0)
        Long version,
        /** 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "고객용 객실 이름. 가정: 1~200자 직접 입력으로 선택하며 미확인 외부 이름 카탈로그를 강제하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "스탠다드 더블")
        @NotBlank

        @Size(min = 1, max = 200)
        String name,
        /** 가정: 응답 필드명을 internalName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "고객에게 표시하지 않는 내부 이름. 선택 입력이라는 의미는 보존하며 가정: 1~200자로 제한한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "본관 더블")
        @Size(min = 1, max = 200)
        String internalName,
        /** 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 설명. 가정: 필수 1~5000자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "더블 침대 한 개가 있는 객실입니다.")
        @NotBlank

        @Size(min = 1, max = 5000)
        String description,
        /** 가정: 응답 필드명을 totalCapacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "무료 투숙객을 포함하는 객실별 총정원. 양수 정수이며 별도 성인·아동 최소 인원을 추가하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "2")
        @NotNull

        @Min(1)
        Integer totalCapacity,
        /** 가정: 응답 필드명을 areaSquareMeters, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "면적 단위는 제곱미터다. 가정: 선택 소수이며 0보다 커야 하고 null이면 미등록이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "24.5")
        @DecimalMin(value = "0", inclusive = false)
        BigDecimal areaSquareMeters,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 욕실 수는 선택 정수 0 이상이며 null은 미등록이다. 가정: 응답 필드명을 bathroomCount, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "1")
        @Min(0)
        Integer bathroomCount,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 전망 선택지 하나의 식별자이며 null은 전망 미등록이다. 가정: 응답 필드명을 viewTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "CITY")
        @Size(min = 1, max = 64)
        String viewTypeId,
        /** 가정: 응답 필드명을 beds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "침대 종류별 수량. 가정: 최소 1개·최대 20개 종류이며 같은 typeId 중복은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = RoomTypesItemBedsItem.class))
        @NotNull

        @Size(min = 1, max = 20)
        @Valid
        List<@NotNull  RoomTypesItemBedsItem> beds,
        /** 숙소 공통 시설과 요금제 혜택으로 자동 복사하지 않는다. 가정: 응답 필드명을 amenityIds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "해당 객실 유형의 시설. 가정: ROOM 시설 식별자만 최대 100개 선택한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"AIR_CONDITIONING\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), uniqueItems = true)
        @NotNull

        @Size(max = 100)
        @Valid
        List<@NotBlank  @Size(min = 1, max = 64) String> amenityIds,
        /** 가정: 응답 필드명을 photos, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "해당 객실 유형의 사진. 가정: 최소 1장·최대 50장, 대표사진 정확히 한 장이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = RoomTypesItemPhotosItem.class))
        @NotNull

        @Size(min = 1, max = 50)
        @Valid
        List<@NotNull  RoomTypesItemPhotosItem> photos
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationResponseRatePlansItemBenefitsItem", description = "현재 요금제의 개별 혜택과 이용 조건.")
    public record RatePlansItemBenefitsItem(
        /** 가정: 응답 필드명을 optionId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지에서 고른 혜택 식별자이다. null이면 파트너가 직접 작성한 포함 서비스다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "BREAKFAST")
        @Size(min = 1, max = 64)
        String optionId,
        /** 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택 이름. 가정: 필수 1~200자다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "조식 포함")
        @NotBlank

        @Size(min = 1, max = 200)
        String name,
        /** 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택의 구체적 제공 내용. 가정: 필수 1~1000자다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "숙박일마다 제공되는 조식")
        @NotBlank

        @Size(min = 1, max = 1000)
        String description,
        /** 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택의 제공 대상·시각·이용 조건. 가정: 필수 0~1000자로 받고 추가 조건이 없으면 빈 문자열이다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "예약 객실에 배정된 투숙객에게 제공")
        @NotNull

        @Size(max = 1000)
        String conditions,
        /** 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 표시용 제공 수량은 선택 양수 정수이며 수량으로 표현하지 않는 혜택은 null이다. 요금 계산에 사용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32")
        @Min(1)
        Integer quantity,
        /** 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 표시용 수량의 단위는 선택 1~30자다. quantity가 null이면 반드시 null이고 수량이 있으면 필수다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 30)
        String unit
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationResponseRatePlansItem", description = "요금제의 현재 혜택·취소·결제 구성.")
    public record RatePlansItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "생성된 요금제 식별자. 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "rate_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String ratePlanId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 저장 성공마다 증가하는 요금제 버전. 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "1")
        @NotNull

        @Min(0)
        Long version,
        /** 가정: 응답 필드명을 publicName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "고객이 비교할 요금제 이름. 가정: 1~200자로 내부 이름과 별도 입력한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "조식 포함 요금")
        @NotBlank

        @Size(min = 1, max = 200)
        String publicName,
        /** 가정: 응답 필드명을 internalName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "운영자가 식별할 내부 이름. 가정: 필수 1~200자이며 고객에게 노출하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "조식 후불")
        @NotBlank

        @Size(min = 1, max = 200)
        String internalName,
        /** 가정: 응답 필드명을 benefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @ArraySchema(arraySchema = @Schema(description = "포함 혜택 각각의 이름·설명·조건·표시 수량. 가정: 최대 50개이며 빈 배열은 포함 혜택 없음이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = RatePlansItemBenefitsItem.class))
        @NotNull

        @Size(max = 50)
        @Valid
        List<@NotNull  RatePlansItemBenefitsItem> benefits,
        /** 가정: 응답 필드명을 cancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "요금제의 취소 정책과 무료 취소 설정 일수. 가정: 자체 정책 코드를 FREE_CANCELLATION과 NON_REFUNDABLE로 정해 두 조건을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^(FREE_CANCELLATION|NON_REFUNDABLE)$", allowableValues = {"FREE_CANCELLATION", "NON_REFUNDABLE"}, example = "FREE_CANCELLATION")
        @NotBlank

        @Size(min = 1, max = 32)
        @Pattern(regexp = "^(FREE_CANCELLATION|NON_REFUNDABLE)$")
        String cancellationPolicyKind,
        /** 체크인 N일 전 Asia/Seoul 00:00 전은 비용 0, 같거나 이후는 총액 전액이다. cancellationPolicyKind가 NON_REFUNDABLE이면 null이며 고객 취소 비용은 항상 총액 전액이다. 가정: 응답 필드명을 cancellationPolicyFreeCancellationDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: paymentTermsScheduledDaysBeforeCheckIn과 독립된 설정이며 두 일수를 같게 제한하지 않는다. */
        @Schema(description = "요금제의 취소 정책과 무료 취소 설정 일수. cancellationPolicyKind가 FREE_CANCELLATION이면 필수인 음이 아닌 일수 N.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "3")
        @Min(0)
        Integer cancellationPolicyFreeCancellationDaysBeforeCheckIn,
        /** 가정: 응답 필드명을 paymentTermsTiming, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "요금제의 결제 시점과 나중 결제 설정 일수. 가정: 자체 결제 시점 코드는 IMMEDIATE와 LATER이다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^(IMMEDIATE|LATER)$", allowableValues = {"IMMEDIATE", "LATER"}, example = "LATER")
        @NotBlank

        @Size(min = 1, max = 16)
        @Pattern(regexp = "^(IMMEDIATE|LATER)$")
        String paymentTermsTiming,
        /** 예정 시각은 체크인 N일 전 Asia/Seoul 00:00이다. 신규 선점 시 그 시각이 지났거나 같으면 후불 예약을 거절한다. paymentTermsTiming이 IMMEDIATE이면 null이다. 가정: 응답 필드명을 paymentTermsScheduledDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: cancellationPolicyFreeCancellationDaysBeforeCheckIn과 독립된 설정이며 두 일수를 같게 제한하지 않는다. */
        @Schema(description = "요금제의 결제 시점과 나중 결제 설정 일수. paymentTermsTiming이 LATER이면 필수인 음이 아닌 일수 N.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", example = "2")
        @Min(0)
        Integer paymentTermsScheduledDaysBeforeCheckIn
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationResponseProductsItem", description = "동일 숙소 객실 유형과 요금제의 판매 상품 연결.")
    public record ProductsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "생성된 판매 상품 식별자. 가정: 응답 필드명을 productId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "product_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String productId,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 저장 성공마다 증가하는 판매 상품 버전. 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "1")
        @NotNull

        @Min(0)
        Long version,
        /** 가정: 응답 필드명을 roomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "같은 숙소의 객실 유형 식별자. 다른 숙소 구성과 연결할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "room_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String roomTypeId,
        /** 가정: 응답 필드명을 ratePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "같은 숙소의 요금제 식별자. 요금제 추가가 객실 수량 증가를 뜻하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "rate_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String ratePlanId,
        /** 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "고객용 판매 상품명. 가정: 1~200자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "스탠다드 더블 · 조식 포함")
        @NotBlank

        @Size(min = 1, max = 200)
        String name,
        /** 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "상품의 객실·서비스 조합 설명. 가정: 1~5000자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "동일 객실 유형과 조식 포함 요금제를 연결한 판매 상품입니다.")
        @NotBlank

        @Size(min = 1, max = 5000)
        String description
    ) {
    }
}

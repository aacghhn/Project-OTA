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

/** 숙소의 현재 기본정보와 사진·시설 구성. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CatalogPropertyResponse", description = "숙소의 현재 기본정보와 사진·시설 구성.")
public record CatalogPropertyResponse(
    /** 구성 소유 숙소의 식별자. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "구성 소유 숙소의 식별자. 가정: 응답 필드명을 propertyId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "property_1")
    @NotBlank
    
    @Size(min = 1, max = 64)
    String propertyId,
    /** 가정: 저장 성공마다 증가하는 버전으로 동시 편집의 기준을 제공한다. 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 저장 성공마다 증가하는 버전으로 동시 편집의 기준을 제공한다. 가정: 응답 필드명을 version, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "1")
    @NotNull
    
    @Min(0)
    Long version,
    /** 가정: 인증 계정의 표시 이름을 읽기 전용으로 제공한다. 소유권 이전 입력이 아니다. 가정: 응답 필드명을 ownerDisplayName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 인증 계정의 표시 이름을 읽기 전용으로 제공한다. 소유권 이전 입력이 아니다. 가정: 응답 필드명을 ownerDisplayName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "파트너 소유자")
    @NotBlank
    
    @Size(min = 1, max = 200)
    String ownerDisplayName,
    /** 고객에게 보이는 숙소명. 가정: 1~200자로 받는다. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "고객에게 보이는 숙소명. 가정: 1~200자로 받는다. 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 200, example = "서울 예시 숙소")
    @NotBlank
    
    @Size(min = 1, max = 200)
    String name,
    /** 이전 숙소명. 가정: 선택 1~200자로 보존한다. 가정: 응답 필드명을 previousName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "이전 숙소명. 가정: 선택 1~200자로 보존한다. 가정: 응답 필드명을 previousName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 200)
    @Size(min = 1, max = 200)
    String previousName,
    /** 체인 소속. 가정: 선택 1~100자로 받으며 브랜드와 합치지 않는다. 가정: 응답 필드명을 chainName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "체인 소속. 가정: 선택 1~100자로 받으며 브랜드와 합치지 않는다. 가정: 응답 필드명을 chainName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 100)
    @Size(min = 1, max = 100)
    String chainName,
    /** 브랜드 소속. 가정: 선택 1~100자로 받으며 체인과 합치지 않는다. 가정: 응답 필드명을 brandName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "브랜드 소속. 가정: 선택 1~100자로 받으며 체인과 합치지 않는다. 가정: 응답 필드명을 brandName, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 100)
    @Size(min = 1, max = 100)
    String brandName,
    /** 숙소 성급이며 이용 후기 평점과 다르다. 가정: 선택 정수 0~5이며 null은 성급 미등록이다. 가정: 응답 필드명을 starRating, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 성급이며 이용 후기 평점과 다르다. 가정: 선택 정수 0~5이며 null은 성급 미등록이다. 가정: 응답 필드명을 starRating, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int32", minimum = "0", maximum = "5", example = "3")
    @Min(0)
    @Max(5)
    Integer starRating,
    /** 고객 요약 영역의 짧은 소개. 가정: 자동 생성 대신 파트너가 1~1000자로 직접 작성한다. 가정: 응답 필드명을 shortDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "고객 요약 영역의 짧은 소개. 가정: 자동 생성 대신 파트너가 1~1000자로 직접 작성한다. 가정: 응답 필드명을 shortDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 1000, example = "도심에 위치한 숙소입니다.")
    @NotBlank
    
    @Size(min = 1, max = 1000)
    String shortDescription,
    /** 고객 상세 영역의 설명. 가정: 1~5000자로 직접 작성한다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "고객 상세 영역의 설명. 가정: 1~5000자로 직접 작성한다. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 5000, example = "숙소의 시설과 위치를 직접 설명합니다.")
    @NotBlank
    
    @Size(min = 1, max = 5000)
    String description,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 가정: 주소 후보 식별자를 선택값으로 받는다. null이면 직접 입력한 주소이며 좌표가 검증되지 않은 상태다. 가정: 응답 필드명을 addressLocationCandidateId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 가정: 주소 후보 식별자를 선택값으로 받는다. null이면 직접 입력한 주소이며 좌표가 검증되지 않은 상태다. 가정: 응답 필드명을 addressLocationCandidateId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64, example = "loc_1")
    @Size(min = 1, max = 64)
    String addressLocationCandidateId,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 국내 숙소 국가. 가정: ISO 국가 코드 KR만 허용한다. 가정: 응답 필드명을 addressCountryCode, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 국내 숙소 국가. 가정: ISO 국가 코드 KR만 허용한다. 가정: 응답 필드명을 addressCountryCode, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KR$", minLength = 2, maxLength = 2, allowableValues = {"KR"}, example = "KR")
    @NotBlank
    
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^KR$")
    String addressCountryCode,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 주·도에 해당하는 광역 행정구역. 가정: 1~100자로 받는다. 가정: 응답 필드명을 addressProvince, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 주·도에 해당하는 광역 행정구역. 가정: 1~100자로 받는다. 가정: 응답 필드명을 addressProvince, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "서울특별시")
    @NotBlank
    
    @Size(min = 1, max = 100)
    String addressProvince,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 도시. 가정: 1~100자로 받으며 광역 행정구역과 별도 보존한다. 가정: 응답 필드명을 addressCity, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 도시. 가정: 1~100자로 받으며 광역 행정구역과 별도 보존한다. 가정: 응답 필드명을 addressCity, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100, example = "서울")
    @NotBlank
    
    @Size(min = 1, max = 100)
    String addressCity,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 세부 지역. 가정: 지역이 없으면 null이며 1~100자로 받는다. 가정: 응답 필드명을 addressDistrict, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 세부 지역. 가정: 지역이 없으면 null이며 1~100자로 받는다. 가정: 응답 필드명을 addressDistrict, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 100, example = "중구")
    @Size(min = 1, max = 100)
    String addressDistrict,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 우편번호. 가정: 국내 5자리 숫자로 받는다. 가정: 응답 필드명을 addressPostalCode, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 우편번호. 가정: 국내 5자리 숫자로 받는다. 가정: 응답 필드명을 addressPostalCode, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^[0-9]{5}$", minLength = 5, maxLength = 5, example = "04524")
    @NotBlank
    
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^[0-9]{5}$")
    String addressPostalCode,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 영문 상세 주소. 가정: 1~500자로 별도 보존하고 한글 주소로 대체하지 않는다. 가정: 응답 필드명을 addressEnglishAddress, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 영문 상세 주소. 가정: 1~500자로 별도 보존하고 한글 주소로 대체하지 않는다. 가정: 응답 필드명을 addressEnglishAddress, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 500, example = "10 Example-ro, Jung-gu, Seoul")
    @NotBlank
    
    @Size(min = 1, max = 500)
    String addressEnglishAddress,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 언어별 주소 목록. 가정: 최대 20개이며 빈 배열은 추가 언어 주소 없음이다. 가정: 응답 필드명을 addressLocalizedAddresses, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 언어별 주소 목록. 가정: 최대 20개이며 빈 배열은 추가 언어 주소 없음이다. 가정: 응답 필드명을 addressLocalizedAddresses, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = AddressLocalizedAddressesItem.class), minItems = 0, maxItems = 20)
    @NotNull
    
    @Size(min = 0, max = 20)
    @Valid
    List<@NotNull  AddressLocalizedAddressesItem> addressLocalizedAddresses,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 가정: 선택한 후보로 검증한 위도를 필수 nullable 숫자로 제공한다. 직접 주소 입력으로 좌표를 확인하지 못했으면 null이다. 가정: 응답 필드명을 addressLatitude, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 가정: 선택한 후보로 검증한 위도를 필수 nullable 숫자로 제공한다. 직접 주소 입력으로 좌표를 확인하지 못했으면 null이다. 가정: 응답 필드명을 addressLatitude, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minimum = "-90", maximum = "90", example = "37.5665")
    @DecimalMin(value = "-90", inclusive = true)
    @DecimalMax("90")
    BigDecimal addressLatitude,
    /** 숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 가정: 선택한 후보로 검증한 경도를 필수 nullable 숫자로 제공한다. 직접 주소 입력으로 좌표를 확인하지 못했으면 null이다. 가정: 응답 필드명을 addressLongitude, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다. 가정: 선택한 후보로 검증한 경도를 필수 nullable 숫자로 제공한다. 직접 주소 입력으로 좌표를 확인하지 못했으면 null이다. 가정: 응답 필드명을 addressLongitude, JSON 타입을 숫자로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minimum = "-180", maximum = "180", example = "126.978")
    @DecimalMin(value = "-180", inclusive = true)
    @DecimalMax("180")
    BigDecimal addressLongitude,
    /** 숙소 공통 시설. 가정: 제공된 PROPERTY 시설 식별자만 최대 100개 선택하며 직접 새 시설명은 입력하지 않는다. 가정: 응답 필드명을 amenityIds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소 공통 시설. 가정: 제공된 PROPERTY 시설 식별자만 최대 100개 선택하며 직접 새 시설명은 입력하지 않는다. 가정: 응답 필드명을 amenityIds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "[\"WIFI\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), minItems = 0, maxItems = 100, uniqueItems = true)
    @NotNull
    
    @Size(min = 0, max = 100)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> amenityIds,
    /** 고객 주요시설 영역에 표시할 숙소 시설. 가정: 전체 amenityIds의 부분집합을 최대 10개 선택한다. 가정: 응답 필드명을 highlightAmenityIds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "고객 주요시설 영역에 표시할 숙소 시설. 가정: 전체 amenityIds의 부분집합을 최대 10개 선택한다. 가정: 응답 필드명을 highlightAmenityIds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "[\"WIFI\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), minItems = 0, maxItems = 10, uniqueItems = true)
    @NotNull
    
    @Size(min = 0, max = 10)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> highlightAmenityIds,
    /** 숙소에 귀속되는 사진. 가정: 최소 1장·최대 50장이며 객실 유형 사진과 합치지 않는다. 가정: 응답 필드명을 photos, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소에 귀속되는 사진. 가정: 최소 1장·최대 50장이며 객실 유형 사진과 합치지 않는다. 가정: 응답 필드명을 photos, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = PhotosItem.class), minItems = 1, maxItems = 50)
    @NotNull
    
    @Size(min = 1, max = 50)
    @Valid
    List<@NotNull  PhotosItem> photos
) {
    /** 언어별 상세 주소. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogPropertyResponseAddressLocalizedAddressesItem", description = "언어별 상세 주소.")
    public record AddressLocalizedAddressesItem(
        /** 가정: BCP47 언어 태그를 2~35자로 받아 고객 언어별 주소를 식별한다. 가정: 응답 필드명을 languageTag, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: BCP47 언어 태그를 2~35자로 받아 고객 언어별 주소를 식별한다. 가정: 응답 필드명을 languageTag, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^[A-Za-z]{2,8}(?:-[A-Za-z0-9]{1,8})*$", minLength = 2, maxLength = 35, example = "ko-KR")
        @NotBlank
    
        @Size(min = 2, max = 35)
        @Pattern(regexp = "^[A-Za-z]{2,8}(?:-[A-Za-z0-9]{1,8})*$")
        String languageTag,
        /** 해당 언어의 상세 주소. 가정: 최대 500자로 직접 입력한다. 가정: 응답 필드명을 address, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 언어의 상세 주소. 가정: 최대 500자로 직접 입력한다. 가정: 응답 필드명을 address, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 500, example = "서울특별시 중구 예시로 10")
        @NotBlank
    
        @Size(min = 1, max = 500)
        String address
    ) {
    }
    /** 해당 숙소 또는 객실에 귀속된 사진 표시 설정. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogPropertyResponsePhotosItem", description = "해당 숙소 또는 객실에 귀속된 사진 표시 설정.")
    public record PhotosItem(
        /** 가정: 같은 숙소 소유로 업로드가 완료된 자산 식별자만 받는다. 타 숙소 자산을 연결할 수 없다. 가정: 응답 필드명을 assetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 사진 표시 설정이 가리키는 업로드 자산이며 같은 배열 항목의 assetMetadataId와 값이 항상 같다. */
        @Schema(description = "가정: 같은 숙소 소유로 업로드가 완료된 자산 식별자만 받는다. 타 숙소 자산을 연결할 수 없다. 가정: 응답 필드명을 assetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: 현재 사진 표시 설정이 가리키는 업로드 자산이며 같은 배열 항목의 assetMetadataId와 값이 항상 같다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "asset_1")
        @NotBlank
    
        @Size(min = 1, max = 64)
        String assetId,
        /** 가정: 고객에게 표시할 사진 설명을 필수 1~300자로 받으며 대체 텍스트로도 사용한다. 가정: 응답 필드명을 caption, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 고객에게 표시할 사진 설명을 필수 1~300자로 받으며 대체 텍스트로도 사용한다. 가정: 응답 필드명을 caption, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 300, example = "객실 전경")
        @NotBlank
    
        @Size(min = 1, max = 300)
        String caption,
        /** 가정: 사진 태그는 제공된 선택지 중 하나이며 null이면 태그가 없다. 가정: 응답 필드명을 tagId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 사진 태그는 제공된 선택지 중 하나이며 null이면 태그가 없다. 가정: 응답 필드명을 tagId, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64, example = "ROOM")
        @Size(min = 1, max = 64)
        String tagId,
        /** 가정: 해당 귀속 대상의 대표사진 여부이다. 사진 배열마다 정확히 한 장만 true여야 한다. 가정: 응답 필드명을 main, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 해당 귀속 대상의 대표사진 여부이다. 사진 배열마다 정확히 한 장만 true여야 한다. 가정: 응답 필드명을 main, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "true")
        @NotNull
    
        Boolean main,
        /** 가정: 해당 대상 안의 표시 순서이며 0부터 시작한다. 같은 배열 안에 중복할 수 없다. 가정: 응답 필드명을 sortOrder, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 해당 대상 안의 표시 순서이며 0부터 시작한다. 같은 배열 안에 중복할 수 없다. 가정: 응답 필드명을 sortOrder, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "0", example = "0")
        @NotNull
    
        @Min(0)
        Integer sortOrder,
        /** 가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 같은 숙소 구성에 연결할 수 있는 사진 자산 식별자. 가정: 응답 필드명을 assetMetadataId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: assetUrl·assetMediaType·assetWidthPixels·assetHeightPixels·assetSizeBytes의 소유 자산이며 같은 배열 항목의 assetId와 값이 항상 같다. 표시 설정의 참조와 파일 메타데이터의 귀속을 구분하기 위해 별도 이름을 유지한다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 같은 숙소 구성에 연결할 수 있는 사진 자산 식별자. 가정: 응답 필드명을 assetMetadataId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. 가정: assetUrl·assetMediaType·assetWidthPixels·assetHeightPixels·assetSizeBytes의 소유 자산이며 같은 배열 항목의 assetId와 값이 항상 같다. 표시 설정의 참조와 파일 메타데이터의 귀속을 구분하기 위해 별도 이름을 유지한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "asset_1")
        @NotBlank
    
        @Size(min = 1, max = 64)
        String assetMetadataId,
        /** 가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 가정: 고객이 볼 수 있는 HTTPS 자산 주소다. 예약 당시 사진 참조를 유지하도록 자산 내용은 변경하지 않는다. 가정: 응답 필드명을 assetUrl, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 가정: 고객이 볼 수 있는 HTTPS 자산 주소다. 예약 당시 사진 참조를 유지하도록 자산 내용은 변경하지 않는다. 가정: 응답 필드명을 assetUrl, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "uri", pattern = "^https://", minLength = 1, maxLength = 2048, example = "https://assets.example.com/property_1/asset_1.png")
        @NotBlank
    
        @Size(min = 1, max = 2048)
        @Pattern(regexp = "^https://")
        String assetUrl,
        /** 가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 실제 이미지 형식. JPEG 또는 PNG다. 가정: 응답 필드명을 assetMediaType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 실제 이미지 형식. JPEG 또는 PNG다. 가정: 응답 필드명을 assetMediaType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^image/(jpeg|png)$", minLength = 1, maxLength = 16, allowableValues = {"image/jpeg", "image/png"}, example = "image/png")
        @NotBlank
    
        @Size(min = 1, max = 16)
        @Pattern(regexp = "^image/(jpeg|png)$")
        String assetMediaType,
        /** 가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 이미지 너비. 채택한 최소값은 800픽셀이다. 가정: 응답 필드명을 assetWidthPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 이미지 너비. 채택한 최소값은 800픽셀이다. 가정: 응답 필드명을 assetWidthPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "800", example = "800")
        @NotNull
    
        @Min(800)
        Integer assetWidthPixels,
        /** 가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 이미지 높이. 채택한 최소값은 600픽셀이다. 가정: 응답 필드명을 assetHeightPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 검증된 이미지 높이. 채택한 최소값은 600픽셀이다. 가정: 응답 필드명을 assetHeightPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "600", example = "600")
        @NotNull
    
        @Min(600)
        Integer assetHeightPixels,
        /** 가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 디코딩된 원본 이미지 크기. 채택한 상한은 십진 4MB인 4,000,000바이트다. 가정: 응답 필드명을 assetSizeBytes, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 검증된 자산의 URL·픽셀·바이트 정보를 필수로 제공해 사진 설정과 파일을 구분한다. 디코딩된 원본 이미지 크기. 채택한 상한은 십진 4MB인 4,000,000바이트다. 가정: 응답 필드명을 assetSizeBytes, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "1", maximum = "4000000", example = "2785")
        @NotNull
    
        @Min(1)
        @Max(4000000)
        Long assetSizeBytes
    ) {
    }
}

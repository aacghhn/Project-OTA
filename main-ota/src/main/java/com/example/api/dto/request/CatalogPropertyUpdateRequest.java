package com.example.api.dto.request;
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

@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CatalogPropertyUpdateRequest", description = "숙소 기본정보 전체 교체. 소유자 변경과 정산 정보는 받지 않는다.")
public record CatalogPropertyUpdateRequest(
    /** 가정: 필드명 expectedVersion, integer 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "가정: 현재 숙소 구성의 버전을 필수로 받아 동시 편집의 덮어쓰기를 막는다. 최초 구성은 0이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "0")
    @NotNull

    @Min(0)
    Long expectedVersion,
    /** 가정: 필드명 name, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "고객에게 보이는 숙소명. 가정: 1~200자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울 예시 숙소")
    @NotBlank

    @Size(min = 1, max = 200)
    String name,
    /** 가정: 필드명 previousName, string 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "이전 숙소명. 가정: 선택 1~200자로 보존한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    @Size(min = 1, max = 200)
    String previousName,
    /** 가정: 필드명 chainName, string 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "체인 소속. 가정: 선택 1~100자로 받으며 브랜드와 합치지 않는다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    @Size(min = 1, max = 100)
    String chainName,
    /** 가정: 필드명 brandName, string 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "브랜드 소속. 가정: 선택 1~100자로 받으며 체인과 합치지 않는다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    @Size(min = 1, max = 100)
    String brandName,
    /** 가정: 필드명 starRating, integer 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "숙소 성급이며 이용 후기 평점과 다르다. 가정: 선택 정수 0~5이며 null은 성급 미등록이다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, format = "int32", example = "3")
    @Min(0)
    @Max(5)
    Integer starRating,
    /** 가정: 필드명 shortDescription, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "고객 요약 영역의 짧은 소개. 가정: 자동 생성 대신 파트너가 1~1000자로 직접 작성한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "도심에 위치한 숙소입니다.")
    @NotBlank

    @Size(min = 1, max = 1000)
    String shortDescription,
    /** 가정: 필드명 description, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "고객 상세 영역의 설명. 가정: 1~5000자로 직접 작성한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "숙소의 시설과 위치를 직접 설명합니다.")
    @NotBlank

    @Size(min = 1, max = 5000)
    String description,
    /** 가정: 필드명 address, 구조 객체 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull

    @Valid
    Address address,
    /** 가정: 필드명 amenityIds, array 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소 공통 시설. 가정: 제공된 PROPERTY 시설 식별자만 최대 100개 선택하며 직접 새 시설명은 입력하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"WIFI\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), uniqueItems = true)
    @NotNull

    @Size(max = 100)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> amenityIds,
    /** 가정: 필드명 highlightAmenityIds, array 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "고객 주요시설 영역에 표시할 숙소 시설. 가정: 전체 amenityIds의 부분집합을 최대 10개 선택한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"WIFI\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), uniqueItems = true)
    @NotNull

    @Size(max = 10)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> highlightAmenityIds,
    /** 가정: 필드명 photos, array 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소에 귀속되는 사진. 가정: 최소 1장·최대 50장이며 객실 유형 사진과 합치지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PhotosItem.class))
    @NotNull

    @Size(min = 1, max = 50)
    @Valid
    List<@NotNull  PhotosItem> photos
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogPropertyUpdateRequestAddressLocalizedAddressesItem", description = "언어별 상세 주소. 같은 언어 태그는 중복하지 않는다.")
    public record AddressLocalizedAddressesItem(
        /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: BCP47 언어 태그를 2~35자로 받아 고객 언어별 주소를 식별한다. 가정: 필드명 languageTag, string 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^[A-Za-z]{2,8}(?:-[A-Za-z0-9]{1,8})*$", example = "ko-KR")
        @NotBlank

        @Size(min = 2, max = 35)
        @Pattern(regexp = "^[A-Za-z]{2,8}(?:-[A-Za-z0-9]{1,8})*$")
        String languageTag,
        /** 가정: 필드명 address, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "해당 언어의 상세 주소. 가정: 최대 500자로 직접 입력한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울특별시 중구 예시로 10")
        @NotBlank

        @Size(min = 1, max = 500)
        String address
    ) {
    }
    /** 가정: 필드명 address, 구조 객체 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogPropertyUpdateRequestAddress", description = "숙소 소재 주소. 국가·행정구역·언어별 주소를 각각 보존한다.")
    public record Address(
        /** 가정: 필드명 locationCandidateId, string 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 주소 후보 식별자를 선택값으로 받는다. null이면 직접 입력한 주소이며 좌표가 검증되지 않은 상태다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, example = "loc_1")
        @Size(min = 1, max = 64)
        String locationCandidateId,
        /** 가정: 필드명 countryCode, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "국내 숙소 국가. 가정: ISO 국가 코드 KR만 허용한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KR$", allowableValues = {"KR"}, example = "KR")
        @NotBlank

        @Size(min = 2, max = 2)
        @Pattern(regexp = "^KR$")
        String countryCode,
        /** 가정: 필드명 province, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "주·도에 해당하는 광역 행정구역. 가정: 1~100자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울특별시")
        @NotBlank

        @Size(min = 1, max = 100)
        String province,
        /** 가정: 필드명 city, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "도시. 가정: 1~100자로 받으며 광역 행정구역과 별도 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "서울")
        @NotBlank

        @Size(min = 1, max = 100)
        String city,
        /** 가정: 필드명 district, string 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "세부 지역. 가정: 지역이 없으면 null이며 1~100자로 받는다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, example = "중구")
        @Size(min = 1, max = 100)
        String district,
        /** 가정: 필드명 postalCode, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "우편번호. 가정: 국내 5자리 숫자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^[0-9]{5}$", example = "04524")
        @NotBlank

        @Size(min = 5, max = 5)
        @Pattern(regexp = "^[0-9]{5}$")
        String postalCode,
        /** 가정: 필드명 englishAddress, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "영문 상세 주소. 가정: 1~500자로 별도 보존하고 한글 주소로 대체하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "10 Example-ro, Jung-gu, Seoul")
        @NotBlank

        @Size(min = 1, max = 500)
        String englishAddress,
        /** 가정: 필드명 localizedAddresses, array 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @ArraySchema(arraySchema = @Schema(description = "언어별 주소 목록. 가정: 최대 20개이며 빈 배열은 추가 언어 주소 없음이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = AddressLocalizedAddressesItem.class))
        @NotNull

        @Size(max = 20)
        @Valid
        List<@NotNull  AddressLocalizedAddressesItem> localizedAddresses
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogPropertyUpdateRequestPhotosItem", description = "숙소 또는 객실 구성에 귀속시키는 사진 한 장. 파일 자체와 태그·대표 여부는 별도 정보다.")
    public record PhotosItem(
        /** 가정: 필드명 assetId, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 같은 숙소 소유로 업로드가 완료된 자산 식별자만 받는다. 타 숙소 자산을 연결할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "asset_1")
        @NotBlank

        @Size(min = 1, max = 64)
        String assetId,
        /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 고객에게 표시할 사진 설명을 필수 1~300자로 받으며 대체 텍스트로도 사용한다. 가정: 필드명 caption, string 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "객실 전경")
        @NotBlank

        @Size(min = 1, max = 300)
        String caption,
        /** 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 사진 태그는 제공된 선택지 중 하나이며 null이면 태그가 없다. 가정: 필드명 tagId, string 표현과 명시적 null 허용을 선택했다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, example = "ROOM")
        @Size(min = 1, max = 64)
        String tagId,
        /** 가정: 필드명 main, boolean 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 해당 귀속 대상의 대표사진 여부이다. 사진 배열마다 정확히 한 장만 true여야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean main,
        /** 가정: 필드명 sortOrder, integer 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 해당 대상 안의 표시 순서이며 0부터 시작한다. 같은 배열 안에 중복할 수 없다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "0")
        @NotNull

        @Min(0)
        Integer sortOrder
    ) {
    }
}

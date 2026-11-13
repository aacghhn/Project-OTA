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

@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CatalogRoomResponse", description = "객실 유형의 현재 구성. 실제 보유수량은 포함하지 않는다.")
public record CatalogRoomResponse(
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
    @ArraySchema(arraySchema = @Schema(description = "침대 종류별 수량. 가정: 최소 1개·최대 20개 종류이며 같은 typeId 중복은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = BedsItem.class))
    @NotNull

    @Size(min = 1, max = 20)
    @Valid
    List<@NotNull  BedsItem> beds,
    /** 숙소 공통 시설과 요금제 혜택으로 자동 복사하지 않는다. 가정: 응답 필드명을 amenityIds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "해당 객실 유형의 시설. 가정: ROOM 시설 식별자만 최대 100개 선택한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"AIR_CONDITIONING\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), uniqueItems = true)
    @NotNull

    @Size(max = 100)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> amenityIds,
    /** 가정: 응답 필드명을 photos, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "해당 객실 유형의 사진. 가정: 최소 1장·최대 50장, 대표사진 정확히 한 장이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PhotosItem.class))
    @NotNull

    @Size(min = 1, max = 50)
    @Valid
    List<@NotNull  PhotosItem> photos
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogRoomResponseBedsItem", description = "현재 객실의 침대 종류와 수량.")
    public record BedsItem(
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
    @Schema(name = "CatalogRoomResponsePhotosItem", description = "해당 숙소 또는 객실에 귀속된 사진 표시 설정.")
    public record PhotosItem(
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
}

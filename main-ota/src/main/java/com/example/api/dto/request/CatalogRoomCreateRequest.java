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
@Schema(name = "CatalogRoomCreateRequest", description = "객실 유형 생성 입력. 실제 객실 보유수량과 날짜별 판매한도는 받지 않는다.")
public record CatalogRoomCreateRequest(
    /** 가정: 필드명 name, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "고객용 객실 이름. 가정: 1~200자 직접 입력으로 선택하며 미확인 외부 이름 카탈로그를 강제하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "스탠다드 더블")
    @NotBlank

    @Size(min = 1, max = 200)
    String name,
    /** 가정: 필드명 internalName, string 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "고객에게 표시하지 않는 내부 이름. 선택 입력이라는 의미는 보존하며 가정: 1~200자로 제한한다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, example = "본관 더블")
    @Size(min = 1, max = 200)
    String internalName,
    /** 가정: 필드명 description, string 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "객실 설명. 가정: 필수 1~5000자로 받는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "더블 침대 한 개가 있는 객실입니다.")
    @NotBlank

    @Size(min = 1, max = 5000)
    String description,
    /** 가정: 필드명 totalCapacity, integer 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "무료 투숙객을 포함하는 객실별 총정원. 양수 정수이며 별도 성인·아동 최소 인원을 추가하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "2")
    @NotNull

    @Min(1)
    Integer totalCapacity,
    /** 가정: 필드명 areaSquareMeters, number 표현과 명시적 null 허용을 선택했다. 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "면적 단위는 제곱미터다. 가정: 선택 소수이며 0보다 커야 하고 null이면 미등록이다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, example = "24.5")
    @DecimalMin(value = "0", inclusive = false)
    BigDecimal areaSquareMeters,
    /** 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "가정: 욕실 수는 선택 정수 0 이상이며 null은 미등록이다. 가정: 필드명 bathroomCount, integer 표현과 명시적 null 허용을 선택했다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, format = "int32", example = "1")
    @Min(0)
    Integer bathroomCount,
    /** 요청에서 선택값이며 누락 또는 null은 미설정/제거이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @Schema(description = "가정: 전망 선택지 하나의 식별자이며 null은 전망 미등록이다. 가정: 필드명 viewTypeId, string 표현과 명시적 null 허용을 선택했다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true, example = "CITY")
    @Size(min = 1, max = 64)
    String viewTypeId,
    /** 가정: 필드명 beds, array 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "침대 종류별 수량. 가정: 최소 1개·최대 20개 종류이며 같은 typeId 중복은 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = BedsItem.class))
    @NotNull

    @Size(min = 1, max = 20)
    @Valid
    List<@NotNull  BedsItem> beds,
    /** 숙소 공통 시설과 요금제 혜택으로 자동 복사하지 않는다. 가정: 필드명 amenityIds, array 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "해당 객실 유형의 시설. 가정: ROOM 시설 식별자만 최대 100개 선택한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"AIR_CONDITIONING\"]"), schema = @Schema(implementation = String.class, minLength = 1, maxLength = 64), uniqueItems = true)
    @NotNull

    @Size(max = 100)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> amenityIds,
    /** 가정: 필드명 photos, array 표현과 null 불허를 선택했다. 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
    @ArraySchema(arraySchema = @Schema(description = "해당 객실 유형의 사진. 가정: 최소 1장·최대 50장, 대표사진 정확히 한 장이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PhotosItem.class))
    @NotNull

    @Size(min = 1, max = 50)
    @Valid
    List<@NotNull  PhotosItem> photos
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogRoomCreateRequestBedsItem", description = "객실 침대 종류와 종류별 수량. 인원별 추가요금 기능은 포함하지 않는다.")
    public record BedsItem(
        /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 선택지 조회가 제공하는 침대 종류 식별자만 허용한다. 가정: 필드명 typeId, string 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "DOUBLE")
        @NotBlank

        @Size(min = 1, max = 64)
        String typeId,
        /** 키는 필수이다. 입력 대상과 표시값을 혼동하지 않고 상태를 전달하기 위한 결정이다. */
        @Schema(description = "가정: 해당 종류 침대 수량을 양수 정수로 받는다. 가정: 필드명 quantity, integer 표현과 null 불허를 선택했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "1")
        @NotNull

        @Min(1)
        Integer quantity
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogRoomCreateRequestPhotosItem", description = "숙소 또는 객실 구성에 귀속시키는 사진 한 장. 파일 자체와 태그·대표 여부는 별도 정보다.")
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

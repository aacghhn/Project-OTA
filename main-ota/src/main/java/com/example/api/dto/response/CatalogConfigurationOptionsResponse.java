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
@Schema(name = "CatalogConfigurationOptionsResponse", description = "구성에 사용할 선택지와 업로드 제한. 실제 사용자 선택은 별도 구성 조회값이다.")
public record CatalogConfigurationOptionsResponse(
    /** 가정: 응답 필드명을 propertyAmenities, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙소 공통 시설 선택지. 서비스와 운동·여가 묶음을 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PropertyAmenitiesItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  PropertyAmenitiesItem> propertyAmenities,
    /** 가정: 응답 필드명을 roomAmenities, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "객실 시설 선택지. 숙소 시설과 별도 목록이다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = RoomAmenitiesItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  RoomAmenitiesItem> roomAmenities,
    /** 가정: 응답 필드명을 bedTypes, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 자체 카탈로그의 침대 종류 선택지다. 외부 전체 목록을 확인했다고 주장하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = BedTypesItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  BedTypesItem> bedTypes,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 자체 카탈로그의 전망 선택지다. 가정: 응답 필드명을 viewTypes, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ViewTypesItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  ViewTypesItem> viewTypes,
    /** 가정: 응답 필드명을 photoTags, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 자체 카탈로그의 사진 태그 선택지다. 사진마다 최대 한 개만 선택한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PhotoTagsItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  PhotoTagsItem> photoTags,
    /** 가정: 응답 필드명을 benefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "포함 혜택 선택지. Breakfast, Lunch included, Dinner included를 각각 별도 선택지로 채택한다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = BenefitsItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  BenefitsItem> benefits,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "무료 취소 기한 있음 또는 환불 불가의 두 정책 선택지. 가정: 응답 필드명을 cancellationKinds, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = CancellationKindsItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  CancellationKindsItem> cancellationKinds,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "지금 결제와 나중에 결제 선택지. 가정: 응답 필드명을 paymentTimings, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = PaymentTimingsItem.class))
    @NotNull

    @Size
    @Valid
    List<@NotNull  PaymentTimingsItem> paymentTimings,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 국내 숙소만 구성하므로 허용 국가 목록은 KR 하나이다. 가정: 응답 필드명을 countryCodes, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"KR\"]"), schema = @Schema(implementation = String.class, allowableValues = {"KR"}))
    @NotNull

    @Size(min = 1, max = 1)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> countryCodes,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "숙박일과 무료 취소·예정 결제 기한을 계산하는 시간대. 가정: 응답 필드명을 timeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^Asia/Seoul$", allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotBlank

    @Size(min = 1, max = 64)
    @Pattern(regexp = "^Asia/Seoul$")
    String timeZone,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 사진 업로드 최소 너비 800픽셀을 자체 정책으로 채택한다. 가정: 응답 필드명을 photoMinimumWidthPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "800")
    @NotNull

    @Min(800)
    @Max(800)
    Integer photoMinimumWidthPixels,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 사진 업로드 최소 높이 600픽셀을 자체 정책으로 채택한다. 가정: 응답 필드명을 photoMinimumHeightPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int32", example = "600")
    @NotNull

    @Min(600)
    @Max(600)
    Integer photoMinimumHeightPixels,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 사진 파일당 상한은 십진 4MB인 4,000,000바이트로 채택한다. 가정: 응답 필드명을 photoMaximumBytes, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "4000000")
    @NotNull

    @Min(4000000)
    @Max(4000000)
    Long photoMaximumBytes,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "가정: 자체 업로드 형식은 image/jpeg와 image/png 두 개이다. 가정: 응답 필드명을 photoAllowedMediaTypes, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"image/jpeg\", \"image/png\"]"), schema = @Schema(implementation = String.class, allowableValues = {"image/jpeg", "image/png"}), uniqueItems = true)
    @NotNull

    @Size(min = 2, max = 2)
    @Valid
    List<@NotBlank  @Size(min = 1, max = 64) String> photoAllowedMediaTypes
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationOptionsResponsePropertyAmenitiesItem", description = "카탈로그 선택지. 선택지 코드와 고객 문구를 별도로 전달한다.")
    public record PropertyAmenitiesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 요청에 사용할 카탈로그 식별자. 가정: 응답 필드명을 id, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIFI")
        @NotBlank

        @Size(min = 1, max = 64)
        String id,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 화면에 표시할 선택지 문구를 필수 1~200자로 제공한다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wi-Fi")
        @NotBlank

        @Size(min = 1, max = 200)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 분류를 필수 1~64자로 제공해 숙소 서비스·운동 여가·객실 시설을 구분한다. 가정: 응답 필드명을 group, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROPERTY_SERVICES")
        @NotBlank

        @Size(min = 1, max = 64)
        String group,
        /** 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 가능 여부. false인 항목을 새 입력에 포함하면 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean enabled,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: enabled=false일 때 이유를 필수로 제공하고 선택 가능하면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String disabledReason,
        /** 가정: 응답 필드명을 defaultSelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 최초 구성에서는 모든 항목을 미선택으로 제공한다. 기존 구성의 선택 상태는 구성 조회값으로 판단한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull

        Boolean defaultSelected
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationOptionsResponseRoomAmenitiesItem", description = "카탈로그 선택지. 선택지 코드와 고객 문구를 별도로 전달한다.")
    public record RoomAmenitiesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 요청에 사용할 카탈로그 식별자. 가정: 응답 필드명을 id, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIFI")
        @NotBlank

        @Size(min = 1, max = 64)
        String id,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 화면에 표시할 선택지 문구를 필수 1~200자로 제공한다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wi-Fi")
        @NotBlank

        @Size(min = 1, max = 200)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 분류를 필수 1~64자로 제공해 숙소 서비스·운동 여가·객실 시설을 구분한다. 가정: 응답 필드명을 group, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROPERTY_SERVICES")
        @NotBlank

        @Size(min = 1, max = 64)
        String group,
        /** 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 가능 여부. false인 항목을 새 입력에 포함하면 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean enabled,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: enabled=false일 때 이유를 필수로 제공하고 선택 가능하면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String disabledReason,
        /** 가정: 응답 필드명을 defaultSelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 최초 구성에서는 모든 항목을 미선택으로 제공한다. 기존 구성의 선택 상태는 구성 조회값으로 판단한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull

        Boolean defaultSelected
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationOptionsResponseBedTypesItem", description = "카탈로그 선택지. 선택지 코드와 고객 문구를 별도로 전달한다.")
    public record BedTypesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 요청에 사용할 카탈로그 식별자. 가정: 응답 필드명을 id, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIFI")
        @NotBlank

        @Size(min = 1, max = 64)
        String id,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 화면에 표시할 선택지 문구를 필수 1~200자로 제공한다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wi-Fi")
        @NotBlank

        @Size(min = 1, max = 200)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 분류를 필수 1~64자로 제공해 숙소 서비스·운동 여가·객실 시설을 구분한다. 가정: 응답 필드명을 group, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROPERTY_SERVICES")
        @NotBlank

        @Size(min = 1, max = 64)
        String group,
        /** 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 가능 여부. false인 항목을 새 입력에 포함하면 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean enabled,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: enabled=false일 때 이유를 필수로 제공하고 선택 가능하면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String disabledReason,
        /** 가정: 응답 필드명을 defaultSelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 최초 구성에서는 모든 항목을 미선택으로 제공한다. 기존 구성의 선택 상태는 구성 조회값으로 판단한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull

        Boolean defaultSelected
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationOptionsResponseViewTypesItem", description = "카탈로그 선택지. 선택지 코드와 고객 문구를 별도로 전달한다.")
    public record ViewTypesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 요청에 사용할 카탈로그 식별자. 가정: 응답 필드명을 id, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIFI")
        @NotBlank

        @Size(min = 1, max = 64)
        String id,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 화면에 표시할 선택지 문구를 필수 1~200자로 제공한다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wi-Fi")
        @NotBlank

        @Size(min = 1, max = 200)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 분류를 필수 1~64자로 제공해 숙소 서비스·운동 여가·객실 시설을 구분한다. 가정: 응답 필드명을 group, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROPERTY_SERVICES")
        @NotBlank

        @Size(min = 1, max = 64)
        String group,
        /** 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 가능 여부. false인 항목을 새 입력에 포함하면 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean enabled,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: enabled=false일 때 이유를 필수로 제공하고 선택 가능하면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String disabledReason,
        /** 가정: 응답 필드명을 defaultSelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 최초 구성에서는 모든 항목을 미선택으로 제공한다. 기존 구성의 선택 상태는 구성 조회값으로 판단한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull

        Boolean defaultSelected
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationOptionsResponsePhotoTagsItem", description = "카탈로그 선택지. 선택지 코드와 고객 문구를 별도로 전달한다.")
    public record PhotoTagsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 요청에 사용할 카탈로그 식별자. 가정: 응답 필드명을 id, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIFI")
        @NotBlank

        @Size(min = 1, max = 64)
        String id,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 화면에 표시할 선택지 문구를 필수 1~200자로 제공한다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wi-Fi")
        @NotBlank

        @Size(min = 1, max = 200)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 분류를 필수 1~64자로 제공해 숙소 서비스·운동 여가·객실 시설을 구분한다. 가정: 응답 필드명을 group, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROPERTY_SERVICES")
        @NotBlank

        @Size(min = 1, max = 64)
        String group,
        /** 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 가능 여부. false인 항목을 새 입력에 포함하면 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean enabled,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: enabled=false일 때 이유를 필수로 제공하고 선택 가능하면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String disabledReason,
        /** 가정: 응답 필드명을 defaultSelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 최초 구성에서는 모든 항목을 미선택으로 제공한다. 기존 구성의 선택 상태는 구성 조회값으로 판단한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull

        Boolean defaultSelected
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationOptionsResponseBenefitsItem", description = "카탈로그 선택지. 선택지 코드와 고객 문구를 별도로 전달한다.")
    public record BenefitsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 요청에 사용할 카탈로그 식별자. 가정: 응답 필드명을 id, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIFI")
        @NotBlank

        @Size(min = 1, max = 64)
        String id,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 화면에 표시할 선택지 문구를 필수 1~200자로 제공한다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wi-Fi")
        @NotBlank

        @Size(min = 1, max = 200)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 분류를 필수 1~64자로 제공해 숙소 서비스·운동 여가·객실 시설을 구분한다. 가정: 응답 필드명을 group, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROPERTY_SERVICES")
        @NotBlank

        @Size(min = 1, max = 64)
        String group,
        /** 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 가능 여부. false인 항목을 새 입력에 포함하면 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean enabled,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: enabled=false일 때 이유를 필수로 제공하고 선택 가능하면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String disabledReason,
        /** 가정: 응답 필드명을 defaultSelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 최초 구성에서는 모든 항목을 미선택으로 제공한다. 기존 구성의 선택 상태는 구성 조회값으로 판단한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull

        Boolean defaultSelected
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationOptionsResponseCancellationKindsItem", description = "카탈로그 선택지. 선택지 코드와 고객 문구를 별도로 전달한다.")
    public record CancellationKindsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 요청에 사용할 카탈로그 식별자. 가정: 응답 필드명을 id, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIFI")
        @NotBlank

        @Size(min = 1, max = 64)
        String id,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 화면에 표시할 선택지 문구를 필수 1~200자로 제공한다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wi-Fi")
        @NotBlank

        @Size(min = 1, max = 200)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 분류를 필수 1~64자로 제공해 숙소 서비스·운동 여가·객실 시설을 구분한다. 가정: 응답 필드명을 group, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROPERTY_SERVICES")
        @NotBlank

        @Size(min = 1, max = 64)
        String group,
        /** 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 가능 여부. false인 항목을 새 입력에 포함하면 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean enabled,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: enabled=false일 때 이유를 필수로 제공하고 선택 가능하면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String disabledReason,
        /** 가정: 응답 필드명을 defaultSelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 최초 구성에서는 모든 항목을 미선택으로 제공한다. 기존 구성의 선택 상태는 구성 조회값으로 판단한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull

        Boolean defaultSelected
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "CatalogConfigurationOptionsResponsePaymentTimingsItem", description = "카탈로그 선택지. 선택지 코드와 고객 문구를 별도로 전달한다.")
    public record PaymentTimingsItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 요청에 사용할 카탈로그 식별자. 가정: 응답 필드명을 id, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIFI")
        @NotBlank

        @Size(min = 1, max = 64)
        String id,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 화면에 표시할 선택지 문구를 필수 1~200자로 제공한다. 가정: 응답 필드명을 label, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wi-Fi")
        @NotBlank

        @Size(min = 1, max = 200)
        String label,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택지 분류를 필수 1~64자로 제공해 숙소 서비스·운동 여가·객실 시설을 구분한다. 가정: 응답 필드명을 group, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROPERTY_SERVICES")
        @NotBlank

        @Size(min = 1, max = 64)
        String group,
        /** 가정: 응답 필드명을 enabled, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 선택 가능 여부. false인 항목을 새 입력에 포함하면 거절한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @NotNull

        Boolean enabled,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: enabled=false일 때 이유를 필수로 제공하고 선택 가능하면 null이다. 가정: 응답 필드명을 disabledReason, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용하도록 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(min = 1, max = 500)
        String disabledReason,
        /** 가정: 응답 필드명을 defaultSelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 최초 구성에서는 모든 항목을 미선택으로 제공한다. 기존 구성의 선택 상태는 구성 조회값으로 판단한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        @NotNull

        Boolean defaultSelected
    ) {
    }
}

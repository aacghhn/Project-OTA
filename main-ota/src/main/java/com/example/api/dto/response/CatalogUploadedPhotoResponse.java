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

/** 업로드가 검증된 불변 사진 자산. 원본 파일과 표시용 설정은 분리한다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "CatalogUploadedPhotoResponse", description = "업로드가 검증된 불변 사진 자산. 원본 파일과 표시용 설정은 분리한다.")
public record CatalogUploadedPhotoResponse(
    /** 같은 숙소 구성에 연결할 수 있는 사진 자산 식별자. 가정: 응답 필드명을 assetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "같은 숙소 구성에 연결할 수 있는 사진 자산 식별자. 가정: 응답 필드명을 assetId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "asset_1")
    @NotBlank
    
    @Size(min = 1, max = 64)
    String assetId,
    /** 가정: 고객이 볼 수 있는 HTTPS 자산 주소다. 예약 당시 사진 참조를 유지하도록 자산 내용은 변경하지 않는다. 가정: 응답 필드명을 url, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 고객이 볼 수 있는 HTTPS 자산 주소다. 예약 당시 사진 참조를 유지하도록 자산 내용은 변경하지 않는다. 가정: 응답 필드명을 url, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "uri", pattern = "^https://", minLength = 1, maxLength = 2048, example = "https://assets.example.com/property_1/asset_1.png")
    @NotBlank
    
    @Size(min = 1, max = 2048)
    @Pattern(regexp = "^https://")
    String url,
    /** 검증된 실제 이미지 형식. JPEG 또는 PNG다. 가정: 응답 필드명을 mediaType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "검증된 실제 이미지 형식. JPEG 또는 PNG다. 가정: 응답 필드명을 mediaType, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^image/(jpeg|png)$", minLength = 1, maxLength = 16, allowableValues = {"image/jpeg", "image/png"}, example = "image/png")
    @NotBlank
    
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^image/(jpeg|png)$")
    String mediaType,
    /** 검증된 이미지 너비. 채택한 최소값은 800픽셀이다. 가정: 응답 필드명을 widthPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "검증된 이미지 너비. 채택한 최소값은 800픽셀이다. 가정: 응답 필드명을 widthPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "800", example = "800")
    @NotNull
    
    @Min(800)
    Integer widthPixels,
    /** 검증된 이미지 높이. 채택한 최소값은 600픽셀이다. 가정: 응답 필드명을 heightPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "검증된 이미지 높이. 채택한 최소값은 600픽셀이다. 가정: 응답 필드명을 heightPixels, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int32", minimum = "600", example = "600")
    @NotNull
    
    @Min(600)
    Integer heightPixels,
    /** 디코딩된 원본 이미지 크기. 채택한 상한은 십진 4MB인 4,000,000바이트다. 가정: 응답 필드명을 sizeBytes, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "디코딩된 원본 이미지 크기. 채택한 상한은 십진 4MB인 4,000,000바이트다. 가정: 응답 필드명을 sizeBytes, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않음으로 정했다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "1", maximum = "4000000", example = "2785")
    @NotNull
    
    @Min(1)
    @Max(4000000)
    Long sizeBytes
) {
}

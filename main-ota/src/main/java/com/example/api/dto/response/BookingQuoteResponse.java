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
@Schema(name = "BookingQuoteResponse", description = "재고를 확보하지 않는 최종 견적.")
public record BookingQuoteResponse(
    /** 가정: 응답 필드명을 quoteToken, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 고객·상품·기간·인원·가격·혜택·취소·결제·동의 버전을 결합한 서명 토큰을 1~4096자 문자열로 반환한다. 견적 읽기는 예약이나 선점을 저장하지 않으며 재전송된 조건의 변조를 막기 위해 토큰을 사용한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "quote-token-1")
    @NotBlank
    @Size(min = 1, max = 4096)
    String quoteToken,
    /** 가정: 응답 필드명을 expiresAt, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "가정: 견적 토큰은 발행 후 10분간 유효하고 이 시각부터 만료한다. 재고 선점 만료와 별개이며 오래된 동의를 방지하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date-time", example = "2026-11-11T14:10:00+09:00")
    @NotNull
    OffsetDateTime expiresAt,
    /** 체크아웃보다 앞서야 한다. 가정: 응답 필드명을 stayCheckIn, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "견적에 포함된 체크인·체크아웃·객실 수·객실별 인원. 체크인 포함, Asia/Seoul 날짜.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
    @NotNull
    LocalDate stayCheckIn,
    /** 가정: 응답 필드명을 stayCheckOut, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "견적에 포함된 체크인·체크아웃·객실 수·객실별 인원. 체크아웃 제외, Asia/Seoul 날짜.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-22")
    @NotNull
    LocalDate stayCheckOut,
    /** 가정: 응답 필드명을 stayRoomCount, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "견적에 포함된 체크인·체크아웃·객실 수·객실별 인원. 양수 객실 수, stayRooms 개수와 일치해야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    @Min(1)
    Integer stayRoomCount,
    /** 인원별 추가 요금은 부과하지 않는다. 가정: 응답 필드명을 stayRooms, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "견적에 포함된 체크인·체크아웃·객실 수·객실별 인원. 객실별 성인·아동 인원.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = StayRoomsItem.class))
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull  StayRoomsItem> stayRooms,
    /** 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 productId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 대상을 구분하는 불투명 식별자.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "product-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String productId,
    /** 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 productRoomTypeId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 대상을 구분하는 불투명 식별자.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "roomType-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String productRoomTypeId,
    /** 가정: 응답 필드명을 productRoomTypeName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 객실 유형 이름.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "스탠다드")
    @NotBlank
    @Size(min = 1)
    String productRoomTypeName,
    /** 가정: 필드명은 productRoomDescription으로 선택하고 공백만 있는 값 없이 필수 1~5000자 문자열로 제한한다. 객실 설정과 예약 당시 설명을 손실 없이 교환하면서 입력 크기를 제한하기 위해 정한 이름과 검증 조건이다. 가정: 응답 필드명을 productRoomDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 제공 객실의 설명.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "객실 유형 설명")
    @NotBlank
    @Size(min = 1, max = 5000)
    String productRoomDescription,
    /** 성인과 무료 아동을 모두 포함한다. 가정: 응답 필드명을 productCapacity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 객실 한 개의 총정원.", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @NotNull
    @Min(1)
    Integer productCapacity,
    /** 가정: 길이는 1~64자이고 클라이언트는 내부 의미를 해석하지 않는다. 식별자 저장과 전달을 제한된 문자열로 통일하기 위해 선택했다. 가정: 응답 필드명을 productRatePlanId, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 대상을 구분하는 불투명 식별자.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "ratePlan-1")
    @NotBlank
    @Size(min = 1, max = 64)
    String productRatePlanId,
    /** 가정: 응답 필드명을 productRatePlanName, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 적용 요금제 이름.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "기본 요금제")
    @NotBlank
    @Size(min = 1)
    String productRatePlanName,
    /** 가정: 필드명은 productDescription으로 선택하고 공백만 있는 값 없이 필수 1~5000자 문자열로 제한한다. 상품 설정과 예약 당시 설명을 손실 없이 교환하면서 입력 크기를 제한하기 위해 정한 이름과 검증 조건이다. 가정: 응답 필드명을 productDescription, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 객실과 요금제를 조합한 상품 설명.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "숙박 상품")
    @NotBlank
    @Size(min = 1, max = 5000)
    String productDescription,
    /** 고객별 추가·제거는 제공하지 않는다. 가정: 응답 필드명을 productBenefits, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 상품에 미리 묶인 혜택.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ProductBenefitsItem.class))
    @NotNull
    @Valid
    List<@NotNull  ProductBenefitsItem> productBenefits,
    /** 가정: 응답 필드명을 productPrices, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 체크인 포함 체크아웃 제외의 모든 숙박일 가격.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ProductPricesItem.class))
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull  ProductPricesItem> productPrices,
    /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 productTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 전체 기간·요청 수량의 필수 요금 포함 총액이며 가격 행 합계와 같다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
    @NotNull 
    @Min(0)
    Long productTotalAmount,
    /** 가정: productTotalCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 productTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 전체 기간·요청 수량의 필수 요금 포함 총액이며 가격 행 합계와 같다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String productTotalCurrency,
    /** 무료 취소가 가능한 정책인지 환불 불가인지 구분한다. 가정: 응답 필드명을 productCancellationPolicyKind, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 현재 견적의 취소 정책.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"FREE_CANCELLATION", "NON_REFUNDABLE"}, example = "FREE_CANCELLATION")
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?:FREE_CANCELLATION|NON_REFUNDABLE)$")
    String productCancellationPolicyKind,
    /** 무료 취소는 체크인 N일 전 00:00 Asia/Seoul을 기한으로 한다. 환불 불가이면 null이다. 예정 청구 N과 독립이다. 가정: 응답 필드명을 productCancellationPolicyDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 현재 견적의 취소 정책.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "3")
    @Min(0)
    Integer productCancellationPolicyDaysBeforeCheckIn,
    /** 기한 전 비용 0, 같은 시각부터 총액 전액. 환불 불가이면 null이고 항상 총액 전액이다. 가정: 응답 필드명을 productCancellationPolicyFreeCancellationDeadline, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 현재 견적의 취소 정책.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-17T00:00:00+09:00")
    OffsetDateTime productCancellationPolicyFreeCancellationDeadline,
    /** 고객은 임의 청구 시각을 입력하지 않는다. 가정: 응답 필드명을 productAllowedPaymentTimings, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 가정: 요금제에서 허용한 IMMEDIATE/LATER만 반환한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"IMMEDIATE\", \"LATER\"]"), schema = @Schema(implementation = String.class, allowableValues = {"IMMEDIATE", "LATER"}))
    @NotNull
    @Size(min = 1)
    @Valid
    List<@NotNull  @Pattern(regexp = "^(?:IMMEDIATE|LATER)$") String> productAllowedPaymentTimings,
    /** 나중 결제를 제공하지 않으면 null이다. 가정: 응답 필드명을 productScheduledPaymentDaysBeforeCheckIn, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 나중 결제는 체크인 N일 전 00:00 Asia/Seoul에 청구한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "1")
    @Min(0)
    Integer productScheduledPaymentDaysBeforeCheckIn,
    /** 선택 자체가 선점은 아니다. 가정: 응답 필드명을 productRemainingRooms, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "동의할 객실·서비스·가격·취소·결제 조건. 동일 객실 유형의 모든 요금제가 공유하는 전체 기간의 OTA 판매 여유.", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull
    @Min(0)
    Integer productRemainingRooms,
    /** 가정: 응답 필드명을 consents, JSON 타입을 배열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @ArraySchema(arraySchema = @Schema(description = "버전별 동의 대상. 이용약관과 개인정보 처리방침은 독립 항목.", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = ConsentsItem.class))
    @NotNull
    @Size(min = 2, max = 2)
    @Valid
    List<@NotNull  ConsentsItem> consents,
    /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
    @Schema(description = "날짜와 청구·취소 기한을 계산한 숙소 시간대. 가정: 응답 필드명을 timeZone, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?:Asia/Seoul)$")
    String timeZone
) {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingQuoteResponseStayRoomsItem", description = "한 객실의 인원 배분.")
    public record StayRoomsItem(
        /** 물리적 호실 번호와 구별하기 위한 순번이다. 가정: 응답 필드명을 roomIndex, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실별 인원 배분의 순서. 가정: 1부터 시작하며 동일 요청 안에서 중복이 없어야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull
        @Min(1)
        Integer roomIndex,
        /** 가정: 응답 필드명을 adults, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "해당 객실의 만 18세 이상 성인 수. 별도의 최소 성인 인원은 두지 않으며 성인과 아동 합계가 1 이상이고 객실 정원 이하여야 한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
        @NotNull
        @Min(0)
        Integer adults,
        /** 가정: 응답 필드명을 children, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "체크인 시점 만 0~17세 아동 수. 무료 투숙 아동도 총정원에 포함한다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
        @NotNull
        @Min(0)
        Integer children
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingQuoteResponseProductBenefitsItem", description = "판매 상품에 포함된 고정 혜택.")
    public record ProductBenefitsItem(
        /** 가정: 응답 필드명을 name, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "상품에 미리 포함된 서비스·혜택 이름. 특정 서비스를 기본 제공으로 추정하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "포함 서비스")
        @NotBlank
        @Size(min = 1)
        String name,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "혜택의 구체적 제공 내용. 가정: 응답 필드명을 description, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "상품에서 약속한 서비스")
        @NotBlank
        @Size(min = 1)
        String description,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 제공 수량은 양수이며 수량으로 표현할 수 없으면 null이다. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 명시적 null을 허용한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "1")
        @Min(1)
        Integer quantity,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 제공 수량의 단위이며 quantity가 null이면 null이다. 가정: 응답 필드명을 unit, JSON 타입을 문자열로 정하고 키는 필수이며 명시적 null을 허용한다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, example = "회")
        String unit,
        /** 가정: 응답 필드명을 conditions, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 대상·시간·이용 조건을 독립 문자열로 보존한다. 추가 조건이 없으면 빈 문자열이다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "객실당 제공")
        @NotNull
        String conditions
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingQuoteResponseProductPricesItem", description = "숙박일별 필수 요금 포함 가격 구성.")
    public record ProductPricesItem(
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 가격이 귀속되는 숙박일. 가정: 응답 필드명을 stayDate, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", example = "2026-12-20")
        @NotNull
        LocalDate stayDate,
        /** 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명을 unitAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 하나·해당 숙박일의 필수 요금 포함 양수 가격. 금액은 1원 이상이어야 하며 0원 판매가격을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
        @NotNull 
        @Min(0)
        Long unitAmount,
        /** 가정: unitCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다. 가정: 응답 필드명을 unitCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "객실 하나·해당 숙박일의 필수 요금 포함 양수 가격. 금액은 1원 이상이어야 하며 0원 판매가격을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String unitCurrency,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "이 숙박일에 요청한 객실 수. 가정: 응답 필드명을 quantity, JSON 타입을 정수로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull
        @Min(1)
        Integer quantity,
        /** 가정: 응답 필드명을 lineTotalAmount, JSON 타입을 64비트 정수로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "서버가 계산한 unitAmount × quantity 금액. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "int64", example = "120000")
        @NotNull 
        @Min(0)
        Long lineTotalAmount,
        /** 가정: 응답 필드명을 lineTotalCurrency, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "서버가 계산한 unitAmount × quantity 금액. 가정: lineTotalCurrency라는 필수 문자열에 KRW를 명시해 해당 금액의 원 단위를 구분한다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String lineTotalCurrency
    ) {
    }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "BookingQuoteResponseConsentsItem", description = "동의 대상과 제공 버전.")
    public record ConsentsItem(
        /** 가정: 응답 필드명을 type, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 동의 대상은 TERMS_OF_USE와 PRIVACY_POLICY 두 개만 정의한다. 이름 없는 추가 동의는 만들지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", allowableValues = {"TERMS_OF_USE", "PRIVACY_POLICY"}, example = "TERMS_OF_USE")
        @NotBlank
        @Size(min = 1)
        @Pattern(regexp = "^(?:TERMS_OF_USE|PRIVACY_POLICY)$")
        String type,
        /** 가정: 응답 필드명을 title, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 이용약관 또는 개인정보 처리방침을 표시 제목으로 쓴다. 실제 관찰 UI 제목이 아니다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "이용약관")
        @NotBlank
        @Size(min = 1)
        String title,
        /** 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 버전은 필수 1~64자 문자열이며 재동의 대상을 식별한다. 가정: 응답 필드명을 version, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "\\S", example = "2026-11-11")
        @NotBlank
        @Size(min = 1, max = 64)
        String version,
        /** 가정: 응답 필드명을 contentUrl, JSON 타입을 문자열로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 자체 정책 전문의 HTTPS 주소를 제공한다. 외부 업체의 정책에 동의시키지 않는다.", requiredMode = Schema.RequiredMode.REQUIRED, format = "uri", pattern = "\\S", example = "https://example.com/terms")
        @NotBlank
        @Size(min = 1)
        String contentUrl,
        /** 가정: 응답 필드명을 required, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 두 동의는 모두 필수이다. 예약 처리의 계약과 개인정보 처리를 구별해 동의 사실을 보존하기 위해 정했다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"true"}, example = "true")
        @NotNull
        @AssertTrue
        Boolean required,
        /** 가정: 응답 필드명을 initiallySelected, JSON 타입을 boolean로 정하고 키는 필수이며 null을 허용하지 않는다. 값의 주체를 접두어로 구분하여 단일 객체를 펼쳐도 원래 값과 단위·상태를 독립적으로 보존하기 위함이다. */
        @Schema(description = "가정: 초기값은 false이며 고객이 명시적으로 동의해야 한다. 묵시적 동의를 피하기 위한 결정이다.", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"false"}, example = "false")
        @NotNull
        @AssertFalse
        Boolean initiallySelected
    ) {
    }
}

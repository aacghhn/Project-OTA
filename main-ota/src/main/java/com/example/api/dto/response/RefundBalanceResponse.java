package com.example.api.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.List;

/** 동일 관측 시점의 예약 종료 사실, 확인된 수금·환불, 추가 의무와 미완료 작업. 조회 성공은 금전 처리 완료를 뜻하지 않는다. 전체 취소 결과의 존재는 cancellationReason으로 구분한다. 이 값이 null이면 cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency도 모두 null이다. 취소 결과가 존재하면 cancellationReason·cancellationDecidedAt·두 비용 값은 null일 수 없고, 자유문장 사유만 입력하지 않은 경우 cancellationReasonDetail이 null일 수 있다. */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(name = "RefundBalanceResponse", description = "동일 관측 시점의 예약 종료 사실, 확인된 수금·환불, 추가 의무와 미완료 작업. 조회 성공은 금전 처리 완료를 뜻하지 않는다. 전체 취소 결과의 존재는 cancellationReason으로 구분한다. 이 값이 null이면 cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency도 모두 null이다. 취소 결과가 존재하면 cancellationReason·cancellationDecidedAt·두 비용 값은 null일 수 없고, 자유문장 사유만 입력하지 않은 경우 cancellationReasonDetail이 null일 수 있다.")
public record RefundBalanceResponse(
    /** 조회 대상 본인 예약의 1~64자 opaque 식별자. 외부 저장소 형식을 숨기기 위해 문자열로 정했다. 가정: 응답 필드명은 reservationId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
    @Schema(description = "조회 대상 본인 예약의 1~64자 opaque 식별자. 외부 저장소 형식을 숨기기 위해 문자열로 정했다. 가정: 응답 필드명은 reservationId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64)
    @NotNull 
    @Size(min = 1, max = 64)
    String reservationId,
    /** 현재 예약 상태. 환불 실패·결과 불명에도 CANCELLED 또는 EXPIRED/RELEASED를 되돌리지 않는다. 가정: 응답 필드명은 reservationStatus, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "현재 예약 상태. 환불 실패·결과 불명에도 CANCELLED 또는 EXPIRED/RELEASED를 되돌리지 않는다. 가정: 응답 필드명은 reservationStatus, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"HELD", "CONFIRMED", "EXPIRED", "RELEASED", "CANCELLED"})
    @NotNull 
    ReservationStatus reservationStatus,
    /** 예약 취소·선점 만료·해제의 안전한 설명. 최초 상태 사유를 1~500자로 제공하고 현재 금전 실패 사유와 분리한다. 아직 확인된 예약 종료 사유가 없으면 null이다. 가정: 응답 필드명은 reservationEndReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
    @Schema(description = "예약 취소·선점 만료·해제의 안전한 설명. 최초 상태 사유를 1~500자로 제공하고 현재 금전 실패 사유와 분리한다. 아직 확인된 예약 종료 사유가 없으면 null이다. 가정: 응답 필드명은 reservationEndReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
    @Size(min = 1, max = 500)
    String reservationEndReason,
    /** 최초 전체 취소 사유이며 반복 요청·후속 환불로 바꾸지 않는다. 선점 만료·해제로 끝나 전체 취소 결과가 없으면 null이다. cancellationReason이 null이면 cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency도 모두 null이다. 값이 있으면 최초 취소 결과가 존재하며 cancellationDecidedAt과 두 비용 필드는 반드시 값이 있어야 한다. 가정: 응답 필드명은 cancellationReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "최초 전체 취소 사유이며 반복 요청·후속 환불로 바꾸지 않는다. 선점 만료·해제로 끝나 전체 취소 결과가 없으면 null이다. cancellationReason이 null이면 cancellationReasonDetail, cancellationDecidedAt, cancellationCustomerFeeAmount, cancellationCustomerFeeCurrency도 모두 null이다. 값이 있으면 최초 취소 결과가 존재하며 cancellationDecidedAt과 두 비용 필드는 반드시 값이 있어야 한다. 가정: 응답 필드명은 cancellationReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, allowableValues = {"CUSTOMER_REQUEST", "SCHEDULED_PAYMENT_FAILED", "PROPERTY_UNAVAILABLE"}, example = "CUSTOMER_REQUEST")
    CancellationReason cancellationReason,
    /** 최초 취소 때 남긴 자유문장 사유를 보존한다. 가정: 값이 있으면 1~500자로 제한한다. cancellationReason이 null이면 null이며, 취소 결과가 있어도 별도 자유문장 사유를 입력하지 않았으면 null이다. 반복 요청으로 최초 사유를 변경하지 않는다. 가정: 응답 필드명은 cancellationReasonDetail, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
    @Schema(description = "최초 취소 때 남긴 자유문장 사유를 보존한다. 가정: 값이 있으면 1~500자로 제한한다. cancellationReason이 null이면 null이며, 취소 결과가 있어도 별도 자유문장 사유를 입력하지 않았으면 null이다. 반복 요청으로 최초 사유를 변경하지 않는다. 가정: 응답 필드명은 cancellationReasonDetail, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
    @Size(min = 1, max = 500)
    String cancellationReasonDetail,
    /** 최초 취소 비용을 판단한 RFC3339 시각이며 반복 조회·취소로 갱신하지 않는다. cancellationReason이 null이면 null이고, 취소 결과가 존재하면 반드시 값이 있어야 한다. 가정: 응답 필드명은 cancellationDecidedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
    @Schema(description = "최초 취소 비용을 판단한 RFC3339 시각이며 반복 조회·취소로 갱신하지 않는다. cancellationReason이 null이면 null이고, 취소 결과가 존재하면 반드시 값이 있어야 한다. 가정: 응답 필드명은 cancellationDecidedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time", example = "2026-12-18T10:00:00+09:00")
    OffsetDateTime cancellationDecidedAt,
    /** 확정된 최초 고객 취소 비용의 KRW 정수 원 금액이며 성공 환불액과 구분한다. 이후 다른 사유나 환불 결과로 재계산하지 않는다. cancellationReason이 null이면 null이고, 취소 결과가 있으면 0 이상의 값이 반드시 있어야 한다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 cancellationCustomerFeeAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "확정된 최초 고객 취소 비용의 KRW 정수 원 금액이며 성공 환불액과 구분한다. 이후 다른 사유나 환불 결과로 재계산하지 않는다. cancellationReason이 null이면 null이고, 취소 결과가 있으면 0 이상의 값이 반드시 있어야 한다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 cancellationCustomerFeeAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "int64", minimum = "0", example = "120000")
    @Min(0)
    Long cancellationCustomerFeeAmount,
    /** cancellationCustomerFeeAmount의 통화. 가정: 문자열 KRW만 허용하여 원 단위를 명시한다. cancellationReason이 null이면 null이고, 취소 결과가 있으면 반드시 KRW이다. 가정: 응답 필드명은 cancellationCustomerFeeCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "cancellationCustomerFeeAmount의 통화. 가정: 문자열 KRW만 허용하여 원 단위를 명시한다. cancellationReason이 null이면 null이고, 취소 결과가 있으면 반드시 KRW이다. 가정: 응답 필드명은 cancellationCustomerFeeCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null 허용 조건은 이 필드에 명시한 취소 결과 존재 여부에 따른다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @Pattern(regexp = "^KRW$")
    String cancellationCustomerFeeCurrency,
    /** 고객의 확정 부담 목표액 F. 취소이면 최초 고객 비용이며 선점 만료·해제는 0원이다. 요청자가 입력하지 않는 서버 확정값이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 targetCustomerCostAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "고객의 확정 부담 목표액 F. 취소이면 최초 고객 비용이며 선점 만료·해제는 0원이다. 요청자가 입력하지 않는 서버 확정값이다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 targetCustomerCostAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long targetCustomerCostAmount,
    /** targetCustomerCostAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 targetCustomerCostCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "targetCustomerCostAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 targetCustomerCostCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String targetCustomerCostCurrency,
    /** 성공 결제 합계 P에서 성공 환불 합계 R을 뺀 확인된 순수금액 P-R. 처리 중·결과 불명 금액을 더하거나 빼지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 confirmedNetCollectionAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "성공 결제 합계 P에서 성공 환불 합계 R을 뺀 확인된 순수금액 P-R. 처리 중·결과 불명 금액을 더하거나 빼지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 confirmedNetCollectionAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long confirmedNetCollectionAmount,
    /** confirmedNetCollectionAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 confirmedNetCollectionCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "confirmedNetCollectionAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 confirmedNetCollectionCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String confirmedNetCollectionCurrency,
    /** 성공이 확인된 서로 다른 논리 결제의 amount를 합산한 수금액 P이다. charges[].attempts[] 중 SUCCEEDED만 합산하고 반복 결과 통지는 다시 더하지 않는다. 동일 observedAt의 상세 내역과 일치해야 한다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactSuccessfulCollectionAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "성공이 확인된 서로 다른 논리 결제의 amount를 합산한 수금액 P이다. charges[].attempts[] 중 SUCCEEDED만 합산하고 반복 결과 통지는 다시 더하지 않는다. 동일 observedAt의 상세 내역과 일치해야 한다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactSuccessfulCollectionAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactSuccessfulCollectionAmount,
    /** financialImpactSuccessfulCollectionAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactSuccessfulCollectionCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "financialImpactSuccessfulCollectionAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactSuccessfulCollectionCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactSuccessfulCollectionCurrency,
    /** 성공이 확인된 서로 다른 refunds[]의 amount를 합산한 환불액 R이다. FAILED 선행 이력과 PROCESSING/UNKNOWN 금액은 합산하지 않으며 financialImpactSuccessfulCollectionAmount를 초과하지 않는다. 동일 observedAt의 상세 내역과 일치해야 한다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactSuccessfulRefundAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "성공이 확인된 서로 다른 refunds[]의 amount를 합산한 환불액 R이다. FAILED 선행 이력과 PROCESSING/UNKNOWN 금액은 합산하지 않으며 financialImpactSuccessfulCollectionAmount를 초과하지 않는다. 동일 observedAt의 상세 내역과 일치해야 한다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactSuccessfulRefundAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactSuccessfulRefundAmount,
    /** financialImpactSuccessfulRefundAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactSuccessfulRefundCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "financialImpactSuccessfulRefundAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactSuccessfulRefundCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactSuccessfulRefundCurrency,
    /** 확인된 환불 잔액 max(financialImpactSuccessfulCollectionAmount-financialImpactSuccessfulRefundAmount-targetCustomerCostAmount,0)이다. 고객 부담 목표액은 취소이면 최초 고객 비용, 선점 만료·해제이면 0원이다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니며 기존 결과 확인 전 새 환불을 만들지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactRefundOutstandingAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "확인된 환불 잔액 max(financialImpactSuccessfulCollectionAmount-financialImpactSuccessfulRefundAmount-targetCustomerCostAmount,0)이다. 고객 부담 목표액은 취소이면 최초 고객 비용, 선점 만료·해제이면 0원이다. 진행·불명 작업이 있으면 최종 지급 확정액이 아니며 기존 결과 확인 전 새 환불을 만들지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactRefundOutstandingAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactRefundOutstandingAmount,
    /** financialImpactRefundOutstandingAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactRefundOutstandingCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "financialImpactRefundOutstandingAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactRefundOutstandingCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactRefundOutstandingCurrency,
    /** 확인된 미수 취소 비용 max(targetCustomerCostAmount-(financialImpactSuccessfulCollectionAmount-financialImpactSuccessfulRefundAmount),0)이다. 고객 부담 목표액은 취소이면 최초 고객 비용, 선점 만료·해제이면 0원이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패가 의무를 없애지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactCustomerOutstandingAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "확인된 미수 취소 비용 max(targetCustomerCostAmount-(financialImpactSuccessfulCollectionAmount-financialImpactSuccessfulRefundAmount),0)이다. 고객 부담 목표액은 취소이면 최초 고객 비용, 선점 만료·해제이면 0원이다. 미결제 취소에도 비용 의무가 남을 수 있으며 수금 실패가 의무를 없애지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactCustomerOutstandingAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactCustomerOutstandingAmount,
    /** financialImpactCustomerOutstandingAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactCustomerOutstandingCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "financialImpactCustomerOutstandingAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactCustomerOutstandingCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactCustomerOutstandingCurrency,
    /** charges[].attempts[] 중 PROCESSING 상태의 수금 대상액 합계이다. 성공 수금액 P에 더하지 않으며 기존 시도의 결과 확인 대상으로 남긴다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactProcessingPaymentAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "charges[].attempts[] 중 PROCESSING 상태의 수금 대상액 합계이다. 성공 수금액 P에 더하지 않으며 기존 시도의 결과 확인 대상으로 남긴다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactProcessingPaymentAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactProcessingPaymentAmount,
    /** financialImpactProcessingPaymentAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactProcessingPaymentCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "financialImpactProcessingPaymentAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactProcessingPaymentCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactProcessingPaymentCurrency,
    /** charges[].attempts[] 중 UNKNOWN 상태의 수금 대상액 합계이다. 성공 수금액 P에 더하거나 실패로 단정하지 않으며 새 시도로 대체하지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactUnknownPaymentAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "charges[].attempts[] 중 UNKNOWN 상태의 수금 대상액 합계이다. 성공 수금액 P에 더하거나 실패로 단정하지 않으며 새 시도로 대체하지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactUnknownPaymentAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactUnknownPaymentAmount,
    /** financialImpactUnknownPaymentAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactUnknownPaymentCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "financialImpactUnknownPaymentAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactUnknownPaymentCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactUnknownPaymentCurrency,
    /** refunds[] 중 PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액 R과 분리하며 financialImpactRefundOutstandingAmount에 이미 포함된 금액일 수 있으므로 중복 환불을 만들지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactProcessingRefundAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "refunds[] 중 PROCESSING 상태의 환불 대상액 합계이다. 성공 환불액 R과 분리하며 financialImpactRefundOutstandingAmount에 이미 포함된 금액일 수 있으므로 중복 환불을 만들지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactProcessingRefundAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactProcessingRefundAmount,
    /** financialImpactProcessingRefundAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactProcessingRefundCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "financialImpactProcessingRefundAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactProcessingRefundCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactProcessingRefundCurrency,
    /** refunds[] 중 UNKNOWN 상태의 환불 대상액 합계이다. 성공 환불액 R로 표시하지 않으며 financialImpactRefundOutstandingAmount에 이미 포함된 금액일 수 있다. 기존 결과 확인 전 같은 금액을 새 환불에 배정하지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactUnknownRefundAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "refunds[] 중 UNKNOWN 상태의 환불 대상액 합계이다. 성공 환불액 R로 표시하지 않으며 financialImpactRefundOutstandingAmount에 이미 포함된 금액일 수 있다. 기존 결과 확인 전 같은 금액을 새 환불에 배정하지 않는다. 가정: 금액은 Long으로 표현하여 32비트를 넘는 합산 값을 보존한다. 가정: 응답 필드명은 financialImpactUnknownRefundAmount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
    @NotNull 
    @Min(0)
    Long financialImpactUnknownRefundAmount,
    /** financialImpactUnknownRefundAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactUnknownRefundCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "financialImpactUnknownRefundAmount의 통화. 가정: 문자열 KRW만 허용하여 해당 금액의 원 단위를 명시하고 다른 금액의 통화와 합치지 않는다. 가정: 응답 필드명은 financialImpactUnknownRefundCurrency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
    @NotNull 
    @Pattern(regexp = "^KRW$")
    String financialImpactUnknownRefundCurrency,
    /** 결제 또는 환불에 PROCESSING/UNKNOWN인 작업이 있으면 true이다. false여도 미수 비용이나 미완료 환불이 있으면 금전 처리는 끝나지 않았다. 같은 observedAt의 charges/refunds 상태에서 계산한다. 가정: 응답 필드명은 financialImpactAmountsProvisional, JSON 타입은 불리언로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "결제 또는 환불에 PROCESSING/UNKNOWN인 작업이 있으면 true이다. false여도 미수 비용이나 미완료 환불이 있으면 금전 처리는 끝나지 않았다. 같은 observedAt의 charges/refunds 상태에서 계산한다. 가정: 응답 필드명은 financialImpactAmountsProvisional, JSON 타입은 불리언로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "false")
    @NotNull 
    Boolean financialImpactAmountsProvisional,
    /** 숙박대금·취소 비용 청구 및 시도의 요약 목록을 생성 순서로 제공한다. charges[].attempts[]와 같은 observedAt의 식별자·상태·금액을 사용하고, 아직 시도가 없는 청구는 attemptId=null로 구분한다. 없으면 빈 배열이며 조회가 새 청구를 만들지 않는다. 상세 시도 이력은 charges에 별도로 보존한다. 가정: 응답 필드명은 financialImpactPayments, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙박대금·취소 비용 청구 및 시도의 요약 목록을 생성 순서로 제공한다. charges[].attempts[]와 같은 observedAt의 식별자·상태·금액을 사용하고, 아직 시도가 없는 청구는 attemptId=null로 구분한다. 없으면 빈 배열이며 조회가 새 청구를 만들지 않는다. 상세 시도 이력은 charges에 별도로 보존한다. 가정: 응답 필드명은 financialImpactPayments, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = FinancialImpactPaymentsItem.class))
    @NotNull 
    @Valid
    List<@NotNull FinancialImpactPaymentsItem> financialImpactPayments,
    /** 원결제별 환불 작업의 요약 목록을 생성 순서로 제공한다. refunds와 같은 observedAt의 식별자·상태·금액을 사용한다. 없으면 빈 배열이며 조회가 새 환불을 만들지 않는다. 재시도 관계와 사건 시각의 상세 이력은 refunds에 별도로 보존한다. 가정: 응답 필드명은 financialImpactRefunds, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @ArraySchema(arraySchema = @Schema(description = "원결제별 환불 작업의 요약 목록을 생성 순서로 제공한다. refunds와 같은 observedAt의 식별자·상태·금액을 사용한다. 없으면 빈 배열이며 조회가 새 환불을 만들지 않는다. 재시도 관계와 사건 시각의 상세 이력은 refunds에 별도로 보존한다. 가정: 응답 필드명은 financialImpactRefunds, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = FinancialImpactRefundsItem.class))
    @NotNull 
    @Valid
    List<@NotNull FinancialImpactRefundsItem> financialImpactRefunds,
    /** 필요한 시스템 후속 작업을 중복 없이 제공한다. 환불 잔액 전액이 기존 PROCESSING/UNKNOWN 환불에 배정되어 있고 다른 미완료 작업이 없으면 WAIT_REFUND_RESULT만 포함한다. 빈 배열이며 환불 잔액·미수 비용도 0인 경우에만 금전 처리가 완료된 상태다. 조회는 이 작업을 새로 생성하지 않는다. 가정: 응답 필드명은 financialImpactRemainingWork, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @ArraySchema(arraySchema = @Schema(description = "필요한 시스템 후속 작업을 중복 없이 제공한다. 환불 잔액 전액이 기존 PROCESSING/UNKNOWN 환불에 배정되어 있고 다른 미완료 작업이 없으면 WAIT_REFUND_RESULT만 포함한다. 빈 배열이며 환불 잔액·미수 비용도 0인 경우에만 금전 처리가 완료된 상태다. 조회는 이 작업을 새로 생성하지 않는다. 가정: 응답 필드명은 financialImpactRemainingWork, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = CancellationRemainingWork.class, description = "가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다.", allowableValues = {"WAIT_PAYMENT_RESULT", "WAIT_REFUND_RESULT", "ISSUE_OR_RECOVER_REFUND", "COLLECT_CANCELLATION_FEE"}))
    @NotNull 
    @Valid
    List<@NotNull CancellationRemainingWork> financialImpactRemainingWork,
    /** 금전 대조 완료 여부만 표시한다. 예약 상태 및 각 결제·환불 상태를 대체하지 않는다. 가정: 응답 필드명은 reconciliationStatus, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "금전 대조 완료 여부만 표시한다. 예약 상태 및 각 결제·환불 상태를 대체하지 않는다. 가정: 응답 필드명은 reconciliationStatus, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"VERIFYING", "ACTION_REQUIRED", "COMPLETED"})
    @NotNull 
    RefundBalanceReconciliationStatus reconciliationStatus,
    /** 숙박대금과 취소 비용 청구를 생성 순서로 제공한다. 없으면 빈 배열이다. 각 청구의 시도와 재시도 관계를 보존한다. 가정: 응답 필드명은 charges, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @ArraySchema(arraySchema = @Schema(description = "숙박대금과 취소 비용 청구를 생성 순서로 제공한다. 없으면 빈 배열이다. 각 청구의 시도와 재시도 관계를 보존한다. 가정: 응답 필드명은 charges, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = ChargesItem.class))
    @NotNull 
    @Valid
    List<@NotNull ChargesItem> charges,
    /** 원결제별 모든 환불 작업을 createdAt 오름차순으로 제공한다. 없으면 빈 배열이다. 실패한 선행 작업과 후속 시도를 함께 남긴다. 가정: 응답 필드명은 refunds, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @ArraySchema(arraySchema = @Schema(description = "원결제별 모든 환불 작업을 createdAt 오름차순으로 제공한다. 없으면 빈 배열이다. 실패한 선행 작업과 후속 시도를 함께 남긴다. 가정: 응답 필드명은 refunds, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = RefundsItem.class))
    @NotNull 
    @Valid
    List<@NotNull RefundsItem> refunds,
    /** 날짜·현지 시각의 해석 기준인 숙소 시간대. 국내 숙소의 Asia/Seoul로 고정하며 사건 시각에는 오프셋을 별도로 포함한다. 가정: 응답 필드명은 timeZone, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
    @Schema(description = "날짜·현지 시각의 해석 기준인 숙소 시간대. 국내 숙소의 Asia/Seoul로 고정하며 사건 시각에는 오프셋을 별도로 포함한다. 가정: 응답 필드명은 timeZone, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"Asia/Seoul"}, example = "Asia/Seoul")
    @NotNull 
    @Pattern(regexp = "^(?:Asia/Seoul)$")
    String timeZone,
    /** 반환한 모든 상태·금액의 동일 관측 시각. 현재 조회 결과의 일관된 기준이며 과거 사건 시각을 덮어쓰지 않는다. 가정: 응답 필드명은 observedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
    @Schema(description = "반환한 모든 상태·금액의 동일 관측 시각. 현재 조회 결과의 일관된 기준이며 과거 사건 시각을 덮어쓰지 않는다. 가정: 응답 필드명은 observedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time")
    @NotNull 
    OffsetDateTime observedAt
) {
    /** 예약 취소·선점 만료·해제에 따른 후속 금전 판단에 포함되는 결제 시도. 숙박대금과 취소 비용 청구를 구분하고 기존 시도 결과만 기록한다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "RefundBalanceResponseFinancialImpactPaymentsItem", description = "예약 취소·선점 만료·해제에 따른 후속 금전 판단에 포함되는 결제 시도. 숙박대금과 취소 비용 청구를 구분하고 기존 시도 결과만 기록한다.")
    public record FinancialImpactPaymentsItem(
        /** 내부 청구 식별자는 저장소 형식을 노출하지 않는 1~64자로 정했다. 가정: 응답 필드명은 chargeId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "내부 청구 식별자는 저장소 형식을 노출하지 않는 1~64자로 정했다. 가정: 응답 필드명은 chargeId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "chg_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String chargeId,
        /** 가정: 필드명 attemptId, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 청구가 아직 결제를 시작하지 않았음을 뜻한다. 청구의 기존 논리 결제 시도를 식별한다. 수금 시작 전에는 null이다. 청구가 아직 결제를 시작하지 않았으면 null이다. 가정: 응답 필드명은 attemptId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "가정: 필드명 attemptId, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 청구가 아직 결제를 시작하지 않았음을 뜻한다. 청구의 기존 논리 결제 시도를 식별한다. 수금 시작 전에는 null이다. 청구가 아직 결제를 시작하지 않았으면 null이다. 가정: 응답 필드명은 attemptId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64)
        @Size(min = 1, max = 64)
        String attemptId,
        /** 숙박대금과 취소 비용을 같은 의무로 합산하지 않기 위한 수금 목적이다. 가정: 응답 필드명은 purpose, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "숙박대금과 취소 비용을 같은 의무로 합산하지 않기 위한 수금 목적이다. 가정: 응답 필드명은 purpose, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"STAY", "CANCELLATION_FEE"})
        @NotNull 
        CancellationPaymentPurpose purpose,
        /** SCHEDULED는 아직 시작 전, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 결과 확인 대기, CANCELLED는 실행 전 청구 중단이다. 가정: 응답 필드명은 status, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "SCHEDULED는 아직 시작 전, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 결과 확인 대기, CANCELLED는 실행 전 청구 중단이다. 가정: 응답 필드명은 status, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"})
        @NotNull 
        PaymentStatus status,
        /** 이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "이 논리 결제 시도가 대상으로 하는 정수 원 금액이며 성공 수금액과 같다고 추정하지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 서버에 이 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명은 updatedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "서버에 이 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명은 updatedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-12-16T12:00:00+09:00")
        @NotNull 
        OffsetDateTime updatedAt,
        /** 가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명을 실패로 채우지 않는다. 확정된 실패 사유가 없으면 null이며, 결과 불명을 실패 사유로 채우지 않는다. 가정: 응답 필드명은 failureReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명을 실패로 채우지 않는다. 확정된 실패 사유가 없으면 null이며, 결과 불명을 실패 사유로 채우지 않는다. 가정: 응답 필드명은 failureReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
    /** 예약 취소·선점 만료·해제에 따라 원결제에 연결된 환불 작업. 외부 환불 성공은 은행 입금 시각을 보장하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "RefundBalanceResponseFinancialImpactRefundsItem", description = "예약 취소·선점 만료·해제에 따라 원결제에 연결된 환불 작업. 외부 환불 성공은 은행 입금 시각을 보장하지 않는다.")
    public record FinancialImpactRefundsItem(
        /** 환불 작업의 1~64자 opaque 식별자로 중복 작업을 구분한다. 가정: 응답 필드명은 refundId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "환불 작업의 1~64자 opaque 식별자로 중복 작업을 구분한다. 가정: 응답 필드명은 refundId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "rfd_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String refundId,
        /** 성공한 원결제 시도의 1~64자 식별자이며 환불의 귀속을 보존한다. 가정: 응답 필드명은 originalPaymentId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "성공한 원결제 시도의 1~64자 식별자이며 환불의 귀속을 보존한다. 가정: 응답 필드명은 originalPaymentId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64, example = "pay_1")
        @NotNull 
        @Size(min = 1, max = 64)
        String originalPaymentId,
        /** PENDING은 실행 대기, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 기존 작업 확인 대기이다. 가정: 응답 필드명은 status, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "PENDING은 실행 대기, PROCESSING은 진행 중, SUCCEEDED/FAILED는 확정 결과, UNKNOWN은 기존 작업 확인 대기이다. 가정: 응답 필드명은 status, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"PENDING", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN"})
        @NotNull 
        RefundStatus status,
        /** 환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "환불 작업의 정수 원 대상액이다. 원결제의 성공 수금액을 넘어 환불할 수 없다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 서버에 환불 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명은 updatedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "서버에 환불 상태를 마지막으로 기록한 RFC3339 시각이다. 가정: 응답 필드명은 updatedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time", example = "2026-12-16T12:00:00+09:00")
        @NotNull 
        OffsetDateTime updatedAt,
        /** 가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 환불 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명에는 실패 사유를 만들지 않는다. 확정된 실패 사유가 없으면 null이며, 결과 불명을 실패 사유로 채우지 않는다. 가정: 응답 필드명은 failureReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "가정: 필드명 failureReason, String 표현을 선택했다. 응답 키는 항상 포함하며 null은 확정된 환불 실패 사유가 없음을 뜻한다. 실패가 확인된 경우 안전한 설명을 1~500자로 반환한다. 결과 불명에는 실패 사유를 만들지 않는다. 확정된 실패 사유가 없으면 null이며, 결과 불명을 실패 사유로 채우지 않는다. 가정: 응답 필드명은 failureReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
    /** 청구에 연결된 논리 결제 시도 하나. 실패 기록과 후속 시도는 서로 다른 식별자로 보존하며 같은 결과 통지를 중복 합산하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "RefundBalanceResponseChargesItemAttemptsItem", description = "청구에 연결된 논리 결제 시도 하나. 실패 기록과 후속 시도는 서로 다른 식별자로 보존하며 같은 결과 통지를 중복 합산하지 않는다.")
    public record ChargesItemAttemptsItem(
        /** 이 결제 시도의 1~64자 opaque 식별자. 길이 제한은 내부 저장소 표현을 노출하지 않기 위한 선택이다. 가정: 응답 필드명은 attemptId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "이 결제 시도의 1~64자 opaque 식별자. 길이 제한은 내부 저장소 표현을 노출하지 않기 위한 선택이다. 가정: 응답 필드명은 attemptId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64)
        @NotNull 
        @Size(min = 1, max = 64)
        String attemptId,
        /** 실패가 확정된 선행 결제 시도의 1~64자 식별자. 새 시도에서만 연결하며 예정 숙박대금의 실패 확정에 새 시도를 만들지 않는다. 실패 후 새 시도가 아닌 최초 시도에는 null이다. 가정: 응답 필드명은 retryOfAttemptId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "실패가 확정된 선행 결제 시도의 1~64자 식별자. 새 시도에서만 연결하며 예정 숙박대금의 실패 확정에 새 시도를 만들지 않는다. 실패 후 새 시도가 아닌 최초 시도에는 null이다. 가정: 응답 필드명은 retryOfAttemptId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64)
        @Size(min = 1, max = 64)
        String retryOfAttemptId,
        /** 기존 시도의 현재 결제 상태. PROCESSING/UNKNOWN은 성공·실패로 단정하지 않고 같은 시도의 결과를 확인한다. 가정: 응답 필드명은 status, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "기존 시도의 현재 결제 상태. PROCESSING/UNKNOWN은 성공·실패로 단정하지 않고 같은 시도의 결과를 확인한다. 가정: 응답 필드명은 status, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"SCHEDULED", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN", "CANCELLED"})
        @NotNull 
        PaymentStatus status,
        /** 이 시도의 수금 대상액. 성공 여부와 무관한 요청 금액이며 성공 합계에는 SUCCEEDED만 포함한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "이 시도의 수금 대상액. 성공 여부와 무관한 요청 금액이며 성공 합계에는 SUCCEEDED만 포함한다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 서버가 보존한 마스킹 결제수단 표시. 원본 카드번호나 결제 비밀값은 제공하지 않는다. 표시 길이는 1~100자로 선택해 화면용 짧은 식별 정보를 전달한다. 가정: 응답 필드명은 paymentMethodLabel, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~100자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "서버가 보존한 마스킹 결제수단 표시. 원본 카드번호나 결제 비밀값은 제공하지 않는다. 표시 길이는 1~100자로 선택해 화면용 짧은 식별 정보를 전달한다. 가정: 응답 필드명은 paymentMethodLabel, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~100자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 100)
        @NotNull 
        @Size(min = 1, max = 100)
        String paymentMethodLabel,
        /** 논리 시도를 생성한 사건 시각. 오프셋을 포함한 RFC3339이다. 가정: 응답 필드명은 createdAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "논리 시도를 생성한 사건 시각. 오프셋을 포함한 RFC3339이다. 가정: 응답 필드명은 createdAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time")
        @NotNull 
        OffsetDateTime createdAt,
        /** 외부 결제 요청을 시작한 사건 시각. 오프셋을 포함한 RFC3339이다. 외부 결제 요청을 아직 시작하지 않았으면 null이다. 가정: 응답 필드명은 startedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "외부 결제 요청을 시작한 사건 시각. 오프셋을 포함한 RFC3339이다. 외부 결제 요청을 아직 시작하지 않았으면 null이다. 가정: 응답 필드명은 startedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime startedAt,
        /** 성공 또는 실패가 확정된 사건 시각. 시간 초과만으로 채우지 않는다. 성공 또는 실패 결과가 아직 확정되지 않았으면 null이다. 가정: 응답 필드명은 resultConfirmedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "성공 또는 실패가 확정된 사건 시각. 시간 초과만으로 채우지 않는다. 성공 또는 실패 결과가 아직 확정되지 않았으면 null이다. 가정: 응답 필드명은 resultConfirmedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime resultConfirmedAt,
        /** 기존 외부 작업의 결과를 마지막으로 확인한 시각. 확인 호출의 성공 여부를 결제 성공과 혼동하지 않는다. 기존 결과의 재확인을 아직 수행하지 않았으면 null이다. 가정: 응답 필드명은 lastCheckedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "기존 외부 작업의 결과를 마지막으로 확인한 시각. 확인 호출의 성공 여부를 결제 성공과 혼동하지 않는다. 기존 결과의 재확인을 아직 수행하지 않았으면 null이다. 가정: 응답 필드명은 lastCheckedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime lastCheckedAt,
        /** 기존 외부 작업을 다음에 확인할 시스템 예정 시각. 청구 예정 시각이나 처리 완료 보장이 아니다. 예약된 결과 확인 작업이 없으면 null이다. 가정: 응답 필드명은 nextCheckAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "기존 외부 작업을 다음에 확인할 시스템 예정 시각. 청구 예정 시각이나 처리 완료 보장이 아니다. 예약된 결과 확인 작업이 없으면 null이다. 가정: 응답 필드명은 nextCheckAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime nextCheckAt,
        /** 실패가 확정됐을 때만 제공하는 안전한 업무 설명. 1~500자로 제한해 외부 민감 응답을 노출하지 않는다. 확정된 실패 사유가 없으면 null이며, 결과 불명을 실패 사유로 채우지 않는다. 가정: 응답 필드명은 failureReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "실패가 확정됐을 때만 제공하는 안전한 업무 설명. 1~500자로 제한해 외부 민감 응답을 노출하지 않는다. 확정된 실패 사유가 없으면 null이며, 결과 불명을 실패 사유로 채우지 않는다. 가정: 응답 필드명은 failureReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
    /** 숙박대금 또는 취소 비용 청구와 그 청구의 결제 시도. 청구 금액·청구 중단과 성공 수금을 구분한다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "RefundBalanceResponseChargesItem", description = "숙박대금 또는 취소 비용 청구와 그 청구의 결제 시도. 청구 금액·청구 중단과 성공 수금을 구분한다.")
    public record ChargesItem(
        /** 청구의 1~64자 opaque 식별자. 같은 청구의 시도들을 묶고 저장소 표현을 숨긴다. 가정: 응답 필드명은 chargeId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "청구의 1~64자 opaque 식별자. 같은 청구의 시도들을 묶고 저장소 표현을 숨긴다. 가정: 응답 필드명은 chargeId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64)
        @NotNull 
        @Size(min = 1, max = 64)
        String chargeId,
        /** STAY는 숙박대금, CANCELLATION_FEE는 최초 확정 취소 비용의 확인된 부족액을 받는 청구이다. 가정: 응답 필드명은 purpose, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "STAY는 숙박대금, CANCELLATION_FEE는 최초 확정 취소 비용의 확인된 부족액을 받는 청구이다. 가정: 응답 필드명은 purpose, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"STAY", "CANCELLATION_FEE"})
        @NotNull 
        CancellationPaymentPurpose purpose,
        /** 청구 생성 시 확정한 원화 정수 원 금액. 재시도할 때 금액을 늘리지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "청구 생성 시 확정한 원화 정수 원 금액. 재시도할 때 금액을 늘리지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 청구 이행 시각. 숙박대금은 예약 당시 약속을 보존하고 취소 비용 청구는 생성 시각이다. 가정: 응답 필드명은 dueAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "청구 이행 시각. 숙박대금은 예약 당시 약속을 보존하고 취소 비용 청구는 생성 시각이다. 가정: 응답 필드명은 dueAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time")
        @NotNull 
        OffsetDateTime dueAt,
        /** true이면 이 청구에 새 수금 시도를 시작하지 않는다. 이미 PROCESSING/UNKNOWN인 기존 시도의 결과 확인은 계속한다. 가정: 응답 필드명은 collectionStopped, JSON 타입은 불리언로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "true이면 이 청구에 새 수금 시도를 시작하지 않는다. 이미 PROCESSING/UNKNOWN인 기존 시도의 결과 확인은 계속한다. 가정: 응답 필드명은 collectionStopped, JSON 타입은 불리언로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        @NotNull 
        Boolean collectionStopped,
        /** 이 청구의 논리 시도들을 createdAt 오름차순으로 제공한다. 아직 시도가 없으면 빈 배열이다. 실패 기록을 후속 성공으로 지우지 않는다. 가정: 응답 필드명은 attempts, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @ArraySchema(arraySchema = @Schema(description = "이 청구의 논리 시도들을 createdAt 오름차순으로 제공한다. 아직 시도가 없으면 빈 배열이다. 실패 기록을 후속 성공으로 지우지 않는다. 가정: 응답 필드명은 attempts, JSON 타입은 배열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false), schema = @Schema(implementation = ChargesItemAttemptsItem.class))
        @NotNull 
        @Valid
        List<@NotNull ChargesItemAttemptsItem> attempts
    ) {
    }
    /** 성공한 원결제에 귀속되는 논리 환불 작업. 외부 환불 성공은 금융기관의 실제 입금 시각을 보장하지 않는다. */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name = "RefundBalanceResponseRefundsItem", description = "성공한 원결제에 귀속되는 논리 환불 작업. 외부 환불 성공은 금융기관의 실제 입금 시각을 보장하지 않는다.")
    public record RefundsItem(
        /** 환불 작업의 1~64자 opaque 식별자. 같은 결과 통지의 중복 합산을 방지할 작업을 식별한다. 가정: 응답 필드명은 refundId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "환불 작업의 1~64자 opaque 식별자. 같은 결과 통지의 중복 합산을 방지할 작업을 식별한다. 가정: 응답 필드명은 refundId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64)
        @NotNull 
        @Size(min = 1, max = 64)
        String refundId,
        /** 성공한 원결제 시도의 1~64자 식별자이며 charges[].attempts[].attemptId를 가리킨다. 환불 대상 수금 사실과 연결한다. 가정: 응답 필드명은 originalPaymentId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "성공한 원결제 시도의 1~64자 식별자이며 charges[].attempts[].attemptId를 가리킨다. 환불 대상 수금 사실과 연결한다. 가정: 응답 필드명은 originalPaymentId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 64)
        @NotNull 
        @Size(min = 1, max = 64)
        String originalPaymentId,
        /** 실패 확정 후 새 환불이 이어받은 선행 환불의 1~64자 식별자. 같은 원결제·금액·사유를 유지한다. 선행 실패를 이어받지 않는 최초 환불 작업에는 null이다. 가정: 응답 필드명은 retryOfRefundId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "실패 확정 후 새 환불이 이어받은 선행 환불의 1~64자 식별자. 같은 원결제·금액·사유를 유지한다. 선행 실패를 이어받지 않는 최초 환불 작업에는 null이다. 가정: 응답 필드명은 retryOfRefundId, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~64자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 64)
        @Size(min = 1, max = 64)
        String retryOfRefundId,
        /** PENDING 실행 대기, PROCESSING 처리 중, SUCCEEDED 성공 확정, FAILED 실패 확정, UNKNOWN 기존 결과 확인 대기를 구분한다. 가정: 응답 필드명은 status, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "PENDING 실행 대기, PROCESSING 처리 중, SUCCEEDED 성공 확정, FAILED 실패 확정, UNKNOWN 기존 결과 확인 대기를 구분한다. 가정: 응답 필드명은 status, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, allowableValues = {"PENDING", "PROCESSING", "SUCCEEDED", "FAILED", "UNKNOWN"})
        @NotNull 
        RefundStatus status,
        /** 이 환불 작업의 대상액. 동일 원결제에 연결된 SUCCEEDED(성공 확정), PROCESSING(처리 중), UNKNOWN(결과 불명) 환불의 금액 합계는 성공한 원결제액을 넘지 않는다. PROCESSING·UNKNOWN에 이미 배정한 금액을 새 환불에 중복 배정하지 않으며 기존 결과 확인 전에는 새 환불이나 재시도를 만들지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "이 환불 작업의 대상액. 동일 원결제에 연결된 SUCCEEDED(성공 확정), PROCESSING(처리 중), UNKNOWN(결과 불명) 환불의 금액 합계는 성공한 원결제액을 넘지 않는다. PROCESSING·UNKNOWN에 이미 배정한 금액을 새 환불에 중복 배정하지 않으며 기존 결과 확인 전에는 새 환불이나 재시도를 만들지 않는다. 가정: 이 금액은 Long으로 표현해 32비트 초과 합산을 보존한다. 가정: 응답 필드명은 amount, JSON 타입은 64비트 정수로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "int64", minimum = "0", example = "120000")
        @NotNull 
        @Min(0)
        Long amount,
        /** 같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. */
        @Schema(description = "같은 항목의 amount에 적용되는 통화이다. 가정: currency라는 필수 문자열에 KRW를 명시해 원 단위를 구분한다. 가정: 응답 필드명은 currency, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, pattern = "^KRW$", allowableValues = {"KRW"}, example = "KRW")
        @NotNull 
        @Pattern(regexp = "^KRW$")
        String currency,
        /** 최초 취소 또는 선점 만료·해제로 반환할 돈이 생긴 이유. 안전한 설명을 1~500자로 정해 금액과 별도로 제공한다. 가정: 응답 필드명은 reason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "최초 취소 또는 선점 만료·해제로 반환할 돈이 생긴 이유. 안전한 설명을 1~500자로 정해 금액과 별도로 제공한다. 가정: 응답 필드명은 reason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, minLength = 1, maxLength = 500)
        @NotNull 
        @Size(min = 1, max = 500)
        String reason,
        /** 환불 작업을 생성한 시각. 조회 시각과 구분한다. 가정: 응답 필드명은 createdAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "환불 작업을 생성한 시각. 조회 시각과 구분한다. 가정: 응답 필드명은 createdAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 null을 허용하지 않는다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, format = "date-time")
        @NotNull 
        OffsetDateTime createdAt,
        /** 외부 환불 요청을 처음 보낸 시각. 아직 요청하지 않았으면 상태는 PENDING이다. 외부 환불 요청을 아직 보내지 않았으면 null이다. 가정: 응답 필드명은 requestedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "외부 환불 요청을 처음 보낸 시각. 아직 요청하지 않았으면 상태는 PENDING이다. 외부 환불 요청을 아직 보내지 않았으면 null이다. 가정: 응답 필드명은 requestedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime requestedAt,
        /** 외부 환불의 성공 또는 실패가 확정된 시각. 성공이어도 실제 은행 입금 확인 시각이 아니다. 성공 또는 실패 결과가 아직 확정되지 않았으면 null이다. 가정: 응답 필드명은 resultConfirmedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "외부 환불의 성공 또는 실패가 확정된 시각. 성공이어도 실제 은행 입금 확인 시각이 아니다. 성공 또는 실패 결과가 아직 확정되지 않았으면 null이다. 가정: 응답 필드명은 resultConfirmedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime resultConfirmedAt,
        /** 기존 외부 환불 결과를 마지막으로 조회한 시각. 새 환불 생성 시각이 아니다. 기존 결과의 재확인을 아직 수행하지 않았으면 null이다. 가정: 응답 필드명은 lastCheckedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "기존 외부 환불 결과를 마지막으로 조회한 시각. 새 환불 생성 시각이 아니다. 기존 결과의 재확인을 아직 수행하지 않았으면 null이다. 가정: 응답 필드명은 lastCheckedAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime lastCheckedAt,
        /** 기존 환불 결과를 다음에 확인할 시스템 예정 시각. 이 시각까지 완료됨을 보장하지 않는다. 예약된 결과 확인 작업이 없으면 null이다. 가정: 응답 필드명은 nextCheckAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다. */
        @Schema(description = "기존 환불 결과를 다음에 확인할 시스템 예정 시각. 이 시각까지 완료됨을 보장하지 않는다. 예약된 결과 확인 작업이 없으면 null이다. 가정: 응답 필드명은 nextCheckAt, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 사건 시각은 OffsetDateTime과 오프셋을 포함한 RFC3339 문자열로 표현하여 시점 해석을 일치시킨다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, format = "date-time")
        OffsetDateTime nextCheckAt,
        /** FAILED에서만 제공하는 안전한 실패 사유. 1~500자로 정해 원시 결제 응답을 노출하지 않는다. 확정된 실패 사유가 없으면 null이며, 결과 불명을 실패 사유로 채우지 않는다. 가정: 응답 필드명은 failureReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다. */
        @Schema(description = "FAILED에서만 제공하는 안전한 실패 사유. 1~500자로 정해 원시 결제 응답을 노출하지 않는다. 확정된 실패 사유가 없으면 null이며, 결과 불명을 실패 사유로 채우지 않는다. 가정: 응답 필드명은 failureReason, JSON 타입은 문자열로 정했다. 응답 키는 필수이며 이 필드에 설명한 경우에만 null을 허용한다. 금액·사유·처리 대상의 귀속을 이름으로 구분하고 각각의 값과 단위·상태를 독립적으로 전달하기 위한 선택이다. 가정: 문자열 길이는 1~500자로 선택했다. 식별자와 화면에 표시할 안전한 문장을 제한된 길이로 전달하기 위한 제약이다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true, minLength = 1, maxLength = 500)
        @Size(min = 1, max = 500)
        String failureReason
    ) {
    }
    /** 가정: CancellationPaymentPurpose 문자열 코드를 선택하여 같은 결제라도 수금 목적을 구분한다. STAY: 숙박대금 청구; CANCELLATION_FEE: 취소 후 남은 고객 취소 비용의 수금. */
    @Schema(name = "CancellationPaymentPurpose", description = "가정: CancellationPaymentPurpose 문자열 코드를 선택하여 같은 결제라도 수금 목적을 구분한다. STAY: 숙박대금 청구; CANCELLATION_FEE: 취소 후 남은 고객 취소 비용의 수금.")
    public enum CancellationPaymentPurpose {
/** 숙박대금 청구. */
    STAY,
    /** 취소 후 남은 고객 취소 비용의 수금. */
    CANCELLATION_FEE
    }
    /** 가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다. */
    @Schema(name = "CancellationRemainingWork", description = "가정: CancellationRemainingWork 문자열 코드를 선택하여 환불 또는 비용 의무와 결과 확인 작업을 구분한다. WAIT_PAYMENT_RESULT: 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다; WAIT_REFUND_RESULT: 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다; ISSUE_OR_RECOVER_REFUND: 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다; COLLECT_CANCELLATION_FEE: 확인된 미수 취소 비용의 수금이 남아 있다.")
    public enum CancellationRemainingWork {
/** 이미 진행 중이거나 결과 불명인 결제의 기존 결과를 확인한다. */
    WAIT_PAYMENT_RESULT,
    /** 이미 진행 중이거나 결과 불명인 환불의 기존 결과를 확인한다. */
    WAIT_REFUND_RESULT,
    /** 미완료 환불의 안전한 신규 실행 또는 실패 복구가 남아 있다. */
    ISSUE_OR_RECOVER_REFUND,
    /** 확인된 미수 취소 비용의 수금이 남아 있다. */
    COLLECT_CANCELLATION_FEE
    }
    /** 가정: 금전 대조 상태를 VERIFYING, ACTION_REQUIRED, COMPLETED 문자열 enum으로 정했다. VERIFYING은 기존 결제·환불의 PROCESSING/UNKNOWN 결과 확인 중, ACTION_REQUIRED는 확인되지 않은 외부 실행 없이 환불 또는 미수 비용 처리·실패 복구가 남음, COMPLETED는 확인된 순수금액이 확정 고객 비용과 같고 미완료 작업이 없음이다. 예약이나 개별 금전 작업의 상태를 합치지 않고 잔여 처리 여부를 설명하기 위해 선택했다. */
    @Schema(name = "RefundBalanceReconciliationStatus", description = "가정: 금전 대조 상태를 VERIFYING, ACTION_REQUIRED, COMPLETED 문자열 enum으로 정했다. VERIFYING은 기존 결제·환불의 PROCESSING/UNKNOWN 결과 확인 중, ACTION_REQUIRED는 확인되지 않은 외부 실행 없이 환불 또는 미수 비용 처리·실패 복구가 남음, COMPLETED는 확인된 순수금액이 확정 고객 비용과 같고 미완료 작업이 없음이다. 예약이나 개별 금전 작업의 상태를 합치지 않고 잔여 처리 여부를 설명하기 위해 선택했다.")
    public enum RefundBalanceReconciliationStatus {
/** 기존 결제 또는 환불의 처리 중·결과 불명 작업을 확인해야 한다. */
    VERIFYING,
    /** 확정된 환불 잔액·미수 비용 또는 실패한 작업의 복구가 남아 있다. */
    ACTION_REQUIRED,
    /** 순수금액이 확정 고객 비용과 같으며 미완료 금전 작업이 없다. */
    COMPLETED
    }
}

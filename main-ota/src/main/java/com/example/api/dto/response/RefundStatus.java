package com.example.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/** 실패가 확정된 작업에 대해서만 새 논리 환불 작업으로 재시도할 수 있으며, 후속 재시도가 성공해도 선행 FAILED는 실패 이력으로 보존한다. 추가 복구 필요성은 연결된 후속 시도의 결과와 남은 작업을 함께 확인하여 판단한다. 고객의 취소 비용 확인·취소 결과·환불 및 잔액 조회와 파트너의 제공 불가 취소 전 확인 및 후속 결과 조회에서는 financialImpactRemainingWork를, 파트너 예약 상세 조회에서는 cancellationFinancialImpactRemainingWork를 확인한다. 전체 금전 대조 상태인 reconciliationStatus는 고객의 환불 및 잔액 조회에 제공되므로 그 조회에서 함께 확인한다. 후속 재시도로 해결되었다면 FAILED 이력이 남아 있어도 추가 복구가 필요하지 않을 수 있으며 단일 FAILED만으로 미완료를 단정하지 않는다. UNKNOWN에서는 성공·실패가 미확정이므로 기존 작업 결과를 먼저 확인하며 새 환불을 중복 생성하지 않는다. */
@Schema(description = "가정: RefundStatus라는 enum 이름과 문자열 코드를 선택하여 업무 상태를 API에서 구분한다. PENDING: 환불 대상이지만 아직 외부 환불을 시작하지 않았다; PROCESSING: 환불 요청 후 결과를 기다린다; SUCCEEDED: 외부 환불 성공이 확인되었으며 은행 입금 시점의 보장은 아니다; FAILED: 해당 논리 환불 작업의 실패가 확정되었다; UNKNOWN: 환불 결과가 확인되지 않아 기존 작업을 확인해야 한다.")
public enum RefundStatus {
    /** 환불 대상이지만 아직 외부 환불을 시작하지 않았다. */
    PENDING,
    /** 환불 요청 후 결과를 기다린다. */
    PROCESSING,
    /** 외부 환불 성공이 확인되었으며 은행 입금 시점의 보장은 아니다. */
    SUCCEEDED,
    /** 해당 논리 환불 작업의 실패가 확정되었다. 실패 확정 후에만 새 논리 환불 작업으로 재시도할 수 있다. 후속 재시도가 성공해도 이 실패 이력은 보존한다. 추가 복구 필요성은 연결된 후속 시도의 결과와 남은 작업을 함께 확인하여 판단한다. 고객의 취소 비용 확인·취소 결과·환불 및 잔액 조회와 파트너의 제공 불가 취소 전 확인 및 후속 결과 조회에서는 financialImpactRemainingWork를, 파트너 예약 상세 조회에서는 cancellationFinancialImpactRemainingWork를 확인한다. 전체 금전 대조 상태인 reconciliationStatus는 고객의 환불 및 잔액 조회에 제공되므로 그 조회에서 함께 확인한다. 과거 FAILED만으로 복구가 남았다고 단정하지 않는다. */
    FAILED,
    /** 환불 결과가 확인되지 않아 기존 작업을 확인해야 한다. */
    UNKNOWN
}

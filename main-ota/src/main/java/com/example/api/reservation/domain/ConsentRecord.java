package com.example.api.reservation.domain;

import java.time.Instant;

/**
 * 고객이 동의한 항목과 그 버전, 동의 시각의 스냅샷. 필수 동의 항목은 입력 경계에서 확인한다.
 */
public record ConsentRecord(String agreementId, String version, Instant agreedAt) {
}

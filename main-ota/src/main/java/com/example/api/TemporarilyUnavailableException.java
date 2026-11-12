package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 요청을 처리할 내부 조회 경로가 일시적으로 불가하다. 기존 결제의 불명 결과 자체를 이 오류로 대체하지 않는다. 가정: TemporarilyUnavailableException이라는 예외 이름과 HTTP 503을 선택하여 호출자가 거절 유형을 구분한다. */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class TemporarilyUnavailableException extends RuntimeException {
    private final String code;

    /** 가정: code는 세부 오류 분기용 문자열, message는 비밀 값을 제외한 안전한 업무 설명이다. */
    public TemporarilyUnavailableException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}

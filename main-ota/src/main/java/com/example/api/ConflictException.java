package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 현재 상태·버전 또는 멱등 키의 이전 요청과 충돌하여 변경을 수행하지 않는다. 가정: ConflictException이라는 예외 이름과 HTTP 409을 선택하여 호출자가 거절 유형을 구분한다. */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    private final String code;

    /** 가정: code는 세부 오류 분기용 문자열, message는 비밀 값을 제외한 안전한 업무 설명이다. */
    public ConflictException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}

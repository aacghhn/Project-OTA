package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 인증 주체가 없거나 인증이 만료되어 요청을 수행할 수 없다. 가정: UnauthenticatedException이라는 예외 이름과 HTTP 401을 선택하여 호출자가 거절 유형을 구분한다. */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedException extends RuntimeException {
    private final String code;

    /** 가정: code는 세부 오류 분기용 문자열, message는 비밀 값을 제외한 안전한 업무 설명이다. */
    public UnauthenticatedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}

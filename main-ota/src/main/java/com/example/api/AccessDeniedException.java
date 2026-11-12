package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 인증되었지만 고객 또는 파트너 역할이 요구된 작업 권한을 갖지 못한다. 가정: AccessDeniedException이라는 예외 이름과 HTTP 403을 선택하여 호출자가 거절 유형을 구분한다. */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {
    private final String code;

    /** 가정: code는 세부 오류 분기용 문자열, message는 비밀 값을 제외한 안전한 업무 설명이다. */
    public AccessDeniedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}

package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 입력 형식·범위 또는 업무 입력 조건을 만족하지 못했을 때 요청을 거절한다. 가정: InvalidRequestException이라는 예외 이름과 HTTP 400을 선택하여 호출자가 거절 유형을 구분한다. */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    private final String code;

    /** 가정: code는 세부 오류 분기용 문자열, message는 비밀 값을 제외한 안전한 업무 설명이다. */
    public InvalidRequestException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}

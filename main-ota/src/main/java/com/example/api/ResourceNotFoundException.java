package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 대상을 찾을 수 없거나 요청자의 소유 범위에서 찾을 수 없어 정보를 제공하지 않는다. 가정: ResourceNotFoundException이라는 예외 이름과 HTTP 404을 선택하여 호출자가 거절 유형을 구분한다. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private final String code;

    /** 가정: code는 세부 오류 분기용 문자열, message는 비밀 값을 제외한 안전한 업무 설명이다. */
    public ResourceNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}

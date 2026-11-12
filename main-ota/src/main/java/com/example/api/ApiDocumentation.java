package com.example.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/** Swagger에 인증 방식과 API 정보를 등록한다. 실제 토큰 검증과 서비스 구현은 호스트 애플리케이션이 제공한다. */
@Configuration(proxyBeanMethods = false)
@OpenAPIDefinition(info = @Info(title = "숙박 예약 및 파트너 관리 API", version = "1.0.0",
    description = "국내 숙소의 KRW 객실박 판매 계약. 숙박일은 Asia/Seoul 기준 체크인 포함·체크아웃 제외이다. 예약 상태와 결제·환불 상태를 분리한다."))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
    description = "가정: 호스트 보안 계층이 Bearer 토큰을 검증하고 Principal.name에 고객 또는 파트너 식별자를 제공한다. 작업별 역할과 자원 소유권을 따로 검사한다.")
public class ApiDocumentation {
}

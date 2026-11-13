package com.example.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springdoc.core.customizers.OperationCustomizer;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.responses.ApiResponse;
import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/** Swagger에 인증 방식과 API 정보를 등록한다. 실제 토큰 검증과 서비스 구현은 호스트 애플리케이션이 제공한다. */
@Configuration(proxyBeanMethods = false)
@OpenAPIDefinition(info = @Info(title = "숙박 예약 및 파트너 관리 API", version = "1.0.0",
    description = "국내 숙소의 KRW 객실박 판매 계약. 숙박일은 Asia/Seoul 기준 체크인 포함·체크아웃 제외이다. 예약 상태와 결제·환불 상태를 분리한다."))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
    description = "가정: 호스트 보안 계층이 Bearer 토큰을 검증하고 Principal.name에 고객 또는 파트너 식별자를 제공한다. 작업별 역할과 자원 소유권을 따로 검사한다.")
public class ApiDocumentation {

    @Bean
    OperationCustomizer commonErrorResponses() {
        return (operation, handler) -> {
            if (!handler.getBeanType().getPackageName().equals("com.example.api")) return operation;
            var profile = errorProfile(handler.getBeanType());
            boolean authenticated = Arrays.stream(handler.getMethod().getParameterTypes())
                .anyMatch(Principal.class::isAssignableFrom);
            if (authenticated) {
                operation.getResponses().addApiResponse("401", errorResponse("401", profile));
                operation.getResponses().addApiResponse("403", errorResponse("403", profile));
            }
            if (!CustomerReservationController.class.isAssignableFrom(handler.getBeanType())) {
                operation.getResponses().addApiResponse("503", errorResponse("503", profile));
            }
            return operation;
        };
    }

    private record ErrorProfile(String mediaType, boolean namedExample) {}

    private static ErrorProfile errorProfile(Class<?> controller) {
        String mediaType = CustomerReservationController.class.isAssignableFrom(controller)
            ? "*/*" : "application/json";
        return new ErrorProfile(mediaType, UnavailabilityController.class.isAssignableFrom(controller));
    }

    private static ApiResponse errorResponse(String status, ErrorProfile profile) {
        String code = switch (status) {
            case "401" -> "UNAUTHENTICATED";
            case "403" -> AccessDeniedException.CODE;
            case "503" -> "TEMPORARILY_UNAVAILABLE";
            default -> throw new IllegalArgumentException("Unsupported common error: " + status);
        };
        String description = switch (status) {
            case "401" -> "유효한 인증이 필요하다.";
            case "403" -> "요청한 작업을 수행할 역할이 없다.";
            case "503" -> "일시적인 장애로 요청을 처리할 수 없다.";
            default -> throw new IllegalArgumentException("Unsupported common error: " + status);
        };
        var value = new LinkedHashMap<String, Object>();
        value.put("code", code);
        value.put("message", errorMessage(status));
        value.put("requestId", "3d96c26b-1f39-49b1-b4a0-62c7cbb0da03");
        value.put("timestamp", "2026-11-11T03:00:00Z");
        value.put("violations", List.of());
        var media = new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"));
        if (profile.namedExample()) {
            media.addExamples("error", new Example().description("error").value(value));
        } else {
            media.example(value);
        }
        return new ApiResponse().description(description)
            .content(new Content().addMediaType(profile.mediaType(), media));
    }

    private static String errorMessage(String status) {
        return switch (status) {
            case "401" -> "로그인이 필요합니다.";
            case "403" -> "접근 권한이 없습니다.";
            case "503" -> "잠시 후 다시 시도해 주세요.";
            default -> throw new IllegalArgumentException("Unsupported common error: " + status);
        };
    }
}

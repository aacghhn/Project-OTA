package com.example.api;

import com.example.api.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** 업무 예외와 MVC 입력 검증 오류를 HTTP 상태와 구체적인 오류 본문으로 변환한다.
 * 가정: 인증 필터에서 발생한 401/403도 호스트 보안 설정이 동일한 ErrorResponse 구조로 작성한다.
 * 이 처리기는 컨트롤러 및 서비스 호출에서 발생한 예외를 다루며 인증 필터 구현을 제공하지 않는다.
 */
@ControllerAdvice
public class ApiExceptionAdvice {
    /** InvalidRequestException의 선언된 HTTP 상태와 같은 상태로 오류를 반환한다. */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getCode(), exception.getMessage(), List.of());
    }

    /** UnauthenticatedException의 선언된 HTTP 상태와 같은 상태로 오류를 반환한다. */
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthenticatedException(UnauthenticatedException exception) {
        return error(HttpStatus.UNAUTHORIZED, exception.getCode(), exception.getMessage(), List.of());
    }

    /** AccessDeniedException의 선언된 HTTP 상태와 같은 상태로 오류를 반환한다. */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return error(HttpStatus.FORBIDDEN, exception.getCode(), exception.getMessage(), List.of());
    }

    /** ResourceNotFoundException의 선언된 HTTP 상태와 같은 상태로 오류를 반환한다. */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return error(HttpStatus.NOT_FOUND, exception.getCode(), exception.getMessage(), List.of());
    }

    /** ConflictException의 선언된 HTTP 상태와 같은 상태로 오류를 반환한다. */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException exception) {
        return error(HttpStatus.CONFLICT, exception.getCode(), exception.getMessage(), List.of());
    }

    /** TemporarilyUnavailableException의 선언된 HTTP 상태와 같은 상태로 오류를 반환한다. */
    @ExceptionHandler(TemporarilyUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleTemporarilyUnavailableException(TemporarilyUnavailableException exception) {
        return error(HttpStatus.SERVICE_UNAVAILABLE, exception.getCode(), exception.getMessage(), List.of());
    }

    /** 요청 본문의 필수값·길이·중첩 값 검증 실패는 400이며 필드별 이유를 보존한다. */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBinding(BindException exception) {
        List<ErrorResponse.FieldViolation> violations = exception.getBindingResult().getFieldErrors().stream()
            .map(e -> new ErrorResponse.FieldViolation("body." + e.getField(), e.getDefaultMessage() == null ? "입력 제약을 만족하지 않습니다." : e.getDefaultMessage()))
            .toList();
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "입력 필드를 확인해 주세요.", violations);
    }

    /** 경로·쿼리·헤더 제약 실패를 400으로 연결한다. 값 자체를 응답하지 않는다. */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException exception) {
        List<ErrorResponse.FieldViolation> violations = exception.getConstraintViolations().stream()
            .map(e -> new ErrorResponse.FieldViolation(e.getPropertyPath().toString(), e.getMessage()))
            .toList();
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "요청 매개변수 제약을 만족하지 않습니다.", violations);
    }

    /** MVC 메서드 입력 검증 실패는 400이다. 반환값 검증 실패는 서버 계약 위반이므로 500으로 구분한다. */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleMethodValidation(HandlerMethodValidationException exception) {
        if (exception.isForReturnValue()) {
            return error(HttpStatus.INTERNAL_SERVER_ERROR, "RESPONSE_CONTRACT_VIOLATION", "응답을 구성하지 못했습니다.", List.of());
        }
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "요청 매개변수 제약을 만족하지 않습니다.", List.of());
    }

    /** 잘못된 JSON·enum·날짜, 매개변수 타입 오류, 필수 매개변수 누락을 같은 400 계약으로 연결한다. */
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class, MissingRequestValueException.class})
    public ResponseEntity<ErrorResponse> handleMalformedRequest(Exception exception) {
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "필수 입력의 누락 또는 형식 오류를 확인해 주세요.", List.of());
    }

    private ResponseEntity<ErrorResponse> error(HttpStatus status, String code, String message, List<ErrorResponse.FieldViolation> violations) {
        return ResponseEntity.status(status).body(new ErrorResponse(code, message, UUID.randomUUID().toString(), OffsetDateTime.now(ZoneOffset.UTC), violations));
    }
}

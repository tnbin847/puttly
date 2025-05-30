package com.tnbin.puttly.global.error;

import com.tnbin.puttly.global.common.response.base.ResponseType;
import com.tnbin.puttly.global.utils.ExceptionUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Objects;

/**
 * 애플리케이션에서 발생하는 다양한 예외를 중앙에서 관리 및 처리하기 위한 전역 예외 처리 클래스
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(BindException ex) {
        return toEntity(ResponseType.INVALID_PARAMETER_VALUE, FieldErrors.of(ex.getBindingResult()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        return toEntity(ResponseType.INVALID_PARAMETER_VALUE, FieldErrors.of(ex.getConstraintViolations()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        final String field = ex.getName();
        final String value = ExceptionUtils.nullableToString(ex.getValue());
        final String reason = field + "의 값은 " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + " 타입이어야 합니다.";
        return toEntity(ResponseType.INVALID_PARAMETER_TYPE, FieldErrors.of(field, value, reason));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return toEntity(ResponseType.NOT_SUPPORTED_METHOD, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaught(Exception ex) {
        return toEntity(ResponseType.UNKNOWN_ERROR, null);
    }

    private ResponseEntity<ErrorResponse> toEntity(final ResponseType errorType, final List<FieldErrors> errors) {
        final HttpStatus httpCode = errorType.getHttpStatus();
        if (httpCode.is4xxClientError()) {
            return ResponseEntity.status(httpCode).body(ErrorResponse.fail(errorType, errors));
        } else {
            return ResponseEntity.status(httpCode).body(ErrorResponse.error(errorType));
        }
    }
}
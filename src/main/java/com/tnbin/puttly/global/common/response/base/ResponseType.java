package com.tnbin.puttly.global.common.response.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 성공 및 에러 응답의 코드와 관련 정보들을 관리하기 위해 정의한 {@link Enum}.
 */

@RequiredArgsConstructor
@Getter
public enum ResponseType {

    /**
     * 성공 응답
     */
    OK (HttpStatus.OK, 100000, "요청 성공"),

    /**
     * 공통 에러 응답
     */
    INVALID_PARAMETER_VALUE (HttpStatus.BAD_REQUEST, -800110, "유효하지 않은 요청 파라미터"),
    INVALID_PARAMETER_TYPE (HttpStatus.BAD_REQUEST, -800130, "잘못된 타입의 요청 파라미터"),
    NOT_SUPPORTED_METHOD (HttpStatus.METHOD_NOT_ALLOWED, -805100, "지원하지 않는 메소드 요청"),
    UNKNOWN_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, -900000, "내부 서버에 에러 발생")
    ;

    private final HttpStatus httpStatus;

    private final int code;

    private final String message;
}
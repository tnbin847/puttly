package com.tnbin.puttly.global.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tnbin.puttly.global.common.response.base.Response;
import com.tnbin.puttly.global.common.response.base.ResponseType;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 *  실패 및 에러 응답 구조 정의 클래스
 */

@Getter
public class ErrorResponse extends Response {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<FieldErrors> errors;

    @Builder
    private ErrorResponse(int code, String message, List<FieldErrors> errors) {
        super(code, message);
        this.errors = errors;
    }

    /**
     * 실패 응답을 반환한다.
     *
     * @param status    응답 상태 코드
     * @param errors    에러 정보 리스트
     * @return          응답 객체
     */
    public static ErrorResponse fail(final ResponseType status, final List<FieldErrors> errors) {
        return ErrorResponse.builder()
                .code(status.getCode())
                .message(status.getMessage())
                .errors(errors == null ? new ArrayList<>() : errors)
                .build();
    }

    /**
     * 에러 응답을 반환한다.
     *
     * @param status    응답 상태 코드
     * @return          응답 객체
     */
    public static ErrorResponse error(final ResponseType status) {
        return ErrorResponse.builder()
                .code(status.getCode())
                .message(status.getMessage())
                .build();
    }
}
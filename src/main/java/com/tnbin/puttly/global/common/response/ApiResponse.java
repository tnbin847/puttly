package com.tnbin.puttly.global.common.response;

import com.tnbin.puttly.global.common.response.base.Response;
import com.tnbin.puttly.global.common.response.base.ResponseType;
import lombok.Getter;

/**
 * 성공 응답 구조 정의 클래스
 *
 * @param <T>   응답 데이터 타입
 */

@Getter
public class ApiResponse<T> extends Response {

    private final T data;

    private ApiResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 성공 응답을 반환한다.
     *
     * @param status    응답 상태 코드
     * @param data      응답 데이터
     * @return          응답 객체
     * @param <T>       응답 데이터 타입
     */
    public static<T> ApiResponse<T> ok(final ResponseType status, final T data) {
        return new ApiResponse<>(status.getCode(), status.getMessage(), data);
    }
}
package com.tnbin.puttly.global.common.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 성공과 실패 및 에러 응답 구조에서 공통적으로 포함될 필드들을 정의한 기본 응답 구조 클래스.
 */

@Getter
public class Response {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime responseTime;

    private final int code;

    private final String message;

    public Response(int code, String message) {
        this.responseTime = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }
}
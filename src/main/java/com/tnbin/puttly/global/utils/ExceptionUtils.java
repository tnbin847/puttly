package com.tnbin.puttly.global.utils;

/**
 * 예외 처리와 관련된 보조적 기능을 제공하는 유틸리티 클래스
 */

public final class ExceptionUtils {

    private ExceptionUtils() {}

    /**
     * 전달된 값을 문자열로 변환한다.
     *
     * @param object    문자열로 변환하고자 하는 값
     * @return          주어진 값이 {@code null}일 경우 빈 문자열을, 아닐 경우 값 자체를 문자열로 변환 후, 반환
     */
    public static String nullableToString(Object object) {
        return object == null ? "" : object.toString();
    }
}
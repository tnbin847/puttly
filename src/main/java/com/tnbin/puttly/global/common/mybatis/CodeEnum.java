package com.tnbin.puttly.global.common.mybatis;

/**
 * 데이터베이스에 저장 또는 조회시 사용될 코드값을 정의한 열거형을 구현하기 위해 정의한 인터페이스
 *
 * @see CodeEnumTypeHandler
 */

public interface CodeEnum {

    /**
     * 데이터베이스 처리 대상인 코드값을 반환한다.
     */
    String getCode();

    /**
     * 코드값에 대한 설명을 반환한다.
     */
    String getDescription();
}
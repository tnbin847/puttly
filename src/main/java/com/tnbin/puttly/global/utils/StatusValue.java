package com.tnbin.puttly.global.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 상태여부에 대한 값들을 코드단에서 처리하기 위해 서로 다른 타입의 데이터들을 상응되는 의미별로 정의한 열거형.
 */

@RequiredArgsConstructor
@Getter
public enum StatusValue {

    YES (1, "Y", true),
    NO (0, "N", false)
    ;

    private final int number;

    private final String string;

    private final boolean flag;
}
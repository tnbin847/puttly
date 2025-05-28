package com.tnbin.puttly.domain.user.dto.enums;

import com.tnbin.puttly.global.common.mybatis.CodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LoginType implements CodeEnum {

    DEFAULT ("LOCAL", "일반"),
    SOCIAL ("OAUTH", "소셜")
    ;

    private final String code;

    private final String description;
}
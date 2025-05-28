package com.tnbin.puttly.domain.user.dto.enums;

import com.tnbin.puttly.global.common.mybatis.CodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role implements CodeEnum {

    USER ("ROLE_USER", "일반 회원"),
    MANAGER ("ROLE_MANAGER", "시스템 매니저"),
    ADMIN ("ROLE_ADMIN", "시스템 관리자")
    ;

    private final String code;

    private final String description;
}
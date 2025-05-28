package com.tnbin.puttly.domain.user.dto;

import com.tnbin.puttly.domain.user.dto.enums.Role;
import com.tnbin.puttly.global.utils.StatusValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRoleRequest {

    private int userId;

    private Role role;

    private String usedYn;

    public UserRoleRequest(int userId, Role role, StatusValue status) {
        this.userId = userId;
        this.role = role;
        this.usedYn = status.getString();
    }
}
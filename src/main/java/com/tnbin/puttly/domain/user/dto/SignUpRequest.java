package com.tnbin.puttly.domain.user.dto;

import com.tnbin.puttly.domain.user.dto.enums.LoginType;
import com.tnbin.puttly.global.utils.StatusValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class SignUpRequest {

    private int id;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일의 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$", message = "비밀번호는 영문과 숫자, 특수문자를 모두 포함한 8~16자로 입력해주세요.")
    @Setter
    private String password;

    @NotBlank(message = "비밀번호를 한 번 더 입력해주세요.")
    private String passwordConfirm;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9]{3,15}$", message = "닉네임은 영문과 숫자를 포함한 3~15자로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "^(01[016789])[0-9]{3,4}[0-9]{4}$", message = "휴대폰 번호는 10~11자의 숫자만 입력해주세요.")
    private String phone;

    private final int enabled = StatusValue.YES.getNumber();

    @Setter
    private LoginType loginType;
}
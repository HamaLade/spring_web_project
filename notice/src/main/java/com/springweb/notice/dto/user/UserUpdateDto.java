package com.springweb.notice.dto.user;

import com.springweb.notice.config.validation.ValidationGroups;
import com.springweb.notice.domain.user.Role;
import com.springweb.notice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserUpdateDto {

    @NotEmpty(message = "패스워드는 필수 입력값 입니다", groups = ValidationGroups.NotEmptyGroup.class)
    @Size(min = 8, max = 60, message = "패스워드는 8 ~ 60자 이여야 합니다", groups = ValidationGroups.SizeGroup.class)
    private String password;

    @NotEmpty(message = "닉네임은 필수 입력값 입니다", groups = ValidationGroups.NotEmptyGroup.class)
    @Size(min = 6, max = 30, message = "닉네임은 6 ~ 30자 이여야 합니다", groups = ValidationGroups.SizeGroup.class)
    private String nickname;

    @NotEmpty(message = "이메일은 필수 입력값 입니다", groups = ValidationGroups.NotEmptyGroup.class)
    @Email(message = "잘못된 이메일 형식입니다", groups = ValidationGroups.EmailGroup.class)
    private String email;

    private Role role;

    public User toEntity() {
        return User.builder()
                .password(this.password)
                .nickname(this.nickname)
                .email(this.email)
                .build();
    }

    public static UserUpdateDto fromEntity(User user) {
        return UserUpdateDto.builder()
                .password(user.getPassword())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }
}

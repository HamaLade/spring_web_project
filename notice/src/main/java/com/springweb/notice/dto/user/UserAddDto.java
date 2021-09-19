package com.springweb.notice.dto.user;

import com.springweb.notice.config.validation.ValidationGroups;
import com.springweb.notice.domain.user.Role;
import com.springweb.notice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.springweb.notice.config.validation.ValidationGroups.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserAddDto {

    @NotEmpty(message = "아이디는 필수 입력값 입니다", groups = NotEmptyGroup.class)
    @Size(min = 6, max = 30, message = "아이디는 6 ~ 30자 이여야 합니다", groups = SizeGroup.class)
    private String username;

    @NotEmpty(message = "패스워드는 필수 입력값 입니다", groups = NotEmptyGroup.class)
    @Size(min = 8, max = 60, message = "패스워드는 8 ~ 60자 이여야 합니다", groups = SizeGroup.class)
    private String password;

    private Role role;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .role(Role.USER)
                .build();
    }

    public static UserAddDto fromEntity(User user) {
        return UserAddDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}

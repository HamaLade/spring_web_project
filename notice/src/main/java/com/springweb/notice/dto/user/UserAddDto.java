package com.springweb.notice.dto.user;

import com.springweb.notice.config.validation.ValidationGroups;
import com.springweb.notice.domain.user.Role;
import com.springweb.notice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;
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

    private String email;

    private Role role;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .email(stringNullCheck(this.email))
                .role(Role.USER)
                .build();
    }

    public String stringNullCheck(String str) {
        if (!StringUtils.hasText(str))
            return null;
        return str;
    }

    public static UserAddDto fromEntity(User user) {
        return UserAddDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}

package com.springweb.notice.dto.user;

import com.springweb.notice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginDto {

    @NotEmpty(message = "아이디를 입력해주세요")
    String username;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    String password;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .build();
    }

    public static LoginDto fromEntity(User user) {
        return LoginDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}

package com.springweb.notice.config.auth;

import com.springweb.notice.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
 * 스프링 시큐리티에서 사용자의 정보를 담는 인터페이스 UserDetails
 * 이 인터페이스를 구현하면 스프링 시큐리티에서 구현한 클래스를 사용자 정보로인식하고 인증 작업을 함
 * VO역할을 한다고 생각하면 됨
 */

@RequiredArgsConstructor
public class PrincipalDetail implements UserDetails {


    private final User user;

    // 계정이 가지고있는 권한 목록을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> user.getRole().getKey()); // 계정이 가지고있는 권한(Role) 키값을 리턴 (ROLE_USER, ROLE_ADMIN)

        return collection;
    }

    public Long getId() {
        return user.getId();
    }

    // 계정의 이름을 리턴
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정의 비밀번호를 리턴
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User updateUser) {
        user.update(updateUser);
    }

    // 계정이 만료되지 않았는지 리턴 (true : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않았는지 리턴한다 (true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴 (true : 만료되지 않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화상태인지 리턴 (true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
}

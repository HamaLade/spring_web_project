package com.springweb.notice.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER", "일반"), // 시큐리티 권한에는 ROLE_을 앞에 붙이는것이 기본
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}

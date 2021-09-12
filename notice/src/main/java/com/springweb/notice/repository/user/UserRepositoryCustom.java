package com.springweb.notice.repository.user;

import com.springweb.notice.domain.user.User;

public interface UserRepositoryCustom {
    void update(User dest, User src);
}

package com.springweb.notice.repository.user;

import com.springweb.notice.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void update(User dest, User src) {
        dest.update(src);
    }
}

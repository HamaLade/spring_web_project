package com.springweb.notice.repository.user;

import com.springweb.notice.domain.user.Role;
import com.springweb.notice.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test
    public void userJpaRepositoryWorkTest() throws Exception {

        User saveUser = userRepository.save(
                User.builder()
                        .username("user1")
                        .password("password")
                        .email("g@g")
                        .role(Role.USER)
                        .build()
        );

        User findId = userRepository.findById(saveUser.getId()).orElse(null);
        Assertions.assertThat(findId).isNotNull();
        Assertions.assertThat(saveUser).isEqualTo(findId);
    }
}
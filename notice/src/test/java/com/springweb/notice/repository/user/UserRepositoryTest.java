package com.springweb.notice.repository.user;

import com.springweb.notice.domain.user.Role;
import com.springweb.notice.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
                        .role(Role.USER)
                        .build()
        );
        System.out.println("\\");
        User findId = userRepository.findById(saveUser.getId()).orElse(null);
        Assertions.assertThat(findId).isNotNull();
        Assertions.assertThat(saveUser).isEqualTo(findId);
    }
}
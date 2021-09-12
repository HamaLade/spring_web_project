package com.springweb.notice.service.user;

import com.springweb.notice.config.auth.PrincipalDetail;
import com.springweb.notice.domain.user.User;
import com.springweb.notice.exception.UserNotExist;
import com.springweb.notice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(User user){
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            return null;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    public boolean update(User user, PrincipalDetail principalDetail) {
        User targetUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (targetUser != null) {
            if (StringUtils.hasText(user.getPassword()))
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            targetUser.update(user);
            userRepository.update(targetUser, user);
            principalDetail.setUser(targetUser);
            return true;
        }
        return false;
    }
}

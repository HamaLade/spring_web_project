package com.springweb.notice.service.user;

import com.springweb.notice.config.auth.PrincipalDetail;
import com.springweb.notice.domain.user.User;
import com.springweb.notice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender mailSender;

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

    @Transactional
    public String findUserAndChangePassword(String username, String email) {
        User findByUsername = userRepository.findByUsername(username).orElse(null);
        if (findByUsername != null && findByUsername.getEmail().equals(email)) {
            String tempPassword = UUID.randomUUID().toString().replace("-", "");
            findByUsername.setPassword(bCryptPasswordEncoder.encode(tempPassword));
            return tempPassword;
        }
        return null;
    }

    public void sendMail(String username,String tempPasswd, String toEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setFrom("gkntjriqwe@gmail.com");
        mailMessage.setSubject(username + "님의 spring notice 임시 패스워드 안내");
        mailMessage.setText(username + "님의 임시 패스워드는 [" + tempPasswd + "] 입니다");

        mailSender.send(mailMessage);

    }

    public boolean deleteUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                userRepository.delete(user);
                return true;
            }
        }
        return false;
    }
}

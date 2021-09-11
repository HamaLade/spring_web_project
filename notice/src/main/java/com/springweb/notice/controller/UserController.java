package com.springweb.notice.controller;

import com.springweb.notice.config.validation.ValidationSequence;
import com.springweb.notice.dto.user.UserAddDto;
import com.springweb.notice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;


    // 회원가입

    @GetMapping("/auth/user/join")
    public String userAddFrom(@ModelAttribute("userAddInfo") UserAddDto userAddDto) {

        return "user/userAdd";
    }

    @PostMapping("/auth/user/join")
    public String userAdd(@Validated(ValidationSequence.class) @ModelAttribute("userAddInfo") UserAddDto userAddDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/userAdd";
        }

        if (userService.join(userAddDto.toEntity()) == null) {
            bindingResult.addError(new FieldError("userAddInfo", "username", "이미 등록된 아이디입니다"));
            return "user/userAdd";
        }

        return "redirect:/";
    }

    // 로그인

    @GetMapping("/auth/login")
    public String loginFrom() {
        return "login";
    }

    // 회원정보

    @GetMapping("/user/update")
    public String userUpdateForm() {
        return "user/updateUser";
    }

}

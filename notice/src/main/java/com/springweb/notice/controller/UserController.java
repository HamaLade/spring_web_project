package com.springweb.notice.controller;

import com.springweb.notice.config.auth.PrincipalDetail;
import com.springweb.notice.config.validation.ValidationSequence;
import com.springweb.notice.dto.user.UserAddDto;
import com.springweb.notice.dto.user.UserDeleteDto;
import com.springweb.notice.dto.user.UserFindDto;
import com.springweb.notice.dto.user.UserUpdateDto;
import com.springweb.notice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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

    // 회원정보 (업데이트)

    @GetMapping("/user/update")
    public String userUpdateForm(@ModelAttribute("userUpdateInfo")UserUpdateDto userUpdateDto) {
        return "user/userUpdate";
    }

    @PutMapping("/user/update")
    public String userUpdate(@Validated @ModelAttribute("userUpdateInfo")UserUpdateDto userUpdateDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        if (bindingResult.hasErrors())
            return "user/userUpdate";

        userUpdateDto.setUsername(principalDetail.getUsername());
        if (!userService.update(userUpdateDto.toEntity(), principalDetail)) {
            bindingResult.addError(new ObjectError("userUpdateInfo", "현재 아이디의 유저 데이터를 찾을 수 없습니다."));
            return "user/userUpdate";
        }

        return "redirect:/";
    }

    // 회원찾기

    @GetMapping("/auth/user/find")
    public String userFindForm(@ModelAttribute("userFindInfo") UserFindDto userFindDto) {
        return "user/userFind";
    }

    @PostMapping("/auth/user/find")
    public String userFind(@Validated @ModelAttribute("userFindInfo") UserFindDto userFindDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "user/userFind";

        String changedPasswd = userService.findUserAndChangePassword(userFindDto.getUsername(), userFindDto.getEmail());
        if (changedPasswd == null) {
            bindingResult.addError(new ObjectError("userFindInfo", "아이디가 존재하지 않거나 입력하신 이메일과 일치하지 않습니다"));
        }

        userService.sendMail(userFindDto.getUsername(), changedPasswd, userFindDto.getEmail());

        return "redirect:/auth/login";
    }

    // 회원 삭제

    @ResponseBody
    @DeleteMapping("/user/delete")
    public String userDelete(@RequestBody UserDeleteDto userDeleteDto){
        log.info("run test1");
        if (StringUtils.hasText(userDeleteDto.getUsername()) && StringUtils.hasText(userDeleteDto.getPassword())) {
            log.info("run test2");
            if (userService.deleteUser(userDeleteDto.getUsername(), userDeleteDto.getPassword()))
                return "true";
        }
        log.info("run test3");
        return "false";
    }


}

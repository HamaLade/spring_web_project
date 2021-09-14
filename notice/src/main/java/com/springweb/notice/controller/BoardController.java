package com.springweb.notice.controller;

import com.springweb.notice.config.auth.PrincipalDetail;
import com.springweb.notice.domain.board.Board;
import com.springweb.notice.dto.board.BoardViewDto;
import com.springweb.notice.dto.board.BoardWriteDto;
import com.springweb.notice.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/write")
    public String writeForm(@ModelAttribute("writeInfo")BoardWriteDto boardWriteDto) {
        return "board/write";
    }

    @PostMapping("/board/write")
    public String write(@Validated @ModelAttribute("writeInfo")BoardWriteDto boardWriteDto, BindingResult bindingResult,
                        @AuthenticationPrincipal PrincipalDetail principalDetail) {

        if (bindingResult.hasErrors())
            return "board/write";

        boardService.write(boardWriteDto.toEntity(principalDetail.getUser()));

        return "index";
    }

    @GetMapping("/board/view/{id}")
    public String view(@PathVariable("id") Long id, @ModelAttribute("viewInfo") BoardViewDto boardViewDto) {

        Board findBoard = boardService.findView(id);
        if (findBoard == null)
            return "index"; // 찾을 수 없는 페이지

        findBoard.addCount();
        boardViewDto.getDataFromEntity(findBoard);

        return "board/view";
    }

    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable("id") Long id) {
        return "board/update";
    }
}

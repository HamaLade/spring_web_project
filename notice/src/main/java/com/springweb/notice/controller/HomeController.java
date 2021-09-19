package com.springweb.notice.controller;

import com.springweb.notice.domain.board.Board;
import com.springweb.notice.dto.board.BoardListDto;
import com.springweb.notice.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(@ModelAttribute("boardList") BoardListDto boardListDto, @RequestParam(required = false, defaultValue = "1") Integer page) {
        if (page <= 0)
            page = 1;
        Page<Board> boardPage = boardService.getPage(page);
        if (boardPage.isEmpty()) {
            if (boardPage.getTotalPages() > 0) // 부를 수 있는 페이지를 초과하여 호출 할 경우(링크를 통해 파라미터 입력)
                return "redirect:/";
            return "index";
        }
        boardListDto.getDataFromPage(boardPage, page);

        return "index";
    }
}

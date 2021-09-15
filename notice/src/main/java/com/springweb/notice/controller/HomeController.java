package com.springweb.notice.controller;

import com.springweb.notice.domain.board.Board;
import com.springweb.notice.dto.board.BoardListDto;
import com.springweb.notice.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        if (boardPage.isEmpty() || boardPage.getContent().isEmpty())
            return "index";
        boardListDto.getDataFromPage(boardPage, page);

        return "index";
    }
}

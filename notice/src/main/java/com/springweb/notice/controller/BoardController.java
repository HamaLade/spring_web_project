package com.springweb.notice.controller;

import com.springweb.notice.config.auth.PrincipalDetail;
import com.springweb.notice.domain.board.Board;
import com.springweb.notice.domain.reply.Reply;
import com.springweb.notice.dto.board.BoardUpdateDto;
import com.springweb.notice.dto.board.BoardViewDto;
import com.springweb.notice.dto.board.BoardWriteDto;
import com.springweb.notice.dto.reply.ReplyViewDto;
import com.springweb.notice.dto.reply.ReplyViewListDto;
import com.springweb.notice.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

//    글 작성

    @GetMapping("/board/write")
    public String writeForm(@ModelAttribute("writeInfo")BoardWriteDto boardWriteDto) {
        return "board/write";
    }

    @PostMapping("/board/write")
    public String write(@Validated @ModelAttribute("writeInfo")BoardWriteDto boardWriteDto, BindingResult bindingResult,
                        @AuthenticationPrincipal PrincipalDetail principalDetail) {

        log.info("writeInfo : {}", boardWriteDto);
        if (bindingResult.hasErrors())
            return "board/write";

        boardService.write(boardWriteDto.toEntity(principalDetail.getUser()));

        return "redirect:/";
    }

//    글 보기

    @GetMapping("/board/view/{id}")
    public String view(@PathVariable("id") Long id,
                       @ModelAttribute("viewInfo") BoardViewDto boardViewDto,
                       @ModelAttribute("replyListInfo") ReplyViewListDto replyViewListDto, Model model,
                       HttpServletRequest request, HttpServletResponse response) {

        Board findBoard = boardService.findView(id, request, response);
        if (findBoard == null)
            return "index"; // 찾을 수 없는 페이지

        boardViewDto.getDataFromEntity(findBoard);
        Page<Reply> replyPage = boardService.getReplyPage(findBoard);
        replyViewListDto.getDataFromPage(replyPage, 1);
        List<ReplyViewDto> replyList = new ArrayList<>();
        for (Reply reply : replyPage) {
            replyList.add(ReplyViewDto.getDataFromEntity(reply));
        }
        model.addAttribute("replyList", replyList);

        return "board/view";
    }

//    글 삭제

    @DeleteMapping("/board/delete/{id}")
    public String delete(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        if (boardService.delete(id, principalDetail.getUser()))
            return "redirect:/";
        else
            return "redirect:/board/view/" + id;
    }
    
//    글 수정

    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable("id") Long id, @ModelAttribute("updateInfo") BoardUpdateDto boardUpdateDto) {

        Board board = boardService.findById(id);
        if (board == null)
            return "index";

        boardUpdateDto.getDataFromBoard(board);

        return "board/update";
    }

    @PutMapping("board/update/{id}")
    public String update(@PathVariable("id") Long id, @Validated @ModelAttribute("updateInfo") BoardUpdateDto boardUpdateDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        if (bindingResult.hasErrors())
            return "/board/update";

        if (!boardService.update(id, principalDetail.getUsername(), boardUpdateDto.getTitle(), boardUpdateDto.getContent()))
            bindingResult.addError(new ObjectError("updateInfo", "글이 존재하지 않거나 허용되지 않은 접근입니다"));

        return "redirect:/board/view/" + id;
    }
}

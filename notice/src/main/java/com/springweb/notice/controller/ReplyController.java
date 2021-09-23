package com.springweb.notice.controller;

import com.springweb.notice.config.auth.PrincipalDetail;
import com.springweb.notice.domain.board.Board;
import com.springweb.notice.domain.reply.Reply;
import com.springweb.notice.dto.reply.ReplyViewDto;
import com.springweb.notice.dto.reply.ReplyViewListDto;
import com.springweb.notice.service.board.BoardService;
import com.springweb.notice.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final BoardService boardService;
    private final ReplyService replyService;

    //리플 추가

    @PostMapping("/reply/add/{boardId}")
    public String addReply(@PathVariable("boardId") Long boardId,
                           @RequestBody Reply reply,
                           @AuthenticationPrincipal PrincipalDetail principalDetail,
                           Model model) {

        Board board = replyService.replyAdd(boardId, reply, principalDetail.getUser());
        if (board == null)
            throw new IllegalArgumentException("게시판의 데이터를 찾을 수 없습니다");

        Page<Reply> replyPage = replyService.getReplyPage(1, board);
        List<ReplyViewDto> replyList = new ArrayList<>();
        for (Reply replys : replyPage) {
            replyList.add(ReplyViewDto.getDataFromEntity(replys));
        }
        ReplyViewListDto replyListInfo = new ReplyViewListDto();
        replyListInfo.getDataFromPage(replyPage, 1);

        model.addAttribute("replyListInfo", replyListInfo);
        model.addAttribute("replyList", replyList);
        return "board/view :: #replyZone";
    }

    // 리플 삭제

    @DeleteMapping("reply/delete/{boardId}/{replyPage}/{replyId}")
    public String deleteReply(@PathVariable("boardId") Long boardId,
                              @PathVariable("replyPage") Integer replyPage,
                              @PathVariable("replyId") Long replyId,
                              Model model) {
        Board board = boardService.findById(boardId);
        if (board == null)
            throw new IllegalArgumentException("게시판의 데이터를 찾을 수 없습니다");
        if (!replyService.replyDelete(replyId))
            throw new IllegalArgumentException("댓글의 데이터를 찾을 수 없습니다");

        Page<Reply> replyPages = replyService.getReplyPage(replyPage, board);
        List<ReplyViewDto> replyList = new ArrayList<>();
        for (Reply reply : replyPages) {
            replyList.add(ReplyViewDto.getDataFromEntity(reply));
        }

        ReplyViewListDto replyListInfo = new ReplyViewListDto();
        replyListInfo.getDataFromPage(replyPages, replyPage);

        model.addAttribute("replyListInfo", replyListInfo);
        model.addAttribute("replyList", replyList);
        return "board/view :: #replyZone";
    }

    // 리플 페이징

    @GetMapping("/reply/page/{boardId}/{replyPage}")
    public String pageReply(@PathVariable("boardId") Long boardId,
                            @PathVariable("replyPage") Integer replyPage,
                            Model model) {

        Board board = boardService.findById(boardId);
        if (board == null)
            throw new IllegalArgumentException("게시판의 데이터를 찾을 수 없습니다");

        Page<Reply> replyPages = replyService.getReplyPage(replyPage, board);
        List<ReplyViewDto> replyList = new ArrayList<>();
        for (Reply reply : replyPages) {
            replyList.add(ReplyViewDto.getDataFromEntity(reply));
        }

        ReplyViewListDto replyListInfo = new ReplyViewListDto();
        replyListInfo.getDataFromPage(replyPages, replyPage);

        model.addAttribute("replyListInfo", replyListInfo);
        model.addAttribute("replyList", replyList);
        return "board/view :: #replyZone";
    }
}

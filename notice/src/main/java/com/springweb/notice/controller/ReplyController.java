package com.springweb.notice.controller;

import com.springweb.notice.config.auth.PrincipalDetail;
import com.springweb.notice.domain.reply.Reply;
import com.springweb.notice.dto.reply.ReplyViewDto;
import com.springweb.notice.dto.reply.ReplyViewListDto;
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

    private final ReplyService replyService;

    @PostMapping("/reply/add/{boardId}")
    public String addReply(@PathVariable("boardId") Long boardId,
                           @RequestBody Reply reply,
                           @AuthenticationPrincipal PrincipalDetail principalDetail,
                           Model model) {

        if (!replyService.replyAdd(boardId, reply, principalDetail.getUser()))
            throw new IllegalArgumentException("게시판의 데이터를 찾을 수 없습니다");
        Page<Reply> replyPage = replyService.getReplyPage(1);
        List<ReplyViewDto> replyList = new ArrayList<>();
        for (Reply replys : replyPage) {
            replyList.add(ReplyViewDto.getDataFromEntity(replys));
        }

        model.addAttribute("replyList", replyList);
        return "board/view :: #replyCard";
    }

    @DeleteMapping("reply/delete/{replyId}/{replyPage}")
    public String deleteReply(@PathVariable("replyId") Long replyId,
                              @PathVariable("replyPage") Integer replyPage,
                              Model model) {
        if (!replyService.replyDelete(replyId))
            throw new IllegalArgumentException("게시판의 데이터를 찾을 수 없습니다");
        Page<Reply> replyPages = replyService.getReplyPage(replyPage);
        List<ReplyViewDto> replyList = new ArrayList<>();
        for (Reply reply : replyPages) {
            replyList.add(ReplyViewDto.getDataFromEntity(reply));
        }

        model.addAttribute("replyList", replyList);
        return "board/view :: #replyCard";
    }
}

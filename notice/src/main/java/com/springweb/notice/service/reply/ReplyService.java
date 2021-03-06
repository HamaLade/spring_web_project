package com.springweb.notice.service.reply;

import com.springweb.notice.domain.board.Board;
import com.springweb.notice.domain.reply.Reply;
import com.springweb.notice.domain.user.User;
import com.springweb.notice.repository.reply.ReplyRepository;
import com.springweb.notice.service.board.BoardService;
import com.springweb.notice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardService boardService;
    private final UserService userService;

    public Board replyAdd(Long boardId, Reply reply, User user) {

        Board board = boardService.findById(boardId);

        if (board == null)
            return null;
        if (StringUtils.hasText(reply.getContent())) {
            reply.replyInit(board, user);
            replyRepository.save(reply);
        }

        return board;
    }

    public boolean replyDelete(Long ReplyId) {
        Optional<Reply> replyOptional = replyRepository.findById(ReplyId);
        Reply reply = replyOptional.orElse(null);

        if (reply == null)
            return false;
        replyRepository.delete(reply);
        return true;
    }

    public Page<Reply> getReplyPage(Integer page, Board board) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "id"));
        return replyRepository.findByBoard(board, pageRequest);
    }
}

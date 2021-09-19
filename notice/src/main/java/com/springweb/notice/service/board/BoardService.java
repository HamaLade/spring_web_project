package com.springweb.notice.service.board;

import com.springweb.notice.domain.board.Board;
import com.springweb.notice.domain.reply.Reply;
import com.springweb.notice.domain.user.User;
import com.springweb.notice.repository.board.BoardRepository;
import com.springweb.notice.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public Long write(Board board) {
        return boardRepository.save(board).getId();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Transactional
    public Board findView(Long id, HttpServletRequest request, HttpServletResponse response) {
        Board result = null;
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
                result = boardRepository.findBoardByIdFetchJoinUser(id).orElse(null);
                if (result != null)
                    result.addCount();
            }
            result = boardRepository.findBoardByIdFetchJoinUser(id).orElse(null);
        } else {
            Cookie newCookie = new Cookie("postView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
            result = boardRepository.findBoardByIdFetchJoinUser(id).orElse(null);
            if (result != null)
                result.addCount();
        }
        return result;
    }

    public boolean delete(Long id, User user) {
        Board view = boardRepository.findBoardByIdFetchJoinUser(id).orElse(null);
        if (view != null && view.getUser().getUsername().equals(user.getUsername()))
            boardRepository.delete(view);
        else
            return false;
        return true;
    }

    public boolean update(Long id, String username, String title, String content) {
        return boardRepository.updateBoard(id, title, lineReplace(content), username);
    }

    public Page<Board> getPage(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "id"));
        return boardRepository.findAll(pageRequest);
    }

    public Page<Reply> getReplyPage() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        return replyRepository.findAll(pageRequest);
    }

    public String lineReplace(String content) {
        return content.replace("\r\n", "<br>");
    }

}

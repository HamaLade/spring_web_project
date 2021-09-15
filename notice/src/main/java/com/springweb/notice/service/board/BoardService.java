package com.springweb.notice.service.board;

import com.springweb.notice.domain.board.Board;
import com.springweb.notice.domain.user.User;
import com.springweb.notice.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Long write(Board board) {
        return boardRepository.save(board).getId();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board findView(Long id) {
        return boardRepository.findBoardByIdJoinUserAndAddCount(id).orElse(null);
    }

    public boolean delete(Long id, User user) {
        Board view = boardRepository.findBoardByIdJoinUser(id).orElse(null);
        if (view != null && view.getUser().getUsername().equals(user.getUsername()))
            boardRepository.delete(view);
        else
            return false;
        return true;
    }

    public boolean update(Long id, String username, String title, String content) {
        return boardRepository.updateBoard(id, title, content, username);
    }

    public Page<Board> getPage(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "id"));
        return boardRepository.findAll(pageRequest);
    }

}

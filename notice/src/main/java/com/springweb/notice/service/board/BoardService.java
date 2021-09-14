package com.springweb.notice.service.board;

import com.springweb.notice.domain.board.Board;
import com.springweb.notice.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Long write(Board board) {
        return boardRepository.save(board).getId();
    }

    public Board findView(Long id) {
        return boardRepository.findBoardByIdJoinUser(id).orElse(null);
    }

}

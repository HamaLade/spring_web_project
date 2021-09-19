package com.springweb.notice.repository.board;

import com.springweb.notice.domain.board.Board;

import java.util.Optional;

public interface BoardRepositoryCutsom {

    Optional<Board> findBoardByIdFetchJoinUser(Long id);

    boolean updateBoard(Long id, String title, String content, String username);
}

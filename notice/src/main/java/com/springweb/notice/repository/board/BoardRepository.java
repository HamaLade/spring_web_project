package com.springweb.notice.repository.board;

import com.springweb.notice.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b, u from Board b join b.user u where b.id=:id")
    Optional<Board> findBoardByIdJoinUser(Long id);
}

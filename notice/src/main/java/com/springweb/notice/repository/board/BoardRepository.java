package com.springweb.notice.repository.board;

import com.springweb.notice.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCutsom {

    Page<Board> findByContentContains(String content, Pageable pageable);

    Page<Board> findAll(Pageable pageable);

    Page<Board> findByTitleContains(String title, Pageable pageable);

}

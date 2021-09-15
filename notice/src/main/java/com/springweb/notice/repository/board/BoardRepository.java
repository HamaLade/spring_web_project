package com.springweb.notice.repository.board;

import com.springweb.notice.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCutsom {

    Page<Board> findAll(Pageable pageable);

    Page<Board> findByTitle(String title, Pageable pageable);

    Page<Board> findByContent(String content, Pageable pageable);

}

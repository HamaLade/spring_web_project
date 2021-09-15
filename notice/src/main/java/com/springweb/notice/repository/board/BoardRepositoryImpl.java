package com.springweb.notice.repository.board;

import com.springweb.notice.domain.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardRepositoryImpl implements BoardRepositoryCutsom{

    private final EntityManager em;


    @Override
    public Optional<Board> findBoardByIdJoinUser(Long id) {
        List<Board> resultList = em.createQuery("select b from Board b join fetch b.user where b.id=:id", Board.class)
                .setParameter("id", id)
                .getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        Board board = resultList.get(0);
        return Optional.of(board);
    }

    @Override
    @Transactional
    public Optional<Board> findBoardByIdJoinUserAndAddCount(Long id) {
        List<Board> resultList = em.createQuery("select b from Board b join fetch b.user where b.id=:id", Board.class)
                .setParameter("id", id)
                .getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        Board board = resultList.get(0);
        board.addCount();
        return Optional.of(board);
    }


    @Override
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    public boolean updateBoard(Long id, String title, String content, String username) {
        List<Board> resultList = em.createQuery("select b from Board b join fetch b.user where b.id=:id", Board.class)
                .setParameter("id", id)
                .getResultList();

        if (resultList.isEmpty() || !resultList.get(0).getUser().getUsername().equals(username))
            return false;
        resultList.get(0).updateBoard(title, content);

        return true;
    }
}

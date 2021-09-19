package com.springweb.notice.repository.reply;

import com.springweb.notice.domain.board.Board;
import com.springweb.notice.domain.reply.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyRepositoryImpl implements ReplyRepositoryCustom{

    private final EntityManager em;

    @Override
    public Optional<Reply> findReplyByIdFetchJoinUser(Long id) {
        List<Reply> resultList = em.createQuery("select r from Reply r join fetch r.user where r.id=:id", Reply.class)
                .setParameter("id", id)
                .getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        Reply reply = resultList.get(0);
        return Optional.of(reply);
    }
}

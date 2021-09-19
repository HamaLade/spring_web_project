package com.springweb.notice.repository.reply;

import com.springweb.notice.domain.reply.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplyRepositoryCustom {

    @Query(value = "select r from Reply r join fetch r.user", countQuery = "select count(r) from Reply r") // 혹은 count 대신 @EntityGraph를 사용해도 됨
    Page<Reply> findAll(Pageable pageable);
}

package com.springweb.notice.repository.reply;

import com.springweb.notice.domain.reply.Reply;

import java.util.Optional;

public interface ReplyRepositoryCustom {

    public Optional<Reply> findReplyByIdFetchJoinUser(Long id);
}

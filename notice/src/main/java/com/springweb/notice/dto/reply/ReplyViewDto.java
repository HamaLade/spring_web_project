package com.springweb.notice.dto.reply;

import com.springweb.notice.domain.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReplyViewDto {

    private Long id;
    private String content;
    private String username;
    private LocalDateTime createTime;

    public static ReplyViewDto getDataFromEntity(Reply reply) {
        return ReplyViewDto.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .username(reply.getUser().getUsername())
                .createTime(reply.getCreateTime())
                .build();
    }

}

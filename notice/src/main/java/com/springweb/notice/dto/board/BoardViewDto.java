package com.springweb.notice.dto.board;

import com.springweb.notice.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardViewDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createTime;
    private int count;

    public void getDataFromEntity(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUser().getUsername();
        this.createTime = board.getCreateTime();
        this.count = board.getCount();
    }
}

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
public class BoardListInfoDto {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createDate;

    public static BoardListInfoDto getDataFromBoard(Board board) {
        return BoardListInfoDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createDate(board.getCreateTime())
                .build();
    }
}

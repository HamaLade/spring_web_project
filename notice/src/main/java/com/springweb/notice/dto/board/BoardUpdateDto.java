package com.springweb.notice.dto.board;

import com.springweb.notice.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardUpdateDto {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    public void getDataFromBoard(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = replaceNewline(board.getContent());
    }

    public String replaceNewline(String content) {
        return content.replace("<br>", "\n");
    }
}

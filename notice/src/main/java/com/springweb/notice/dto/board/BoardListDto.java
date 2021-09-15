package com.springweb.notice.dto.board;

import com.springweb.notice.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardListDto {
    private int page;
    private int totalPage;

    private List<BoardListInfoDto> infoList = new ArrayList<>();

    public void getDataFromPage(Page<Board> page, int nowPage) {
        this.page = nowPage;
        this.totalPage = page.getTotalPages();
        List<Board> contents = page.getContent();
        for (Board content : contents) {
            this.infoList.add(BoardListInfoDto.getDataFromBoard(content));
        }
    }

}

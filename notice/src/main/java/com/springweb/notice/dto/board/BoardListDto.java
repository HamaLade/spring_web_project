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
    private int nowPage;
    private int startPage;
    private int endPage;
    private int totalPage;

    private List<BoardListInfoDto> infoList = new ArrayList<>();

    public void getDataFromPage(Page<Board> page, int nowPage) {
        this.nowPage = nowPage;
        this.startPage = Math.max(0, page.getPageable().getPageNumber() - 3) + 1; // getPageable.getPageNumber는 현재의 페이지를 리턴하는데 페이지는 0부터 시작하므로 주의해야함
        this.endPage = Math.min(page.getTotalPages() + 1, page.getPageable().getPageNumber() + 4) - 1;
        this.totalPage = page.getTotalPages();
        List<Board> contents = page.getContent();
        for (Board content : contents) {
            this.infoList.add(BoardListInfoDto.getDataFromBoard(content));
        }
    }

}

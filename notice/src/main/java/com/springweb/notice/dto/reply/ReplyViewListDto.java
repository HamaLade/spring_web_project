package com.springweb.notice.dto.reply;

import com.springweb.notice.domain.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReplyViewListDto {

    private int nowPage;
    private int startPage;
    private int endPage;
    private int totalPage;

    public void getDataFromPage(Page<Reply> page, int nowPage) {
        this.nowPage = nowPage;
        this.startPage = Math.max(0, page.getPageable().getPageNumber() - 3) + 1;
        this.endPage = Math.min(page.getTotalPages() + 1, page.getPageable().getPageNumber() + 4) - 1;
        this.totalPage = page.getTotalPages();
    }
}

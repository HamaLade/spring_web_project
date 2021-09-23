package com.springweb.notice.dto.board;

import com.springweb.notice.domain.board.Board;
import com.springweb.notice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardWriteDto {

    @NotEmpty
    @Size(max = 150)
    private String title;

    @NotEmpty
    private String content;

    private int count;

    private User user;

    public Board toEntity(User user) {
        return Board.builder()
                .title(this.title)
                .content(lineReplace(this.content))
                .count(0)
                .user(user)
                .build();
    }

    public String lineReplace(String content) {
        return content.replace("\n", "<br>");
    }

    public void setUser(User user) {
        this.user = user;
    }
}

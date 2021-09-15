package com.springweb.notice.domain.board;

import com.springweb.notice.domain.BaseTimeEntity;
import com.springweb.notice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@SequenceGenerator(name = "board_seq_generator", sequenceName = "board_seq")
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Lob
    private String content;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addCount() {
        this.count++;
    }

    public void updateBoard(String title, String content) {
        if (StringUtils.hasText(title))
            this.title = title;
        if (StringUtils.hasText(content))
            this.content = content;
    }
}

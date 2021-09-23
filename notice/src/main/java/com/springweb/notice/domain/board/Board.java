package com.springweb.notice.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springweb.notice.domain.BaseTimeEntity;
import com.springweb.notice.domain.reply.Reply;
import com.springweb.notice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@SequenceGenerator(name = "board_seq_generator", sequenceName = "board_seq")
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @Column(name = "board_id")
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

    @OrderBy("id desc")
    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> replyList = new ArrayList<>();

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

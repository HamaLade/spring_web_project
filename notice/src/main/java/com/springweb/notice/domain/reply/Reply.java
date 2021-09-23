package com.springweb.notice.domain.reply;

import com.springweb.notice.domain.BaseTimeEntity;
import com.springweb.notice.domain.board.Board;
import com.springweb.notice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@SequenceGenerator(name = "reply_seq_generator", sequenceName = "reply_seq")
@Entity
public class Reply extends BaseTimeEntity {

    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_seq_generator")
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void boardChanger(Board board) {
        this.board = board;
        board.getReplyList().add(this);
    }

    public void boardDelete(Board board) {
        board.getReplyList().remove(this);
    }

    public Reply replyInit(Board board, User user) {
        this.content = this.getContent().replace("\n", "<br>");

        boardChanger(board);
        this.user = user;

        return this;
    }
}

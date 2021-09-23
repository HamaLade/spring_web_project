package com.springweb.notice.domain.user;

import com.springweb.notice.domain.BaseTimeEntity;
import com.springweb.notice.domain.board.Board;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@SequenceGenerator(name = "user_seq_generator", sequenceName = "user_seq")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Setter
    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 60)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Board> board;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void update(User user) {
        if (StringUtils.hasText(user.password))
            this.password = user.getPassword();
        if (StringUtils.hasText(user.email))
            this.email = user.getEmail();
    }
}

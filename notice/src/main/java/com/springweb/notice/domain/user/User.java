package com.springweb.notice.domain.user;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@SequenceGenerator(name = "user_seq_generator", sequenceName = "user_seq")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Setter
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false, length = 60)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

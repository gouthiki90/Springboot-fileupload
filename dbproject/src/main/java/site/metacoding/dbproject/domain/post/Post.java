package site.metacoding.dbproject.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import site.metacoding.dbproject.domain.user.User;

@Entity
public class Post {
    // IDENTITY 전략은 DB에게 번호증가 전략을 위임하는 것 - 알아서 디비에 맞게 찾아준다.
    @Id // 프라이머리키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 프라이머리키

    @Column(length = 300, nullable = false) // 20byte로 바꾸기, 유니크 설정
    private String title; // 아이디

    @Lob // CLOB 4GB 문자 타입의 자료형이 되었음
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne // userId로 만들어줌
    private User user;

    private LocalDateTime createDate;
}

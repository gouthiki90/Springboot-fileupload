package site.metacoding.dbproject.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.dbproject.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class) // 테이블 주시해서 수정과 입력 날짜 주시함
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
    @ManyToOne(fetch = FetchType.EAGER) // userId로 만들어줌
    private User user; // 세션에 있으니 로그인 하면 불러옴

    @CreatedDate // INSERT
    private LocalDateTime createDate; // INSERT 될 때 들어간 날짜 들어감
    @LastModifiedDate // INSERT, UPDATE
    private LocalDateTime updateDate; // 마지막에 업데이트 될 때 날짜 수정됨
}

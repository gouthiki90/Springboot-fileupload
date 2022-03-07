package site.metacoding.dbproject.domain.user;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA 라이브러리는 Java Persistence(영구적인) API(노출되어 있는 메서드)이라고 한다. 
// 1. CRUD 메서드 기본 제공
// 2. 자바코드로 DB를 자동 생성하는 기능 제공 - 설정파일 설정하기
// 3. ORM 제공 - Object Realtion Mapping

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class) // 테이블 주시해서 수정과 입력 날짜 주시함
@Entity // 서버 실행시 해당 클래스로 테이블을 생성
public class User {
    // IDENTITY 전략은 DB에게 번호증가 전략을 위임하는 것 - 알아서 디비에 맞게 찾아준다.
    @Id // 프라이머리키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 프라이머리키

    @Column(length = 20, unique = true) // 20byte로 바꾸기, 유니크 설정
    private String username; // 아이디
    @Column(length = 12, nullable = false)
    private String password;

    @Column(length = 16000000)
    private String email;

    @CreatedDate // INSERT
    private LocalDateTime createDate; // INSERT 될 때 들어간 날짜 들어감
    @LastModifiedDate // INSERT, UPDATE
    private LocalDateTime updateDate; // 마지막에 업데이트 될 때 날짜 수정됨
}

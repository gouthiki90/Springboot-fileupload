package site.metacoding.dbproject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.domain.user.UserRepository;

@RequiredArgsConstructor // DI를 하게 해줌, final 생성자 만들어줌
@Service
public class UserService { // 레파지토리에 의존함

    private final UserRepository userRepository; // 의존성 주입

    // 컨트롤러에 필요한 서비스들
    // 스택 내부에 들어오는 로직은 비즈니스 로직이다. 핵심 로직.

    public String 유저네임중복검사(String username){ // sameCheck
        User userEntity =  userRepository.mUsernameSameCheck(username);

        //2. 있으면 json 응답 클래스 리턴
        if(userEntity == null){
            return "없어";
        } else {
            return "있어";
        }
    }
    
    @Transactional
    public void 회원가입(User user){ // joinForm
        userRepository.save(user); // 응답 안 해줘도 되고 실행만 해주면 됨
    }

    public User 로그인(User user){ // login
        return userRepository.mLogin(user.getUsername(), user.getPassword());
    }

    
    public User 유저정보보기(Integer id){ // detail
        Optional<User> userOp = userRepository.findById(id); // 유저 정보 DB 데이터 찾기

        if (userOp.isPresent()) { // data가 있으면
            User userEntity = userOp.get(); // userEntity 생성

            return userEntity; // userEntity 주기

        } else {
            return null; // 없으면 null 보내기
        }
    }

    @Transactional
    public void 유저수정(Integer id, User user){

        //1. 영속화
        // Optional<User> userOp = userRepository.findByid(id);

        // if(userOp.isPresent){
            // User userEntity = userOp.get();
            // userEntity
            // userEntity.setEmail(user.getEmail());
        // }
        // return userEntity; 엔티티를 돌려주면서 세션 변경
    } // 2. 트랜잭션 종료 + 영속화 되어있는 것들 전부 더티체킹(변경감지해서 디비에 flush)함, update가 된다.
}

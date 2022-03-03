package site.metacoding.dbproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {

    // 회원가입 페이지(정적) - 누구나 들어갈 수 있음, 로그인X
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // 회원가입 액션 - 로그인X
    @PostMapping("/join") // 인증이 필요한 것이기 때문에 테이블명 안 적음
    public String join() {
        return "redirect:user/loginForm"; // 로그인 페이지로 이동해주는 컨트롤러 메서드를 재활용
    }

    // 로그인 페이지(정적) - 로그인X
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // SELECT * FROM user WHERE username=? AND passwrod=?
    // 원래 SELCT는 무조건 get 요청인데 로그인만 post로 예외이다.
    // 주소에 패스워드를 남길 수 없기 때문이다.
    // 로그인X
    @PostMapping("/login") // user 안 적음
    public String login() {
        return "메인페이지를 돌려주면 됨"; // PostController 만들고 수정하기
    }

    // 유저 정보 페이지, 동적 페이지이기 때문에 id로 검색
    // 로그인 O
    @GetMapping("/user/{id}")
    public String detail(@PathVariable int id) {
        return "user/detail"; // 리턴값 상대경로
    }

    // 유저 수정 페이지
    // 로그인 O
    @GetMapping("/user/{id}/updateForm")
    public String updateForm(@PathVariable int id) {
        return "user/updateForm"; // 리턴값 상대경로
    }

    // 유저수정을 수행
    // 로그인O
    @PutMapping("/user/{id}")
    public String update(@PathVariable int id) {
        return "redirect:/userInfo" + id;
    }

    // 로그아웃
    // 로그인O
    @GetMapping("/logout")
    public String logout() {
        return "메인페이지를 돌려주면 됨"; // PostController 만들고 수정하기
    }
}

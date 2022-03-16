package site.metacoding.dbproject.web;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.study.responseObject;
import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.domain.user.UserRepository;
import site.metacoding.dbproject.service.UserService;
import site.metacoding.dbproject.web.dto.ResponseDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService; // 의존성 주입
    private final HttpSession session; // 사용자와 공유해서 사용함

    // http://localhost:8080/api/user/username/same-check?username=s
    // user의 username이 동일한지 확인 - 응답은 json으로 한다.
    @GetMapping("/api/user/username/same-check") // 데이터를 주는 컨트롤러이다.
    public @ResponseBody ResponseDto<String> sameCheck(String username){

        String result = userService.유저네임중복검사(username);

        return new ResponseDto<String>(1, "통신 성공", result);
    }

    @CrossOrigin
    @GetMapping("/gonggongdata")
    public String test(){
        RestTemplate rt = new RestTemplate();
        String response = rt.getForObject("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=eCazEq%2FCP4iBdrQDcQQrLr3rgUrV%2ByZOFRdwxGTrcfeZbe3FqDvkQ6iMAWgeXRDOa%2FABNLYI3Dhz7hzxyUuI4A%3D%3D&pageNo=1&numOfRows=1000&dataType=json&base_date=20220315&base_time=0600&nx=55&ny=127", String.class);
        // 파싱 안 하고 문자열로 받기

        System.out.println(response);

        return response.toString();
    }


    // 회원가입 페이지(정적) - 누구나 들어갈 수 있음, 로그인X
    @GetMapping("/joinForm")
    public String joinForm() { // 요청할 때마다 스택 생성
        return "user/joinForm";
    }

    // useranme=ssar$password=1234&email=ssar@nate.com (x-www-form)
    // 회원가입 액션 - 로그인X
    @PostMapping("/join") // 인증이 필요한 것이기 때문에 테이블명 안 적음
    public @ResponseBody String join(User user) { // user오브젝트에 있는 필드를 사용할 수 있다.
        // 데이터를 리턴하도록 함, RestController가 됨

        // 1. username, password, email null 체크하기
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return "redirect:/joinForm"; // 다시 다운 받게 돼서 뒤로 가기가 안 된다.
            // 이를 브라우저가 해석하도록 문자열 버퍼로 날려줌
        }
        if (user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals("")) {
            return "redirect:/joinForm";
        }

        userService.회원가입(user);

        return "/loginForm";
    }

    // 로그인 페이지(정적) - 로그인X
    @GetMapping("/loginForm")
    public String loginForm(HttpServletRequest request, Model model) {
        // request.getHeader("Cookie");
        Cookie[] cookies = request.getCookies(); // 배열 타입으로 리턴해줌
        // Jsession, remember 두 개가 있음
        if (request.getCookies() != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("remember")) // remember가 있으면 값을 부른다.
                    model.addAttribute("remember", cookie.getValue());
                // 쿠키 자동 전송
            }
        }
        return "user/loginForm";
    }

    // SELECT * FROM user WHERE username=? AND passwrod=?
    // 원래 SELCT는 무조건 get 요청인데 로그인만 post로 예외이다.
    // 주소에 패스워드를 남길 수 없기 때문이다.
    // 로그인X
    @PostMapping("/login") // user 안 적음
    public String login(HttpServletRequest request, User user, HttpServletResponse response) {
        HttpSession session = request.getSession(); // 세션 영역에 접근 sessionId : 85
        // 1. DB 연결해서 username, password있는 지 확인
        User userEntity = userService.로그인(user);

        if(userEntity != null){ // 로그인 됐을 때
            session.setAttribute("principal", userEntity); // 세션 넣기

            if (user.getRemember() != null && user.getRemember().equals("on")) {
                // 리스펀스 쿠키에다 담아준다.
                response.setHeader("Set-Cookie", "remember=" + userEntity.getUsername()); // 쿠키에 유저네임 저장하기
                // response.addHeader(name, value); 여러개 쿠키 기입 가능
                // response.addCookie(cookie); 쓰지 말기
            }
            return "redirect:/"; // 메인 페이지로 가기
        } else { // 로그인 안 됐을 때
            return "redirect:/loginForm";
        }
        
    }

    // 로그아웃
    // 로그인O
    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 로그인 세션 전부 날린다.
        // session.removeAttribute("principal"); 해당 Jsession 영역의 키값만 날아간다.
        return "redirect:/loginForm"; // PostController 만들고 수정하기
    }

    // http://localhost:8080/user/1
    // 유저 정보 페이지, 동적 페이지이기 때문에 id로 검색
    // 로그인 O
    @GetMapping("/s/user/{id}")
    public String detail(@PathVariable int id, Model model) {
        // 유효성 검사하기
        User principal = (User) session.getAttribute("principal");

        // 인증 체크하기
        if (principal == null) {
            return "erro/page1";
        }

        // 다른 사용자 정보를 볼 수 없도록 권한 주기
        if (principal.getId() != id) { // 본인 id가 id와 맞지 않으면
            return "erro/page1";
        }

        User userEntity = userService.유저정보보기(id);

        if(userEntity == null){ // userEntity가 null이면
            return "erro/page1"; // 에러 페이지  
        }else {
            model.addAttribute("user", userEntity); // 데이터 있으면 모델에 담기
            return "user/detail";
        }
    }

    // 유저 수정 페이지
    // 로그인 O
    @GetMapping("/s/user/updateForm")
    public String updateForm(Model model) {
        // 세션에서 받기, 원래는 DB에서 가져와야 함
        return "user/updateForm"; // 리턴값 상대경로
    }

    // @RquestBody = BR + JSON 파싱, 자바 오브젝트일 때만
    // @ResponseBOdy = BW + JSON 파싱, 자바 오브젝트일 때만
    // 유저수정을 수행
    // 로그인O
    @PutMapping("/s/user/{id}")
    public @ResponseBody ResponseDto<String> update(@PathVariable Integer id, @RequestBody User user) { // password와 email을 오브젝트로 받음
        // 자바스크립트와 연결하기 때문에 데이터로 리턴해줘야 됨
        User principal = (User) session.getAttribute("principal");

        // 인증 체크하기
        if (principal == null) {
            return new ResponseDto<String>(-1, "인증 안됨", null);
        }

        // 다른 사용자 정보를 볼 수 없도록 권한 주기
        if (principal.getId() != id) { // 본인 id가 id와 맞지 않으면
            return new ResponseDto<String>(-1, "권한 없음", null);
        }

        // User userEntity = userService.유저수정(id, user);
        // session.set("principal", userEntity); 세션 변경, 덮어씌우기

        return new ResponseDto<String>(1, "성공", null);
    }

}

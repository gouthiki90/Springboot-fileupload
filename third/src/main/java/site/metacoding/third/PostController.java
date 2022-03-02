package site.metacoding.third;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    // resources/templtes/{리턴값}.mustache
    @GetMapping("/post/writeForm")
    public String writeForm() {
        return "post/writeForm"; // 파일 경로(뷰리졸버가 설정함 - mustache 라이브러리로 인해서)
    }

    // Post 메서드로 요청 -> http://localhost:8080/post
    // x-www 타입으로 날아옴
    // 스프링의 기본 파싱 전략으로 x-www 타입만 파싱한다.
    @PostMapping("/post")
    public String write(String title, String content, Model model) {
        // 1. 타이틀과 컨텐트를 DB에 인서트하기
        System.out.println("title : " + title);
        System.out.println("content : " + content);
        // 2. 글목록 페이지로 이동하게 해주기

        model.addAttribute("title2", title);
        return "post/list"; // 파일 응답
    }
}

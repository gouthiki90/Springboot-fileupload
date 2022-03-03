package site.metacoding.dbproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class PostController {

    // 글쓰기 페이지 /post/writeForm
    @GetMapping("/post/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }

    // 메인페이지라서 주소를 2개 건다. 둘 다 가능하다는 것.
    // 글목록 페이지 /post/list, /
    @GetMapping({ "/", "/post/list" })
    public String list() {
        return "post/list";
    }

    // 글 상세보기 페이지 /post/{id} (삭제버튼, 수정버튼)
    @GetMapping("/post/{id}")
    public String detail(@PathVariable Integer id) {
        return "post/{id}";
    }

    // 글 수정 페이지 /post/{id}/updateForm
    @GetMapping("/post/{id}/updateForm")
    public String updateForm(@PathVariable Integer id) {
        return "post/{id}/updateForm"; // viewResolver 도움을 받는다.
    }

    // 글 삭제 /post
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable Integer id) {
        return "redirect:/";
    }

    // 글 수정 /post{id}
    @PutMapping("/post/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:post/" + id; // 글 상세보기
    }

    // 글 쓰기 /post
    @PostMapping("/post")
    public String write(String title, String content) {
        return "redirect:/";
    }
}

package site.metacoding.dbproject.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.post.Post;
import site.metacoding.dbproject.domain.post.PostRepository;
import site.metacoding.dbproject.domain.user.User;

@RequiredArgsConstructor // final이 붙은 필드에 대한 생성자 만들어준다.
@Controller
public class PostController {

    private final HttpSession session;
    private final PostRepository repository;

    // 글쓰기 페이지 /post/writeForm
    @GetMapping("/s/post/writeForm") // 인증이 필요함
    public String writeForm() {
        if(session.getAttribute("principal") == null){ // principal이 null이면
            return "redirect:/loginForm"; // 로그인 폼으로 리턴
        }
        return "post/writeForm";
    }

    // 메인페이지라서 주소를 2개 건다. 둘 다 가능하다는 것.
    // 글목록 페이지 /post/list, /
    @GetMapping({ "/", "/post/list/{{id}}"}) // 인증이 필요없게끔 한다.
    public String list(Model model) {
        //1.postRepository의 findAll() 호출
        model.addAttribute("posts", repository.findAll());
        //2. 모델에 담기
        return "post/list";
    }

    // 글 상세보기 페이지 /post/{id} (삭제버튼, 수정버튼)
    // Get 요청에 /post 제외 시키기, 인증이 필요없기 때문
    @GetMapping("/post/{id}") // 모든 사람이 글을 읽을 수 있도록 인증 없이 한다.
    public String detail(@PathVariable Integer id, Model model) {
        Optional<Post> postOp = repository.findById(id);
      
        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            model.addAttribute("post", postEntity);
            return "post/detail";
        } else {
            return "error/page1";
        }
    }

    // 글 수정 페이지 /post/{id}/updateForm
    // 인증 반드시 필요함
    @GetMapping("/s/post/{id}/updateForm")
    public String updateForm(@PathVariable Integer id) {
        return "post/{id}/updateForm"; // viewResolver 도움을 받는다.
    }

    // 글 삭제 /post
    // 인증 필요함
    @DeleteMapping("/s/post/{id}")
    public String delete(@PathVariable Integer id) {
        return "redirect:/";
    }

    // 글 수정 /post{id}
    // 인증 필요함
    @PutMapping("/s/post/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:post/" + id; // 글 상세보기
    }

    // 글 쓰기 /post
    // 인증 필요함
    @PostMapping("/s/post")
    public String write(Post post) {

        if(session.getAttribute("principal") == null){ // principal이 null이면
            return "redirect:/loginForm"; // 로그인 폼으로 리턴
        }

        User principal = (User) session.getAttribute("principal");
        // User 오브젝트 넣기
        post.setUser(principal); // post에 넣기
        // INSERT into post(title, content, userId) VALUES(사용자, 사용자, 세션오브젝트의 PK);

        repository.save(post);
        return "redirect:/";
    }
}

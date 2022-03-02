package site.metacoding.second.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import site.metacoding.second.domain.Post;

// View(글쓰기 페이지, 글 목록 페이지, 글 상세보기 페이지)
@Controller
public class PostController {

    @GetMapping("/post/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }

    @GetMapping("/post/list")
    public String writeList(Model model) {

        Post post1 = new Post(1, "제목", "내용");
        Post post2 = new Post(2, "제목", "내용");
        Post post3 = new Post(3, "제목", "내용");
        Post post4 = new Post(4, "제목", "내용");

        List<Post> posts = new ArrayList();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);

        model.addAttribute("posts", posts);

        return "post/list";
    }

    @GetMapping("/post/detail")
    public String detail(Model model) { // DS가 보고 리퀘스트 넣어줌
        // 1. DB 연결해서 SELECT 해야 됨
        // 2. ResultSet을 자바 오브젝트로 변경해야 됨
        Post post = new Post(1, "제목1", "내용1");

        // 3. request에 담아야 됨
        model.addAttribute("post", post);
        return "post/detail"; // requestdispatcher로 forward함 -> 리퀘스트 복제
    }
}

package site.metacoding.dbproject.web;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.post.Post;
import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.service.PostService;
import site.metacoding.dbproject.web.dto.ResponseDto;

@RequiredArgsConstructor // final이 붙은 필드에 대한 생성자 만들어준다.
@Controller
public class PostController {

    private final HttpSession session;
    private final PostService postService;

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
    public String list(@RequestParam(defaultValue = "0") Integer page, Model model) {
        //1.postRepository의 findAll() 호출
        //model.addAttribute("posts", repository.findAll(Sort.by(Sort.Direction.DESC, "id"))); id를 거꾸로 index를 읽는다.

        Page<Post> pagePosts = postService.글목록보기(page);

        model.addAttribute("posts", pagePosts);

        model.addAttribute("nextPage", page+1); // 페이지를 모델에 담아서 뷰에 적용한다. 페이지 값을.
        model.addAttribute("prevPage", page-1); // 이전으로 가는 페이지 -1
        return "post/list";
    }

    // @GetMapping("/test/post/list")
    // public @ResponseBody Page<Post> listTest(@RequestParam(defaultValue = "0") Integer page){ Integer는 null이 있음

    //      if(page == null){ 변수 초기화로 디폴트값 적용 가능
    //          page = 0;
    //      }

    //     PageRequest pq = PageRequest.of(page, 3);
    //     return repository.findAll(pq);
    // }

    // 글 상세보기 페이지 /post/{id} (삭제버튼, 수정버튼)
    // Get 요청에 /post 제외 시키기, 인증이 필요없기 때문
    @GetMapping("/post/{id}") // 모든 사람이 글을 읽을 수 있도록 인증 없이 한다.
    public String detail(@PathVariable Integer id, Model model) {
        // 핵심로직
        Post postEntity = postService.글상세보기(id); // EGAER 전략이기 때문에 user 들고 있음
        // 부가로직
        if(postEntity == null){
            return "error/page1";
        }else {
            model.addAttribute("post", postEntity); // 모델에 담기
            return "post/detail";
        }

        // String rawContent = postEntity.getContent(); 순수한 컨텐트를 가져옴
        // String encContent = rawContent.replaceAll("<script>", "&lt;script&gt;").replaceAll("</script>", "&lt;script/&gt;"); 꺽쇠 막아주기
        // postEntity.setContent(encContent); Entity 덮어씌우기
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
    public @ResponseBody ResponseDto<String> delete(@PathVariable Integer id) {

        User principal = (User) session.getAttribute("principal"); // 세션 가져오기

        if(principal == null){ // 로그인이 안 됐으면
            return new ResponseDto<String>(1, "로그인이 되지 않았습니다.", null);
        }

        Post postEntity = postService.글상세보기(id); // entity의 id 찾기
        if(principal.getId() != postEntity.getUser().getId()){ // 세션 id와 entity id 비교하기
            return new ResponseDto<String>(-1, "해당 글을 삭제할 권한이 없습니다.", null);
        }

        postService.글삭제하기(id);

        return new ResponseDto<String>(1, "성공", null);
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
        postService.글쓰기(post, principal);
        return "redirect:/";
    }
}

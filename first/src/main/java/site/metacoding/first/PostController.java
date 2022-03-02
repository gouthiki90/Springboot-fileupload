package site.metacoding.first;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//file을 리턴하는 컨트롤러(RequestDispatcher id = request.getRequestDispathcher("파일명"));
// dis.forward(request, response)
// 리턴할 때 동작한다. 리퀘스트한 파일이 파일명에 들어감.
@Controller
public class PostController {

    @GetMapping("/imgTest")
    public String imgTest() {
        return "imgTest";
    }

    @GetMapping("/writeForm")
    public String wirteForm() {

        return "wirteFrom"; // file을 찾아서 리턴, 상대경로로 해야됨
        // 확장자 mustache
    }

    @GetMapping("/updateForm")
    public String updateForm() {

        return "updateForm";
    }
}

package site.metacoding.second.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostApiController {

    // SELECT * FROM post WHERE id = ?
    // 구체적으로 달라는 요청은 body가 필요 없음
    @GetMapping("/post/{id}") // post 뒤에 있는 숫자는 무조건 PK이고 DS가 만들어줌
    public String test1(@PathVariable int id) {
        return "id 주세요 : " + id;
    }

    // SELECT * FROM post WHERE title = ?
    // PK가 아닌 것들은 쿼리스트링으로 받음
    @GetMapping("/post")
    public String search(String title) {
        return "title 주세요 : " + title;
    }

    // 추가할 데이터가 필요(INSERT) -> body가 필요함
    // body : title=제목1&content=내용1
    // header : Content-type:Type:application/x-www-form-urlencoded -> 쿼리스트링으로
    // 파싱(form태그로)
    // x-www-form-urlencoded타입만 파싱 가능
    @PostMapping("/post")
    public String test2(String title, String content) {
        return "title 줄게요 : " + title + "conetent 줄게요 : " + content;
    }

    // UPDATE post SET title = ?, content = ? WHERE id = ?
    // WHERE절은 반드시 주소에 적어야 한다.
    // title, content, PK 가지고 수정해야됨
    // body 데이터 필요함
    @PutMapping("/post/{id}")
    public String test3(String title, String content, @PathVariable int id) { // UPDATE할 때 id가 필요하기 때문에 id 넣기
        return "수정해주세요 : title" + title + "content : " + content + "id : " + id;
    }

    // DELETE FROM post WHERE title = ?
    // http://localhost:8000/post/1
    // DELETE FROM post WHERE id = ?
    // body 필요하지 않음
    @DeleteMapping("/post/{id}")
    public String test4(@PathVariable int id) {
        return "삭제해주세요 : " + id;
    }
}

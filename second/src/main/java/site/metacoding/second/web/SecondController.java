package site.metacoding.second.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 데이터를 리턴
public class SecondController {

    // Get 요청 맵핑 Read - SELECT
    @GetMapping("/user") // user테이블의 모든 정보를 Get 요청하는 쿼리문 완성
    // 주소가 같아도 http의 메서드로 분기하기 때문에 다른 요청으로 인식된다.
    public String test1() {
        return "<h1>test1</h1>";
    }

    // Post 요청 Create - INSERT
    @PostMapping("/user")
    public String test2() {
        return "<h1>test2</h1>";
    }

    // Put 요청 Update - UPDATE
    @PutMapping("/user")
    public String test3() {
        return "<h1>test3</h1>";
    }

    // Delete 요청 Delete - DELETE
    @DeleteMapping("/user")
    public String test4() {
        return "<h1>test4</h1>";
    }
}

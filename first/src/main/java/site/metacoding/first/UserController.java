package site.metacoding.first;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//data를 리턴하는 컨트롤러(버퍼를 통해서)
// IoC에 의존함
@RestController
public class UserController {

    // DI라고 한다. IoC컨테이너에 있으니까 주입할 수 있음.
    // Dependency Injection 의존성 주입
    public UserController(Dog d, HttpServletRequest request) {
        System.out.println("UserController 실행됨");
    }

    @GetMapping("/home") // DS가 home 때려줌
    public void home() {
        System.out.println("home----------------");
    }

    @GetMapping("/bye")
    public void bye() {
        System.out.println("bye--------------------");
    }

    @GetMapping("/data")
    public String data() {
        return "<h1>data</h1>"; // printwirter - write - flush
    }
}

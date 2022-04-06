package site.metacoding.fileuploadsample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/join") // 사진을 받는 것이기 때문에 post
    public String join(JoinDto joinDto) { // 버퍼로 읽는 것은 1. JSON, 2. 있는 그대로 받고 싶을 때

        UUID uuid = UUID.randomUUID();

        String requestFileName = joinDto.getFile().getOriginalFilename(); // 전송 받은 파일명 가져오기
        System.out.println("전송받은 파일명" + requestFileName);

        // aee7986c-e671-4ba7-b7c6-2acc6a78167c_a.png
        String imgUrl = uuid + "_" + requestFileName; // uuid와 함께 되는 파일명으로 됨

        // 메모리에 있는 파일 데이터를 파일시스템으로 옮겨야 한다.
        // 1. 빈 파일 생성 haha.png
        // 자바에 파일 객체가 있음 File file = new File("경로");
        // 2. 빈 파일에 스트림을 연결함
        // 3. for문 돌리면서 바이트로 쓰면 된다. BR쓰지 않고 FileWriter객체로
        // 사이즈를 알고 있다면 for문으로 돌려서 받는다.

        try {
            // 폴더가 이미 만들어져있어야 한다.
            // 리눅스는 /, 윈도우는 ₩사용
            // imgUrl = a.png 전역적으로 경로를 지정해야 한다. OS마다 경로가 다르기 때문
            // 상대경로를 사용한다. 배포되어도 충돌나지 않기 때문에. 최상위경로를 붙이지 않음.
            Path filePath = Paths.get("src/main/resources/static/upload/" + imgUrl); // 경로 문법은 get 메서드가 정한다.
            System.out.println(filePath);
            Files.write(filePath, joinDto.getFile().getBytes()); // path, byte

            userRepository.save(joinDto.toEntity(imgUrl)); // DB에 들어가는 것은 경로가 된다. 파일명만 저장하기.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/joinComplete"; // viewResolver 실행
    }

    @GetMapping("/main")
    public String main(Model model) {
        User user = userRepository.findById(1).get(); // 모두 셀렉해서

        model.addAttribute("user", user); 
        return "/main";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "/joinForm";
    }

}

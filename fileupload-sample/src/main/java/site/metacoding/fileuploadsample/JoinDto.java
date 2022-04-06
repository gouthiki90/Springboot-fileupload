package site.metacoding.fileuploadsample;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinDto {

    private String username; // form 태그 name="username"
    private MultipartFile file; // 이미지 받기 form 태그 name="file"
    // 파일을 받고 User 오브젝트에 url을 넣도록 한다.

    public User toEntity(String imgUrl) { // 외부에서 받은 파일명 set해서 DB 저장하기
        User user = new User();
        user.setUsername(username);
        user.setImgurl(imgUrl);

        return user;
    }
}

package site.metacoding.dbproject.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private Integer code; // -1 실패, 1 성공
    private T data; // 뭘 담을지 모르겠으니 응답할 때 정하겠다.
    private String msg; // 왜 통신을 실패했는 지에 대한 내용
    // body data 담기
}

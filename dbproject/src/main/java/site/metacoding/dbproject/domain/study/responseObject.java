package site.metacoding.dbproject.domain.study;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class responseObject {
    
    private HttpServletResponse response;

    public responseObject(HttpServletResponse response){
        this.response = response;
    }

    public void getResponse(HttpServletResponse response){
        response.setHeader("name", "value");
        // 응답이 포함된 헤더 정보에 headerName의 이름과 값을 설정에 추가한다.

        Cookie cookie = new Cookie("name", "value");
        response.addCookie(cookie); // 응답 헤더에 쿠키를 추가한다.
        
        response.getContentType(); // 응답 페이지의 contentType을 설정한다.
    }
}

package site.metacoding.dbproject.domain.study;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class urlAndCookieObject {
    private HttpServletRequest request;

    public urlAndCookieObject(HttpServletRequest request){
        this.request = request;
    }

    public void getObject(){

        request.getRequestURI(); // 요청 URI를 문자열로 리턴한다.
        request.getQueryString(); // 요청에 사용된 쿼리 문장을 문자열로 리턴한다.
        request.getRemoteHost(); // 사용자의 호스트 이름을 문자열로 리턴한다.

        request.getRemoteAddr(); // 사용자의 IP 주소를 문자열로 리턴한다.
        request.getProtocol(); // 요청에 사용된 프로토콜 이름을 문자열로 리턴한다.
        request.getMethod(); // 요청에 사용된 요청 방식(GPDP)을 문자열로 리턴한다.

        request.getContextPath(); // 해당 템플릿 엔진 페이지의 context path를 문자열로 리턴한다.
        request.getServerPort(); // 서버의 포트 번호를 int 타입으로 리턴한다.
        request.getServerName(); // 서버의 도메인 이름을 문자열로 리턴한다.

        Cookie[] cookies = request.getCookies(); // HTTP 요청 메시지의 헤더에 포함된 쿠키를 배열로 리턴한다.

        request.getSession(); // 요청한 사용자에게 할당된 HttpSession 객체를 반환한다. 이전에 생성된 객체가 없으면 새로 생성해서 리턴한다.
        request.getSession(true); // true일 경우 getSession()과 동일한 결과를 리턴하지만, false면 이전에 생성된 객체가 없을 경우 null을 리턴한다.
        request.getRequestedSessionId(); // 요청된 사용자에게 지정된 세션 ID를 문자열로 리턴한다.
        request.isRequestedSessionIdValid(); // 요청에 포함된 사용자의 세션 ID가 유효하면 true를 리턴하고 그렇지 않으면 false를 리턴한다.
    }
}

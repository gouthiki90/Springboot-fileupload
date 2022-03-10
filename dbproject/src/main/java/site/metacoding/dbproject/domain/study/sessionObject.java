package site.metacoding.dbproject.domain.study;

import javax.servlet.http.HttpSession;

public class sessionObject {
    private HttpSession session;

    public sessionObject(HttpSession session){
        this.session = session;
    }

    public void getSession(HttpSession session){
        session.getId(); // 해당 세션의 세션 ID를 리턴한다.
        session.getCreationTime(); // 해당 세션이 생성된 순간까지를 리턴한다.

        session.getLastAccessedTime();
        // epoch로부터 해당 세션이 마지막으로 접근된 시간까지의 경과 시간을 밀리초로 계산하여 long 자료형으로 리턴한다.

        session.getMaxInactiveInterval();
        // 사용자의 요청이 없을 시 서버가 해당 세션을 유지하도록 지정된 시간을 초 단위로 리턴한다.

        session.invalidate(); // 세션의 속성값으로 저장된 모든 객체를 반납하여 세션을 종료시킨다.
        session.isNew(); // 새로운 세션일 경우 ture를 리턴하고 기존 세션이 유지되고 있으면 false를 리턴한다.

        session.setMaxInactiveInterval(30);
        // 사용자의 요청이 없더라도 세션을 유지할 시간을 초 단위의 정수값으로 설정한다. 음수로 설정할 경우 세션은 무효화 되지 않는다.

    }
    
}

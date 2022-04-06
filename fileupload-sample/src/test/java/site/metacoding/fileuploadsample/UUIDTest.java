package site.metacoding.fileuploadsample;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UUIDTest {
    
    @Test
    public void 유유아이디_연습(){ // 자기만의 스레드 만들어서 독단적으로 테스트하기
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
    }
}

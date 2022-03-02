package site.metacoding.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstApplication { // 이 클래스를 실행 시킨다.

	public static void main(String[] args) { // 메인을 실행하면 스프링app을 실행시킨다.
		SpringApplication.run(FirstApplication.class, args); // 스프링이란 성의 모든 문맥을 리턴한다.
	}

}

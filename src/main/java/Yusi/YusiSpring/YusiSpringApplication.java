package Yusi.YusiSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //스프링부트 애플리케이션 실행, 톰캣이라는 웹 서버를 내장하고 있기에 자체적으로 띄우면서 메인 메서드 실행
public class YusiSpringApplication {
	public static void main(String[] args) {

        SpringApplication.run(YusiSpringApplication.class, args);
	}
}
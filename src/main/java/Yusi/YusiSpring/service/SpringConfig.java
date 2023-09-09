package Yusi.YusiSpring.service;

import Yusi.YusiSpring.repository.MemberRepository;
import Yusi.YusiSpring.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean //이 Bean이 있으면 아래 로직을 호출해서 스프링 빈에 등록해준다.
    public MemberService memberService(){
        return new MemberService(memberRepository());
        //이렇게 하면 MemberService 객체에 멤버 리포지토리 빈을 넣어준다.
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
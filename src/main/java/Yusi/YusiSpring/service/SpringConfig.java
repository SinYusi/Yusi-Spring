package Yusi.YusiSpring.service;

import Yusi.YusiSpring.repository.JdbcMemberRepository;
import Yusi.YusiSpring.repository.MemberRepository;
import Yusi.YusiSpring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private DataSource dataSource;
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    //spring boot에서 만들어준 datasource를 주입해주는 과정
    @Bean //이 Bean이 있으면 아래 로직을 호출해서 스프링 빈에 등록해준다.
    public MemberService memberService(){
        return new MemberService(memberRepository());
        //이렇게 하면 MemberService 객체에 멤버 리포지토리 빈을 넣어준다.
    }

    @Bean
    public MemberRepository memberRepository(){
   //     return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        return new JdbcMemberRepository(dataSource);
        //이렇게 하면 웹 어플리케이션을 띄워서 테스트를 해볼 필요가 없다.
        //물론 데이터베이스는 띄워야 한다.
    }
}
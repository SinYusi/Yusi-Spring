package Yusi.YusiSpring.service;

import Yusi.YusiSpring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    //@PersistenceContext 원래라면 이 어노테이션을 사용해 EntityManager를 사용한다.
    //private EntityManager em;
    //@Autowired
    //public SpringConfig(EntityManager em) {
    //this.em = em;
    //}

    //private DataSource dataSource;
    //@Autowired
    //public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
    //  }
    //spring boot에서 만들어준 datasource를 주입해주는 과정
    private final MemberRepository memberRepository;
    //스프링 데이터 JPA가 만들어놓은 멤버 리포지토리가 등록된다.
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Bean //이 Bean이 있으면 아래 로직을 호출해서 스프링 빈에 등록해준다.
    public MemberService memberService(){
        return new MemberService(memberRepository);
        //이렇게 하면 MemberService 객체에 멤버 리포지토리 빈을 넣어준다.
    }

//    @Bean
//    public MemberRepository memberRepository(){
   //     return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcMemberRepository(dataSource);
        //return new JpaMemberRepository(em); //jap는 entity라는 객체가 필요하다.

        //이렇게 하면 웹 어플리케이션을 띄워서 테스트를 해볼 필요가 없다.
        //물론 데이터베이스는 띄워야 한다.
//    }
}
package Yusi.YusiSpring.repository;

import Yusi.YusiSpring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //스프링에서는 MemberRepository을 빈으로 등록해야 하는데, 이 인터페이스로는 등록한 적이 없다.
    //하지만 JpaRepository라는 것을 통해서 스프링 데이터 Jpa가 인터페이스에 대한 구현체를 직접 만든다.
    //그리고 스프링 빈에 등록한다. 그렇기에 SpringConfig에서 인젝션이 가능하다.
    @Override
    Optional<Member> findByName(String name);
}

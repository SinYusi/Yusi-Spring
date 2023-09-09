package Yusi.YusiSpring.repository;
import Yusi.YusiSpring.domain.Member;
import java.util.Optional;
import java.util.List;
public interface MemberRepository {
    Member save(Member member);//회원을 저장하면 저장된 회원 반환.
    Optional<Member> findById(Long id);//id로 회원을 찾기
    Optional<Member>findByName(String name);//name으로 회원을 찾기
    List<Member>findAll();
}

package Yusi.YusiSpring.repository;

import Yusi.YusiSpring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach//메서드 실행이 끝날 때마다 실행되는 메서드. 콜백 메서드
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //Optional 객체에서 값을 꺼낼 때 get() 사용
        //get으로 꺼내는 방법은 좋은 방법이 아니지만 테스트 케이스같은 경우에는 괜찮다.
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        //spring1 회원 추가
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //spring2 회원 추가
        Member result = repository.findByName("spring1").get();
        //멤버 찾기
        assertThat(result).isEqualTo(member1);
        //멤버 찾기 결과를 비교
    }

    @Test
    public void findAll(){
        Member member3 = new Member();
        member3.setName("spring1");
        repository.save(member3);

        Member member4 = new Member();
        member4.setName("spring2");
        repository.save(member4);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
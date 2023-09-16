package Yusi.YusiSpring.service;

import Yusi.YusiSpring.domain.Member;
import Yusi.YusiSpring.repository.MemberRepository;
import Yusi.YusiSpring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
//테스트를 시작할 때 트랜잭셔널을 먼저 실행하고
//디비에 인서트 쿼리를 다 날리고 테스트가 끝나면 롤백해준다
//그렇게 디비에 넣었던 데이터가 디비에 반영이 안되고 다 지워진다.
//디비에 반영이 안된다면 코드에 일일히 수정을 넣지 않아도 여러번 테스트가 가능하다
class MemberServiceIntegrationTest {
    //테스트는 사실상 제일 끝단에 있는 것이므로 제일 편한 방법을 사용하면 된다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    //테스트할 때에는 다른 곳에서 특별히 쓰는 곳이 없다.
    //그러므로 그냥 @Autowired를 사용하여 변수주입을 하는 것이 편하다.
    //구현체는 스프링 컨테이너에서 매칭이 될 것이다.

    @Test
    void 회원가입() {
        //db에 연동되는 것이므로 db에는 아무 데이터도 없어야 한다.
        //그렇기에 테스트 db를 따로 구축한다.
        //SpringConfig를 통해서 스프링 전체가 테스트에 올라와서 테스트가 진행된다.
        //given
        Member member = new Member();
        member.setName("hello");
        //이것을 실행했을 때
        //when
        Long saveId = memberService.join(member);
        //결과가 나와야한다.
        //then
        Member findMember = memberService.findOne(saveId).get(); //id로
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);

        //위와 같이 try catch로 예외를 확인해볼 수 있다.
        //하지만 우리는 좋은 문법을 활용해보자.
        assertThrows(IllegalStateException.class, ()->memberService.join(member2));
        //람다 로직을 실행을 할 건데
        //앞의 예외가 터져야 하는 것이다.
        //IllegalStateException이 아닌 다른 예외 처리를 넣으면 테스트 실패
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //try catch문에서 활용한 메시지를 활용한 방법
        //then
    }
}
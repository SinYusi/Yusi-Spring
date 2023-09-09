package Yusi.YusiSpring.service;

import Yusi.YusiSpring.domain.Member;
import static org.assertj.core.api.Assertions.*;

import Yusi.YusiSpring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    //이 곳에서 new를 사용하여 새로운 객체를 만들면
    // MemberService의 저장소와 다른 저장소이다.
    //즉 다른 객체를 사용한다.
    //이는 store가 static 변수가 아니라면 문제가 발생한다.
    //본체에 생성자를 만든다.
    //테스트를 동작하기 전 memberService 객체 안에 memberRepository 객체가 들어갈 수 있도록
    //설정해준다.
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    //이렇게 하면 같은 메모리멤버리포지토리가 사용된다.
    //이를 Dependency Injection이라고 한다.(DI)

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }
    //이전 수업에서 진행했던 AfterEach
    //테스트 하나 끝날 때마다 작동하는 메서드

    @Test
    //테스트 코드는 이와 같이 메서드 이름을 과감하게 한글로 해도 괜찮다.
    void 회원가입() {
        //강의사님이 추천하는 문법 given when then

        //이러한 상황이 주어져서
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
    //테스트는 정상플로우도 중요하지만 예외플로우가 훨씬 더 중요하다.
    //위 코드는 간단하게 정상작동 하는 것만 알아볼 수 있다.
    //위는 반 쪽짜리 테스트
    //우리는 중복 회원이 안되게 했으니 이를 검증할 필요가 있다.

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        /*
        try{
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123");
            //예외를 발생할 때 나오는 e의 메시지는 "이미 존재하는 회원입니다."
            //이 문구와 만약 다르다면 예외 처리가 되지 않은 것이다.
            //다시 말해 중복 회원이 가능해진다는 것
        }
        */
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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
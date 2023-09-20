package Yusi.YusiSpring.service;

import Yusi.YusiSpring.domain.Member;
import Yusi.YusiSpring.repository.MemberRepository;
import Yusi.YusiSpring.repository.MemoryMemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
//서비스 클래스는 비즈니스에 관련된 네이밍을 해야한다.
@Transactional
//데이터를 저장하거나 변경하려면 트랜잭션이 항상 필요하다.
//데이터 변경은 모두 트랜잭션 안에서 진행되어야 한다.
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //테스트에서 같은 저장소를 사용하기 위한 생성자.

    //멤버 리포지토리
    //@Transactional 회원가입 할 때만 필요하니 여기에 트랜잭션을 붙여도 된다.
    /**
     *회원 가입
     */
    public Long join(Member member){

        long start = System.currentTimeMillis();
        //밀리세컨트로 시작하는 시간

        try {
            //같은 이름이 있는 중복 회원 X
            validateDuplicateMember(member);//중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        }finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = "+timeMs + "ms");
        }
    }
    //중복 회원 검증
    private void validateDuplicateMember(Member member) {
        //여기서 Optional 객체 result를 만들어서 아래 코드를 result로 진행해도 되지만
        //다음과 같이 코드를 간편화할 수 있다.
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                //ifPresent는 이미 어떤 값이 있으면 괄호 안의 로직을 진행하는 것
                //Optional이기에 가능하다.
                //과거에는 ==null로 진행했지만 Optional로 이런 것들을 간편화
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }
    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        long start = System.currentTimeMillis();
        try{
            return memberRepository.findAll();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers : " + timeMs + "ms");
        }
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}

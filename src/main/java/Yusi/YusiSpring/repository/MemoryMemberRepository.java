package Yusi.YusiSpring.repository;
import Yusi.YusiSpring.domain.Member;
import java.util.*;
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long,Member> store = new HashMap<>();
    //멤버 객체 정보를 저장할 저장소.
    //실무에서는 컨커런트 해쉬맵을 사용, 동시송출 문제.
    private static long sequence = 0L;
    //키값을 생성해주는 것
    //실무에서는 텀 롱 사용
    @Override
    public Member save(Member member) { //회원이 저장하면서 이름은 넘어온 상태
        member.setId(++sequence);//시퀀스 값을 하나 올려주면서 id에 저장
        store.put(member.getId(), member);//해쉬맵에 하나 저장.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //store.get(id)는 HashMap에서 키값 찾기
        //Null이 반환될 가능성이 있어서 Optional로 감싸서 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                //스트림 내 요소에 대해서 필터링 하는 작업
                .findAny();
                //filter 조건에 일치하는 요소를 Optional로 리턴
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        //store에 저장된 값들을 List로 바꿔서 반환
    }
    public void clearStore() {
        store.clear();
        //모두 삭제
    }
}

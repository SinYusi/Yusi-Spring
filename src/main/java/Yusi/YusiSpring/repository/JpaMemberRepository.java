package Yusi.YusiSpring.repository;

import Yusi.YusiSpring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;
    //jpa는 EntityManager라는 것으로 모든 것이 동작한다.
    //우리는 build.gradle을 통해 data-jpa라는 라이브러리를 받았다.
    //그렇게 하면 스프링 부트가 자동으로 EntityManager라는 것을 생성해준다.
    //그렇게 우리는 이 만들어진 것을 injection받으면 된다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); //persist가 영구 저장하다라는 뜻이다.
        //이렇게 하면 인서트 쿼리를 다 해서 db에 집어넣고
        //멤버에 setid까지 다 해준다.
        //한마디로 모든 것을 다 해준다는 것이다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        //조회할 타입이랑 pk값을 넣어주면 조회가 가능하다.
        return Optional.ofNullable(member);
    }
    //저장 조회는 sql문을 따로 작성할 필요가 없다.

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        //createQuery 안에 있는 sql문이 jpql이라는 기술인데, 이것이 뭐냐면
        //우리는 보통 테이블 대상으로 sql 쿼리를 날리는데,
        //이 기술은 객체를 대상으로 쿼리를 날리는 것이다.
        //그럼 이것이 sql로 변형이 된다.
        //Member가 Entity인데, 이 Entity를 대상으로 쿼리를 날린다.
        //쿼리 문에 m이라는 것을 볼 수 있는데, 이는 Entity 자체를 말한다.
        //다시 말해 select m이라고 하면 Entity(Member) 자체를 select 한다는 것이고
        //from Member m이라고 하면 Entity(Member) 자체로부터 select를 한다는 것이다.
        return result;
    }
}

package Yusi.YusiSpring.domain;

import javax.persistence.*;

//jpa를 사용하려면 엔티티라는 것을 매핑해줘야 한다.
//@Entity를 사용하면 jpa가 관리하는 entity라고 표현한다.
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Id는 이것이 키값이라고 알려주는 것
    //현재 우리는 키값을 h2데이터베이스에서 직접 하나씩 높혀가며 만들어주고 있다.
    //이를 아이덴티티 전략(strategy = GenerationType.IDENTITY)이라고 부른다.
    private Long id;

    //@Column(name = "username") 이는 만약 db가 username이라면 username과 name을 매핑시켜주는 어노테이션이다.
    //이러한 어노테이션을 통해 db와 매핑이 되고, jpa기술을 사용한다.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

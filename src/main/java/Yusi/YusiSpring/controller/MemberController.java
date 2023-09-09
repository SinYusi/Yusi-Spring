package Yusi.YusiSpring.controller;

import Yusi.YusiSpring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
//이 컨트롤러를 해놓으면 기능은 아무것도 없지만 스프링 프로젝트를 생성할 때
//만들어졌던 스프링 통에 MemberController라는 객체를 생성해서 스프링에 넣어둔다
//그리고 스프링이 이를 관리한다.
//이를 스프링 컨테이너에서 스프링 빈이 관리된다라고 표현한다.
public class MemberController {
    //private final MemberService memberService = new MemberService();
    //이런 식으로 new를 사용해서 객체 생성을 할 수 있다.
    //하지만 스프링이 관리를 하면 다 스프링에 등록을 해주고 스프링에서부터 받아서 쓰도록
    //바꿔야 한다.
    //그 이유는 멤버 서비스를 다른 컨트롤러들이 사용할 수 있다. 그러면 하나만 생성하고
    //같이 공용으로 사용하면 된다. 이제부터는 스프링 컨테이너에 등록하고 사용하면
    //된다. 아래가 그 방법이다.
    private final MemberService memberService;
    @Autowired //wired = 연결
    //생성자에 Autowired가 있다면 스프링 컨테이너의 멤버 서비스를 가지고 와서 연결시켜준다.
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
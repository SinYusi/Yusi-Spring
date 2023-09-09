package Yusi.YusiSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/") // /만 있다면 첫번째 호출
    public String home(){
        return "home";
    }
}

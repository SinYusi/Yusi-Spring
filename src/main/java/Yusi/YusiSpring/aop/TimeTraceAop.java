package Yusi.YusiSpring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* Yusi.YusiSpring..*(..))")//어떤 메서드에 적용할지 타겟팅을 해주는 어노테이션
    //YusiSpring 패키지 하위의 파일에 다 적용을 한다는 것이다.
    public Object execute(ProceedingJoinPoint joinPoint)throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try{
            return joinPoint.proceed(); //중간에 다른 메서드가 인터셉트하는 것
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
        //만약 변경사항이 생긴다면 이 로직만 바꾸면 되는 것이다.
    }
}

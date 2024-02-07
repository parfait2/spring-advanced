package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {
    /* 실행 */
//    정상: http://localhost:8080/v1/request?itemId=hello
//    예외: http://localhost:8080/v1/request?itemId=ex

    /**
     * @RequiredArgsConstructor 어노테이션이 부여되어 있으므로 해당 클래스의 생성자를 통해 의존성을 주입 받음.
     * 의존성 주입 : 클래스 간의 의존 관계를 코드 외부에서 결정하고 제공하는 디자인 패턴
     * 스프링의 IoC(Inversion of Control) 컨테이너가 이러한 의존성 주입을 담당하여 객체 간의 결합도를 낮추고 모듈화된 구조를 유지할 수 있게 한다.
     * */

    // HelloTraceV1을 주입 받는다.
    // HelloTraceV1은 @Component 어노테이션을 가지고 있으므로 컴포넌트 스캔의 대상이다.
    // -> 자동으로 스프링 빈으로 등록된다.
    private final HelloTraceV1 trace;
    private final OrderServiceV1 orderService;


    @GetMapping("/v1/request")
    public String request(String itemId) {

//        TraceStatus status = trace.begin("OrderController.request()");
//        orderService.orderItem(itemId); // 예외가 터져도 이 로그를 출력해줘야 한다. 따라서 밑의 예시처럼 try~catch를 사용.
//        trace.end(status);
//        return "ok";

        TraceStatus status = null;
        try {
            // 로그 시작 시 {컨트롤러 이름 + 메서드 이름}을 메세지 이름으로 줌.
            // -> 어떤 컨트롤러와 메서드가 호출되었는 지 로그로 편리하게 확인 가능.
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;// 로그 처리 때문에 예외가 사라지면 안 되므로 예외를 꼭 다시 던져주어야 한다.
        }
    }
}

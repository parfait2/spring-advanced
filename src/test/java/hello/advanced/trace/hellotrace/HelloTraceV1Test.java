package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

public class HelloTraceV1Test {

    /**
     * 테스트는 일반적으로 자동으로 검증하는 과정이 필요하다.
     * 이 테스트는 검증하는 과정이 없고 결과를 콘솔로 직접 확인해야 한다.
     *
     * */




    /*
        [31881de0] hello
        [31881de0] hello time=1ms
     */
    @Test
    void begin_end() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    /*
        [4768c7eb] hello
        [4768c7eb] hello time=12ms ex=java.lang.IllegalStateException
    */
    @Test
    void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }
}

package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 실제 로그를 시작하고 종료할 수 있다. 그리고 로직도 출력하고 실행 시간도 측정할 수 있다.
 * */

@Slf4j
// @Component 어노테이션
// 싱글톤으로 사용하기 위해 스프링 빈으로 등록한다. 컴포넌트 스캔의 대상이 된다.
// +) 클래스의 인스턴스가 스프링 컨테이너에서 싱글톤으로 관리됨.
// -> 하나의 어플리케이션 컨텍스트에서 빈의 인스턴스가 하나만 생성되고, 여러 곳에서 공유해서 사용한다.
@Component
public class HelloTraceV1 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    /* 로그 추적기에서 사용되는 공개 메서드 */
    // begin(), end(), exception()

    /**
     * 로그를 시작한다.
     * @param message
     * @return 현재 로그의 상태
     * */
    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    /**
     * 로그를 정상 종료한다.
     * @param status
     * : 시작 로그의 상태. 실행 시간 계산 및 종료 시에도 시작할 때와 동일한 로그 메세지 출력 가능.
     * 정상 흐름에서 호출.
     * */
    public void end(TraceStatus status) {
        complete(status, null);
    }

    /**
     * 로그를 예외 상황으로 출력한다.
     * 실행 시간, 예외 정보를 포함한 결과 로그 출력.
     * */
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    /* 로그 추적기에서 사용되는 비공개 메서드 */
    // complete(), addSpace()

    /**
     * end(), exception()의 요청 흐름을 한 곳에서 편리하게 처리한다.
     * 실행 시간을 측정하고 로그를 남긴다.
     * */
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }

    // level = 0
    // level = 1 | -->
    // level = 2 |      | -->

    // level = 2 ex |       |<X-
    // level = 1 ex |<X-
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }

}
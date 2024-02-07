package hello.advanced.trace;

/**
 * 로그의 상태 정보를 나타낸다.
 * */
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
    private String message;

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs; // 로그 종료 시 이 시작 시간을 기준으로 시작부터 종료까지 전체 수행 시간을 구할 수 있다.
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}

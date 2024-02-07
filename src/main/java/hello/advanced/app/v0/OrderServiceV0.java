package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service // component scan의 대상 -> 자동 스프링 빈으로 등록
@RequiredArgsConstructor // 자동으로 의존 관계 주입이 된다.
public class OrderServiceV0 {

    private final OrderRepositoryV0 orderRepository;

    /* @RequiredArgsConstructor */
//    @Autowired
//    public OrderServiceV0(OrderRepositoryV0 orderRepository) {
//        this.orderRepository = orderRepository;
//    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}

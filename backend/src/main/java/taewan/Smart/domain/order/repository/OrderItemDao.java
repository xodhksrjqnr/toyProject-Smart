package taewan.Smart.domain.order.repository;

import taewan.Smart.domain.order.entity.OrderItem;

import java.util.Optional;

public interface OrderItemDao {

    boolean existsByProductId(Long productId);
    Optional<OrderItem> findById(Long orderItemId);
    Long save(OrderItem orderItem);
    long count();
}

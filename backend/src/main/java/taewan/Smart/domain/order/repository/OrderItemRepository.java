package taewan.Smart.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.domain.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

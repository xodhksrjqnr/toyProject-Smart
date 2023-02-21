package taewan.Smart.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taewan.Smart.domain.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select case when count(o) > 0 then true else false end from OrderItem o where o.product.productId = :productId")
    boolean existsByProductId(Long productId);
}

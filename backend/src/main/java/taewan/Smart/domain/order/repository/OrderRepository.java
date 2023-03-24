package taewan.Smart.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taewan.Smart.domain.order.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderItems where o.memberId=:memberId")
    List<Order> findAllByMemberId(Long memberId);
    long countAllByMemberId(Long memberId);
}

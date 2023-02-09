package taewan.Smart.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.domain.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberId(Long memberId);
    Optional<Order> findAllById(Long id);
}

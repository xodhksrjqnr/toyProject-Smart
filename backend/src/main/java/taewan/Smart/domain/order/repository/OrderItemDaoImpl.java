package taewan.Smart.domain.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.order.entity.OrderItem;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemDaoImpl implements OrderItemDao {
    private final EntityManager entityManager;

    @Override
    public boolean existsByProductId(Long productId) {
        return entityManager
                .createNativeQuery("select exists(select * from order_items where product_id=:productId)")
                .setParameter("productId", productId)
                .getSingleResult() == BigInteger.ONE;
    }

    @Override
    public Optional<OrderItem> findById(Long orderItemId) {
        try {
            return Optional.of(
                    entityManager.find(OrderItem.class, orderItemId)
            );
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Long save(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem.getOrderItemId();
    }

    @Override
    public long count() {
        return ((Number) entityManager
                .createQuery("select count(oi) from OrderItem oi")
                .getSingleResult())
                .longValue();
    }
}

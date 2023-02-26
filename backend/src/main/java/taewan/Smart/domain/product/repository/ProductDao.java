package taewan.Smart.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import taewan.Smart.domain.product.entity.Product;

import java.util.Optional;

public interface ProductDao {

    boolean existsByName(String name);
    boolean existsByNameAndProductIdNot(Long productId, String name);
    Optional<Product> findById(Long productId);
    Page<Product> findAllByFilter(Pageable pageable, String code, String name);
    Long save(Product product);
    void deleteById(Long productId);
    long count();
}

package taewan.Smart.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCodeContains(Pageable pageable, String code);
    Page<Product> findAllByNameContains(Pageable pageable, String name);
    Page<Product> findAllByCodeContainsAndNameContains(Pageable pageable, String code, String name);
}

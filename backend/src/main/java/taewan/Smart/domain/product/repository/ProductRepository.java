package taewan.Smart.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.domain.product.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
    Page<Product> findAllByCodeContains(Pageable pageable, String code);
    Page<Product> findAllByNameContains(Pageable pageable, String name);
    Page<Product> findAllByCodeContainsAndNameContains(Pageable pageable, String code, String name);
}

package taewan.Smart.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCodeContaining(Pageable pageable, String code);
}

package taewan.Smart.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

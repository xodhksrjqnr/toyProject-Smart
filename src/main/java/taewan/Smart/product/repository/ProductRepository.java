package taewan.Smart.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taewan.Smart.product.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p where p.code=:code and p.gender=:gender")
    List<Product> findByFilter(Long code, Integer gender);
}

package taewan.Smart.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

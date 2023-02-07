package taewan.Smart.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

package taewan.Smart.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.category.entity.CategoryItem;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
}

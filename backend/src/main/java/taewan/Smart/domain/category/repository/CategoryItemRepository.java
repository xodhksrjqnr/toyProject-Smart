package taewan.Smart.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.domain.category.entity.CategoryItem;

import java.util.Optional;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {

    Optional<CategoryItem> findByName(String name);
}

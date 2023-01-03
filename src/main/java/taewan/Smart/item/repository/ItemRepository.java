package taewan.Smart.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

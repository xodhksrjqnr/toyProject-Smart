package taewan.Smart.item;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import taewan.Smart.item.dto.ItemSaveDto;
import taewan.Smart.item.entity.Item;
import taewan.Smart.item.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

    @Autowired private ItemRepository itemRepository;

    static List<ItemSaveDto> dtos = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        for (int i = 1; i <= 3; i++) {
            ItemSaveDto dto = new ItemSaveDto();
            dto.setItemName("item" + i);
            dto.setItemImg("itemImg" + i);
            dto.setPrice(1000 * i);
            dto.setCategory(10000L + i);
            dto.setItemInformation("itemInfo" + i);
            dtos.add(dto);
        }
    }

    @Test
    void 물품_저장() {
        //given
        ItemSaveDto dto = dtos.get(0);

        //when
        Item saved = itemRepository.save(new Item(dto));

        //then
        assertThat(saved.getItemName()).isEqualTo(dto.getItemName());
        assertThat(saved.getItemImg()).isEqualTo(dto.getItemImg());
        assertThat(saved.getPrice()).isEqualTo(dto.getPrice());
        assertThat(saved.getCategory()).isEqualTo(dto.getCategory());
        assertThat(saved.getItemInformation()).isEqualTo(dto.getItemInformation());
    }

    @Test
    void 물품_단일조회() {
        //given
        ItemSaveDto dto = dtos.get(0);
        Item saved = itemRepository.save(new Item(dto));

        //when
        Item found = itemRepository.findById(saved.getItemId()).orElseThrow();

        //then
        assertThat(saved.toString()).isEqualTo(found.toString());
    }

    @Test
    void 없는_물품_단일조회() {
        //given
        //when
        Optional<Item> found = itemRepository.findById(1L);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 물품_전체조회() {
        //given
        List<Item> saved = new ArrayList<>();
        for (ItemSaveDto dto : dtos)
            saved.add(itemRepository.save(new Item(dto)));

        //when
        List<Item> found = itemRepository.findAll();

        //then
        assertThat(found.size()).isEqualTo(3);
        for (int i = 0; i < 3; i++)
            assertThat(found.get(i).toString()).isEqualTo(saved.get(i).toString());
    }

    @Test
    void 물품_삭제() {
        //given
        ItemSaveDto dto = dtos.get(0);
        Item saved = itemRepository.save(new Item(dto));

        //when
        itemRepository.deleteById(saved.getItemId());

        //then
        assertThat(itemRepository.count()).isEqualTo(0);
        assertThat(itemRepository.findById(saved.getItemId())).isEmpty();
    }
}
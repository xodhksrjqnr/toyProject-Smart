package taewan.Smart.item;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taewan.Smart.item.dto.ItemInfoDto;
import taewan.Smart.item.dto.ItemSaveDto;
import taewan.Smart.item.dto.ItemUpdateDto;
import taewan.Smart.item.entity.Item;
import taewan.Smart.item.repository.ItemRepository;
import taewan.Smart.item.service.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock private ItemRepository itemRepository;
    @InjectMocks private ItemServiceImpl itemService;

    static List<ItemSaveDto> dtos = new ArrayList<>();
    static List<Item> entities = new ArrayList<>();

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

            Item item = new Item();
            ItemUpdateDto dto2 = new ItemUpdateDto();
            dto2.setItemId((long)i);
            dto2.setItemName("item" + i);
            dto2.setItemImg("itemImg" + i);
            dto2.setPrice(1000 * i);
            dto2.setCategory(10000L + i);
            dto2.setItemInformation("itemInfo" + i);
            item.updateItem(dto2);
            entities.add(item);
        }
    }

    @Test
    void 물품_저장() {
        //given
        Item item = new Item(dtos.get(0));
        when(itemRepository.save(item)).thenReturn(entities.get(0));

        //when
        Long savedId = itemService.save(dtos.get(0));

        //then
        assertThat(savedId).isEqualTo(1);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void 물품_단일조회() {
        //given
        Item item = entities.get(0);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        //when
        ItemInfoDto found = itemService.findOne(1L);

        //then
        assertThat(found.toString().replace("ItemInfoDto", ""))
                .isEqualTo(item.toString().replace("Item", ""));
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void 없는_물품_단일조회() {
        //given
        Item item = entities.get(0);
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class, () -> itemService.findOne(1L));
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void 물품_전체조회() {
        //given
        when(itemRepository.findAll()).thenReturn(entities);

        //when
        List<ItemInfoDto> found = itemService.findAll();

        //then
        assertThat(found.size()).isEqualTo(3);
        for (int i = 0; i < 3; i++) {
            assertThat(found.get(i).toString().replace("ItemInfoDto", ""))
                    .isEqualTo(entities.get(i).toString().replace("Item", ""));
        }
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void 물품_수정() {
        //given
        Item item = entities.get(0);
        ItemUpdateDto dto = new ItemUpdateDto();
        dto.setItemId(1L);
        dto.setItemName("item5");
        dto.setItemImg("itemImg5");
        dto.setPrice(1000 * 5);
        dto.setCategory(10000L + 5);
        dto.setItemInformation("itemInfo5");
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        //when
        Long id = itemService.modify(dto);

        //then
        assertThat(id).isEqualTo(1L);
        assertThat(item.toString().replace("Item", ""))
                .isEqualTo(dto.toString().replace("ItemUpdateDto", ""));
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void 물품_삭제() {
        //given

        //when
        itemService.delete(1L);

        //then
        verify(itemRepository, times(1)).deleteById(1L);
    }
}
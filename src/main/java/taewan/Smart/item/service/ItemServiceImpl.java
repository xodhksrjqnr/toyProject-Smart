package taewan.Smart.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taewan.Smart.item.entity.Item;
import taewan.Smart.item.dto.ItemInfoDto;
import taewan.Smart.item.dto.ItemSaveDto;
import taewan.Smart.item.dto.ItemUpdateDto;
import taewan.Smart.item.repository.ItemRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemInfoDto findOne(Long itemId) {
        return new ItemInfoDto(itemRepository.findById(itemId).orElseThrow());
    }

    @Override
    public List<ItemInfoDto> findAll() {
        return Arrays.asList(itemRepository.findAll()
                .stream().map(ItemInfoDto::new)
                .toArray(ItemInfoDto[]::new));
    }

    @Override
    public Long save(ItemSaveDto itemSaveDto) {
        return itemRepository.save(new Item(itemSaveDto)).getItemId();
    }

    @Override
    public Long modify(Long itemId, ItemUpdateDto itemUpdateDto) {
        Item found = itemRepository.findById(itemId).orElseThrow();

        found.updateItem(itemUpdateDto);
        return itemId;
    }

    @Override
    public void delete(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}

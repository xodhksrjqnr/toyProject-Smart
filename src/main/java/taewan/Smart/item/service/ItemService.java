package taewan.Smart.item.service;

import taewan.Smart.item.entity.ItemInfoDto;
import taewan.Smart.item.entity.ItemSaveDto;
import taewan.Smart.item.entity.ItemUpdateDto;

import java.util.List;

public interface ItemService {

    ItemInfoDto findOne(Long itemId);
    List<ItemInfoDto> findAll();
    Long save(ItemSaveDto itemSaveDto);
    Long modify(Long itemId, ItemUpdateDto itemUpdateDto);
    void delete(Long itemId);
}

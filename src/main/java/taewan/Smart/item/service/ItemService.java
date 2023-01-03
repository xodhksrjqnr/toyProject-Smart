package taewan.Smart.item.service;

import taewan.Smart.item.dto.ItemInfoDto;
import taewan.Smart.item.dto.ItemSaveDto;
import taewan.Smart.item.dto.ItemUpdateDto;

import java.util.List;

public interface ItemService {

    ItemInfoDto findOne(Long itemId);
    List<ItemInfoDto> findAll();
    Long save(ItemSaveDto itemSaveDto);
    Long modify(Long itemId, ItemUpdateDto itemUpdateDto);
    void delete(Long itemId);
}

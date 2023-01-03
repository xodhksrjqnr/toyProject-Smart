package taewan.Smart.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.item.entity.ItemInfoDto;
import taewan.Smart.item.entity.ItemSaveDto;
import taewan.Smart.item.entity.ItemUpdateDto;
import taewan.Smart.item.service.ItemService;

import java.util.List;

@Controller
@RequestMapping("item")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    public String theItemPage(@PathVariable Long itemId) {
        ItemInfoDto item = itemService.findOne(itemId);
        return "";
    }

    @GetMapping
    public String itemsPage() {
        List<ItemInfoDto> items = itemService.findAll();
        return "";
    }

    @PostMapping
    public String itemUpload(ItemSaveDto dto) {
        Long saved = itemService.save(dto);
        return "";
    }

    @PostMapping("/{itemId}")
    public String itemUpdate(@PathVariable Long itemId, ItemUpdateDto dto) {
        Long updatedId = itemService.modify(itemId, dto);
        return "";
    }

    @DeleteMapping("/{itemId}")
    public String itemDelete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return "";
    }
}

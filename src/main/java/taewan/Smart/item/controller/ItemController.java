package taewan.Smart.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.item.dto.ItemInfoDto;
import taewan.Smart.item.dto.ItemSaveDto;
import taewan.Smart.item.dto.ItemUpdateDto;
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
    public String searchOne(@PathVariable Long itemId, Model model) {
        model.addAttribute("item", itemService.findOne(itemId));
        return "item/view";
    }

    @GetMapping
    public String itemListPage(Model model) {
        model.addAttribute("itemList", itemService.findAll());
        return "item/list_view";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("item", new ItemSaveDto());
        return "item/write";
    }

    @PostMapping
    public String upload(ItemSaveDto dto) {
        Long saved = itemService.save(dto);
        return "redirect:item/" + saved;
    }

    @GetMapping("/update/{itemId}")
    public String updateForm(@PathVariable Long itemId, Model model) {
        model.addAttribute("item", itemService.findOne(itemId));
        return "item/update";
    }

    @PostMapping("/update")
    public String update(ItemUpdateDto dto) {
        Long updatedId = itemService.modify(dto);
        return "redirect:/item/" + updatedId;
    }

    @PostMapping("/delete/{itemId}")
    public String remove(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return "redirect:/item";
    }
}

package taewan.Smart.item;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import taewan.Smart.item.controller.ItemController;
import taewan.Smart.item.dto.ItemInfoDto;
import taewan.Smart.item.dto.ItemSaveDto;
import taewan.Smart.item.dto.ItemUpdateDto;
import taewan.Smart.item.entity.Item;
import taewan.Smart.item.service.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean ItemServiceImpl itemService;
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
    void 물품_단일조회() throws Exception {
        //given
        when(itemService.findOne(1L))
                .thenReturn(new ItemInfoDto(entities.get(0)));

        //when //then
        mockMvc.perform(get("/item/{itemId}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("item"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("item/view"));
    }

    @Test
    void 없는_물품_단일조회() throws Exception {
        //given
        when(itemService.findOne(1L))
                .thenThrow(new NoSuchElementException());

        //when //then
        mockMvc.perform(get("/item/{itemId}", 1))
                .andExpect(status().isNotFound())
                .andExpect(e -> assertTrue(e.getResolvedException() instanceof NoSuchElementException))
                .andExpect(view().name("error"));
    }

    @Test
    void 물품_전체조회() throws Exception {
        //given
        List<ItemInfoDto> infoDtos = new ArrayList<>();
        for (Item m : entities)
            infoDtos.add(new ItemInfoDto(m));
        when(itemService.findAll()).thenReturn(infoDtos);

        //when //then
        mockMvc.perform(get("/item"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("itemList"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("item/list_view"));
    }

    @Test
    void 물품_가입양식() throws Exception {
        //when //then
        mockMvc.perform(get("/item/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("item"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("item/write"));
    }

    @Test
    void 물품_등록() throws Exception {
        //given
        when(itemService.save(dtos.get(0))).thenReturn(1L);

        //when //then
        mockMvc.perform(post("/item"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/item/{itemId}"));
    }

    @Test
    void 물품정보_수정양식() throws Exception {
        //given
        when(itemService.findOne(1L))
                .thenReturn(new ItemInfoDto(entities.get(0)));

        //when //then
        mockMvc.perform(get("/item/update/{itemId}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("item"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("item/update"));
    }

    @Test
    void 물품정보_수정() throws Exception {
        //given
        ItemUpdateDto dto = new ItemUpdateDto();
        dto.setItemId(1L);
        dto.setItemName("item5");
        dto.setItemImg("itemImg5");
        dto.setPrice(1000 * 5);
        dto.setCategory(10000L + 5);
        dto.setItemInformation("itemInfo5");
        when(itemService.modify(dto)).thenReturn(1L);

        //when //then
        mockMvc.perform(post("/item/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/item/{itemId}"));
    }

    @Test
    void 물품_탈퇴() throws Exception {
        //when //then
        mockMvc.perform(post("/item/delete/{itemId}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/item"));
    }
}
package taewan.Smart.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import taewan.Smart.product.controller.ProductController;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.entity.Product;
import taewan.Smart.product.service.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean ProductServiceImpl productService;
    static List<ProductSaveDto> dtos = new ArrayList<>();
    static List<Product> entities = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        for (int i = 1; i <= 3; i++) {
            ProductSaveDto dto = new ProductSaveDto();
            dto.setProductName("product" + i);
            dto.setProductImg("productImg" + i);
            dto.setPrice(1000 * i);
            dto.setCategory(10000L + i);
            dto.setProductInformation("productInfo" + i);
            dtos.add(dto);

            Product product = new Product();
            ProductUpdateDto dto2 = new ProductUpdateDto();
            dto2.setProductId((long)i);
            dto2.setProductName("product" + i);
            dto2.setProductImg("productImg" + i);
            dto2.setPrice(1000 * i);
            dto2.setCategory(10000L + i);
            dto2.setProductInformation("productInfo" + i);
            product.updateProduct(dto2);
            entities.add(product);
        }
    }

    @Test
    void 물품_단일조회() throws Exception {
        //given
        when(productService.findOne(1L))
                .thenReturn(new ProductInfoDto(entities.get(0)));

        //when //then
        mockMvc.perform(get("/product/{productId}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("product/view"));
    }

    @Test
    void 없는_물품_단일조회() throws Exception {
        //given
        when(productService.findOne(1L))
                .thenThrow(new NoSuchElementException());

        //when //then
        mockMvc.perform(get("/product/{productId}", 1))
                .andExpect(status().isNotFound())
                .andExpect(e -> assertTrue(e.getResolvedException() instanceof NoSuchElementException))
                .andExpect(view().name("error"));
    }

    @Test
    void 물품_전체조회() throws Exception {
        //given
        List<ProductInfoDto> infoDtos = new ArrayList<>();
        for (Product m : entities)
            infoDtos.add(new ProductInfoDto(m));
        when(productService.findAll()).thenReturn(infoDtos);

        //when //then
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productList"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("product/list_view"));
    }

    @Test
    void 물품_가입양식() throws Exception {
        //when //then
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("product/write"));
    }

    @Test
    void 물품_등록() throws Exception {
        //given
        when(productService.save(dtos.get(0))).thenReturn(1L);

        //when //then
        mockMvc.perform(post("/product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/product/{productId}"));
    }

    @Test
    void 물품정보_수정양식() throws Exception {
        //given
        when(productService.findOne(1L))
                .thenReturn(new ProductInfoDto(entities.get(0)));

        //when //then
        mockMvc.perform(get("/product/update/{productId}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("product/update"));
    }

    @Test
    void 물품정보_수정() throws Exception {
        //given
        ProductUpdateDto dto = new ProductUpdateDto();
        dto.setProductId(1L);
        dto.setProductName("product5");
        dto.setProductImg("productImg5");
        dto.setPrice(1000 * 5);
        dto.setCategory(10000L + 5);
        dto.setProductInformation("productInfo5");
        when(productService.modify(dto)).thenReturn(1L);

        //when //then
        mockMvc.perform(post("/product/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/product/{productId}"));
    }

    @Test
    void 물품_탈퇴() throws Exception {
        //when //then
        mockMvc.perform(post("/product/delete/{productId}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product"));
    }
}
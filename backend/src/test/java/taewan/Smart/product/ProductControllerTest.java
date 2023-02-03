package taewan.Smart.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import taewan.Smart.product.controller.ProductController;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.service.ProductServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean ProductServiceImpl productService;

    private static String root = "/Users/taewan/Desktop/";

    private static Map<String, String> codeInfo = Map.of(
            "A01", "후드 티셔츠",
            "A02", "맨투맨",
            "B01", "코트",
            "B02", "패딩",
            "C01", "트레이닝 팬츠",
            "C02", "숏팬츠"
    );

    static List<ProductSaveDto> dtos = new ArrayList<>();

    static List<ProductInfoDto> dtos2 = new ArrayList<>();

    @BeforeAll
    static void setup() throws IOException {
        String[] tmp = {"A", "B", "C"};
        String[] tmp2 = {"M", "W"};

        for (int i = 1; i <= 3; i++) {
            ProductSaveDto dto = new ProductSaveDto();
            dto.setName("product" + i);
            dto.setPrice((int)(Math.random() * 10 + 1) * 10000);
            dto.setCode(tmp[i - 1] + "0" + (i % 2 + 1) + tmp2[i % 2]);
            dto.setSize("s,m,l,xl,xxl");
            dto.setDetailInfo(createMultipartFile(dto.getCode(), i));
            List<MultipartFile> imgs = new ArrayList<>();
            for (int j = 1; j <= 3; j++)
                imgs.add(createMultipartFile(dto.getCode(), j));
            dto.setImgFiles(imgs);
            dtos.add(dto);

            ProductInfoDto dto2 = new ProductInfoDto();
            dto2.setId((long)i);
            dto2.setName(dto.getName());
            dto2.setPrice(dto.getPrice());
            dto2.setCode(dto.getCode());
            dto2.setSize(dto.getSize());
            String path = "http://localhost:8080/images/products/" + dto2.getCode() + "/" + dto2.getName();
            dto2.setDetailInfo(path + "/" + UUID.randomUUID());
            List<String> imgFiles = new ArrayList<>();
            for (int j = 1; j <= 3; j++)
                imgFiles.add(path + "/view/" + UUID.randomUUID());
            dto2.setImgFiles(imgFiles);
            dtos2.add(dto2);
        }
    }

    static MultipartFile createMultipartFile(String code, int i) throws IOException {
        String keyword = codeInfo.get(code.substring(0, 3)) + i;
        String path = root + "testImg/product";
        File[] imgs = new File(path).listFiles();
        String imgName = "구두1.jpeg";
        String contentType = "image/";

        for (File img : imgs) {
            String normalizedName = Normalizer.normalize(img.getName(), Normalizer.Form.NFC);
            if (normalizedName.contains(keyword)) {
                imgName = normalizedName;
                contentType += imgName.substring(imgName.lastIndexOf(".") + 1);
                break;
            }
        }
        if (contentType.equals("image/"))
            contentType += "jpeg";
        return new MockMultipartFile("image", imgName, contentType, new FileInputStream(path + "/" + imgName));
    }

    @Test
    void 제품_단일조회() throws Exception {
        //given
        when(productService.findOne(1L)).thenReturn(dtos2.get(0));

        //when //then
        mockMvc.perform(get("/products/{productId}", 1))
                .andExpect(status().isOk());
        verify(productService, times(1)).findOne(1L);
    }

    @Test
    void 없는_제품_단일조회() throws Exception {
        //given
        when(productService.findOne(1L)).thenThrow(new NoSuchElementException());

        //when //then
        mockMvc.perform(get("/products/{productId}", 1))
                .andExpect(status().isNotFound())
                .andExpect(e -> assertTrue(e.getResolvedException() instanceof NoSuchElementException));
        verify(productService, times(1)).findOne(1L);
    }

    @Test
    void 제품_페이지단위_조회() throws Exception {
        //given
        Pageable pageable = PageRequest.ofSize(10);
        Page<ProductInfoDto> page = new PageImpl<>(dtos2, pageable, 3);
        when(productService.findAll(pageable)).thenReturn(page);

        //when //then
        mockMvc.perform(
                get("/products")
                        .param("size", "10"))
                .andExpect(status().isOk());
        verify(productService, times(1)).findAll(pageable);
    }

    @Test
    void 제품_필터조회() throws Exception {
        //given
        Pageable pageable = PageRequest.ofSize(10);
        String code = dtos2.get(0).getCode();
        String search = dtos2.get(0).getName();
        List<ProductInfoDto> found = new ArrayList<>();
        found.add(dtos2.get(0));
        Page<ProductInfoDto> page = new PageImpl<>(found, pageable, 3);
        when(productService.findAllWithFilter(pageable, code, "")).thenReturn(page);
        when(productService.findAllWithFilter(pageable, "", search)).thenReturn(page);
        when(productService.findAllWithFilter(pageable, code, search)).thenReturn(page);

        //when //then
        mockMvc.perform(
                get("/products/filter")
                        .param("code", code)
                        .param("size", "10"))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/products/filter")
                                .param("search", search)
                                .param("size", "10"))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/products/filter")
                                .param("code", code)
                                .param("search", search)
                                .param("size", "10"))
                .andExpect(status().isOk());
        verify(productService, times(3)).findAllWithFilter(any(), any(), any());
    }

    @Test
    void 제품_등록() throws Exception {
        //given
        when(productService.save(any())).thenReturn(1L);

        //when //then
        mockMvc.perform(post("/products"))
                .andExpect(status().isOk());
        verify(productService, times(1)).save(any());
    }

    @Test
    void 제품_수정() throws Exception {
        //given
        when(productService.modify(any())).thenReturn(1L);

        //when //then
        mockMvc.perform(post("/products/update"))
                .andExpect(status().isOk());
        verify(productService, times(1)).modify(any());
    }

    @Test
    void 제품_삭제() throws Exception {
        //when //then
        mockMvc.perform(post("/products/{productId}/delete", 1))
                .andExpect(status().isOk());
        verify(productService, times(1)).delete(1L);
    }
}
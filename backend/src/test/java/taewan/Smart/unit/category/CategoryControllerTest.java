package taewan.Smart.unit.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import taewan.Smart.domain.category.controller.CategoryController;
import taewan.Smart.domain.category.service.CategoryService;
import taewan.Smart.global.error.GlobalExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;
    @Mock private CategoryService categoryService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new CategoryController(categoryService))
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    @DisplayName("카테고리 저장 테스트")
    void upload() throws Exception {
        //when
        ResultActions result = mockMvc.perform(post("/category")
                        .param("classification", "상의")
                        .param("middleClassification", "맨투맨")
        );

        //then
        result.andExpect(status().isOk());
        verify(categoryService, only()).save(any());
        verify(categoryService, times(1)).save(any());
    }

    @Test
    @DisplayName("등록된 카테고리명과 등록되지 않은 상세 카테고리명이 입력된 경우 status.isOk를 반환")
    void upload_duplicate_categoryName() throws Exception {
        //when
        ResultActions result = mockMvc.perform(post("/category")
                .param("classification", "상의")
                .param("middleClassification", "맨투맨")
        );

        //then
        result.andExpect(status().isOk());
        verify(categoryService, only()).save(any());
        verify(categoryService, times(1)).save(any());
    }

    @Test
    @DisplayName("등록되지 않은 카테고리명과 등록된 상세 카테고리명이 입력된 경우 status.isBadRequest를 반환")
    void upload_duplicate_categoryItemName() throws Exception {
        //given
        doThrow(DuplicateKeyException.class).when(categoryService).save(any());

        //when //then
        mockMvc.perform(
                post("/category")
                        .param("classification", "상의")
                        .param("middleClassification", "맨투맨"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertEquals(
                                result.getResolvedException().getClass(),
                                DuplicateKeyException.class
                        )
                );
        verify(categoryService, only()).save(any());
        verify(categoryService, times(1)).save(any());
    }

    @Test
    @DisplayName("등록된 카테고리명과 등록된 상세 카테고리명이 입력된 경우 status.isBadRequest를 반환")
    void upload_duplicate_all() throws Exception {
        //given
        doThrow(DuplicateKeyException.class).when(categoryService).save(any());

        //when //then
        mockMvc.perform(
                        post("/category")
                                .param("classification", "상의")
                                .param("middleClassification", "맨투맨"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertEquals(
                                result.getResolvedException().getClass(),
                                DuplicateKeyException.class
                        )
                );
        verify(categoryService, only()).save(any());
        verify(categoryService, times(1)).save(any());
    }

    @Test
    @DisplayName("상세 카테고리를 포함하는 카테고리 전체 조회")
    void searchAll() throws Exception {
        //when
        ResultActions result = mockMvc.perform(get("/category"));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

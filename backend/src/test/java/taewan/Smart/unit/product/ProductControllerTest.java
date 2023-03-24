package taewan.Smart.unit.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import taewan.Smart.domain.product.controller.ProductController;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.service.ProductService;
import taewan.Smart.fixture.ExceptionTestFixture;
import taewan.Smart.fixture.ProductTestFixture;
import taewan.Smart.global.error.GlobalExceptionHandler;
import taewan.Smart.global.exception.ForeignKeyException;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ProductService productService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(productService))
                .setControllerAdvice(GlobalExceptionHandler.class)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @BeforeAll
    static void beforeAllSetup() {
        ProductTestFixture.setCreateDefault();
    }

    @Test
    @DisplayName("제품 저장 테스트")
    void upload() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUploadRequest();

        when(productService.save(any())).thenReturn(1L);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        verify(productService, only()).save(any());
        verify(productService, times(1)).save(any());
    }

    @Test
    @DisplayName("제품 이름이 비어있는 경우 제품 저장 시 isBadRequest를 반환")
    void upload_empty_name() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 = ProductTestFixture.createUploadRequest("name", "");
        MockHttpServletRequestBuilder request2 = ProductTestFixture.createUploadRequest("name", " ");

        //when
        ResultActions isNull = mockMvc.perform(request1);
        ResultActions isBlank = mockMvc.perform(request2);

        //then
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(productService, never()).save(any());
    }

    @Test
    @DisplayName("제품 가격이 유효하지 않은 경우 제품 저장 시 isBadRequest를 반환")
    void upload_invalid_price() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUploadRequest("price", -1);

        //when
        ResultActions isMinus = mockMvc.perform(request);

        //then
        isMinus.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(productService, never()).save(any());
    }

    @Test
    @DisplayName("제품 분류번호가 유효하지 않은 경우 제품 저장 시 isBadRequest를 반환")
    void upload_invalid_code() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 = ProductTestFixture.createUploadRequest("code", "");
        MockHttpServletRequestBuilder request2 = ProductTestFixture.createUploadRequest("code", " ");
        MockHttpServletRequestBuilder request3 = ProductTestFixture.createUploadRequest("code", "A11B");

        //when
        ResultActions isNull = mockMvc.perform(request1);
        ResultActions isBlank = mockMvc.perform(request2);
        ResultActions invalidPattern = mockMvc.perform(request3);

        //then
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        invalidPattern.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(productService, never()).save(any());
    }

    @Test
    @DisplayName("제품 사이즈가 비어있는 경우 제품 저장 시 isBadRequest를 반환")
    void upload_invalid_size() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 = ProductTestFixture.createUploadRequest("size", "");
        MockHttpServletRequestBuilder request2 = ProductTestFixture.createUploadRequest("size", " ");

        //when
        ResultActions isNull = mockMvc.perform(request1);
        ResultActions isBlank = mockMvc.perform(request2);

        //then
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(productService, never()).save(any());
    }

    @Test
    @DisplayName("저장하려는 제품명이 이미 등록된 경우 isBadRequest를 반환")
    void upload_duplicate_name() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUploadRequest();

        when(productService.save(any())).thenThrow(DuplicateKeyException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isDuplicateKeyException());
        verify(productService, only()).save(any());
        verify(productService, times(1)).save(any());
    }

    @Test
    @DisplayName("ProductSaveDto의 imgFiles가 비어있는 경우 isBadRequest를 반환")
    void upload_empty_imgFiles() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUploadRequest();

        when(productService.save(any())).thenThrow(IllegalArgumentException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isIllegalArgumentException());
        verify(productService, only()).save(any());
        verify(productService, times(1)).save(any());
    }

    @Test
    @DisplayName("ProductSaveDto의 detailInfo가 비어있는 경우 isBadRequest를 반환")
    void upload_empty_detailInfo() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUploadRequest();

        when(productService.save(any())).thenThrow(IllegalArgumentException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isIllegalArgumentException());
        verify(productService, only()).save(any());
        verify(productService, times(1)).save(any());
    }

    @Test
    @DisplayName("제품 단일 조회 테스트")
    void searchOne() throws Exception {
        //given
        MockHttpServletRequestBuilder request = get("/products/{productId}", 1);
        ProductInfoDto dto = ProductInfoDto.builder()
                .productId(1L)
                .size("s,m,l")
                .imgFiles(new ArrayList<>())
                .detailInfo("path")
                .price(1000)
                .code("A01M")
                .name("test")
                .build();

        when(productService.findOne(anyLong())).thenReturn(dto);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(productService, only()).findOne(anyLong());
        verify(productService, times(1)).findOne(anyLong());
    }

    @Test
    @DisplayName("등록되지 않은 제품 기본키를 이용해 제품 단일 조회 시 isNotFound를 반환")
    void searchOne_invalid_productId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = get("/products/{productId}", 0);

        when(productService.findOne(anyLong())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(productService, only()).findOne(anyLong());
        verify(productService, times(1)).findOne(anyLong());
    }

//    Pageable 처리 필요
    @Test
    @DisplayName("필터 조합에 따른 제품 전체 조회 테스트")
    void searchAllByFilter() throws Exception {
        //given
        Page<ProductInfoDto> page = new PageImpl<>(new ArrayList<>());
        MockHttpServletRequestBuilder request1 = get("/products/filter")
                .param("code", "A")
                .param("search", "t")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "productId,DESC");
        MockHttpServletRequestBuilder request2 = get("/products/filter")
                .param("search", "t")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "productId,DESC");
        MockHttpServletRequestBuilder request3 = get("/products/filter")
                .param("code", "A")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "productId,DESC");
        MockHttpServletRequestBuilder request4 = get("/products/filter")
                .param("code", "A")
                .param("search", "t");

        when(productService.findAllByFilter(any(), any(), any())).thenReturn(page);

        //when
        ResultActions basic = mockMvc.perform(request1);
        ResultActions defaultCode = mockMvc.perform(request2);
        ResultActions defaultSearch = mockMvc.perform(request3);
        ResultActions defaultPageable = mockMvc.perform(request4);

        //then
        basic.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        defaultCode.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        defaultSearch.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        defaultPageable.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(productService, times(4)).findAllByFilter(any(), any(), any());
    }

    @Test
    @DisplayName("제품 정보 수정 테스트")
    void modify() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUpdateRequest();

        when(productService.update(any())).thenReturn(1L);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(productService, only()).update(any());
        verify(productService, times(1)).update(any());
    }

    @Test
    @DisplayName("제품 이름이 비어있을 때 수정 시 isBadRequest를 반환")
    void modify_empty_name() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 = ProductTestFixture.createUpdateRequest("name", "");
        MockHttpServletRequestBuilder request2 = ProductTestFixture.createUpdateRequest("name", " ");

        //when
        ResultActions isNull = mockMvc.perform(request1);
        ResultActions isBlank = mockMvc.perform(request2);

        //then
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(productService, never()).update(any());
    }

    @Test
    @DisplayName("제품 가격이 유효하지 않은 경우 수정 시 isBadRequest를 반환")
    void modify_invalid_price() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUpdateRequest("price", -1);

        //when
        ResultActions isMinus = mockMvc.perform(request);

        //then
        isMinus.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(productService, never()).update(any());
    }

    @Test
    @DisplayName("제품 코드가 유효하지 않은 경우 수정 시 isBadRequest를 반환")
    void modify_invalid_code() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 = ProductTestFixture.createUpdateRequest("code", "");
        MockHttpServletRequestBuilder request2 = ProductTestFixture.createUpdateRequest("code", " ");
        MockHttpServletRequestBuilder request3 = ProductTestFixture.createUpdateRequest("code", "a11G");

        //when
        ResultActions isNull = mockMvc.perform(request1);
        ResultActions isBlank = mockMvc.perform(request2);
        ResultActions invalidPattern = mockMvc.perform(request2);

        //then
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        invalidPattern.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(productService, never()).update(any());
    }

    @Test
    @DisplayName("제품 사이즈가 비어있는 경우 수정 시 isBadRequest를 반환")
    void modify_empty_size() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 = ProductTestFixture.createUpdateRequest("size", "");
        MockHttpServletRequestBuilder request2 = ProductTestFixture.createUpdateRequest("size", " ");

        //when
        ResultActions isNull = mockMvc.perform(request1);
        ResultActions isBlank = mockMvc.perform(request2);

        //then
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(productService, never()).update(any());
    }

    @Test
    @DisplayName("등록되지 않은 제품 기본키를 이용해 제품 정보 수정 시 isNotFound를 반환")
    void modify_invalid_productId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUpdateRequest();

        when(productService.update(any())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(productService, only()).update(any());
        verify(productService, times(1)).update(any());
    }

    @Test
    @DisplayName("수정하려는 제품명을 이미 다른 제품으로 등록되어 있는 경우 제품 정보 수정 시 isBadRequest를 반환")
    void modify_duplicate_name() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUpdateRequest();

        when(productService.update(any())).thenThrow(DuplicateKeyException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isDuplicateKeyException());
        verify(productService, only()).update(any());
        verify(productService, times(1)).update(any());
    }

    @Test
    @DisplayName("ProductUpdateDto의 imgFiles가 비어있는 경우 isBadRequest를 반환")
    void modify_empty_imgFiles() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUpdateRequest();

        when(productService.update(any())).thenThrow(IllegalArgumentException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isIllegalArgumentException());
        verify(productService, only()).update(any());
        verify(productService, times(1)).update(any());
    }

    @Test
    @DisplayName("ProductUpdateDto의 detailInfo가 비어있는 경우 isBadRequest를 반환")
    void modify_empty_detailInfo() throws Exception {
        //given
        MockHttpServletRequestBuilder request = ProductTestFixture.createUpdateRequest();

        when(productService.update(any())).thenThrow(IllegalArgumentException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isIllegalArgumentException());
        verify(productService, only()).update(any());
        verify(productService, times(1)).update(any());
    }

    @Test
    @DisplayName("제품 삭제 테스트")
    void delete() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/products/{productId}/delete", 1L);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(productService, only()).delete(anyLong());
        verify(productService, times(1)).delete(anyLong());
    }

    @Test
    @DisplayName("등록되지 않은 제품 기본키를 이용해 제품 삭제 시 isNotFound를 반환")
    void delete_invalid_productId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/products/{productId}/delete", 1L);

        doThrow(NoSuchElementException.class).when(productService).delete(anyLong());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(productService, only()).delete(anyLong());
        verify(productService, times(1)).delete(anyLong());
    }

    @Test
    @DisplayName("제품과 관련된 주문 아이템이 존재하는 경우 삭제 시 isBadRequest를 반환")
    void delete_foreignKey_productId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/products/{productId}/delete", 1L);

        doThrow(ForeignKeyException.class).when(productService).delete(anyLong());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isForeignKeyException());
        verify(productService, only()).delete(anyLong());
        verify(productService, times(1)).delete(anyLong());
    }
}
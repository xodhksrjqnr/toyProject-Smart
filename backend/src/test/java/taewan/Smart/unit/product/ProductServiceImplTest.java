package taewan.Smart.unit.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import taewan.Smart.domain.order.repository.OrderItemDao;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductDao;
import taewan.Smart.domain.product.service.ProductServiceImpl;
import taewan.Smart.fixture.ProductTestFixture;
import taewan.Smart.global.exception.ForeignKeyException;
import taewan.Smart.global.util.CustomFileUtils;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static taewan.Smart.global.util.CustomFileUtils.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private MockedStatic<CustomFileUtils> mockedFileUtil;
    @Mock private ProductDao productDao;
    @Mock private OrderItemDao orderItemDao;
    @InjectMocks private ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        mockedFileUtil = mockStatic(CustomFileUtils.class);
    }

    @AfterEach
    void destroy() {
        mockedFileUtil.close();
    }

    @Test
    @DisplayName("제품 저장 테스트")
    void save() {
        //given
        ProductSaveDto dto = ProductSaveDto.builder()
                .imgFiles(new ArrayList<>())
                .name("test")
                .code("A01M")
                .size("s")
                .price(1000)
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.existsByName(anyString())).thenReturn(false);
        when(productDao.save(any())).thenReturn(1L);
        when(saveFile(any(), anyString())).thenReturn("path");
        when(saveFiles(any(), anyString())).thenReturn(new ArrayList<>());

        //when
        productService.save(dto);

        //then
        verify(productDao, times(1)).existsByName(anyString());
        verify(productDao, times(1)).save(any());
        mockedFileUtil.verify(
                () -> saveFile(any(), anyString()), times(1)
        );
        mockedFileUtil.verify(
                () -> saveFiles(anyList(), anyString()), times(1)
        );
    }

    @Test
    @DisplayName("이미 등록된 제품명을 이용해 제품을 저장하는 경우 DuplicateKeyException가 발생")
    void save_duplicate_productName() {
        //given
        ProductSaveDto dto = ProductSaveDto.builder()
                .imgFiles(new ArrayList<>())
                .name("test")
                .code("A01M")
                .size("s")
                .price(1000)
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.existsByName(anyString())).thenReturn(true);

        //when
        assertThrows(DuplicateKeyException.class,
                () -> productService.save(dto));
        verify(productDao, only()).existsByName(anyString());
        verify(productDao, times(1)).existsByName(anyString());
    }

    @Test
    @DisplayName("제품 이미지가 없는 경우 제품 저장 시 IllegalArgumentException가 발생")
    void save_empty_imageFiles() {
        //given
        ProductSaveDto dto = ProductSaveDto.builder()
                .imgFiles(new ArrayList<>())
                .name("test")
                .code("A01M")
                .size("s")
                .price(1000)
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.existsByName(anyString())).thenReturn(false);
        when(saveFiles(any(), anyString())).thenThrow(IllegalArgumentException.class);

        //when //then
        assertThrows(IllegalArgumentException.class,
                () -> productService.save(dto));
        verify(productDao, times(1)).existsByName(anyString());
        mockedFileUtil.verify(
                () -> saveFiles(anyList(), anyString()), times(1)
        );
    }

    @Test
    @DisplayName("제품 상세 설명 이미지가 없는 경우 제품 저장 시 IllegalArgumentException가 발생")
    void save_empty_detailInfo() {
        //given
        ProductSaveDto dto = ProductSaveDto.builder()
                .imgFiles(new ArrayList<>())
                .name("test")
                .code("A01M")
                .size("s,m,l")
                .price(1000)
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.existsByName(anyString())).thenReturn(false);
        when(saveFile(any(), anyString())).thenThrow(IllegalArgumentException.class);
        when(saveFiles(any(), anyString())).thenReturn(new ArrayList<>());

        //when //then
        assertThrows(IllegalArgumentException.class,
                () -> productService.save(dto));
        verify(productDao, times(1)).existsByName(anyString());
        mockedFileUtil.verify(
                () -> saveFiles(anyList(), anyString()), times(1)
        );
        mockedFileUtil.verify(
                () -> saveFile(any(), anyString()), times(1)
        );
    }

    @Test
    @DisplayName("제품 기본키를 이용한 제품 조회 테스트")
    void findOne() {
        //given
        Product product = ProductTestFixture.createProduct();

        when(productDao.findById(any())).thenReturn(Optional.of(product));

        //when
        ProductInfoDto dto = productService.findOne(1L);

        //then
        assertEquals(product.getProductId(), dto.getProductId());
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getCode(), dto.getCode());
        assertEquals(product.getPrice(), dto.getPrice());
        assertEquals(product.getSize(), dto.getSize());
        verify(productDao, only()).findById(any());
        verify(productDao, times(1)).findById(any());
    }

    @Test
    @DisplayName("존재하지 않는 제품 기본키를 이용해 제품을 조회하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_productId() {
        //given
        when(productDao.findById(any())).thenThrow(NoSuchElementException.class);

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> productService.findOne(1L));
        verify(productDao, only()).findById(any());
        verify(productDao, times(1)).findById(any());
    }

    @Test
    @DisplayName("필터 조건들의 조합을 이용한 필터 조회 테스트")
    void findAllByFilter() {
        //given
        when(productDao.findAllByFilter(any(), anyString(), anyString()))
                .thenReturn(new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 10), 0L));

        //when
        productService.findAllByFilter(PageRequest.of(0, 10), "A", "test");

        //then
        verify(productDao, only()).findAllByFilter(any(), anyString(), anyString());
        verify(productDao, times(1)).findAllByFilter(any(), anyString(), anyString());
    }

    @Test
    @DisplayName("제품 정보 수정 테스트")
    void update() {
        //given
        Product product = ProductTestFixture.createProduct();
        ProductUpdateDto dto = ProductUpdateDto.builder()
                .imgFiles(new ArrayList<>())
                .name(product.getName() + "new")
                .code(product.getCode())
                .size(product.getSize())
                .price(product.getPrice())
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.findById(any())).thenReturn(Optional.of(product));
        when(productDao.existsByNameAndProductIdNot(any(), anyString())).thenReturn(false);
        when(saveFile(any(), anyString())).thenReturn("path");
        when(saveFiles(any(), anyString())).thenReturn(new ArrayList<>());

        //when
        productService.update(dto);

        //then
        assertEquals(product.getName(), dto.getName());
        verify(productDao, times(1)).findById(any());
        verify(productDao, times(1)).existsByNameAndProductIdNot(any(), anyString());
        mockedFileUtil.verify(
                () -> deleteDirectory(anyString()), times(1)
        );
        mockedFileUtil.verify(
                () -> saveFiles(anyList(), anyString()), times(1)
        );
        mockedFileUtil.verify(
                () -> saveFile(any(), anyString()), times(1)
        );
    }

    @Test
    @DisplayName("존재하지 않는 제품 기본키를 이용해 제품 정보를 수정하는 경우 NoSuchElementException가 발생")
    void update_invalid_productId() {
        //given
        Product product = ProductTestFixture.createProduct();
        ProductUpdateDto dto = ProductUpdateDto.builder()
                .imgFiles(new ArrayList<>())
                .name(product.getName() + "new")
                .code(product.getCode())
                .size(product.getSize())
                .price(product.getPrice())
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.findById(any())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> productService.update(dto));
        verify(productDao, times(1)).findById(any());
    }

    @Test
    @DisplayName("수정하려는 제품명이 이미 다른 제품으로 등록된 경우 DuplicateKeyException가 발생")
    void update_duplicate_productName() {
        //given
        Product product = ProductTestFixture.createProduct();
        ProductUpdateDto dto = ProductUpdateDto.builder()
                .imgFiles(new ArrayList<>())
                .name(product.getName() + "new")
                .code(product.getCode())
                .size(product.getSize())
                .price(product.getPrice())
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.findById(any())).thenReturn(Optional.of(product));
        when(productDao.existsByNameAndProductIdNot(any(), anyString())).thenReturn(true);

        //when //then
        assertThrows(DuplicateKeyException.class,
                () -> productService.update(dto));
        verify(productDao, times(1)).findById(any());
        verify(productDao, times(1)).existsByNameAndProductIdNot(any(), anyString());
    }

    @Test
    @DisplayName("제품 이미지가 없는 경우 제품 정보 수정 시 IllegalArgumentException가 발생")
    void update_empty_imageFiles() {
        //given
        Product product = ProductTestFixture.createProduct();
        ProductUpdateDto dto = ProductUpdateDto.builder()
                .imgFiles(new ArrayList<>())
                .name(product.getName() + "new")
                .code(product.getCode())
                .size(product.getSize())
                .price(product.getPrice())
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.findById(any())).thenReturn(Optional.of(product));
        when(productDao.existsByNameAndProductIdNot(any(), anyString())).thenReturn(false);
        when(saveFiles(any(), anyString())).thenThrow(IllegalArgumentException.class);

        //when //then
        assertThrows(IllegalArgumentException.class,
                () -> productService.update(dto));
        verify(productDao, times(1)).findById(any());
        verify(productDao, times(1)).existsByNameAndProductIdNot(any(), anyString());
        mockedFileUtil.verify(
                () -> deleteDirectory(anyString()), times(1)
        );
        mockedFileUtil.verify(
                () -> saveFiles(anyList(), anyString()), times(1)
        );
    }

    @Test
    @DisplayName("제품 상세 설명 이미지가 없는 경우 제품 정보 수정 시 IllegalArgumentException가 발생")
    void update_empty_detailInfo() {
        //given
        Product product = ProductTestFixture.createProduct();
        ProductUpdateDto dto = ProductUpdateDto.builder()
                .imgFiles(new ArrayList<>())
                .name(product.getName() + "new")
                .code(product.getCode())
                .size(product.getSize())
                .price(product.getPrice())
                .detailInfo(new MockMultipartFile("test", new byte[0]))
                .build();

        when(productDao.findById(any())).thenReturn(Optional.of(product));
        when(productDao.existsByNameAndProductIdNot(any(), anyString())).thenReturn(false);
        when(saveFiles(any(), anyString())).thenReturn(new ArrayList<>());
        when(saveFile(any(), anyString())).thenThrow(IllegalArgumentException.class);

        //when //then
        assertThrows(IllegalArgumentException.class,
                () -> productService.update(dto));
        verify(productDao, times(1)).findById(any());
        verify(productDao, times(1)).existsByNameAndProductIdNot(any(), anyString());
        mockedFileUtil.verify(
                () -> deleteDirectory(anyString()), times(1)
        );
        mockedFileUtil.verify(
                () -> saveFiles(anyList(), anyString()), times(1)
        );
        mockedFileUtil.verify(
                () -> saveFile(any(), anyString()), times(1)
        );
    }

    @Test
    @DisplayName("제품 삭제 테스트")
    void delete() {
        //given
        Product product = ProductTestFixture.createProduct();

        when(productDao.findById(any())).thenReturn(Optional.of(product));
        when(orderItemDao.existsByProductId(any())).thenReturn(false);

        //when
        productService.delete(1L);

        //then
        verify(productDao, times(1)).findById(any());
        verify(orderItemDao, times(1)).existsByProductId(any());
        verify(productDao, times(1)).deleteById(any());
        mockedFileUtil.verify(
                () -> deleteDirectory(anyString()), times(1)
        );
    }

    @Test
    @DisplayName("존재하지 않는 제품 기본키를 이용해 제품 삭제 시 NoSuchElementException가 발생")
    void delete_invalid_productId() {
        //given
        Product product = ProductTestFixture.createProduct();

        when(productDao.findById(any())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> productService.delete(1L));
        verify(productDao, times(1)).findById(any());
    }

    @Test
    @DisplayName("제품 관련 주문 아이템이 존재하는 경우 제품 삭제 시 ForeignKeyException가 발생")
    void delete_foreignKey_productId() {
        //given
        Product product = ProductTestFixture.createProduct();

        when(productDao.findById(any())).thenReturn(Optional.of(product));
        when(orderItemDao.existsByProductId(any())).thenReturn(true);

        //when //then
        assertThrows(ForeignKeyException.class,
                () -> productService.delete(1L));
        verify(productDao, times(1)).findById(any());
        verify(orderItemDao, times(1)).existsByProductId(any());
    }
}
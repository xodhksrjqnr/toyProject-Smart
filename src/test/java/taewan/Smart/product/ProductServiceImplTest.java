package taewan.Smart.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.entity.Product;
import taewan.Smart.product.repository.ProductRepository;
import taewan.Smart.product.service.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    @InjectMocks private ProductServiceImpl productService;

    static List<ProductSaveDto> dtos = new ArrayList<>();
    static List<Product> entities = new ArrayList<>();

    @BeforeAll
    static void setUp() {
//        for (int i = 1; i <= 3; i++) {
//            ProductSaveDto dto = new ProductSaveDto();
//            dto.setProductName("product" + i);
//            dto.setProductImg("productImg" + i);
//            dto.setPrice(1000 * i);
//            dto.setCategory(10000L + i);
//            dto.setProductInformation("productInfo" + i);
//            dtos.add(dto);
//
//            Product product = new Product();
//            ProductUpdateDto dto2 = new ProductUpdateDto();
//            dto2.setProductId((long)i);
//            dto2.setProductName("product" + i);
//            dto2.setProductImg("productImg" + i);
//            dto2.setPrice(1000 * i);
//            dto2.setCategory(10000L + i);
//            dto2.setProductInformation("productInfo" + i);
//            product.updateProduct(dto2);
//            entities.add(product);
//        }
    }

    @Test
    void 물품_저장() {
        //given
//        Product product = new Product(dtos.get(0));
//        when(productRepository.save(product)).thenReturn(entities.get(0));
//
//        //when
//        Long savedId = productService.save(dtos.get(0));
//
//        //then
//        assertThat(savedId).isEqualTo(1);
//        verify(productRepository, times(1)).save(product);
    }

    @Test
    void 물품_단일조회() {
        //given
        Product product = entities.get(0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        //when
        ProductInfoDto found = productService.findOne(1L);

        //then
        assertThat(found.toString().replace("ProductInfoDto", ""))
                .isEqualTo(product.toString().replace("Product", ""));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void 없는_물품_단일조회() {
        //given
        Product product = entities.get(0);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class, () -> productService.findOne(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void 물품_전체조회() {
        //given
//        when(productRepository.findAll()).thenReturn(entities);
//
//        //when
//        List<ProductInfoDto> found = productService.findAll();
//
//        //then
//        assertThat(found.size()).isEqualTo(3);
//        for (int i = 0; i < 3; i++) {
//            assertThat(found.get(i).toString().replace("ProductInfoDto", ""))
//                    .isEqualTo(entities.get(i).toString().replace("Product", ""));
//        }
//        verify(productRepository, times(1)).findAll();
    }

    @Test
    void 물품_수정() {
        //given
//        Product product = entities.get(0);
//        ProductUpdateDto dto = new ProductUpdateDto();
//        dto.setProductId(1L);
//        dto.setProductName("product5");
//        dto.setProductImg("productImg5");
//        dto.setPrice(1000 * 5);
//        dto.setCategory(10000L + 5);
//        dto.setProductInformation("productInfo5");
//        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//
//        //when
//        Long id = productService.modify(dto);
//
//        //then
//        assertThat(id).isEqualTo(1L);
//        assertThat(product.toString().replace("Product", ""))
//                .isEqualTo(dto.toString().replace("ProductUpdateDto", ""));
//        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void 물품_삭제() {
        //given

        //when
        productService.delete(1L);

        //then
        verify(productRepository, times(1)).deleteById(1L);
    }
}
package taewan.Smart.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.entity.Product;
import taewan.Smart.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired private ProductRepository productRepository;

    static List<ProductSaveDto> dtos = new ArrayList<>();

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
//        }
    }

    @Test
    void 물품_저장() {
        //given
//        ProductSaveDto dto = dtos.get(0);
//
//        //when
//        Product saved = productRepository.save(new Product(dto));
//
//        //then
//        assertThat(saved.getProductName()).isEqualTo(dto.getProductName());
//        assertThat(saved.getProductImg()).isEqualTo(dto.getProductImg());
//        assertThat(saved.getPrice()).isEqualTo(dto.getPrice());
//        assertThat(saved.getCategory()).isEqualTo(dto.getCategory());
//        assertThat(saved.getProductInformation()).isEqualTo(dto.getProductInformation());
    }

    @Test
    void 물품_단일조회() {
        //given
//        ProductSaveDto dto = dtos.get(0);
//        Product saved = productRepository.save(new Product(dto));
//
//        //when
//        Product found = productRepository.findById(saved.getProductId()).orElseThrow();
//
//        //then
//        assertThat(saved.toString()).isEqualTo(found.toString());
    }

    @Test
    void 없는_물품_단일조회() {
        //given
        //when
        Optional<Product> found = productRepository.findById(1L);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 물품_전체조회() {
        //given
//        List<Product> saved = new ArrayList<>();
//        for (ProductSaveDto dto : dtos)
//            saved.add(productRepository.save(new Product(dto)));
//
//        //when
//        List<Product> found = productRepository.findAll();
//
//        //then
//        assertThat(found.size()).isEqualTo(3);
//        for (int i = 0; i < 3; i++)
//            assertThat(found.get(i).toString()).isEqualTo(saved.get(i).toString());
    }

    @Test
    void 물품_삭제() {
        //given
//        ProductSaveDto dto = dtos.get(0);
//        Product saved = productRepository.save(new Product(dto));
//
//        //when
//        productRepository.deleteById(saved.getProductId());
//
//        //then
//        assertThat(productRepository.count()).isEqualTo(0);
//        assertThat(productRepository.findById(saved.getProductId())).isEmpty();
    }
}
package taewan.Smart.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductRepository;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static taewan.Smart.product.ProductFixture.*;

@DataJpaTest
@EnableJpaRepositories(basePackages = "taewan.Smart.domain.product.repository")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired private ProductRepository productRepository;
    @Value("${address.server}") private String ROOT;

    @Test
    void 제품_저장() {
        //given
        Product product = createProduct(ROOT);

        //when
        Product saved = productRepository.save(product);

        //then
        assertEquals(saved.getName(), product.getName());
        assertEquals(saved.getImgFolderPath(), product.getImgFolderPath());
        assertEquals(saved.getPrice(), product.getPrice());
        assertEquals(saved.getCode(), product.getCode());
        assertEquals(saved.getSize(), product.getSize());
        assertEquals(saved.getDetailInfo(), product.getDetailInfo());
    }

    @Test
    void 제품_단일조회() {
        //given
        Product product = createProduct(ROOT);
        Product saved = productRepository.save(product);

        //when
        Product found = productRepository.findById(saved.getProductId()).orElseThrow();

        //then
        assertEquals(saved.toString(), found.toString());
    }

    @Test
    void 없는_제품_단일조회() {
        //given //when //then
        assertThat(productRepository.findById(1L)).isEmpty();
    }

    @Test
    void 페이지단위_제품_조회() {
        //given
        List<Product> saved = productRepository.saveAll(createProducts(ROOT));
        int page = 0;
        int size = 5;
        int resultSize = Math.min(size, saved.size());

        //when
        Page<Product> result = productRepository.findAll(PageRequest.of(page, size));

        //then
        assertEquals(result.getNumberOfElements(), resultSize);
        List<Product> found = result.getContent();
        for (int i = 0; i < size; i++)
            assertEquals(found.get(i).toString(), saved.get(i).toString());
    }

    @Test
    void 제품명_조회() {
        //given
        productRepository.saveAll(createProducts(ROOT));

        //when //then
        assertThat(productRepository.findByName("product0")).isEmpty();
        assertThat(productRepository.findByName("product1")).isNotEmpty();
        assertThat(productRepository.findByName("product11")).isEmpty();
    }

    @Test
    void 없는_제품명_조회() {
        //given //when //then
        assertThat(productRepository.findByName("no-name-product")).isEmpty();
    }

    @Test
    void 분류번호를_포함한_페이지단위_제품_필터조회() {
        //given
        List<Product> saved = productRepository.saveAll(createProducts(ROOT));
        Map<String, Integer> createdCodes = getCodeInfo(saved);
        int page = 0;
        int size = saved.size();

        //when //then
        for (String code : createdCodes.keySet()) {
            Page<Product> result = productRepository.findAllByCodeContains(PageRequest.of(page, size), code);

            assertEquals(result.getTotalElements(), (long)createdCodes.get(code));
            assertEquals(result.getNumberOfElements(), createdCodes.get(code));
            result.getContent().forEach(p -> assertThat(p.getCode().contains(code)));
        }
    }

    @Test
    void 없는_분류번호를_포함한_페이지단위_제품_필터조회() {
        //given
        int page = 0;
        int size = 20;

        //when
        Page<Product> result = productRepository.findAllByCodeContains(PageRequest.of(page, size), "noCode");

        //then
        assertEquals(result.getNumberOfElements(), 0);
        assertEquals(result.getTotalElements(), 0);
    }

    @Test
    void 제품명을_포함한_페이지단위_필터조회() {
        //given
        List<Product> saved = productRepository.saveAll(createProducts(ROOT));
        int page = 0;
        int size = saved.size();

        //when //then
        for (Product product : saved) {
            Page<Product> result = productRepository.findAllByNameContains(PageRequest.of(page, size), product.getName());

            result.forEach(p -> p.getName().contains(product.getName()));
        }
        Page<Product> result = productRepository.findAllByNameContains(PageRequest.of(page, size), "product");

        assertEquals(result.getTotalElements(), size);
        assertEquals(result.getNumberOfElements(), size);
        result.forEach(p -> p.getName().contains("product"));
    }

    @Test
    void 없는_제품명을_포함한_페이지단위_필터조회() {
        //given
        int page = 0;
        int size = 10;
        String name = "noName";

        // when
        Page<Product> result = productRepository.findAllByNameContains(PageRequest.of(page, size), name);

        //then
        assertEquals(result.getNumberOfElements(), 0);
        assertEquals(result.getTotalElements(), 0);
    }

    @Test
    void 제품명과_분류번호를_포함한_페이지단위_필터조회() {
        //given
        List<Product> saved = productRepository.saveAll(createProducts(ROOT));
        int page = 0;
        int size = saved.size();
        String code = saved.get(0).getCode();
        String name = saved.get(0).getName();

        //when
        Page<Product> result = productRepository.findAllByCodeContainsAndNameContains(PageRequest.of(page, size), code, name);

        //then
        result.getContent().forEach(p -> {
            assertThat(p.getCode().contains(code)).isTrue();
            assertThat(p.getName().contains(name)).isTrue();
        });
    }

    @Test
    void 없는_제품명과_분류번호를_포함한_페이지단위_필터조회() {
        //given
        List<Product> saved = productRepository.saveAll(createProducts(ROOT));
        int page = 0;
        int size = saved.size();
        String code = saved.get(0).getCode();
        String name = "noName";

        //when
        Page<Product> result = productRepository.findAllByCodeContainsAndNameContains(PageRequest.of(page, size), code, name);

        //then
        assertEquals(result.getNumberOfElements(), 0);
        assertEquals(result.getTotalElements(), 0);
    }

    @Test
    void 제품명과_없는_분류번호를_포함한_페이지단위_필터조회() {
        //given
        List<Product> saved = productRepository.saveAll(createProducts(ROOT));
        int page = 0;
        int size = saved.size();
        String code = "Y01M";
        String name = saved.get(0).getName();

        //when
        Page<Product> result = productRepository.findAllByCodeContainsAndNameContains(PageRequest.of(page, size), code, name);

        //then
        assertEquals(result.getNumberOfElements(), 0);
        assertEquals(result.getTotalElements(), 0);
    }

    @Test
    void 없는_제품명과_없는_분류번호를_포함한_페이지단위_필터조회() {
        //given
        List<Product> saved = productRepository.saveAll(createProducts(ROOT));
        int page = 0;
        int size = saved.size();
        String code = "Y01M";
        String name = "noName";

        //when
        Page<Product> result = productRepository.findAllByCodeContainsAndNameContains(PageRequest.of(page, size), code, name);

        //then
        assertEquals(result.getNumberOfElements(), 0);
        assertEquals(result.getTotalElements(), 0);
    }

    @Test
    void 제품_삭제() {
        //given
        Product saved = productRepository.save(createProduct(ROOT));

        //when
        productRepository.deleteById(saved.getProductId());

        //then
        assertEquals(productRepository.count(), 0);
        assertThat(productRepository.findById(saved.getProductId())).isEmpty();
    }
}
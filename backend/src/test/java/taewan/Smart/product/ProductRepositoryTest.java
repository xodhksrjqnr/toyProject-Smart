package taewan.Smart.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.entity.Product;
import taewan.Smart.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired private ProductRepository productRepository;

    static List<ProductSaveDto> dtos = new ArrayList<>();

    @BeforeAll
    static void setup() {
        String[] tmp = {"A", "B", "C"};
        String[] tmp2 = {"M", "W"};

        for (int i = 1; i <= 3; i++) {
            ProductSaveDto dto = new ProductSaveDto();
            dto.setName("product" + i);
            dto.setPrice((int)(Math.random() * 10 + 1) * 10000);
            dto.setCode(tmp[i - 1] + "0" + i % 3 + tmp2[i % 2]);
            dto.setSize("s,m,l,xl,xxl");
            dtos.add(dto);
        }
    }

    private String createPath(ProductSaveDto dto) {
        return "home/test/images/" + dto.getCode() + "/" + dto.getName() + "/";
    }

    @Test
    void 제품_저장() {
        //given
        ProductSaveDto dto = dtos.get(0);
        String imgFolderPath = createPath(dto) + "view";
        String detailInfoPath = createPath(dto) + UUID.randomUUID();

        //when
        Product saved = productRepository.save(new Product(dto, imgFolderPath, detailInfoPath));

        //then
        assertEquals(saved.getName(), dto.getName());
        assertEquals(saved.getImgFolderPath(), imgFolderPath);
        assertEquals(saved.getPrice(), dto.getPrice());
        assertEquals(saved.getCode(), dto.getCode());
        assertEquals(saved.getSize(), dto.getSize());
        assertEquals(saved.getDetailInfo(), detailInfoPath);
    }

    @Test
    void 제품_단일조회() {
        //given
        ProductSaveDto dto = dtos.get(0);
        String imgFolderPath = createPath(dto) + "view";
        String detailInfoPath = createPath(dto) + UUID.randomUUID();
        Product saved = productRepository.save(new Product(dto, imgFolderPath, detailInfoPath));

        //when
        Product found = productRepository.findById(saved.getId()).orElseThrow();

        //then
        assertEquals(saved.toString(), found.toString());
    }

    @Test
    void 없는_제품_단일조회() {
        //given
        //when
        Optional<Product> found = productRepository.findById(1L);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 제품_페이지단위_조회() {
        //given
        List<Product> saved = new ArrayList<>();
        for (ProductSaveDto dto : dtos) {
            String imgFolderPath = createPath(dto) + "view";
            String detailInfoPath = createPath(dto) + UUID.randomUUID();
            saved.add(productRepository.save(new Product(dto, imgFolderPath, detailInfoPath)));
        }

        //when
        Page<Product> page = productRepository.findAll(PageRequest.of(0, 10));

        //then
        assertEquals(page.getNumberOfElements(), 3);
        List<Product> found = page.getContent();
        for (int i = 0; i < 3; i++)
            assertEquals(found.get(i).toString(), saved.get(i).toString());
    }

    @Test
    void 제품_제품명조회() {
        //given
        for (ProductSaveDto dto : dtos) {
            String imgFolderPath = createPath(dto) + "view";
            String detailInfoPath = createPath(dto) + UUID.randomUUID();
            productRepository.save(new Product(dto, imgFolderPath, detailInfoPath));
        }

        //when //then
        assertThat(productRepository.findByName(dtos.get(0).getName())).isNotEmpty();
        assertThat(productRepository.findByName(dtos.get(1).getName())).isNotEmpty();
        assertThat(productRepository.findByName(dtos.get(2).getName())).isNotEmpty();
    }

    @Test
    void 없는_제품_제품명조회() {
        //given //when //then
        assertThat(productRepository.findByName("test")).isEmpty();
    }

    @Test
    void 제품_분류번호포함_페이지단위_필터조회() {
        //given
        List<Product> saved = new ArrayList<>();
        for (ProductSaveDto dto : dtos) {
            String imgFolderPath = createPath(dto) + "view";
            String detailInfoPath = createPath(dto) + UUID.randomUUID();
            saved.add(productRepository.save(new Product(dto, imgFolderPath, detailInfoPath)));
        }

        //when
        Page<Product> page = productRepository.findAllByCodeContains(PageRequest.of(0, 10), dtos.get(0).getCode());

        //then
        assertEquals(page.getNumberOfElements(), 1);
        List<Product> found = page.getContent();
        assertEquals(saved.get(0).toString(), found.get(0).toString());
    }

    @Test
    void 없는_제품_분류번호포함_페이지단위_필터조회() {
        //given //when
        Page<Product> page = productRepository.findAllByCodeContains(PageRequest.of(0, 10), dtos.get(0).getCode());

        //then
        assertEquals(page.getNumberOfElements(), 0);
        assertEquals(page.getTotalElements(), 0);
    }

    @Test
    void 제품_이름포함_페이지단위_필터조회() {
        //given
        List<Product> saved = new ArrayList<>();
        for (ProductSaveDto dto : dtos) {
            String imgFolderPath = createPath(dto) + "view";
            String detailInfoPath = createPath(dto) + UUID.randomUUID();
            saved.add(productRepository.save(new Product(dto, imgFolderPath, detailInfoPath)));
        }

        //when
        Page<Product> page = productRepository.findAllByNameContains(PageRequest.of(0, 10), dtos.get(0).getName());

        //then
        assertEquals(page.getNumberOfElements(), 1);
        List<Product> found = page.getContent();
        assertEquals(saved.get(0).toString(), found.get(0).toString());
    }

    @Test
    void 없는_제품_이름포함_페이지단위_필터조회() {
        //given //when
        Page<Product> page = productRepository.findAllByNameContains(PageRequest.of(0, 10), dtos.get(0).getCode());

        //then
        assertEquals(page.getNumberOfElements(), 0);
        assertEquals(page.getTotalElements(), 0);
    }

    @Test
    void 제품_이름과분류번호포함_페이지단위_필터조회() {
        //given
        List<Product> saved = new ArrayList<>();
        for (ProductSaveDto dto : dtos) {
            String imgFolderPath = createPath(dto) + "view";
            String detailInfoPath = createPath(dto) + UUID.randomUUID();
            saved.add(productRepository.save(new Product(dto, imgFolderPath, detailInfoPath)));
        }

        //when
        Page<Product> page = productRepository.findAllByCodeContainsAndNameContains(PageRequest.of(0, 10), dtos.get(0).getCode(), dtos.get(0).getName());

        //then
        assertEquals(page.getNumberOfElements(), 1);
        List<Product> found = page.getContent();
        assertEquals(saved.get(0).toString(), found.get(0).toString());
    }

    @Test
    void 없는_제품_이름과분류번호포함_페이지단위_필터조회() {
        //given //when
        Page<Product> page = productRepository.findAllByCodeContainsAndNameContains(PageRequest.of(0, 10), dtos.get(0).getCode(), dtos.get(0).getName());

        //then
        assertEquals(page.getNumberOfElements(), 0);
        assertEquals(page.getTotalElements(), 0);
    }

    @Test
    void 제품_삭제() {
        //given
        ProductSaveDto dto = dtos.get(0);
        String imgFolderPath = createPath(dto) + "view";
        String detailInfoPath = createPath(dto) + UUID.randomUUID();
        Product saved = productRepository.save(new Product(dto, imgFolderPath, detailInfoPath));

        //when
        productRepository.deleteById(saved.getId());

        //then
        assertEquals(productRepository.count(), 0);
        assertThat(productRepository.findById(saved.getId())).isEmpty();
    }
}
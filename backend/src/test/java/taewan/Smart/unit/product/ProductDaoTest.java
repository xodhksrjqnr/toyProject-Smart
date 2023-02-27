package taewan.Smart.unit.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductDao;
import taewan.Smart.domain.product.repository.ProductDaoImpl;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Import(ProductDaoImpl.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = "taewan.Smart.domain.product.repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ProductDaoTest {
    @Autowired
    private ProductDao productDao;

    private static Product createProduct(int index) {
        return Product.builder()
                .name("test" + index)
                .price(1000 * index)
                .code("A01M")
                .size("s,m,l")
                .imgPath("testPath" + index)
                .build();
    }

    private static Product createProduct() {
        return createProduct(1);
    }

    private static String toStringForTest(Product product) {
        StringBuilder str = new StringBuilder()
                .append(product.getProductId()).append(",")
                .append(product.getName()).append(",")
                .append(product.getCode()).append(",")
                .append(product.getPrice()).append(",")
                .append(product.getSize()).append(",")
                .append(product.getImgPath());

        return str.toString();
    }

    @Test
    @DisplayName("유효한 제품 저장 테스트")
    void save() {
        //given
        Product product = createProduct();

        //when
        productDao.save(product);

        //then
        assertEquals(productDao.count(), 1);
    }

    @Test
    @DisplayName("동일한 제품명을 가진 제품 저장 테스트")
    void save_duplicate() {
        //given
        Product product1 = createProduct();
        Product product2 = Product.builder()
                .name(product1.getName())
                .price(2000)
                .code("A01W")
                .size("s,m,l,xl")
                .imgPath("testPath")
                .build();

        //when
        productDao.save(product1);

        //then
        assertThrows(PersistenceException.class,
                () -> productDao.save(product2));
    }

    @Test
    @DisplayName("기본키가 유효한 제품 삭제 테스트")
    void delete() {
        //given
        Product product = createProduct();

        //when
        Long savedId = productDao.save(product);

        //then
        assertEquals(productDao.count(), 1L);
        productDao.deleteById(savedId);
        assertEquals(productDao.count(), 0L);
    }

    @Test
    @DisplayName("기본키가 유효하지 않은 제품 삭제 테스트")
    void delete_invalid_productId() {
        //given
        Long invalidId = 0L;

        //when //then
        assertEquals(productDao.count(), 0L);
        productDao.deleteById(invalidId);
    }

    @Test
    @DisplayName("찾으려는 제품명을 가진 제품의 존재여부 테스트")
    void existsByName() {
        //given
        Product product = createProduct();
        String invalidName = "invalidName";

        //when
        productDao.save(product);

        //then
        assertThat(productDao.existsByName(product.getName())).isTrue();
        assertThat(productDao.existsByName(invalidName)).isFalse();
    }

    @Test
    @DisplayName("제품명이 같고 기본키가 다른 제품의 존재여부 테스트")
    void existsByNameAndProductIdNot() {
        //given
        Product product = createProduct();
        String invalidName = "invalidName";
        Long invalidId = 0L;

        //when
        Long validId = productDao.save(product);

        /**
         * result1 : validId, validName
         * result2 : validId, invalidName
         * result3 : invalidId, validName
         * result4 : invalidId, invalidName
         */
        boolean result1 = productDao
                .existsByNameAndProductIdNot(validId, product.getName());
        boolean result2 = productDao
                .existsByNameAndProductIdNot(validId, invalidName);
        boolean result3 = productDao
                .existsByNameAndProductIdNot(invalidId, product.getName());
        boolean result4 = productDao
                .existsByNameAndProductIdNot(invalidId, invalidName);

        //then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
        assertThat(result3).isTrue();
        assertThat(result4).isFalse();
    }

    @Test
    @DisplayName("유효한 기본키를 이용한 제품 조회 테스트")
    void findById() {
        //given
        Product product = createProduct();

        //when
        Long validId = productDao.save(product);
        Product found = productDao.findById(validId)
                .orElseThrow();

        //then
        assertEquals(toStringForTest(found), toStringForTest(product));
    }

    @Test
    @DisplayName("유효하지 않은 기본키를 이용한 제품 조회 테스트")
    void findById_invalid_productId() {
        //given
        Long invalidId = 0L;

        //when //then
        assertThat(productDao.findById(invalidId)).isEmpty();
    }

    @Test
    @DisplayName("pageable만 유효한 경우의 제품 필터 조회 테스트")
    void findAllByFilter_valid_pageable() {
        //given
        Product[] products = new Product[20];

        for (int i = 0; i < 20; i++)
            products[i] = createProduct(i + 1);

        //when
        for (Product p : products)
            productDao.save(p);

        /**
         * pageable1 : 테이블에 저장된 데이터보다 적게 조회
         * pageable2 : 테이블에 저장된 데이터보다 많게 조회
         * pageable3 : 다음 페이지부터 조회
         * pageable4 : name을 역순으로 정렬 조회
         * pageable5 : name을 역순으로 정렬 후 product_id 순으로 정렬 조회
         */
        Pageable pageable1 = PageRequest.of(0, 5);
        Pageable pageable2 = PageRequest.of(0, 30);
        Pageable pageable3 = PageRequest.of(1, 30);
        Pageable pageable4 = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "name"));
        Pageable pageable5 = PageRequest.of(0, 10,
                Sort.by("name").descending().and(
                        Sort.by("product_id").ascending()
                )
        );

        Page<Product> result1 = productDao.findAllByFilter(pageable1, "", "");
        Page<Product> result2 = productDao.findAllByFilter(pageable2, "", "");
        Page<Product> result3 = productDao.findAllByFilter(pageable3, "", "");
        Page<Product> result4 = productDao.findAllByFilter(pageable4, "", "");
        Page<Product> result5 = productDao.findAllByFilter(pageable5, "", "");

        //then
        assertEquals(result1.getNumberOfElements(), 5);
        assertEquals(result1.getTotalElements(), 20);
        assertEquals(result2.getNumberOfElements(), 20);
        assertEquals(result2.getTotalElements(), 20);
        assertEquals(result3.getNumberOfElements(), 0);
        assertEquals(result3.getTotalElements(), 20);
        //sort의 경우 로그 확인
    }

    @Test
    @DisplayName("분류코드만 유효한 경우의 제품 필터 조회 테스트")
    void findAllByFilter_valid_code() {
        //given
        Product[] products = new Product[20];

        for (int i = 0; i < 20; i++)
            products[i] = createProduct(i + 1);

        String invalidCode = "invalidCode";
        String validCode = products[0].getCode();
        Pageable pageable = PageRequest.of(0, 20);

        //when
        for (Product p : products)
            productDao.save(p);

        Page<Product> result1 = productDao.findAllByFilter(pageable, validCode, "");
        Page<Product> result2 = productDao.findAllByFilter(pageable, invalidCode, "");

        //then
        assertEquals(result1.getNumberOfElements(), 20);
        assertEquals(result2.getNumberOfElements(), 0);
    }

    @Test
    @DisplayName("제품명만 유효한 경우의 제품 필터 조회 테스트")
    void findAllByFilter_valid_name() {
        //given
        Product[] products = new Product[20];

        for (int i = 0; i < 20; i++)
            products[i] = createProduct(i + 1);

        String invalidName = "invalidName";
        String validName = "test";
        Pageable pageable = PageRequest.of(0, 20);

        //when
        for (Product p : products)
            productDao.save(p);

        Page<Product> result1 = productDao.findAllByFilter(pageable, "", validName);
        Page<Product> result2 = productDao.findAllByFilter(pageable, "", invalidName);

        //then
        assertEquals(result1.getNumberOfElements(), 20);
        assertEquals(result2.getNumberOfElements(), 0);
    }

    @Test
    @DisplayName("조건 조합에 따른 제품 필터 조회 테스트")
    void findAllByFilter() {
        //given
        Product[] products = new Product[20];

        for (int i = 0; i < 20; i++)
            products[i] = createProduct(i + 1);

        String invalidCode = "invalidCode";
        String validCode = products[0].getCode();
        String invalidName = "invalidName";
        String validName = "test";
        Pageable pageable1 = PageRequest.of(0, 10);
        Pageable pageable2 = PageRequest.of(0, 30);

        //when
        for (Product p : products)
            productDao.save(p);

        /**
         * result1 : pageable1, invalidCode, invalidName
         * result2 : pageable1, validCode, invalidName
         * result3 : pageable1, invalidCode, validName
         * result4 : pageable1, validCode, validName
         * result5 : pageable2, invalidCode, invalidName
         * result6 : pageable2, validCode, invalidName
         * result7 : pageable2, invalidCode, validName
         * result8 : pageable2, validCode, validName
         */
        Page<Product> result1 = productDao.findAllByFilter(pageable1, invalidCode, invalidName);
        Page<Product> result2 = productDao.findAllByFilter(pageable1, validCode, invalidName);
        Page<Product> result3 = productDao.findAllByFilter(pageable1, invalidCode, validName);
        Page<Product> result4 = productDao.findAllByFilter(pageable1, validCode, validName);
        Page<Product> result5 = productDao.findAllByFilter(pageable2, invalidCode, invalidName);
        Page<Product> result6 = productDao.findAllByFilter(pageable2, validCode, invalidName);
        Page<Product> result7 = productDao.findAllByFilter(pageable2, invalidCode, validName);
        Page<Product> result8 = productDao.findAllByFilter(pageable2, validCode, validName);

        //then
        assertEquals(result1.getNumberOfElements(), 0);
        assertEquals(result2.getNumberOfElements(), 0);
        assertEquals(result3.getNumberOfElements(), 0);
        assertEquals(result4.getNumberOfElements(), 10);
        assertEquals(result5.getNumberOfElements(), 0);
        assertEquals(result6.getNumberOfElements(), 0);
        assertEquals(result7.getNumberOfElements(), 0);
        assertEquals(result8.getNumberOfElements(), 20);
    }

    @Test
    @DisplayName("제품 테이블에 저장된 전체 데이터 개수 조회 테스트")
    void count() {
        //given
        Product product1 = createProduct();
        Product product2 = createProduct(2);

        //when //then
        assertEquals(productDao.count(), 0L);
        productDao.save(product1);
        assertEquals(productDao.count(), 1L);
        productDao.save(product2);
        assertEquals(productDao.count(), 2L);
    }
}

package taewan.Smart.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.category.dto.CategorySaveDto;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;
import taewan.Smart.domain.category.service.CategoryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static taewan.Smart.fixture.CategoryTestFixture.getCategorySaveDtoList;
import static taewan.Smart.global.error.ExceptionStatus.CATEGORY_NAME_DUPLICATE;

@SpringBootTest
@Transactional
public class CategoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryItemRepository categoryItemRepository;
    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 저장 테스트")
    void save() {
        //given
        CategorySaveDto dto = getCategorySaveDtoList().get(0);

        //when
        categoryService.save(dto);

        //then
        assertEquals(categoryRepository.count(), 1L);
        assertEquals(categoryItemRepository.count(), 1L);
    }

    @Test
    @DisplayName("상세 카테고리명이 중복된 경우 DuplicateKeyException가 발생")
    void save_duplicate_categoryItems() {
        //given
        CategorySaveDto dto1 = getCategorySaveDtoList().get(0);
        CategorySaveDto dto2 = new CategorySaveDto("하의", "후드 집업");

        //when
        categoryService.save(dto1);
        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> categoryService.save(dto2));
        assertEquals(ex.getMessage(), CATEGORY_NAME_DUPLICATE.exception().getMessage());

        //then
        assertEquals(categoryRepository.count(), 1L);
        assertEquals(categoryItemRepository.count(), 1L);
    }
}

package taewan.Smart.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategorySaveDto;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;
import taewan.Smart.domain.category.service.CategoryService;

import java.util.List;

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
    void 카테고리_저장_테스트() {
        //given
        CategorySaveDto dto = getCategorySaveDtoList().get(0);

        //when
        categoryService.save(dto);

        //then
        assertEquals(categoryRepository.count(), 1L);
        assertEquals(categoryItemRepository.count(), 1L);
    }

    @Test
    void 중분류_이름이_중복된_카테고리_저장_테스트() {
        //given
        CategorySaveDto dto1 = getCategorySaveDtoList().get(0);

        CategorySaveDto dto2 = CategorySaveDto.builder()
                .classification("하의")
                .middleClassification("후드 집업")
                .build();

        //when
        categoryService.save(dto1);
        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> categoryService.save(dto2));
        assertEquals(ex.getMessage(), CATEGORY_NAME_DUPLICATE.exception().getMessage());

        //then
        assertEquals(categoryRepository.count(), 1L);
        assertEquals(categoryItemRepository.count(), 1L);
    }

    @Test
    void 카테고리_전체_조회_테스트() {
        //given
        List<CategorySaveDto> categorySaveDtoList = getCategorySaveDtoList();

        categorySaveDtoList.forEach(c -> categoryService.save(c));

        //when
        List<CategoryInfoDto> found = categoryService.findAll();

        //then
        assertEquals(found.size(), categorySaveDtoList.size());
    }
}

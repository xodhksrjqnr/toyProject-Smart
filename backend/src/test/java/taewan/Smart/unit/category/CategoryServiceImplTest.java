package taewan.Smart.unit.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategoryItemInfoDto;
import taewan.Smart.domain.category.dto.CategorySaveDto;
import taewan.Smart.domain.category.entity.Category;
import taewan.Smart.domain.category.entity.CategoryItem;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;
import taewan.Smart.domain.category.service.CategoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock private CategoryRepository categoryRepository;
    @Mock private CategoryItemRepository categoryItemRepository;
    @InjectMocks private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("카테고리와 상세 카테고리 모두 등록되어있지 않은 경우 각각 새로운 분류번호 할당 후 저장 및 로그 출력")
    void save() {
        //given
        String categoryName = "상의";
        String categoryItemName = "맨투맨";
        CategorySaveDto dto = new CategorySaveDto(categoryName, categoryItemName);
        Category category = new Category("A", categoryName);

        when(categoryItemRepository.findByName(categoryItemName)).thenReturn(Optional.empty());
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());
        when(categoryRepository.save(any())).thenReturn(category);

        //when //then
        categoryService.save(dto);
        verify(categoryRepository, times(1)).save(any());
        verify(categoryRepository, times(1)).findByName(any());
        verify(categoryItemRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("등록된 카테고리와 등록되지 않은 상세 카테고리를 추가하는 경우 상세 카테고리만 새로운 분류번호 할당 후 저장 및 로그 출력")
    void save_duplicate_category() {
        //given
        String categoryName = "상의";
        String categoryItemName = "맨투맨";
        CategorySaveDto dto = new CategorySaveDto(categoryName, categoryItemName);
        Category category = new Category("A", categoryName);

        when(categoryItemRepository.findByName(categoryItemName)).thenReturn(Optional.empty());
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));

        //when //then
        categoryService.save(dto);
        verify(categoryRepository, times(1)).findByName(any());
        verify(categoryItemRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("등록되지 않은 카테고리와 등록된 상세 카테고리를 추가하는 경우 DuplicateKeyException가 발생")
    void save_duplicate_categoryItem() {
        //given
        String categoryName = "상의";
        String categoryItemName = "맨투맨";
        CategorySaveDto dto = new CategorySaveDto(categoryName, categoryItemName);
        CategoryItem categoryItem = new CategoryItem("01", categoryItemName);

        when(categoryItemRepository.findByName(categoryItemName)).thenReturn(Optional.of(categoryItem));

        //when //then
        assertThrows(DuplicateKeyException.class,
                () -> categoryService.save(dto));
        verify(categoryRepository, never()).save(any());
        verify(categoryRepository, never()).findByName(any());
        verify(categoryItemRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("상세 카테고리를 포함하는 카테고리 전체 조회 시 toInfoDto에 의한 데이터 초기화 확인")
    void findAll() {
        //given
        Category category = new Category("A", "상의");
        CategoryItem categoryItem = new CategoryItem("01", "맨투맨");
        List<Category> found = new ArrayList<>();

        category.addCategoryItem(categoryItem);
        found.add(category);

        when(categoryRepository.findAll()).thenReturn(found);

        //when
        CategoryInfoDto result = categoryService.findAll().get(0);
        CategoryItemInfoDto result2 = result.getCategoryItemInfoDtoList().get(0);

        //then
        assertEquals(result.getName(), category.getName());
        assertEquals(result.getCode(), category.getCode());
        assertEquals(result.getCategoryItemInfoDtoList().size(), category.getCategoryItems().size());
        assertEquals(result2.getCode(), categoryItem.getCode());
        assertEquals(result2.getName(), categoryItem.getName());
        verify(categoryRepository, times(1)).findAll();
        verify(categoryRepository, only()).findAll();
    }
}

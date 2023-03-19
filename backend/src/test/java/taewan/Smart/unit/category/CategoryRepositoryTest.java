package taewan.Smart.unit.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import taewan.Smart.domain.category.entity.Category;
import taewan.Smart.domain.category.entity.CategoryItem;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaRepositories(basePackages = "taewan.Smart.domain.category.repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryItemRepository categoryItemRepository;

    //CategoryRepository 테스트

    @Test
    @DisplayName("이름이 등록된 카테고리 조회 테스트")
    void findByName_category() {
        //given
        Category category = new Category("A", "상의");
        String findName = "상의";

        //when
        categoryRepository.save(category);
        Optional<Category> found = categoryRepository.findByName(findName);

        //then
        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("등록되지 않은 이름으로 카테고리를 조회하는 경우 반환 Optional이 비어있음")
    void findByName_invalid_name_category() {
        //given
        String findName = "invalidName";

        //when
        Optional<Category> found = categoryRepository.findByName(findName);

        //then
        assertThat(found).isEmpty();
    }

    //CategoryItemRepository 테스트
    @Test
    @DisplayName("이름이 등록된 상세 카테고리 조회 테스트")
    void findByName_categoryItem() {
        //given
        Category category = new Category("A", "상의");
        CategoryItem categoryItem = new CategoryItem("01", "후드 티셔츠");
        String findName = "후드 티셔츠";

        //when
        category.addCategoryItem(categoryItem);
        categoryRepository.save(category);
        Optional<CategoryItem> found = categoryItemRepository.findByName(findName);

        //then
        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("등록되지 않은 이름으로 상세 카테고리를 조회하는 경우 반환 Optional이 비어있음")
    void findByName_invalid_name_categoryItem() {
        //given
        String findName = "invalidName";

        //when
        Optional<CategoryItem> found = categoryItemRepository.findByName(findName);

        //then
        assertThat(found).isEmpty();
    }
}

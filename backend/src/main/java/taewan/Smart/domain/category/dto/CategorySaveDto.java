package taewan.Smart.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import taewan.Smart.domain.category.entity.Category;
import taewan.Smart.domain.category.entity.CategoryItem;

@Getter
@Builder
@AllArgsConstructor
public class CategorySaveDto {

    private String classification;
    private String middleClassification;

    public Category toEntity(String categoryCode, String categoryItemCode) {
        Category category = Category.builder()
                .name(this.classification)
                .code(categoryCode)
                .build();

        category.addCategoryItem(this.toItemEntity(categoryItemCode));
        return category;
    }

    private CategoryItem toItemEntity(String code) {
        return CategoryItem.builder()
                .name(this.middleClassification)
                .code(code)
                .build();
    }
}

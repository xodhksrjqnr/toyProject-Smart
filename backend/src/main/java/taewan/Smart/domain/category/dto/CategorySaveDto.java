package taewan.Smart.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import taewan.Smart.domain.category.entity.Category;
import taewan.Smart.domain.category.entity.CategoryItem;

@Getter
@AllArgsConstructor
public class CategorySaveDto {

    private String classification;
    private String middleClassification;

    public Category toEntity(String categoryCode, String categoryItemCode) {
        Category category = new Category(categoryCode, this.classification);

        category.addCategoryItem(this.toItemEntity(categoryItemCode));
        return category;
    }

    public CategoryItem toItemEntity(String code) {
        return new CategoryItem(code, this.middleClassification);
    }
}

package taewan.Smart.domain.category.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryFullInfoDto {

    private CategoryInfoDto category;
    private List<CategoryItemInfoDto> categoryItem;

    public CategoryFullInfoDto(CategoryInfoDto category, List<CategoryItemInfoDto> categoryItem) {
        this.category = category;
        this.categoryItem = categoryItem;
    }
}

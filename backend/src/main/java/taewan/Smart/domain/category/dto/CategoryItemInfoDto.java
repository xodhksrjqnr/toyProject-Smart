package taewan.Smart.domain.category.dto;

import lombok.Getter;
import taewan.Smart.domain.category.entity.CategoryItem;

@Getter
public class CategoryItemInfoDto {

    private Long id;
    private String name;
    private String code;

    public CategoryItemInfoDto(CategoryItem categoryItem) {
        this.id = categoryItem.getCategoryItemId();
        this.name = categoryItem.getName();
        this.code = categoryItem.getCode();
    }
}

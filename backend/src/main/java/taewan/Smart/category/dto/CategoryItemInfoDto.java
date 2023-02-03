package taewan.Smart.category.dto;

import lombok.Getter;
import taewan.Smart.category.entity.CategoryItem;

@Getter
public class CategoryItemInfoDto {

    private Long id;
    private String name;
    private String code;
    private String parentCode;

    public CategoryItemInfoDto(CategoryItem categoryItem) {
        this.id = categoryItem.getId();
        this.name = categoryItem.getName();
        this.code = categoryItem.getCode();
        this.parentCode = categoryItem.getParentCode();
    }
}

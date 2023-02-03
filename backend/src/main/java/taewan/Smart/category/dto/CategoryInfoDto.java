package taewan.Smart.category.dto;

import lombok.Getter;
import taewan.Smart.category.entity.Category;

@Getter
public class CategoryInfoDto {

    private Long id;
    private String name;
    private String code;

    public CategoryInfoDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.code = category.getCode();
    }
}

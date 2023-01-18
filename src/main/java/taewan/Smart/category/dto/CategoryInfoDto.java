package taewan.Smart.category.dto;

import lombok.Getter;
import taewan.Smart.category.entity.Category;

@Getter
public class CategoryInfoDto {

    private Long id;
    private String type1;
    private String type2;
    private Long code;

    public CategoryInfoDto(Category category) {
        this.id = category.getId();
        this.type1 = category.getType1();
        this.type2 = category.getType2();
        this.code = category.getCode();
    }
}

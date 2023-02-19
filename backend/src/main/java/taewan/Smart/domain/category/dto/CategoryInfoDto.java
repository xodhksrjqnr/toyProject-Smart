package taewan.Smart.domain.category.dto;

import lombok.Getter;
import taewan.Smart.domain.category.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryInfoDto {

    private Long id;
    private String name;
    private String code;
    private List<CategoryItemInfoDto> categoryItemInfoDtoList;

    public CategoryInfoDto(Category category) {
        this.id = category.getCategoryId();
        this.name = category.getName();
        this.code = category.getCode();
        this.categoryItemInfoDtoList = category.getCategoryItems().stream()
                .map(CategoryItemInfoDto::new).collect(Collectors.toList());
    }
}

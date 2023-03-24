package taewan.Smart.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryInfoDto {

    private Long id;
    private String name;
    private String code;
    private List<CategoryItemInfoDto> categoryItemInfoDtoList;
}

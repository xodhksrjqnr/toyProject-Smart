package taewan.Smart.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryItemInfoDto {

    private Long id;
    private String name;
    private String code;
}

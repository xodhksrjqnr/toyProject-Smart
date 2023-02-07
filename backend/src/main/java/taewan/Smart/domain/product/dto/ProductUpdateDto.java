package taewan.Smart.domain.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductUpdateDto extends ProductSaveDto {

    private Long id;
}

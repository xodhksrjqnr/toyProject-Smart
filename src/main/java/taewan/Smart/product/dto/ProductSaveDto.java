package taewan.Smart.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSaveDto {

    private String productImg;
    private String productName;
    private Integer price;
    private Long category;
    private String productInformation;
}

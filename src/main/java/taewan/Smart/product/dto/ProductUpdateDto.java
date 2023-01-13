package taewan.Smart.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductUpdateDto {

    private Long productId;
    private String productImg;
    private String productName;
    private Integer price;
    private Long category;
    private String productInformation;
}

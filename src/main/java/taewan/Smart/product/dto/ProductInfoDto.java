package taewan.Smart.product.dto;

import lombok.Getter;
import lombok.ToString;
import taewan.Smart.product.entity.Product;

@Getter
@ToString
public class ProductInfoDto {

    private Long productId;
    private String productImg;
    private String productName;
    private Integer price;
    private Long category;
    private String productInformation;

    public ProductInfoDto(Product product) {
        this.productId = product.getProductId();
        this.productImg = product.getProductImg();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.productInformation = product.getProductInformation();
    }
}

package taewan.Smart.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import taewan.Smart.domain.product.entity.Product;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductInfoDto {

    private Long productId;
    private List<String> imgFiles;
    private String name;
    private Integer price;
    private String code;
    private String size;
    private String detailInfo;

    public ProductInfoDto(Product product, List<String> productAccessImgUrl, String detailInfoAccessImgUrl) {
        this.productId = product.getProductId();
        this.imgFiles = productAccessImgUrl;
        this.name = product.getName();
        this.price = product.getPrice();
        this.code = product.getCode();
        this.size = product.getSize();
        this.detailInfo = detailInfoAccessImgUrl;
    }
}

package taewan.Smart.product.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productImg;
    private String productName;
    private Integer price;
    private Long category;
    private String productInformation;

    public Product(ProductSaveDto dto) {
        this.productImg = dto.getProductImg();
        this.productName = dto.getProductName();
        this.price = dto.getPrice();
        this.category = dto.getCategory();
        this.productInformation = dto.getProductInformation();
    }

    public void updateProduct(ProductUpdateDto dto) {
        this.productId = dto.getProductId();
        this.productImg = dto.getProductImg();
        this.productName = dto.getProductName();
        this.price = dto.getPrice();
        this.category = dto.getCategory();
        this.productInformation = dto.getProductInformation();
    }
}

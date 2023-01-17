package taewan.Smart.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import taewan.Smart.product.embedded.Size;
import taewan.Smart.product.entity.Product;

import javax.persistence.Embedded;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductInfoDto {

    private Long id;
    private List<String> imgFiles;
    private String name;
    private Integer price;
    private Long code;
    private Integer gender;
    @Embedded
    private Size size;
    private String detailInfo;

    public ProductInfoDto(Product product, List<String> imgFiles) {
        this.id = product.getId();
        this.imgFiles = imgFiles;
        this.name = product.getName();
        this.price = product.getPrice();
        this.code = product.getCode();
        this.gender = product.getGender();
        this.size = product.getSize();
        this.detailInfo = product.getDetailInfo();
    }
}

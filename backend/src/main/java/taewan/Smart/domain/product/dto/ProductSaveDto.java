package taewan.Smart.domain.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import taewan.Smart.domain.product.entity.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductSaveDto {

    private List<MultipartFile> imgFiles;
    @NotBlank(message = "제품 이름이 필요합니다.")
    private String name;
    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    private Integer price;
    @NotBlank(message = "제품 분류번호가 필요합니다.")
    private String code;
    @NotBlank
    private String size;
    private MultipartFile detailInfo;

    public Product toEntity(String imgFolderPath, String detailInfoPath) {
        return Product.builder()
                .imgFolderPath(imgFolderPath)
                .name(this.name)
                .price(this.price)
                .code(this.code)
                .size(this.size)
                .detailInfo(detailInfoPath)
                .build();
    }

    public String getDirectoryPath() {
        return "images/products/" + this.code + "/" + this.name;
    }

    public String getViewPath() {
        return getDirectoryPath() + "/view";
    }
}

package taewan.Smart.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString(callSuper = true)
public class ProductUpdateDto implements ProductDto {

    private Long productId;
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

    public String getDirectoryPath() {
        return "products/" + this.code + "/" + this.name + "/";
    }

    public String getViewPath() {
        return getDirectoryPath() + "view";
    }
}

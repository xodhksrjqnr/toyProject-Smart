package taewan.Smart.domain.product.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductUpdateDto implements ProductDto {

    private Long productId;
    private List<MultipartFile> imgFiles;
    @NotBlank(message = "제품 이름이 필요합니다.")
    private String name;
    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    private Integer price;
    @Pattern(regexp = "[A-Z][0-9]{2}[MW]", message = "제품 분류번호의 형식이 유효하지 않습니다.")
    private String code;
    @NotBlank(message = "제품 유효 사이즈가 필요합니다.")
    private String size;
    private MultipartFile detailInfo;

    public String getImgSavePath() {
        return "products/" + code + "/" + name;
    }
}

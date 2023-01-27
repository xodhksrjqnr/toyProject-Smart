package taewan.Smart.product.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductSaveDto {

    @NotEmpty(message = "최소 1개 이상의 제품 이미지가 필요합니다.")
    private List<MultipartFile> imgFiles;
    @NotBlank(message = "제품 이름이 필요합니다.")
    private String name;
    @PositiveOrZero
    private Integer price;
    @NotBlank(message = "제품 분류번호가 필요합니다.")
    private String code;
    private String size;
    @NotNull(message = "제품 설명 이미지가 필요합니다.")
    private MultipartFile detailInfo;
}

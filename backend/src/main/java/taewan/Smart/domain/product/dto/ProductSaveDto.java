package taewan.Smart.domain.product.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductSaveDto {

    private List<MultipartFile> imgFiles;
    @NotBlank(message = "DetailErrorMessage:제품 이름이 필요합니다.")
    private String name;
    @PositiveOrZero(message = "DetailErrorMessage:0 이상이어야 합니다.")
    private Integer price;
    @NotBlank(message = "DetailErrorMessage:제품 분류번호가 필요합니다.")
    private String code;
    private String size;
    private MultipartFile detailInfo;
}

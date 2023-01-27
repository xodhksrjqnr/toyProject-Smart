package taewan.Smart.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductUpdateDto {

    private Long id;
    private List<MultipartFile> imgFiles;
    private String name;
    private Integer price;
    private String code;
    private String size;
    private MultipartFile detailInfo;;
}

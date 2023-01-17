package taewan.Smart.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import taewan.Smart.product.embedded.Size;

import javax.persistence.Embedded;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductUpdateDto {

    private Long id;
    private List<MultipartFile> imgFiles;
    private String name;
    private Integer price;
    private Long code;
    private Integer gender;
    @Embedded
    private Size size;
    private MultipartFile detailInfo;;
}

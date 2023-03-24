package taewan.Smart.domain.product.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductInfoDto {

    private Long productId;
    private List<String> imgFiles;
    private String name;
    private Integer price;
    private String code;
    private String size;
    private String detailInfo;
}

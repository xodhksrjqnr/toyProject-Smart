package taewan.Smart.domain.product.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.global.converter.PathConverter;
import taewan.Smart.global.util.CustomFileUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(unique = true)
    private String name;
    private Integer price;
    private String code;
    private String size;
    private String imgPath;
    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    private Product(String name, Integer price, String code, String size, String imgPath) {
        this.name = name;
        this.price = price;
        this.code = code;
        this.size = size;
        this.imgPath = imgPath;
    }

    public void updateProduct(ProductUpdateDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.code = dto.getCode();
        this.size = dto.getSize();
        this.imgPath = dto.getImgSavePath();
    }

    public ProductInfoDto toInfoDto() {
        String localPath = PathConverter.toImgAccessLocal(imgPath);
        List<String> urls = PathConverter.toImgAccessUrl(CustomFileUtils.findFilePaths(localPath));
        String detailInfoPath = null;

        for (int i = 0; i < urls.size(); i++) {
            String path = urls.get(i);

            if (!path.contains("info")) continue;
            detailInfoPath = path;
            urls.remove(i);
            break;
        }
        return ProductInfoDto.builder()
                .productId(productId)
                .name(name)
                .code(code)
                .size(size)
                .price(price)
                .imgFiles(urls)
                .detailInfo(detailInfoPath)
                .build();
    }
}

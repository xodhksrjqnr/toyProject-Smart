package taewan.Smart.domain.product.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import taewan.Smart.domain.product.dto.ProductUpdateDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotBlank
    private String imgFolderPath;
    @NotBlank
    private String name;
    @PositiveOrZero
    private Integer price;
    @NotBlank
    private String code;
    @NotBlank
    private String size;
    @NotBlank
    private String detailInfo;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Builder
    public Product(String imgFolderPath, String name, Integer price, String code, String size, String detailInfo) {
        this.imgFolderPath = imgFolderPath;
        this.name = name;
        this.price = price;
        this.code = code;
        this.size = size;
        this.detailInfo = detailInfo;
    }

    public void updateProduct(ProductUpdateDto dto, String imgFolderPath, String infoPath) {
        this.imgFolderPath = imgFolderPath;
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.code = dto.getCode();
        this.size = dto.getSize();
        this.detailInfo = infoPath;
    }

    public String getDirectoryPath() {
        return this.imgFolderPath.replaceFirst("view", "");
    }
}

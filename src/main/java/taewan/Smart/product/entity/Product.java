package taewan.Smart.product.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.embedded.Size;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgFolderPath;
    private String name;
    private Integer price;
    private String code;
    @Embedded
    private Size size;
    private String detailInfo;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Product(ProductSaveDto dto, String imgFolderPath, String infoPath) {
        this.imgFolderPath = imgFolderPath;
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.code = dto.getCode();
        this.size = dto.getSize();
        this.detailInfo = infoPath;
    }

    public void updateProduct(ProductUpdateDto dto, String imgFolderPath, String infoPath) {
        this.id = dto.getId();
        this.imgFolderPath = imgFolderPath;
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.code = dto.getCode();
        this.size = dto.getSize();
        this.detailInfo = infoPath;
    }
}

package taewan.Smart.product.entity;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String imgFolderPath;
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private String code;
    private String size;
    @NotNull
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
        this.imgFolderPath = imgFolderPath;
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.code = dto.getCode();
        this.size = dto.getSize();
        this.detailInfo = infoPath;
    }
}

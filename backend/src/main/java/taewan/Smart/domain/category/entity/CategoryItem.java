package taewan.Smart.domain.category.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taewan.Smart.domain.category.dto.CategoryItemInfoDto;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "category_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "smallint unsigned")
    private Long categoryItemId;
    private String name;
    @Column(columnDefinition = "char(2)")
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public CategoryItem(String code, String name) {
        this.name = name;
        this.code = code;
    }

    void setCategory(Category category) {
        this.category = category;
    }

    public CategoryItemInfoDto toInfoDto() {
        return CategoryItemInfoDto.builder()
                .id(categoryItemId)
                .name(name)
                .code(code)
                .build();
    }
}

package taewan.Smart.domain.category.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taewan.Smart.domain.category.dto.CategoryInfoDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "smallint unsigned")
    private Long categoryId;
    private String name;
    @Column(columnDefinition = "char(1)")
    private String code;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public Category(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void addCategoryItem(CategoryItem categoryItem) {
        this.categoryItems.add(categoryItem);
        categoryItem.setCategory(this);
    }

    public CategoryInfoDto toInfoDto() {
        return CategoryInfoDto.builder()
                .id(categoryId)
                .name(name)
                .code(code)
                .categoryItemInfoDtoList(new ArrayList<>())
                .build();
    }
}

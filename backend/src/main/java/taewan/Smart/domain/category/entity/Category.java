package taewan.Smart.domain.category.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue
    private Long categoryId;
    private String name;
    private String code;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @Builder
    private Category(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void addCategoryItem(CategoryItem categoryItem) {
        this.categoryItems.add(categoryItem);
        categoryItem.setCategory(this);
    }
}

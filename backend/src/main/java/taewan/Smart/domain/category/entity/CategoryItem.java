package taewan.Smart.domain.category.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryItem {

    @Id @GeneratedValue
    private Long categoryItemId;
    private String name;
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    private CategoryItem(String name, String code) {
        this.name = name;
        this.code = code;
    }

    void setCategory(Category category) {
        this.category = category;
    }
}

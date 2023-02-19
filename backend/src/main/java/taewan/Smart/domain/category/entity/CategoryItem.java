package taewan.Smart.domain.category.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CategoryItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryItemId;
    private String name;
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public CategoryItem(String name, String code) {
        this.name = name;
        this.code = code;
    }
}

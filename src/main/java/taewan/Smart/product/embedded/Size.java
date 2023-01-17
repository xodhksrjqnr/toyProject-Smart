package taewan.Smart.product.embedded;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Size {
    Integer s;
    Integer m;
    Integer l;
    Integer xl;
    Integer xxl;
}

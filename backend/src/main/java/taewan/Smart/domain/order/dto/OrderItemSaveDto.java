package taewan.Smart.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemSaveDto {

    private Long productId;
    private String size;
    private Integer quantity;
}

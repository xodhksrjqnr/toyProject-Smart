package taewan.Smart.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemSaveDto {

    private Long productId;
    private String size;
    private Integer quantity;
}

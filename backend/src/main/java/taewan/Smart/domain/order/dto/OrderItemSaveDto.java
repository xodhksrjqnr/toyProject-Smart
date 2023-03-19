package taewan.Smart.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemSaveDto {

    private Long productId;
    private String size;
    private Integer quantity;
}

package taewan.Smart.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemSaveDto {

    private Long productId;
    private String size;
    private Integer quantity;
}

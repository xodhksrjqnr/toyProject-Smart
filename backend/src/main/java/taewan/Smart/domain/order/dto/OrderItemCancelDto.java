package taewan.Smart.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemCancelDto {

    private Long orderItemId;
    private String reason;
}

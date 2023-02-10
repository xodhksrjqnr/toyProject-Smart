package taewan.Smart.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCancelDto {

    private Long orderId;
    private Long orderItemId;
    private String reason;
}

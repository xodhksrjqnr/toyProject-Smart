package taewan.Smart.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemCancelDto {

    private Long orderItemId;
    private String reason;
}

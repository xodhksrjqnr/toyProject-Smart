package taewan.Smart.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemCancelDto {

    private Long orderItemId;
    private String reason;
}

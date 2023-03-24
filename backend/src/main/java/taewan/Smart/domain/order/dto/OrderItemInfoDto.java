package taewan.Smart.domain.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemInfoDto {

    private Long productId;
    private Long orderItemId;
    private String thumbnail;
    private String name;
    private String size;
    private Integer quantity;
    private Integer price;
    private String deliveryStatus;
}

package taewan.Smart.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderInfoDto {

    private Long orderId;
    private List<OrderItemInfoDto> orderItemInfoDtoList;

    public OrderInfoDto(Long orderId, List<OrderItemInfoDto> dtos) {
        this.orderId = orderId;
        this.orderItemInfoDtoList = dtos;
    }
}

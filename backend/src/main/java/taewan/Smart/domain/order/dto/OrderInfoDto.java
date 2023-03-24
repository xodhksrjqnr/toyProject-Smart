package taewan.Smart.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderInfoDto {

    private Long orderId;
    private List<OrderItemInfoDto> orderItemInfoDtoList;

    public OrderInfoDto(Long orderId, List<OrderItemInfoDto> dtos) {
        this.orderId = orderId;
        this.orderItemInfoDtoList = dtos;
    }
}

package taewan.Smart.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderInfoDto {

    private Long id;
    private List<OrderItemInfoDto> orderItemInfoDtoList;

    public OrderInfoDto(Long id, List<OrderItemInfoDto> dtos) {
        this.id = id;
        this.orderItemInfoDtoList = dtos;
    }
}

package taewan.Smart.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderSaveDto {

    List<OrderItemSaveDto> orderItemSaveDtoList;
}

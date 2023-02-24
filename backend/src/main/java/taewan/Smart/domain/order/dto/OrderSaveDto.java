package taewan.Smart.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderSaveDto {

    @JsonProperty("orderList")
    List<OrderItemSaveDto> orderItemSaveDtoList;

    public OrderSaveDto(List<OrderItemSaveDto> orderItemSaveDtoList) {
        this.orderItemSaveDtoList = orderItemSaveDtoList;
    }
}

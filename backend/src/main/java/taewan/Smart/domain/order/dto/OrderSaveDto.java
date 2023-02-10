package taewan.Smart.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderSaveDto {

    @JsonProperty("orderList")
    List<OrderItemSaveDto> orderItemSaveDtoList;
}

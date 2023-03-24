package taewan.Smart.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSaveDto {

    @JsonProperty("orderList")
    List<OrderItemSaveDto> orderItemSaveDtoList;
}

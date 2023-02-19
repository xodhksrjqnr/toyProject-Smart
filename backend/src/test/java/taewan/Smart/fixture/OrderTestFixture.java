package taewan.Smart.fixture;

import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;

import java.util.ArrayList;
import java.util.List;

public class OrderTestFixture {

    private static final String[] size = {"s", "m", "l", "xl", "xxl"};

    private static final OrderSaveDto orderSaveDto = createOrderSaveDto();

    public static OrderSaveDto createOrderSaveDto() {
        OrderSaveDto dto = new OrderSaveDto();

        dto.setOrderItemSaveDtoList(createOrderItemSaveDtoList());
        return dto;
    }

    public static OrderItemSaveDto createOrderItemSaveDto() {
        return createOrderItemSaveDto(1);
    }

    public static OrderItemSaveDto createOrderItemSaveDto(int index) {
        OrderItemSaveDto dto = new OrderItemSaveDto();

        dto.setProductId((long)(index + 1));
        dto.setSize(size[index % 5]);
        dto.setQuantity(index % 5);
        return dto;
    }

    public static List<OrderItemSaveDto> createOrderItemSaveDtoList() {
        return createOrderItemSaveDtoList(5);
    }

    public static List<OrderItemSaveDto> createOrderItemSaveDtoList(int size) {
        List<OrderItemSaveDto> list = new ArrayList<>();

        for (int i = 0; i < size; i++)
            list.add(createOrderItemSaveDto(i));
        return list;
    }

    public static OrderSaveDto getOrderSaveDto() {
        return orderSaveDto;
    }
}
